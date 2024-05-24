package us.ihmc.remotecaptury.test;

import org.junit.jupiter.api.Test;
import us.ihmc.remotecaptury.library.RemoteCapturyNativeLibrary;

import static org.junit.jupiter.api.Assertions.*;
import static us.ihmc.remotecaptury.global.remotecaptury.*;

public class TestNativeLibraryLoads
{
   private static final int ACTOR_ID = 30000;
   @Test
   public void testLibraryLoads()
   {
      RemoteCapturyNativeLibrary.load();

      // Connects to CapturyLive software
      Captury_connect("127.0.0.1", (short) 2101);
      int connectionStatus = Captury_getConnectionStatus();
      System.out.println("Connection status: " + connectionStatus);
      boolean validConnectionStatus = (connectionStatus == CAPTURY_DISCONNECTED | connectionStatus == CAPTURY_CONNECTING
                                       | connectionStatus == CAPTURY_CONNECTED);
      assertTrue(validConnectionStatus);

      // Checks for running camera
      int cameraStatus = Captury_startStreamingImages(CAPTURY_STREAM_IMAGES, 0xa36391a4);
      System.out.println("Camera Status: " + cameraStatus);
      boolean cameraIdentified = (cameraStatus == 0 || cameraStatus == 1);
      assertTrue(cameraIdentified);

      // Check tracking
      int tracking = Captury_startTracking(ACTOR_ID, 0, 0, 720);
      System.out.println("Tracking Status: " + tracking);
      boolean validTracking = (tracking == 0 | tracking == 1);
      assertTrue(validTracking);

      // Checks for actor
      int actorStatus = Captury_getActorStatus(ACTOR_ID);
      System.out.println("Actor Status: " + actorStatus);
      boolean validActorStatus = (actorStatus == ACTOR_UNKNOWN | actorStatus == ACTOR_SCALING|
                                  actorStatus == ACTOR_TRACKING | actorStatus == ACTOR_STOPPED|
                                  actorStatus == ACTOR_DELETED);
      assertTrue(validActorStatus);

      // Check to stop track
      int stopTracking = Captury_stopTracking(ACTOR_ID);
      System.out.println("End Tracking Status: " + stopTracking);
      boolean endTracking = (stopTracking == 0 | stopTracking == 1);
      assertTrue(endTracking);

      // Check to delete actor
      int deleteActor = Captury_deleteActor(ACTOR_ID);
      System.out.println("Actor Deleteion Status: " + deleteActor);
      boolean actorDeletion = (deleteActor == 0 | deleteActor == 1);
      assertTrue(actorDeletion);

      // Disconnects from CapturyLiveSoftware
      int disconnectedProperly = Captury_disconnect();
      System.out.println("Disconnect Status: " + disconnectedProperly);
      int disconnectStatus = Captury_getConnectionStatus();
      assertEquals(CAPTURY_DISCONNECTED, disconnectStatus);
   }
}