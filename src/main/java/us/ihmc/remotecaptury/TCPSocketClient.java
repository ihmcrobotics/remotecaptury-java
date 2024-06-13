package us.ihmc.remotecaptury;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import us.ihmc.euclid.referenceFrame.FramePose3D;
import us.ihmc.euclid.referenceFrame.ReferenceFrame;
import us.ihmc.euclid.referenceFrame.tools.ReferenceFrameTools;
import us.ihmc.euclid.transform.RigidBodyTransform;
import us.ihmc.euclid.transform.interfaces.RigidBodyTransformReadOnly;
import us.ihmc.euclid.tuple3D.Point3D;
import us.ihmc.euclid.yawPitchRoll.YawPitchRoll;
import us.ihmc.rdx.tools.RDXModelInstance;
import us.ihmc.rdx.ui.graphics.RDXReferenceFrameGraphic;
import us.ihmc.remotecaptury.library.RemoteCapturyNativeLibrary;
import us.ihmc.robotics.referenceFrames.MutableReferenceFrame;

import java.io.*;
import java.net.*;

public class TCPSocketClient {

   private ServerSocket serverSocket;
   private Socket clientSocket;
   private ObjectInputStream objectInputStream;
   private CapturyPoseSerialized capturyPoseSerialized;
   private final ObjectMap<String, RDXReferenceFrameGraphic> renderableMap = new ObjectMap<>(69);
   private final ObjectMap<String, ReferenceFrame> referenceFrameObjectMap = new ObjectMap<>(69);
   private final float[] startingTranslation = {0, 0, 0};
   private final float GLOBALSIZECHANGE = 0.0007385F;
   private final int STARTINGTRANSFORMNUM = 0;
   private final String[] jointNames = {"Hips", "Spine", "Spine1", "Spine2", "Spine3", "Spine4", "Neck", "Head", "HeadEE", "LeftShoulder", "LeftArm", "LeftForeArm", "LeftHand", "LeftHandThumb1", "LeftHandThumb2", "LeftHandThumb3", "LeftHandThumbEE", "LeftHandIndex1", "LeftHandIndex2", "LeftHandIndex3", "LeftHandIndexEE", "LeftHandMiddle1", "LeftHandMiddle2", "LeftHandMiddle3", "LeftHandMiddleEE", "LeftHandRing1", "LeftHandRing2", "LeftHandRing3", "LeftHandRingEE", "LeftHandPinky1", "LeftHandPinky2", "LeftHandPinky3", "LeftHandPinkyEE", "LeftHandEE", "RightShoulder", "RightArm", "RightForeArm", "RightHand", "RightHandThumb1", "RightHandThumb2", "RightHandThumb3", "RightHandThumbEE", "RightHandIndex1", "RightHandIndex2", "RightHandIndex3", "RightHandIndexEE", "RightHandMiddle1", "RightHandMiddle2", "RightHandMiddle3", "RightHandMiddleEE", "RightHandRing1", "RightHandRing2", "RightHandRing3", "RightHandRingEE", "RightHandPinky1", "RightHandPinky2", "RightHandPinky3", "RightHandPinkyEE", "RightHandEE", "LeftUpLeg", "LeftLeg", "LeftFoot", "LeftToeBase", "LeftFootEE", "RightUpLeg", "RightLeg", "RightFoot", "RightToeBase", "RightFootEE"};

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

   public void updatePose(TCPSocketClient client) {
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
         for(int j = STARTINGTRANSFORMNUM; j < jointNames.length+STARTINGTRANSFORMNUM; j++)
         {
            RigidBodyTransform transform = new RigidBodyTransform();
            transform.getRotation().setYawPitchRoll(Math.toRadians(capturyPoseSerialized.transforms().getPointer(j).rotation().get(0)*GLOBALSIZECHANGE),
                                                    Math.toRadians(capturyPoseSerialized.transforms().getPointer(j).rotation().get(1)*GLOBALSIZECHANGE),
                                                    Math.toRadians(capturyPoseSerialized.transforms().getPointer(j).rotation().get(2)*GLOBALSIZECHANGE));
            transform.getTranslation().setX(capturyPoseSerialized.transforms().getPointer(j).translation().get(0)*GLOBALSIZECHANGE);
            transform.getTranslation().setY(capturyPoseSerialized.transforms().getPointer(j).translation().get(1)*GLOBALSIZECHANGE);
            transform.getTranslation().setZ(capturyPoseSerialized.transforms().getPointer(j).translation().get(0)*GLOBALSIZECHANGE);
            if(j == STARTINGTRANSFORMNUM)
            {
               ReferenceFrame firstFrame = ReferenceFrameTools.constructFrameWithUnchangingTransformToParent(jointNames[j-STARTINGTRANSFORMNUM], ReferenceFrame.getWorldFrame(), transform);
               referenceFrameObjectMap.put(jointNames[j-STARTINGTRANSFORMNUM], firstFrame);
            }
            else{
               ReferenceFrame frame = ReferenceFrameTools.constructFrameWithChangingTransformToParent(jointNames[j-STARTINGTRANSFORMNUM], referenceFrameObjectMap.get(jointNames[j-1-STARTINGTRANSFORMNUM]), transform);

               referenceFrameObjectMap.put(jointNames[j-STARTINGTRANSFORMNUM], frame);

            }
            System.out.println(transform.getTranslationX());
            FramePose3D framePose = new FramePose3D();
            framePose.setToZero(referenceFrameObjectMap.get(jointNames[j]));
            framePose.changeFrame(ReferenceFrame.getWorldFrame());

            updateRenderableObject(jointNames[j-STARTINGTRANSFORMNUM], framePose);
         }

      } catch (IOException e) {
         throw new RuntimeException(e);
      } catch (ClassNotFoundException e) {
         throw new RuntimeException(e);
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