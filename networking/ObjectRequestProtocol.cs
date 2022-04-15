using System;
using model;

namespace networking
{
	public interface IRequest 
	{}
	
	[Serializable]
	public class LoginRequest : IRequest
	{
		public LoginRequest(User user)
		{
			this.User = user;
		}

		public virtual User User { get; }
	}

	[Serializable]
	public class LogoutRequest : IRequest
	{
		public LogoutRequest(User user)
		{
			this.User = user;
		}

		public virtual User User { get; }
	}

	[Serializable]
	public class BuyTicketRequest : IRequest
	{
		public BuyTicketRequest(Ticket message)
		{
			this.Ticket = message;
		}
		public virtual Ticket Ticket { get; }
	}

	[Serializable]
	public class GetPerformancesRequest : IRequest
	{
		public GetPerformancesRequest()
		{}
	}

	[Serializable]
	public class GetPerformancesByDateRequest : IRequest
	{
		public GetPerformancesByDateRequest(DateTime date)
		{
			this.Date = date;
		}
		public virtual DateTime Date { get; }
	}

}