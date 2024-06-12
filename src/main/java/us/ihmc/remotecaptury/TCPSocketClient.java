package us.ihmc.remotecaptury;

import us.ihmc.remotecaptury.library.RemoteCapturyNativeLibrary;
import us.ihmc.remotecaptury.CapturyPoseSerialized;

import java.io.*;
import java.net.*;

public class TCPSocketClient {

   private ServerSocket serverSocket;
   private Socket clientSocket;
   private ObjectInputStream objectInputStream;

   public void startConnection(int port) {
      try
      {
         serverSocket = new ServerSocket(port);
         clientSocket = serverSocket.accept();
         objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
      }
      catch (IOException e)
      {
         throw new RuntimeException(e);
      }
   }

   public CapturyPoseSerialized receiveCapturyPoseSerialized() throws IOException, ClassNotFoundException {
      CapturyPoseSerialized capturyPoseSerialized;
      try {
         capturyPoseSerialized = (CapturyPoseSerialized) objectInputStream.readObject();
      }
      catch (ClassNotFoundException e) {
         throw e;
      }
      catch (IOException e) {
         closeSockets();
         throw e;
      }
      return capturyPoseSerialized;
   }


   public void stopConnection(){
      try
      {
         closeSockets();
      }
      catch (IOException e)
      {
         throw new RuntimeException(e);
      }
   }

   private void closeSockets() throws IOException {
      if (objectInputStream!= null) {
         objectInputStream.close();
      }
      if (clientSocket!= null) {
         clientSocket.close();
      }
      if (serverSocket!= null) {
         serverSocket.close();
      }
   }

   public static CapturyPoseSerialized updatePose(TCPSocketClient client)
   {
      RemoteCapturyNativeLibrary.load();
      CapturyPoseSerialized capturyPoseSerialized = null;
      try
      {
         capturyPoseSerialized = client.receiveCapturyPoseSerialized();
      }
      catch (IOException e)
      {
         throw new RuntimeException(e);
      }
      catch (ClassNotFoundException e)
      {
         throw new RuntimeException(e);
      }
      // Process received CapturyPoseSerialized object here
      if(capturyPoseSerialized != null)
      {
         System.out.println(capturyPoseSerialized.transforms().getPointer(12).rotation().get(1));
      }
      try
      {
         Thread.sleep(10);
      }
      catch (InterruptedException e)
      {
         throw new RuntimeException(e);
      }
      return capturyPoseSerialized;
   }
}