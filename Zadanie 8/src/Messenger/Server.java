package Messenger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    static Socket c1;
    static Socket c2;

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(7777, 2);
        Socket s;
        while (true)
        {
            int i = 0;
            s = ss.accept();

            System.out.println("Nowe żądanie połączenia : " + s);
            if (c1 == null || c1.isClosed())
                c1 = s;
            else if (c2 == null || c2.isClosed())
                c2 = s;
            else
                continue;

            System.out.println("Tworzę handler dla podanego gniazda...");
            ClientHandler mtch = new ClientHandler(s);
            Thread t = new Thread(mtch);
            t.start();
        }
    }
}