package us.ihmc.remotecaptury.test;

import us.ihmc.remotecaptury.library.RemoteCapturyNativeLibrary;
import us.ihmc.remotecaptury.test.CapturyPoseSerialized;

import java.io.*;
import java.net.*;

public class TCPSocketClient {

   private ServerSocket serverSocket;
   private Socket clientSocket;
   private BufferedInputStream bufferedInputStream;
   private ObjectInputStream objectInputStream;

   public void startConnection(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      clientSocket = serverSocket.accept();
      bufferedInputStream = new BufferedInputStream(clientSocket.getInputStream());
      objectInputStream = new ObjectInputStream(bufferedInputStream);
   }

   public CapturyPoseSerialized receiveCapturyPoseSerialized() throws IOException, ClassNotFoundException {
      CapturyPoseSerialized capturyPoseSerialized;
      try {
         capturyPoseSerialized = (CapturyPoseSerialized) objectInputStream.readObject();
      } catch (ClassNotFoundException e) {
         throw e;
      } catch (IOException e) {
         closeSockets();
         throw e;
      }
      return capturyPoseSerialized;
   }

   public void stopConnection() throws IOException {
      closeSockets();
   }

   private void closeSockets() throws IOException {
      if (objectInputStream!= null) {
         objectInputStream.close();
      }
      if (bufferedInputStream!= null) {
         bufferedInputStream.close();
      }
      if (clientSocket!= null) {
         clientSocket.close();
      }
      if (serverSocket!= null) {
         serverSocket.close();
      }
   }

   public static void main(String[] args) throws IOException, ClassNotFoundException {
      RemoteCapturyNativeLibrary.load();
      TCPSocketClient client = new TCPSocketClient();
      client.startConnection(6666);
      while (true) {
         CapturyPoseSerialized capturyPoseSerialized = client.receiveCapturyPoseSerialized();
         // Process received CapturyPoseSerialized object here
         System.out.println(capturyPoseSerialized.numTransforms());
      }
   }
}