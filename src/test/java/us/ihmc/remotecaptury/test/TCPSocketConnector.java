package us.ihmc.remotecaptury.test;

import us.ihmc.remotecaptury.CapturyPose;
import us.ihmc.remotecaptury.library.RemoteCapturyNativeLibrary;

import java.io.*;
import java.net.*;

public class TCPSocketConnector
{
   private Socket clientSocket;
   private DataOutputStream out;
   private DataInputStream in;

   public void startConnection(String ip, int port) throws IOException
   {
      clientSocket = new Socket(ip, port);
      out = new DataOutputStream(clientSocket.getOutputStream());
      in = new DataInputStream(clientSocket.getInputStream());
   }
   public void sendFloatArray(float[] array) throws IOException {
      out.writeInt(array.length);
      for (float f : array) {
         out.writeFloat(f);
      }
      out.flush();
   }

   public void stopConnection() throws IOException {
      in.close();
      out.close();
      clientSocket.close();
   }
}
