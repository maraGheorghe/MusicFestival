using System;
using System.Windows.Forms;
using Grpc.Core;
using log4net.Config;
using networking;
using service;

namespace client
{
    static class StartClient
    {
        [STAThread]
        static void Main()
        {
            XmlConfigurator.Configure(new System.IO.FileInfo("log4j.xml"));
            AppContext.SetSwitch("System.Net.Http.SocketsHttpHandler.Http2UnencryptedSupport", true);
            Channel channel = new Channel("127.0.0.1:26122", ChannelCredentials.Insecure);
            var server = new MusicFestivalService.MusicFestivalServiceClient(channel);
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);         
            Controller controller = new Controller(server);
            LoginController loginWindow = new LoginController(controller);
            Application.Run(loginWindow);
        }
    }
}