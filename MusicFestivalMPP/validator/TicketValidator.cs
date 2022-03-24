using System;
using log4net;
using log4net.Util;
using MusicFestivalMPP.model;

namespace MusicFestivalMPP.validator;

public class TicketValidator: IValidator<Ticket>
{
    private static readonly ILog Log = LogManager.GetLogger("TicketValidator");
    
    public void Validate(Ticket ticket)
    {
        Log.InfoFormat("Validating ticket: {0}.", ticket);
        String errorMessage = "";
        if(ticket == null)
            throw new ValidationException("Ticket must be not null!");
        if(string.IsNullOrEmpty(ticket.OwnerName))
            errorMessage += "Owner name is a required field.\n";
        else if(ticket.NoOfSeats <= 0)
            errorMessage += "You must buy at least one seat!\n";
        else if(ticket.NoOfSeats > ticket.Performance.NoOfAvailableSeats)
            errorMessage += "You can't buy more seats than the number of available seats for this show! there are only "
                            + ticket.Performance.NoOfAvailableSeats.ToString() + " seats left!";
        if(errorMessage != "")
            throw new ValidationException(errorMessage);
        Log.InfoExt("Exiting validating ticket.");
    }
}