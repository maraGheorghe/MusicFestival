using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Runtime.Remoting;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using model;
using service;

namespace networking
{
	
	public class ServerProxy : IService
	{
		private string host;
		private int port;

		private IObserver client;

		private NetworkStream stream;
		
        private IFormatter formatter;
		private TcpClient connection;

		private Queue<IResponse> responses;
		private volatile bool finished;
        private EventWaitHandle _waitHandle;
		public ServerProxy(string host, int port)
		{
			this.host = host;
			this.port = port;
			responses = new Queue<IResponse>();
		}
		
		private void CloseConnection()
		{
			finished=true;
			try
			{
				stream.Close();
				connection.Close();
                _waitHandle.Close();
				client = null;
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
		}

		private void SendRequest(IRequest request)
		{
			try
			{
                formatter.Serialize(stream, request);
                stream.Flush();
			}
			catch (Exception e)
			{
				throw new ServiceException("Error sending object: " + e);
			}
		}

		private IResponse ReadResponse()
		{
			IResponse response =null;
			try
			{
                _waitHandle.WaitOne();
				lock (responses)
				{
                    //Monitor.Wait(responses); 
                    response = responses.Dequeue();
				}
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
			return response;
		}
		private void InitializeConnection()
		{
			 try
			 {
				connection=new TcpClient(host,port);
				stream=connection.GetStream();
                formatter = new BinaryFormatter();
				finished=false;
                _waitHandle = new AutoResetEvent(false);
				StartReader();
			 }
			 catch (Exception e)
			 {
				 Console.WriteLine(e.StackTrace);
			 }
		}
		private void StartReader()
		{
			Thread tw =new Thread(Run);
			tw.Start();
		}
		
		private void HandleUpdate(IUpdateResponse update)
		{
			if (update is TicketBoughtResponse ticket)
			{
				Console.WriteLine("Ticket bought: " + ticket.Ticket);
				try
				{
					client.TicketBought(ticket.Ticket);
				}
				catch (ServiceException e)
				{
                    Console.WriteLine(e.StackTrace); 
				}
			}
		}
		
		public virtual void Run()
		{
			while(!finished)
			{
				try
				{
					object response = formatter.Deserialize(stream);
					Console.WriteLine("Response received: " + response);
					if (response is IUpdateResponse updateResponse)
					{
						 HandleUpdate(updateResponse);
					}
					else
					{
						lock (responses)
						{
							responses.Enqueue((IResponse)response);
						}
                        _waitHandle.Set();
					}
				}
				catch (Exception e)
				{
					Console.WriteLine("Reading error: " + e);
				}
				
			}
		}
		
		public void Login(string username, string password, IObserver client)
		{
			InitializeConnection();
			User user = new User(username, password);
			SendRequest(new LoginRequest(user));
			IResponse response = ReadResponse();
			if (response is OkResponse)
			{
				this.client = client;
				return;
			}
			if (response is ErrorResponse)
			{
				ErrorResponse error = (ErrorResponse)response;
				CloseConnection();
				throw new ServiceException(error.Message);
			}
		}

		public void Logout(User user, IObserver client)
		{
			SendRequest(new LogoutRequest(user));
			IResponse response = ReadResponse();
			CloseConnection();
			if (response is ErrorResponse)
			{
				ErrorResponse error = (ErrorResponse)response;
				throw new ServiceException(error.Message);
			}
		}

		public List<Performance> FindAllPerformances()
		{
			SendRequest(new GetPerformancesRequest());
			IResponse response = ReadResponse();
			if (response is ErrorResponse)
			{
				ErrorResponse error = (ErrorResponse) response;
				throw new ServerException(error.Message);
			}
			GetPerformancesResponse performances = (GetPerformancesResponse) response;
			return performances.Performances;
		}
		
		
		public List<Performance> FindAllPerformancesForADay(DateTime date)
		{
			SendRequest(new GetPerformancesByDateRequest(date));
			IResponse response = ReadResponse();
			if (response is ErrorResponse)
			{
				ErrorResponse error = (ErrorResponse) response;
				throw new ServerException(error.Message);
			}
			GetPerformancesByDateResponse performances = (GetPerformancesByDateResponse) response;
			return performances.Performances;
		}

		public void SaveTicket(long performanceId, DateTime date, string place, int noOfAvailableSeats, int noOfSoldSeats,
			string artist, string owner, int noOfSeats)
		{
			Ticket ticket = new Ticket(new Performance(performanceId, date, place, noOfAvailableSeats, noOfSoldSeats,
				artist), owner, noOfSeats);
			SendRequest(new BuyTicketRequest(ticket));
			IResponse response = ReadResponse();
			if (response is ErrorResponse)
			{
				ErrorResponse error = (ErrorResponse)response;
				throw new ServiceException(error.Message);
			}
		}
	}
}