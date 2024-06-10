package us.ihmc.remotecaptury.test;

import us.ihmc.remotecaptury.CapturyActor;
import us.ihmc.remotecaptury.CapturyPose;
import us.ihmc.remotecaptury.CapturyTransform;
import us.ihmc.remotecaptury.library.RemoteCapturyNativeLibrary;

import java.io.*;
import java.net.*;

public class TCPSocketClient
{

   private ServerSocket serverSocket;
   private Socket clientSocket;
   private DataInputStream in;

   public void startConnection(int port) throws IOException
   {
      serverSocket = new ServerSocket(port);
      clientSocket = serverSocket.accept();
      in = new DataInputStream(clientSocket.getInputStream());
   }

   public float receiveFloat() throws IOException
   {
      float f = in.readFloat();
      return f;
   }

   public void stopConnection() throws IOException
   {
      in.close();
      clientSocket.close();
      serverSocket.close();
   }

   public static void main(String[] args) throws IOException
   {
      TCPSocketClient client = new TCPSocketClient();
      client.startConnection(6666);

      float recievedFloat = client.receiveFloat();
      System.out.println("Float received: " + recievedFloat);

      client.stopConnection();
   }
}