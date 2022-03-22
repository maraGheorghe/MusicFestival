package validator;

import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserValidator implements Validator<User> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void validate(User user) throws ValidationException {
        logger.traceEntry("Validating user: {}.", user);
        String errorMessage = "";
        if(user == null)
            throw new ValidationException("User must be not null!");
        if(user.getUsername() == null || "".equals(user.getUsername()))
            errorMessage += "Username is a required field.\n";
        else if(user.getUsername().length() < 4)
            errorMessage += "Email must have at least 4 characters!\n";
        if(user.getPassword() == null || "".equals(user.getPassword()))
            errorMessage += "Password is a required filed.\n";
        else if(user.getPassword().length() < 8)
            errorMessage += "Password must have at least 8 characters!\n";
        if(!errorMessage.equals(""))
            throw new ValidationException(errorMessage);
        logger.traceExit();
    }
}
