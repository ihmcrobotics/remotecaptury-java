package us.ihmc.remotecaptury;

import java.io.*;
import java.net.*;

public class TCPSocketConnector {
   private Socket clientSocket;
   private ObjectOutputStream objectOutputStream;
   private ByteArrayOutputStream buffer = new ByteArrayOutputStream();

   public void startConnection(String ip, int port) throws IOException {
      try {
         clientSocket = new Socket(ip, port);
         objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
      } catch (IOException e) {
         logError("Error establishing connection", e);
         closeConnection();
         throw e;
      }
   }

   public void sendCapturyPoseSerialized(CapturyPoseSerialized capturyPoseSerialized) throws IOException {
      try {
         buffer.reset();
         ObjectOutputStream bos = new ObjectOutputStream(buffer);
         bos.writeObject(capturyPoseSerialized);
         bos.flush();
         objectOutputStream.write(buffer.toByteArray());
         objectOutputStream.flush();
      } catch (IOException e) {
         logError("Error sending object", e);
         closeConnection();
         throw e;
      }
   }

   public void stopConnection() throws IOException {
      closeConnection();
   }

   private void closeConnection() throws IOException {
      if (objectOutputStream != null) {
         objectOutputStream.close();
      }
      if (clientSocket != null) {
         clientSocket.close();
      }
   }

   private void logError(String message, Throwable t) {
      // Log the error using a logging framework or a simple println statement
      System.err.println(message + ": " + t.getMessage());
   }
}