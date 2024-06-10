package us.ihmc.remotecaptury.test;

import org.bytedeco.javacpp.BytePointer;
import us.ihmc.remotecaptury.CapturyActor;
import us.ihmc.remotecaptury.CapturyPose;
import us.ihmc.remotecaptury.CapturyTransform;
import us.ihmc.remotecaptury.library.RemoteCapturyNativeLibrary;

import java.io.IOException;

import static us.ihmc.remotecaptury.global.remotecaptury.*;

public class ExampleCode
{
   private static volatile boolean running = true;

   static
   {
      Runtime.getRuntime().addShutdownHook(new Thread(() -> running = false));
   }

      private static void connect()
      {
         System.out.println("Connecting...");
         Captury_connect("172.16.66.239", (short) 2101);
         Captury_startStreamingImages(CAPTURY_STREAM_IMAGES, 0xa36391a4);
         Captury_startStreamingImages(CAPTURY_STREAM_IMAGES, 0xa363947a);
         Captury_startStreamingImages(CAPTURY_STREAM_IMAGES, 0xa3639485);
         Captury_startStreamingImages(CAPTURY_STREAM_IMAGES, 0xa36429bc);
         Captury_startStreamingImages(CAPTURY_STREAM_IMAGES, 0xa36429bd);
         Captury_startStreamingImages(CAPTURY_STREAM_IMAGES, 0xa36429be);
         Captury_startStreamingImages(CAPTURY_STREAM_IMAGES, 0xa36429c0);
         Captury_startStreamingImages(CAPTURY_STREAM_IMAGES, 0xa36429c2);
         Captury_startStreaming(CAPTURY_STREAM_POSES);
         Captury_startStreaming(CAPTURY_STREAM_LOCAL_POSES);
      }

   private static final int ACTOR_ID = 30000; // TODO: figure out where this comes from

   public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException
   {
      // Load native library
      RemoteCapturyNativeLibrary.load();
      Captury_connect("172.16.66.239", (short) 2101);
      TCPSocketConnector connector = new TCPSocketConnector();
      connector.startConnection("172.16.66.239", 6666);
      // Captury SDK logging thread
      new Thread(() ->
      {
         while (running)
         {
            BytePointer nextLogMessage = Captury_getNextLogMessage();

            if (nextLogMessage != null && !nextLogMessage.isNull())
            {
               System.out.println("[CapturyLive] " + nextLogMessage.getString());

               nextLogMessage.close();
            }

            try
            {
               Thread.sleep(10);
            }
            catch (InterruptedException e)
            {
               e.printStackTrace();
            }
         }
      }, "CapturyLogPrinter").start();
      Captury_disconnect();
      Thread.sleep(3000);
      //Turns off printing all log at end as well
      Captury_enablePrintf(0);

      // Disconnect CapturyLive
//      Captury_stopStreaming();
//      Captury_disconnect();
//      while(Captury_getConnectionStatus() != CAPTURY_DISCONNECTED){
//         Captury_stopStreaming();
//         Captury_disconnect();
//      }
//      Thread.sleep(5000);
      // Start tracking
      Captury_startTracking(ACTOR_ID, 0, 0, 720);
      // Initialize actor
      CapturyActor actors = new CapturyActor();
      while (Captury_getActorStatus(ACTOR_ID) == ACTOR_UNKNOWN)
      {
         if (Captury_getConnectionStatus() == CAPTURY_CONNECTED)
         {
            System.out.println("Snapping Actor");
            Captury_snapActor(0, 0, 720);
            //In miliseconds C++ code was in seconds
            Thread.sleep(3000);
            Captury_getActors(actors);
         }
         else
         {
            connect();
            Thread.sleep(1000);
         }
      }

      Thread.sleep(5000);
      System.out.println(actors.id());
      int[] translationNums = {9, 10, 11, 12, 34, 35, 36, 37};
      int[] rotationNums = {9, 11, 12, 34, 36, 37};
      float[] transaltionArray = new float[translationNums.length];
      float[] rotationArray = new float[rotationNums.length];
      while (Captury_getConnectionStatus() == CAPTURY_CONNECTED)
      {

         CapturyPose pose = Captury_getCurrentPose(ACTOR_ID);
         Captury_convertPoseToLocal(pose, ACTOR_ID);
         CapturyTransform transform = pose.transforms();
         for(int i = 0; i < translationNums.length; i++)
         {
            //TODO: Figure out how to get every translation and rotation from the different transforms
            // Have to go all the way back to LeftArm to get a translation otherwise it is all rotations with respect to other objects
            // Left Shoulder is the first one to have parent joint as global
            transaltionArray[i] = transform.getPointer(translationNums[i]).translation().get();
         }
         for(int j = 0; j < rotationNums.length; j++){
            rotationArray[j] = transform.getPointer(rotationNums[j]).rotation().get();
         }
         //         int transformNum = 12;

//         CapturyTransform transform = pose.transforms().getPointer(transformNum);
//         float rot = transform.rotation().get();
//         // Joint names are in unknown.skel after recording motion
//         String jointName = Captury_getActor(ACTOR_ID).joints().getPointer(transformNum).name().getString();
//         System.out.println(jointName);
//         System.out.println(rot);
//         Thread.sleep(1);
            connector.sendFloatArray(transaltionArray);
            connector.sendFloatArray(rotationArray);
            Thread.sleep(1000);
      }

      Thread.sleep(3000);

      connector.stopConnection();
      Captury_deleteActor(ACTOR_ID);
      Captury_stopTracking(ACTOR_ID); // TODO: does this need to come before deleteActor?
      Captury_stopStreaming();
      Captury_disconnect();
      connect();
   }
}
