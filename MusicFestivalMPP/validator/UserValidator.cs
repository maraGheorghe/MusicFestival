using System;
using log4net;
using log4net.Util;
using MusicFestivalMPP.model;

namespace MusicFestivalMPP.validator;

public class UserValidator: IValidator<User>
{
    private static readonly ILog Log = LogManager.GetLogger("UserValidator");
    
    public void Validate(User user)
    {
        Log.InfoFormat("Validating user: {0}.", user);
        String errorMessage = "";
        if(user == null)
            throw new ValidationException("User must be not null!");
        if(string.IsNullOrEmpty(user.Username))
            errorMessage += "Username is a required field.\n";
        else if(user.Username.Length < 4)
            errorMessage += "Email must have at least 4 characters!\n";
        if(string.IsNullOrEmpty(user.Password))
            errorMessage += "Password is a required filed.\n";
        else if(user.Password.Length < 8)
            errorMessage += "Password must have at least 8 characters!\n";
        if(errorMessage != "")
            throw new ValidationException(errorMessage);
        Log.InfoExt("Exiting validating user.");
    }
}