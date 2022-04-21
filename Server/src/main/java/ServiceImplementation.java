import io.grpc.stub.StreamObserver;
import model.Performance;
import model.Ticket;
import model.User;
import repository.interfaces.RepositoryInterfacePerformance;
import repository.interfaces.RepositoryInterfaceTicket;
import repository.interfaces.RepositoryInterfaceUser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.ServiceException;
import validators.TicketValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceImplementation extends MusicFestivalServiceGrpc.MusicFestivalServiceImplBase {

    private RepositoryInterfaceUser userRepository;
    private RepositoryInterfacePerformance performanceRepository;
    private RepositoryInterfaceTicket ticketRepository;
    private static List<StreamObserver<MusicFestival.TicketBoughtMessageFromService>> observers = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger();


    public ServiceImplementation(RepositoryInterfaceUser userRepository, RepositoryInterfacePerformance
            performanceRepository, RepositoryInterfaceTicket ticketRepository) {
        logger.info("Initializing ServiceImplementation.");
        this.userRepository = userRepository;
        this.performanceRepository = performanceRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void login(MusicFestival.LoginRequest request, StreamObserver<MusicFestival.LoginResponse> responseObserver) {
        Optional<User> user = userRepository.checkConnection(request.getUsername(), request.getPassword());
        MusicFestival.LoginResponse response = MusicFestival.LoginResponse.newBuilder().setType(1).
                setError("Username and password do not match! Please try again.").build();;
        if (user.isPresent())
             response = MusicFestival.LoginResponse.newBuilder().setUsername(user.get()
                    .getUsername()).setPassword(user.get().getPassword()).setId(user.get().getID()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void logout(MusicFestival.LogoutRequest request, StreamObserver<MusicFestival.LogoutResponse> responseObserver) {
        var response = MusicFestival.LogoutResponse.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void findAllPerformances(MusicFestival.GetPerformancesRequest request, StreamObserver<MusicFestival.GetPerformancesResponse> responseObserver) {
        List<Performance> performances = performanceRepository.findAll();
        var performancesMessage = performances.stream()
                .map(performance -> {
                            LocalDateTime date = performance.getDate();
                    return MusicFestival.PerformanceMessage.newBuilder()
                        .setId(performance.getID())
                        .setDate(MusicFestival.DateTimeMessage.newBuilder().setYear(date.getYear())
                                .setMonth(date.getMonthValue())
                                .setDay(date.getDayOfMonth())
                                .setHour(date.getHour())
                                .setMinute(date.getMinute()).build())
                        .setPlace(performance.getPlace())
                        .setNoOfAvailableSeats(performance.getNoOfAvailableSeats())
                        .setNoOfSoldSeats(performance.getNoOfSoldSeats())
                        .setArtist(performance.getArtist()).build();
                }).toList();
        var response = MusicFestival.GetPerformancesResponse.newBuilder()
                .addAllPerformances(performancesMessage).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void findAllPerformancesByDate(MusicFestival.GetPerformancesByDateRequest request, StreamObserver<MusicFestival.GetPerformancesByDateResponse> responseObserver) {
        LocalDate date = LocalDate.of(request.getDate().getYear(), request.getDate().getMonth(), request.getDate().getDay());
        List<Performance> performances = performanceRepository.findAllPerformancesForADay(date);
        System.out.println("\n\n" + date + " " + performances.size());
        var performancesMessage = performances.stream()
                .map(performance -> {
                    return MusicFestival.PerformanceMessage.newBuilder()
                            .setId(performance.getID())
                            .setDate(request.getDate())
                            .setPlace(performance.getPlace())
                            .setNoOfAvailableSeats(performance.getNoOfAvailableSeats())
                            .setNoOfSoldSeats(performance.getNoOfSoldSeats())
                            .setArtist(performance.getArtist()).build();
                }).toList();
        var response = MusicFestival.GetPerformancesByDateResponse.newBuilder()
                .addAllPerformances(performancesMessage).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void buyTicket(MusicFestival.BuyTicketRequest request, StreamObserver<MusicFestival.BuyTicketResponse> responseObserver) {
        MusicFestival.PerformanceMessage performanceMessage = request.getPerformance();
        MusicFestival.DateTimeMessage dateTimeMessage = performanceMessage.getDate();
        LocalDateTime date = LocalDateTime.of(dateTimeMessage.getYear(), dateTimeMessage.getMonth(),
                dateTimeMessage.getDay(), dateTimeMessage.getHour(), dateTimeMessage.getMinute());
        Performance performance = new Performance(performanceMessage.getId(), date,
            performanceMessage.getPlace(), performanceMessage.getNoOfAvailableSeats(), performanceMessage.getNoOfSoldSeats(),
            performanceMessage.getArtist());
        Ticket ticket = new Ticket(performance, request.getOwner(), request.getNumberOfSeats());
        MusicFestival.BuyTicketResponse response = MusicFestival.BuyTicketResponse.newBuilder().setType(1).setError("Ticket could not be saved!").build();;
        try {
            new TicketValidator().validate(ticket);
            var optionalTicket = ticketRepository.save(new Ticket(performance, request.getOwner(), request.getNumberOfSeats()));
            if(optionalTicket.isPresent())
                response = MusicFestival.BuyTicketResponse.newBuilder().setType(0).setTicket(request).build();
        } catch (ServiceException e) {
            response = MusicFestival.BuyTicketResponse.newBuilder().setType(1).setError(e.getMessage()).build();
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        for (StreamObserver<MusicFestival.TicketBoughtMessageFromService> observer : observers)
            observer.onNext(MusicFestival.TicketBoughtMessageFromService.newBuilder().setTicket(request).build());
    }

    @Override
    public StreamObserver<MusicFestival.BuyTicketRequest> ticketBought(StreamObserver<MusicFestival.TicketBoughtMessageFromService> responseObserver) {
        observers.add(responseObserver);
        return new StreamObserver<MusicFestival.BuyTicketRequest>() {
            @Override
            public void onNext(MusicFestival.BuyTicketRequest value) {
                for (StreamObserver<MusicFestival.TicketBoughtMessageFromService> observer : observers)
                    observer.onNext(MusicFestival.TicketBoughtMessageFromService.newBuilder().setTicket(value).build());
            }

            @Override
            public void onError(Throwable t) {
                observers.remove(responseObserver);
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                observers.remove(responseObserver);
                responseObserver.onCompleted();
            }
        };
    }
}
