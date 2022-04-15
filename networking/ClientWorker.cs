using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using model;
using service;

namespace networking
{

	public class ClientWorker : IObserver
	{
		private IService server;
		private TcpClient connection;

		private NetworkStream stream;
		private IFormatter formatter;
		private volatile bool connected;

		public ClientWorker(IService server, TcpClient connection)
		{
			this.server = server;
			this.connection = connection;
			try
			{
				stream = connection.GetStream();
				formatter = new BinaryFormatter();
				connected = true;
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
		}

		public virtual void Run()
		{
			while (connected)
			{
				try
				{
					object request = formatter.Deserialize(stream);
					object response = HandleRequest((IRequest) request);
					if (response != null)
					{
						SendResponse((IResponse) response);
					}
				}
				catch (Exception e)
				{
					Console.WriteLine(e.StackTrace);
				}

				try
				{
					Thread.Sleep(1000);
				}
				catch (Exception e)
				{
					Console.WriteLine(e.StackTrace);
				}
			}

			try
			{
				stream.Close();
				connection.Close();
			}
			catch (Exception e)
			{
				Console.WriteLine("Error " + e);
			}
		}

		private IResponse HandleRequest(IRequest request)
		{
			IResponse response = null;
			if (request is LoginRequest)
			{
				Console.WriteLine("Login request ...");
				LoginRequest loginRequest = (LoginRequest) request;
				User user = loginRequest.User;
				try
				{
					lock (server)
					{
						server.Login(user.Username, user.Password, this);
					}
					return new OkResponse();
				}
				catch (ServiceException e)
				{
					connected = false;
					return new ErrorResponse(e.Message);
				}
			}
			if (request is LogoutRequest)
			{
				Console.WriteLine("Logout request");
				LogoutRequest logoutRequest = (LogoutRequest) request;
				User user = logoutRequest.User;
				try
				{
					lock (server)
					{
						server.Logout(user, this);
					}
					connected = false;
					return new OkResponse();
				}
				catch (ServiceException e)
				{
					return new ErrorResponse(e.Message);
				}
			}
			if (request is BuyTicketRequest)
			{
				Console.WriteLine("BuyTicketRequest ...");
				BuyTicketRequest buyTicketRequest = (BuyTicketRequest) request;
				Ticket ticket = buyTicketRequest.Ticket;
				try
				{
					lock (server)
					{
						server.SaveTicket(ticket.Performance.Id, ticket.Performance.Date, ticket.Performance.Place, 
							ticket.Performance.NoOfAvailableSeats, ticket.Performance.NoOfSoldSeats, ticket.Performance.Artist,
							ticket.OwnerName, ticket.NoOfSeats);
					}
					return new OkResponse();
				}
				catch (ServiceException e)
				{
					return new ErrorResponse(e.Message);
				}
			}
			if (request is GetPerformancesRequest)
			{
				Console.WriteLine("GetPerformancesRequest ...");
				try
				{
					List<Performance> performances;
					lock (server)
					{
						performances = server.FindAllPerformances();
					}
					return new GetPerformancesResponse(performances);
				}
				catch (ServiceException e)
				{
					return new ErrorResponse(e.Message);
				}
			}
			if (request is GetPerformancesByDateRequest)
			{
				Console.WriteLine("GetPerformancesByDateRequest ...");
				GetPerformancesByDateRequest getPerformancesByDateRequest = (GetPerformancesByDateRequest)request;
				DateTime date = getPerformancesByDateRequest.Date;
				try
				{
					List<Performance> performances;
					lock (server)
					{
						performances = server.FindAllPerformancesForADay(date);
					}
					return new GetPerformancesByDateResponse(performances);
				}
				catch (ServiceException e)
				{
					return new ErrorResponse(e.Message);
				}
			}
			return response;
		}

		private void SendResponse(IResponse response)
		{
			Console.WriteLine("Sending response: " + response);
			lock (stream)
			{
				formatter.Serialize(stream, response);
				stream.Flush();
			}
		}

		public void TicketBought(Ticket ticket)
		{
			Console.WriteLine("Ticket bought: " + ticket);
			try
			{
				SendResponse(new TicketBoughtResponse(ticket));
			}
			catch (Exception e)
			{
				throw new ServiceException("Sending error: " + e);
			}
		}
	}
}