package us.ihmc.remotecaptury.test;

import us.ihmc.remotecaptury.CapturyActor;
import us.ihmc.remotecaptury.library.RemoteCapturyNativeLibrary;

import static us.ihmc.remotecaptury.global.remotecaptury.*;

public class TestNativeLibraryLoads
{
   private static volatile boolean running = true;

   static
   {
      Runtime.getRuntime().addShutdownHook(new Thread(() -> running = false));
   }

   private static void connect()
   {
      System.out.println("Connecting...");

      Captury_connect("100.100.100.1", (short) 2101);

      Captury_startStreamingImages(CAPTURY_STREAM_IMAGES, 0xa36391a4);
      Captury_startStreamingImages(CAPTURY_STREAM_IMAGES, 0xa363947a);
      Captury_startStreamingImages(CAPTURY_STREAM_IMAGES, 0xa3639485);
      Captury_startStreamingImages(CAPTURY_STREAM_IMAGES, 0xa36429bc);
      Captury_startStreamingImages(CAPTURY_STREAM_IMAGES, 0xa36429bd);
      Captury_startStreamingImages(CAPTURY_STREAM_IMAGES, 0xa36429be);
      Captury_startStreamingImages(CAPTURY_STREAM_IMAGES, 0xa36429c0);
      Captury_startStreamingImages(CAPTURY_STREAM_IMAGES, 0xa36429c2);
      Captury_startStreaming(CAPTURY_STREAM_POSES);
   }

   private static final int ACTOR_ID = 30000; // TODO: figure out where this comes from

   public static void main(String[] args) throws InterruptedException
   {
      // Load native library
      System.setProperty("org.bytedeco.javacpp.loadLibraries", "false");
      RemoteCapturyNativeLibrary.load();

      // Connect
      connect();

      // Start tracking
      Captury_startTracking(ACTOR_ID, 0, 0, 720);

      // Initialize actor
      CapturyActor actors = new CapturyActor();
      while (Captury_getActorStatus(ACTOR_ID) == ACTOR_UNKNOWN)
      {
         if (Captury_getConnectionStatus() == CAPTURY_CONNECTED)
         {
            Captury_snapActor(0, 0, 720);
            Captury_getActors(actors);
            System.out.println(actors.name()); // TODO: ???
            Thread.sleep(3);
         }
         else
         {
            connect();
            Thread.sleep(1000);
         }
      }

      while (running)
      {
         float rot = Captury_getCurrentPose(ACTOR_ID).transforms().rotation(0);
         System.out.println(rot);
         Thread.sleep(1);
      }

      Captury_deleteActor(ACTOR_ID);
      Captury_stopTracking(ACTOR_ID); // TODO: does this need to come before deleteActor?
      Captury_stopStreaming();
      Captury_disconnect();
   }
}
