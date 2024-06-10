package us.ihmc.remotecaptury.test;

import us.ihmc.remotecaptury.CapturyActor;
import us.ihmc.remotecaptury.CapturyPose;
import us.ihmc.remotecaptury.library.RemoteCapturyNativeLibrary;

import java.io.*;
import java.net.*;

public class TCPSocketClient
{
   private ServerSocket serverSocket;
   private Socket clientSocket;
   private ObjectOutputStream out;
   private ObjectInputStream in;

   public void startConnection(int port) throws IOException
   {
      serverSocket = new ServerSocket(port);
      clientSocket = serverSocket.accept();
      out = new ObjectOutputStream(clientSocket.getOutputStream());
      in = new ObjectInputStream(clientSocket.getInputStream());
   }

   public Object receiveObject() throws IOException, ClassNotFoundException
   {
      Object obj = in.readObject();
      return obj;
   }

   public void stopConnection() throws IOException
   {
      in.close();
      out.close();
      clientSocket.close();
      serverSocket.close();
   }

   public static void main(String[] args) throws IOException, ClassNotFoundException
   {
      RemoteCapturyNativeLibrary.load();
      TCPSocketClient client = new TCPSocketClient();
      client.startConnection(6666);

      Object receivedObject = client.receiveObject();
      if (receivedObject instanceof CapturyPose)
      {
         System.out.println("Received a CapturyPose Object");
      }
      else if(receivedObject instanceof CapturyActor)
      {
         System.out.println("Received a CapturyActor Object");
      }
      else
      {
         System.out.println("Received an Unrecognized object");
      }

      client.stopConnection();
   }
}
