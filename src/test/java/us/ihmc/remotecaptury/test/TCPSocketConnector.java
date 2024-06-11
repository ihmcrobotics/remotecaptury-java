package us.ihmc.remotecaptury.test;

import us.ihmc.remotecaptury.test.CapturyPoseSerialized;

import java.io.*;
import java.net.*;

public class TCPSocketConnector {

   private Socket clientSocket;
   private ObjectOutputStream objectOutputStream;

   public void startConnection(String ip, int port) throws IOException {
      clientSocket = new Socket(ip, port);
      objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
   }

   public void sendCapturyPoseSerialized(CapturyPoseSerialized capturyPoseSerialized) throws IOException {
      objectOutputStream.writeObject(capturyPoseSerialized);
      objectOutputStream.flush();
   }

   public void stopConnection() throws IOException {
      objectOutputStream.close();
      clientSocket.close();
   }
}
