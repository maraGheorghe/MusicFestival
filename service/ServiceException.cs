using System;

namespace service
{
    public class ServiceException : Exception
    {
        public ServiceException():base() {}
        public ServiceException(String msg) : base(msg) {}

        public ServiceException(String msg, Exception ex) : base(msg, ex) {}

    }
}
