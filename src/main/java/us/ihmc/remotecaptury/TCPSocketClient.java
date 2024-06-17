package us.ihmc.remotecaptury;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import us.ihmc.euclid.referenceFrame.FramePose3D;
import us.ihmc.euclid.referenceFrame.ReferenceFrame;
import us.ihmc.euclid.referenceFrame.tools.ReferenceFrameTools;
import us.ihmc.euclid.transform.RigidBodyTransform;
import us.ihmc.rdx.tools.RDXModelInstance;
import us.ihmc.rdx.ui.graphics.RDXReferenceFrameGraphic;
import us.ihmc.remotecaptury.library.RemoteCapturyNativeLibrary;

import java.io.*;
import java.net.*;

public class TCPSocketClient {

   private ServerSocket serverSocket;
   private Socket clientSocket;
   private ObjectInputStream objectInputStream;
   private CapturyPoseSerialized capturyPoseSerialized;
   private final float GLOBALSIZECHANGE = 0.00125F;
   private final float GLOBALROTATIONCHANGE = 0.0125F;
   // According to people at capturyLive transforms are the same list and order as joints
   private final String[] jointNames = {"Hips","Spine","Spine1","Spine2","Spine3","Spine4","Neck","Head","HeadEE","LeftShoulder","LeftArm","LeftForeArm","LeftHand","LeftHandThumb1","LeftHandThumb2","LeftHandThumb3","LeftHandThumbEE","LeftHandIndex1","LeftHandIndex2","LeftHandIndex3","LeftHandIndexEE","LeftHandMiddle1","LeftHandMiddle2","LeftHandMiddle3","LeftHandMiddleEE","LeftHandRing1","LeftHandRing2","LeftHandRing3","LeftHandRingEE","LeftHandPinky1","LeftHandPinky2","LeftHandPinky3","LeftHandPinkyEE","LeftHandEE","RightShoulder","RightArm","RightForeArm","RightHand","RightHandThumb1","RightHandThumb2","RightHandThumb3","RightHandThumbEE","RightHandIndex1","RightHandIndex2","RightHandIndex3","RightHandIndexEE","RightHandMiddle1","RightHandMiddle2","RightHandMiddle3","RightHandMiddleEE","RightHandRing1","RightHandRing2","RightHandRing3","RightHandRingEE","RightHandPinky1","RightHandPinky2","RightHandPinky3","RightHandPinkyEE","RightHandEE","LeftUpLeg","LeftLeg","LeftFoot","LeftToeBase","LeftFootEE","RightUpLeg","RightLeg","RightFoot","RightToeBase","RightFootEE"};
   private final int[] parentNum = {-1, 0, 1, 2, 3, 4, 5, 6, 7, 4, 9, 10, 11, 12, 13, 14, 15, 12, 17, 18, 19, 12, 21, 22, 23, 12, 25, 26, 27, 12, 29, 30, 31, 12, 4, 34, 35, 36, 37, 38, 39, 40, 37, 42, 43, 44, 37, 46, 47, 48, 37, 50, 51, 52, 37, 54, 55, 56, 37, 0, 59, 60, 61, 62, 0, 64, 65, 66, 67};
   private final ObjectMap<String, RDXReferenceFrameGraphic> renderableMap = new ObjectMap<>(jointNames.length);
   private final ObjectMap<String, ReferenceFrame> referenceFrameObjectMap = new ObjectMap<>(jointNames.length);
   private final ObjectMap<String, RigidBodyTransform> transformObjectMap = new ObjectMap<>(jointNames.length);
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

   public void setUpPose(TCPSocketClient client) {

      RemoteCapturyNativeLibrary.load();
      capturyPoseSerialized = null;
      try {
         capturyPoseSerialized = client.receiveCapturyPoseSerialized();
         updateTransforms(client);
         updateFrames();

      } catch (IOException e) {
         throw new RuntimeException(e);
      } catch (ClassNotFoundException e) {
         throw new RuntimeException(e);
      }

   }
   private void updateTransforms(TCPSocketClient client)
   {
      transformObjectMap.clear();
      try
      {
         Thread.sleep(20);
         capturyPoseSerialized = client.receiveCapturyPoseSerialized();
      } catch (IOException e) {
         throw new RuntimeException(e);
      } catch (ClassNotFoundException e) {
         throw new RuntimeException(e);
      }
      catch (InterruptedException e)
      {
         throw new RuntimeException(e);
      }
      for(int j = 0; j < jointNames.length; j++)
      {
         RigidBodyTransform transform = new RigidBodyTransform();
         if (capturyPoseSerialized.transforms().getPointer(j).rotation() == null)
         {
            transform.getRotation().setYawPitchRoll(0, 0, 0);
         }
         else
         {
            transform.getRotation()
                     .setYawPitchRoll(capturyPoseSerialized.transforms().getPointer(j).rotation(0)*GLOBALROTATIONCHANGE,
                                      capturyPoseSerialized.transforms().getPointer(j).rotation(2)*GLOBALROTATIONCHANGE,
                                      capturyPoseSerialized.transforms().getPointer(j).rotation(1)*GLOBALROTATIONCHANGE
                                      );
         }
         transform.getTranslation()
                  .setX(capturyPoseSerialized.transforms().getPointer(j).translation(0) * GLOBALSIZECHANGE);
         transform.getTranslation()
                  .setY(capturyPoseSerialized.transforms().getPointer(j).translation(2) * GLOBALSIZECHANGE);
         transform.getTranslation()
                  .setZ(capturyPoseSerialized.transforms().getPointer(j).translation(1) * GLOBALSIZECHANGE);
         transformObjectMap.put(jointNames[j], transform);
      }
      System.out.println(transformObjectMap.get(jointNames[0]).getTranslationX());
   }
   private void updateFrames(){
      referenceFrameObjectMap.clear();
      for(int j = 0; j < jointNames.length; j++)
      {
         if(j == 0)
         {
            ReferenceFrame firstFrame = ReferenceFrameTools.constructFrameWithChangingTransformToParent(jointNames[j], ReferenceFrame.getWorldFrame(), transformObjectMap.get(jointNames[j]));
            referenceFrameObjectMap.put(jointNames[j], firstFrame);
         }
         else{
            ReferenceFrame frame = ReferenceFrameTools.constructFrameWithChangingTransformToParent(jointNames[j], referenceFrameObjectMap.get(jointNames[parentNum[j]]), transformObjectMap.get(jointNames[j]));
            referenceFrameObjectMap.put(jointNames[j], frame);

         }
      }
   }
   public void updatePose(TCPSocketClient client){
      updateTransforms(client);
      renderableMap.clear();
      updateFrames();
      for(int i = 0; i < jointNames.length; i++)
      {
         FramePose3D framePose = new FramePose3D();
         framePose.setToZero(referenceFrameObjectMap.get(jointNames[i]));
         framePose.changeFrame(ReferenceFrame.getWorldFrame());

         updateRenderableObject(jointNames[i], framePose);
      }
   }
   private void updateRenderableObject(String key, FramePose3D framePose) {
      RDXReferenceFrameGraphic object = new RDXReferenceFrameGraphic(0.2);
      renderableMap.put(key, object);
      object.setPoseInWorldFrame(framePose);
   }

   public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool)
   {
      if (renderableMap != null)
      {
         for (RDXModelInstance object : renderableMap.values())
         {
            if (object != null)
            {
               object.getRenderables(renderables, pool);
            }
         }
      }
   }
}