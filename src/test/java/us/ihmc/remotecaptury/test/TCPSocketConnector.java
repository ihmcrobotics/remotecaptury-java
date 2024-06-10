package us.ihmc.remotecaptury.test;

import us.ihmc.remotecaptury.CapturyActor;
import us.ihmc.remotecaptury.CapturyPose;
import us.ihmc.remotecaptury.library.RemoteCapturyNativeLibrary;

import java.net.*;
import java.io.*;

public class TCPSocketConnector
{
   private ServerSocket serverSocket;
   private Socket clientSocket;
   private ObjectOutputStream out;
   private ObjectInputStream in;

   public void start(int port) throws IOException, ClassNotFoundException
   {
      RemoteCapturyNativeLibrary.load();
      serverSocket = new ServerSocket(port);
      clientSocket = serverSocket.accept();
      out = new ObjectOutputStream(clientSocket.getOutputStream());
      in = new ObjectInputStream(clientSocket.getInputStream());
      Object obj = in.readObject();
      if (obj instanceof CapturyPose)
      {
         System.out.println("Recieved a CapturyPose Object");
      }
      else if(obj instanceof CapturyActor)
      {
         System.out.println("Recieved a CapturyActor Object");
      }
      else
      {
         System.out.println("Recieved an Unrecognized object");
      }

      out.writeObject("Object recieved");
   }

   public void stop() throws IOException
   {
      in.close();
      out.close();
      clientSocket.close();
      serverSocket.close();
   }
   public static void main(String[] args) throws IOException, ClassNotFoundException
   {
      TCPSocketConnector server = new TCPSocketConnector();
      server.start(6666);
   }
}
