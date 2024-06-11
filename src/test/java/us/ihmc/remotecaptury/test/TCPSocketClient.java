package us.ihmc.remotecaptury.test;

import us.ihmc.remotecaptury.library.RemoteCapturyNativeLibrary;
import us.ihmc.remotecaptury.test.CapturyPoseSerialized;

import java.io.*;
import java.net.*;

public class TCPSocketClient {

   private ServerSocket serverSocket;
   private Socket clientSocket;
   private ObjectInputStream objectInputStream;

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
      RemoteCapturyNativeLibrary.load();
      boolean running = true;
      TCPSocketClient client = new TCPSocketClient();
      client.startConnection(6666);
      while (running) {
         CapturyPoseSerialized capturyPoseSerialized = client.receiveCapturyPoseSerialized();
         System.out.println(capturyPoseSerialized.numTransforms());
         Thread.sleep(1000);
      }
      client.stopConnection();
   }
}
