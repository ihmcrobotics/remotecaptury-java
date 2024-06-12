package us.ihmc.remotecaptury;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import us.ihmc.euclid.geometry.interfaces.Pose3DReadOnly;
import us.ihmc.euclid.geometry.interfaces.Pose3DBasics;
import us.ihmc.euclid.referenceFrame.FramePose3D;
import us.ihmc.euclid.transform.RigidBodyTransform;
import us.ihmc.rdx.tools.RDXModelBuilder;
import us.ihmc.rdx.tools.RDXModelInstance;
import us.ihmc.remotecaptury.library.RemoteCapturyNativeLibrary;

import java.io.*;
import java.net.*;

public class TCPSocketClient {

   private ServerSocket serverSocket;
   private Socket clientSocket;
   private ObjectInputStream objectInputStream;
   private CapturyPoseSerialized capturyPoseSerialized;
   private RDXModelInstance handObject;
   private RigidBodyTransform handTransform = new RigidBodyTransform();
   private FramePose3D handFramePose = new FramePose3D();

   public void startConnection(int port) {
      try
      {
         System.out.println("TCP Socket Client Connecting...");
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

   public void updatePose(TCPSocketClient client)
   {
      RemoteCapturyNativeLibrary.load();
       capturyPoseSerialized = null;
      try
      {
         capturyPoseSerialized = client.receiveCapturyPoseSerialized();
         if(handObject == null)
         {
            handObject = new RDXModelInstance(RDXModelBuilder.createBox(0.1F, 0.2F, 0.1F, new Color(0x870707ff)));
         }

         handTransform.setRotationYawPitchRollAndZeroTranslation(capturyPoseSerialized.transforms().getPointer(12).rotation().get(0), capturyPoseSerialized.transforms().getPointer(12).rotation().get(1), capturyPoseSerialized.transforms().getPointer(12).rotation().get(2));
         handTransform.setTranslationAndIdentityRotation(capturyPoseSerialized.transforms().getPointer(12).translation().get(0), capturyPoseSerialized.transforms().getPointer(12).translation().get(1), capturyPoseSerialized.transforms().getPointer(12).translation().get(2));
         handFramePose.set(handTransform);
         System.out.println(capturyPoseSerialized.transforms().getPointer(12).translation().get(0));
         handObject.setPoseInWorldFrame(handFramePose);
         System.out.println(handFramePose.getTranslationX());
         //handObject.transform.setTranslation(capturyPoseSerialized.transforms().getPointer(12).translation().get(0), capturyPoseSerialized.transforms().getPointer(12).translation().get(1), capturyPoseSerialized.transforms().getPointer(12).translation().get(2));
         //handObject.transform.setFromEulerAnglesRad(capturyPoseSerialized.transforms().getPointer(12).rotation().get(0), capturyPoseSerialized.transforms().getPointer(12).rotation().get(1), capturyPoseSerialized.transforms().getPointer(12).rotation().get(2));

      }
      catch (IOException e)
      {
         throw new RuntimeException(e);
      }
      catch (ClassNotFoundException e)
      {
         throw new RuntimeException(e);
      }

      try
      {
         Thread.sleep(10);
      }
      catch (InterruptedException e)
      {
         throw new RuntimeException(e);
      }

   }
   public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool)
   {
      if(handObject != null)
      {
         handObject.getRenderables(renderables, pool);
      }
   }
}