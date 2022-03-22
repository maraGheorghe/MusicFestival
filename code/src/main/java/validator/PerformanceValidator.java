package validator;

import model.Performance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class PerformanceValidator implements Validator<Performance> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void validate(Performance performance) throws ValidationException {
        logger.traceEntry("Validating performance: {}.", performance);
        String errorMessage = "";
        if(performance == null)
            throw new ValidationException("Performance must be not null!");
        if(performance.getDate() == null)
            errorMessage += "Date is a required field.\n";
        else if(performance.getDate().isBefore(LocalDateTime.now()))
            errorMessage += "This show has already taken place!\n";
        if(performance.getArtist() == null || "".equals(performance.getArtist()))
            errorMessage += "Artist is a required filed.\n";
        if(!errorMessage.equals(""))
            throw new ValidationException(errorMessage);
        logger.traceExit();
    }
}
