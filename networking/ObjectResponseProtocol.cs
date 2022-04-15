using System;
using System.Collections.Generic;
using model;

namespace networking
{
	public interface IResponse 
	{}
	
	[Serializable]
	public class OkResponse : IResponse
	{}

    [Serializable]
	public class ErrorResponse : IResponse
	{
		public ErrorResponse(string message)
		{
			this.Message = message;
		}
		public virtual string Message { get; }
	}
	
	public interface IUpdateResponse : IResponse
	{}

	[Serializable]
	public class TicketBoughtResponse : IUpdateResponse
	{
		public TicketBoughtResponse(Ticket ticket)
		{
			this.Ticket = ticket;
		}

		public virtual Ticket Ticket { get; }
	}
	
	[Serializable] 
	public class GetPerformancesResponse : IResponse
	{
		public GetPerformancesResponse(List<Performance> performances)
		{
			this.Performances = performances;
		}
		public virtual List<Performance> Performances { get; }
	}

	[Serializable] 
	public class GetPerformancesByDateResponse : IResponse
	{
		public GetPerformancesByDateResponse(List<Performance> performances)
		{
			this.Performances = performances;
		}
		public virtual List<Performance> Performances { get; }
	}
}