package us.ihmc.remotecaptury;

import java.io.*;
import java.net.*;

public class TCPSocketConnector {

   private Socket clientSocket;
   private ObjectOutputStream objectOutputStream;

   public void startConnection(String ip, int port) throws IOException {
      try {
         clientSocket = new Socket(ip, port);
         objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
      } catch (IOException e) {
         closeConnection();
         throw e;
      }
   }

   public void sendCapturyPoseSerialized(CapturyPoseSerialized capturyPoseSerialized) throws IOException {
      try {
         objectOutputStream.writeObject(capturyPoseSerialized);
         objectOutputStream.flush();
      } catch (IOException e) {
         closeConnection();
         throw e;
      }
   }

   public void stopConnection() throws IOException {
      closeConnection();
   }

   private void closeConnection() throws IOException {
      if (objectOutputStream!= null) {
         objectOutputStream.close();
      }
      if (clientSocket!= null) {
         clientSocket.close();
      }
   }
}