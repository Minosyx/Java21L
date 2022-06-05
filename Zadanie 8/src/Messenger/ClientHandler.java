package Messenger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.Socket;

class ClientHandler implements Runnable
{
    Socket s;

    public ClientHandler(Socket s){
        this.s = s;
    }

    @Override
    public void run() {

        String received;
        while (!s.isClosed())
        {
            try
            {
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                try {
                    received = dis.readUTF();
                    if (!Server.c1.isClosed() && !Server.c2.isClosed()) {
                        if (s.getInputStream() == Server.c1.getInputStream())
                            new DataOutputStream(Server.c2.getOutputStream()).writeUTF(received);
                        else if (s.getInputStream() == Server.c2.getInputStream())
                            new DataOutputStream(Server.c1.getOutputStream()).writeUTF(received);
                    }
                } catch (EOFException e){
                    s.close();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
