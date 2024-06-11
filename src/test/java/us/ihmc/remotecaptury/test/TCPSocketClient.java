package us.ihmc.remotecaptury.test;

import us.ihmc.remotecaptury.test.CapturyPoseSerialized;

import java.io.*;
import java.net.*;

public class TCPSocketClient {

   private ServerSocket serverSocket;
   private Socket clientSocket;
   private ObjectInputStream objectInputStream;
   private static CapturyPoseSerialized capturyPoseSerialized;

   public void startConnection(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      clientSocket = serverSocket.accept();
      objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
   }

   public CapturyPoseSerialized receiveCapturyPoseSerialized() throws IOException, ClassNotFoundException {
      return (CapturyPoseSerialized) objectInputStream.readObject();
   }

   public void stopConnection() throws IOException {
      objectInputStream.close();
      clientSocket.close();
      serverSocket.close();
   }

   public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException
   {
      boolean running = true;
      TCPSocketClient client = new TCPSocketClient();
      client.startConnection(6666);
      while (running) {
         capturyPoseSerialized = client.receiveCapturyPoseSerialized();
         // Do whatever you want with the received CapturyPoseSerialized object
         System.out.println("Received CapturyPoseSerialized object: " + capturyPoseSerialized);
         Thread.sleep(1000);
      }
      client.stopConnection();
   }
}
