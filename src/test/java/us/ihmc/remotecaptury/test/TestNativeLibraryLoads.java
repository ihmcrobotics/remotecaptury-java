package us.ihmc.remotecaptury.test;

import org.junit.jupiter.api.Test;
import us.ihmc.remotecaptury.global.remotecaptury;
import us.ihmc.remotecaptury.library.RemoteCapturyNativeLibrary;

import static org.junit.jupiter.api.Assertions.*;

public class TestNativeLibraryLoads
{
   @Test
   public void testLibraryLoads()
   {
      RemoteCapturyNativeLibrary.load();

      int connectionStatus = remotecaptury.Captury_getConnectionStatus();

      System.out.println("Connection status: " + connectionStatus);

      boolean validConnectionStatus = (connectionStatus == remotecaptury.CAPTURY_DISCONNECTED | connectionStatus == remotecaptury.CAPTURY_CONNECTING
                                       | connectionStatus == remotecaptury.CAPTURY_CONNECTED);

      assertTrue(validConnectionStatus);
   }
}
