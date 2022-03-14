package repository;

import model.Performance;
import model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.interfaces.RepositoryInterfaceTicket;
import util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class RepositoryTicket implements RepositoryInterfaceTicket {

    private JDBCUtils jdbcUtils;

    private static final Logger logger = LogManager.getLogger();

    public RepositoryTicket(Properties properties) {
        logger.info("Initializing RepositoryTicket with properties: {} ", properties);
        jdbcUtils = new JDBCUtils(properties);
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        logger.traceEntry("Saving ticket: {} ", ticket);
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into tickets (performance_id, owner, seats) values (?, ?, ?) ;")) {
            preparedStatement.setLong(1, ticket.getPerformance().getID());
            preparedStatement.setString(2, ticket.getOwnerName());
            preparedStatement.setInt(3, ticket.getNoOfSeats());
            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} instances.", result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
        }
        logger.traceExit();
        return Optional.of(ticket);
    }

    @Override
    public Optional<Ticket> find(Long ID) {
        logger.traceEntry("Finding ticket with ID: {}.", ID);
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from tickets where ticket_id = ?")) {
            preparedStatement.setLong(1, ID);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    Performance performance = getPerformanceOfTicket(ID);
                    String owner = resultSet.getString("owner");
                    Integer seats = resultSet.getInt("seats");
                    Ticket ticket = new Ticket(ID, performance, owner, seats);
                    logger.traceExit(ticket);
                    return Optional.of(ticket);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
        }
        logger.traceExit();
        return Optional.empty();
    }

    @Override
    public Optional<Ticket> delete(Ticket ticket) {
        logger.traceEntry("Deleting ticket: {} ", ticket);
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from tickets where ticket_id = ?; ")) {
            preparedStatement.setLong(1, ticket.getID());
            int result = preparedStatement.executeUpdate();
            logger.trace("Deleted {} instances.", result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
            return Optional.empty();
        }
        logger.traceExit();
        return Optional.of(ticket);
    }

    @Override
    public Optional<Ticket> update(Ticket ticket) {
        logger.traceEntry("Updating ticket: {} ", ticket);
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("update tickets set performance_id = ?, owner = ?, seats = ? where ticket_id = ?")) {
            preparedStatement.setLong(1, ticket.getPerformance().getID());
            preparedStatement.setString(2, ticket.getOwnerName());
            preparedStatement.setInt(3, ticket.getNoOfSeats());
            preparedStatement.setLong(4, ticket.getID());
            int result = preparedStatement.executeUpdate();
            logger.trace("Updated {} instances.", result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
            return Optional.empty();
        }
        logger.traceExit();
        return Optional.of(ticket);
    }

    @Override
    public List<Ticket> findAll() {
        logger.traceEntry("Finding all tickets.");
        Connection connection = jdbcUtils.getConnection();
        List<Ticket> tickets = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from tickets")) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    Long ticket_id = resultSet.getLong("ticket_id");
                    Performance performance = getPerformanceOfTicket(ticket_id);
                    String owner = resultSet.getString("owner");
                    int seats = resultSet.getInt("seats");
                    Ticket ticket = new Ticket(ticket_id, performance, owner, seats);
                    tickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
        }
        logger.traceExit();
        return tickets;
    }

    private int getNumberOfSoldTickets(Long performanceID) {
        logger.traceEntry("Counting the number of sold tickets for the performance witd ID: {}.", performanceID);
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select sum(seats) as count from tickets where performance_id = ?")) {
            preparedStatement.setLong(1, performanceID);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next())
                    return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
        }
        return 0;
    }

    @Override
    public Performance getPerformanceOfTicket(Long ticketID) {
        logger.traceEntry("Finding performance for the ticket with ID: {}.", ticketID);
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select p.performance_id, p.performance_date, p.place, p.no_of_seats, p.artist from performances p inner join tickets t on p.performance_id = t.performance_id where ticket_id = ?")) {
            preparedStatement.setLong(1, ticketID);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    Long performanceID = resultSet.getLong("performance_id");
                    LocalDate date = resultSet.getDate("performance_date").toLocalDate();
                    String place = resultSet.getString("place");
                    int sold = getNumberOfSoldTickets(performanceID);
                    int available = resultSet.getInt("no_of_seats") - sold;
                    String artist = resultSet.getString("artist");
                    Performance performance = new Performance(performanceID, date, place, available, sold, artist);
                    logger.traceExit(performance);
                    return performance;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
        }
        logger.traceExit();
        return null;
    }
}
