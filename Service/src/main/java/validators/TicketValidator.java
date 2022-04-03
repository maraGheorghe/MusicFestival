package validators;

import model.Ticket;
import services.ServiceException;

public class TicketValidator {
    public void validate(Ticket ticket) throws ServiceException {
        String errorMessage = "";
        if(ticket == null)
            throw new ServiceException("Ticket must be not null!");
        if(ticket.getOwnerName() == null || "".equals(ticket.getOwnerName()))
            errorMessage += "Owner name is a required field.\n";
        else if(!ticket.getOwnerName().matches("^([A-Z][a-z]*)([ ][A-Z][a-z]*)?$"))
            errorMessage += "Owner name does not respect the format!\n";
        if(ticket.getNoOfSeats() == null)
            errorMessage += "Number of seats is a required filed.\n";
        else if(ticket.getNoOfSeats() <= 0)
            errorMessage += "You must buy at least one seat!\n";
        else if(ticket.getNoOfSeats() > ticket.getPerformance().getNoOfAvailableSeats())
            errorMessage += "You can't buy more seats than the number of available seats for this show! There are only "
                    + ticket.getPerformance().getNoOfAvailableSeats().toString() + " seats left!";
        if(!errorMessage.equals(""))
            throw new ServiceException(errorMessage);
    }
}
