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
   private final ObjectMap<String, RDXReferenceFrameGraphic> renderableMap = new ObjectMap<>(69);
   private final ObjectMap<String, ReferenceFrame> referenceFrameObjectMap = new ObjectMap<>(69);
   private final float[] startingTranslation = {0, 0, 0};
   private final float GLOBALSIZECHANGE = 0.0025F;
   private final int STARTINGTRANSFORMNUM = 0;
   private final String[] jointNames = {"Root_tx","Root_ty","Root_tz","Root_ry","Root_rz","Root_rx","GlobalScale","Spine4_ry","Spine4_rz","Spine4_rx","Spine3_ty","Spine3_ry","Spine3_rz","Spine3_rx","Spine2_ty","Spine2_ry","Spine2_rz","Spine2_rx","Spine1_ty","Spine1_ry","Spine1_rz","Spine1_rx","Spine_ty","Spine_ry","Spine_rz","Spine_rx","Neck_ry","Neck_rz","Neck_rx","Head_ry","Head_rz","Head_rx","HeadEE","LeftShoulder_ry","LeftShoulder_rz","LeftArm_tx","LeftArm_rx","LeftArm_rz","LeftArm_ry","LeftArmScale","LeftForeArm_rx","LeftForeArm_rz","LeftForeArmScale","LeftForeArmRoll","LeftHand_rx","LeftHand_ry","LeftHandEE","RightShoulder_ry","RightShoulder_rz","RightArm_tx","RightArm_rx","RightArm_rz","RightArm_ry","RightArmScale","RightForeArm_rx","RightForeArm_rz","RightForeArmScale","RightForeArmRoll","RightHand_rx","RightHand_ry","RightHandEE","LeftUpLeg_tx","LeftUpLeg_ty","LeftUpLeg_rx","LeftUpLeg_rz","LeftUpLeg_ry","LeftLegScale","LeftLeg_rx","LeftLeg_ry","LeftLeg_rz","LeftLowLegScale","LeftFoot_rx","LeftFoot_ry","LeftFoot_rz","LeftFootScale","LeftToeBase_rx","LeftFootEE","RightUpLeg_tx","RightUpLeg_ty","RightUpLeg_rx","RightUpLeg_rz","RightUpLeg_ry","RightLegScale","RightLeg_rx","RightLeg_ry","RightLeg_rz","RightLowLegScale","RightFoot_rx","RightFoot_ry","RightFoot_rz","RightFootScale","RightToeBase_rx","RightFootEE"};
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
            transform.getRotation().setYawPitchRoll(Math.toRadians(capturyPoseSerialized.transforms().getPointer(j).rotation().get(0)),
                                                    Math.toRadians(capturyPoseSerialized.transforms().getPointer(j).rotation().get(1)),
                                                    Math.toRadians(capturyPoseSerialized.transforms().getPointer(j).rotation().get(2)));
            transform.getTranslation().setX(capturyPoseSerialized.transforms().getPointer(j).translation().get(0)*GLOBALSIZECHANGE);
            transform.getTranslation().setY(capturyPoseSerialized.transforms().getPointer(j).translation().get(1)*GLOBALSIZECHANGE);
            transform.getTranslation().setZ(capturyPoseSerialized.transforms().getPointer(j).translation().get(2)*GLOBALSIZECHANGE);
            if(j == STARTINGTRANSFORMNUM)
            {
               ReferenceFrame firstFrame = ReferenceFrameTools.constructFrameWithUnchangingTransformToParent(jointNames[j-STARTINGTRANSFORMNUM], ReferenceFrame.getWorldFrame(), transform);
               referenceFrameObjectMap.put(jointNames[j-STARTINGTRANSFORMNUM], firstFrame);
            }
            else if (j == 7 || j == 10 || j == 33 || j == 48)
            {
               ReferenceFrame frameToGlobal = ReferenceFrameTools.constructFrameWithUnchangingTransformToParent(jointNames[6], ReferenceFrame.getWorldFrame(), transform);
               referenceFrameObjectMap.put(jointNames[j-STARTINGTRANSFORMNUM], frameToGlobal);
            }
            else if(j == 61 || j == 77)
            {
               ReferenceFrame frameToSpine = ReferenceFrameTools.constructFrameWithUnchangingTransformToParent(jointNames[25], ReferenceFrame.getWorldFrame(), transform);
               referenceFrameObjectMap.put(jointNames[j-STARTINGTRANSFORMNUM], frameToSpine);
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