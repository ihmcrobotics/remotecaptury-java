package us.ihmc.remotecaptury.test;

import us.ihmc.remotecaptury.CapturyPose;
import us.ihmc.remotecaptury.library.RemoteCapturyNativeLibrary;

import java.io.*;
import java.net.*;

public class TCPSocketConnector
{
   private Socket clientSocket;
   private ObjectOutputStream out;
   private ObjectInputStream in;

   public void startConnection(String ip, int port) throws IOException
   {
      clientSocket = new Socket(ip, port);
      out = new ObjectOutputStream(clientSocket.getOutputStream());
      in = new ObjectInputStream(clientSocket.getInputStream());
   }

   public void sendObject(Object obj) throws IOException
   {
      out.writeObject(obj);
      out.flush();
   }

   public void stopConnection() throws IOException
   {
      in.close();
      out.close();
      clientSocket.close();
   }

   public static void main(String[] args) throws IOException, ClassNotFoundException
   {
      RemoteCapturyNativeLibrary.load();
      TCPSocketConnector connector = new TCPSocketConnector();
      connector.startConnection("localhost", 6666);

      CapturyPose pose = new CapturyPose();
      // Initialize the CapturyPose object with data

      connector.sendObject(pose);
      System.out.println("Object sent");

      connector.stopConnection();
   }
}
