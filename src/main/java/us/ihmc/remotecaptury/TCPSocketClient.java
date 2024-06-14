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
   private final float[] startingTranslation = {0, 0, 0};
   private final float GLOBALSIZECHANGE = 0.00125F;
   private final String[] jointNames = {"Hips","Spine","Spine1","Spine2","Spine3","Spine4","Neck","Head","HeadEE","LeftShoulder","LeftArm","LeftForeArm","LeftHand","LeftHandThumb1","LeftHandThumb2","LeftHandThumb3","LeftHandThumbEE","LeftHandIndex1","LeftHandIndex2","LeftHandIndex3","LeftHandIndexEE","LeftHandMiddle1","LeftHandMiddle2","LeftHandMiddle3","LeftHandMiddleEE","LeftHandRing1","LeftHandRing2","LeftHandRing3","LeftHandRingEE","LeftHandPinky1","LeftHandPinky2","LeftHandPinky3","LeftHandPinkyEE","LeftHandEE","RightShoulder","RightArm","RightForeArm","RightHand","RightHandThumb1","RightHandThumb2","RightHandThumb3","RightHandThumbEE","RightHandIndex1","RightHandIndex2","RightHandIndex3","RightHandIndexEE","RightHandMiddle1","RightHandMiddle2","RightHandMiddle3","RightHandMiddleEE","RightHandRing1","RightHandRing2","RightHandRing3","RightHandRingEE","RightHandPinky1","RightHandPinky2","RightHandPinky3","RightHandPinkyEE","RightHandEE","LeftUpLeg","LeftLeg","LeftFoot","LeftToeBase","LeftFootEE","RightUpLeg","RightLeg","RightFoot","RightToeBase","RightFootEE"};
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

         for (int i = 0; i < startingTranslation.length; i++) {
            if (startingTranslation[i] == 0) {
               startingTranslation[i] = capturyPoseSerialized.transforms().getPointer(0).translation().get(i) * GLOBALSIZECHANGE;
               System.out.println(startingTranslation[i]);
            }
         }
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
         capturyPoseSerialized = client.receiveCapturyPoseSerialized();
      } catch (IOException e) {
         throw new RuntimeException(e);
      } catch (ClassNotFoundException e) {
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
                     .setYawPitchRoll(capturyPoseSerialized.transforms().getPointer(j).rotation(0),
                                      capturyPoseSerialized.transforms().getPointer(j).rotation(1),
                                      capturyPoseSerialized.transforms().getPointer(j).rotation(2));
         }
         transform.getTranslation()
                  .setX(capturyPoseSerialized.transforms().getPointer(j).translation(0) * GLOBALSIZECHANGE);
         transform.getTranslation()
                  .setY(capturyPoseSerialized.transforms().getPointer(j).translation(1) * GLOBALSIZECHANGE);
         transform.getTranslation()
                  .setZ(capturyPoseSerialized.transforms().getPointer(j).translation(2) * GLOBALSIZECHANGE);
         transformObjectMap.put(jointNames[j], transform);
      }
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
         else if (j == 4 || j == 3)
         {
            ReferenceFrame frameToHip = ReferenceFrameTools.constructFrameWithChangingTransformToParent(jointNames[j], referenceFrameObjectMap.get(jointNames[0]), transformObjectMap.get(jointNames[j]));
            referenceFrameObjectMap.put(jointNames[j], frameToHip);
         }
         else if(j == 9 || j == 36)
         {
            ReferenceFrame frameToSpine = ReferenceFrameTools.constructFrameWithChangingTransformToParent(jointNames[j], referenceFrameObjectMap.get(jointNames[5]), transformObjectMap.get(jointNames[j]));
            referenceFrameObjectMap.put(jointNames[j], frameToSpine);
         }
         else if(j == 59 || j == 63)
         {
            ReferenceFrame frameToSpine = ReferenceFrameTools.constructFrameWithChangingTransformToParent(jointNames[j], referenceFrameObjectMap.get(jointNames[1]), transformObjectMap.get(jointNames[j]));
            referenceFrameObjectMap.put(jointNames[j], frameToSpine);
         }
         else{
            ReferenceFrame frame = ReferenceFrameTools.constructFrameWithChangingTransformToParent(jointNames[j], referenceFrameObjectMap.get(jointNames[j-1]), transformObjectMap.get(jointNames[j]));
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