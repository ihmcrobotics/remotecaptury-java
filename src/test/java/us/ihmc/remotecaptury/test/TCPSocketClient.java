package us.ihmc.remotecaptury.test;

import us.ihmc.remotecaptury.CapturyPose;
import us.ihmc.remotecaptury.library.RemoteCapturyNativeLibrary;

import java.io.*;
import java.net.*;

public class TCPSocketClient
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
   public String sendObject(Object obj) throws IOException, ClassNotFoundException
   {
      out.writeObject(obj);
      out.flush();
      String resp = in.readLine();
      return resp;
   }

   public void stopConnect() throws IOException
   {
      in.close();
      out.close();
      clientSocket.close();
   }

   public static void main(String[] args) throws IOException, ClassNotFoundException
   {
      RemoteCapturyNativeLibrary.load();
      TCPSocketClient client = new TCPSocketClient();
      client.startConnection("172.16.66.239", 6666);

      CapturyPose pose = new CapturyPose();
      // Initialize the CapturyPose object with data

      String response = client.sendObject(pose);
      System.out.println("Server response: " + response);

      client.stopConnect();
   }
}
