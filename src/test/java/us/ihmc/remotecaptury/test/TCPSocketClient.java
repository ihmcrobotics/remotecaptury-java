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

   public float[] receiveFloatArray() throws IOException
   {
      int length = in.readInt();
      float[] array = new float[length];
      for (int i = 0; i < length; i++)
      {
         array[i] = in.readFloat();
      }
      return array;
   }

   public void stopConnection() throws IOException
   {
      in.close();
      clientSocket.close();
      serverSocket.close();
   }

   public static void main(String[] args) throws IOException, InterruptedException
   {
      boolean running = true;
      TCPSocketClient client = new TCPSocketClient();
      client.startConnection(6666);
      while(running)
      {
         float[] translationArray = client.receiveFloatArray();
         float[] rotationArray = client.receiveFloatArray();
         System.out.println("Translation array received: " + java.util.Arrays.toString(translationArray));
         System.out.println("Rotation array received: " + java.util.Arrays.toString(rotationArray));
         Thread.sleep(100);
      }
      client.stopConnection();
   }
}