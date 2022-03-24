using System;
using System.Web.UI.WebControls.WebParts;

namespace MusicFestivalMPP.validator;

public class ValidationException: Exception
{
    public ValidationException(string message) : base(message) {}
}