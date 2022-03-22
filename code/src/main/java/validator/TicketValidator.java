package validator;

import model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TicketValidator implements Validator<Ticket>{

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void validate(Ticket ticket) throws ValidationException {
        logger.traceEntry("Validating ticket: {}.", ticket);
        String errorMessage = "";
        if(ticket == null)
            throw new ValidationException("Ticket must be not null!");
        if(ticket.getOwnerName() == null || "".equals(ticket.getOwnerName()))
            errorMessage += "Owner name is a required field.\n";
        else if(!ticket.getOwnerName().matches("^([A-Z][a-z]*)([ ][A-Z][a-z]*)?$"))
            errorMessage += "Owner name does not respect the format!\n";
        if(ticket.getNoOfSeats() == null)
            errorMessage += "Number of seats is a required filed.\n";
        else if(ticket.getNoOfSeats() <= 0)
            errorMessage += "You must buy at least one seat!\n";
        else if(ticket.getNoOfSeats() > ticket.getPerformance().getNoOfAvailableSeats())
            errorMessage += "You can't buy more seats than the number of available seats for this show! there are only "
                    + ticket.getPerformance().getNoOfAvailableSeats().toString() + " seats left!";
        if(!errorMessage.equals(""))
            throw new ValidationException(errorMessage);
        logger.traceExit();
    }
}
