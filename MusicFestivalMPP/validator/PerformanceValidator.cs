using System;
using log4net;
using log4net.Util;
using MusicFestivalMPP.model;

namespace MusicFestivalMPP.validator;

public class PerformanceValidator: IValidator<Performance>
{
    private static readonly ILog Log = LogManager.GetLogger("PerformanceValidator");
    
    public void Validate(Performance performance)
    {
        Log.InfoFormat("Validating performance: {0}.", performance);
        String errorMessage = "";
        if(performance == null)
            throw new ValidationException("Performance must be not null!");
        else if(performance.Date < DateTime.Now)
            errorMessage += "This show has already taken place!\n";
        if(string.IsNullOrEmpty(performance.Artist))
            errorMessage += "Artist is a required filed.\n";
        if(errorMessage != "")
            throw new ValidationException(errorMessage);
        Log.InfoExt("Exiting validating performance.");
    }
}