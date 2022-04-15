using System;
using System.Windows.Forms;
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
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);         
            IService server = new ServerProxy("127.0.0.1", 22126);
            Controller controller = new Controller(server);
            LoginController loginWindow = new LoginController(controller);
            Application.Run(loginWindow);
        }
    }
}