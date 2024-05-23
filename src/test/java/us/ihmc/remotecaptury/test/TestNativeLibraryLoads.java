package us.ihmc.remotecaptury.test;

import org.junit.jupiter.api.Test;
import us.ihmc.remotecaptury.library.RemoteCapturyNativeLibrary;

import static org.junit.jupiter.api.Assertions.*;
import static us.ihmc.remotecaptury.global.remotecaptury.Captury_disconnect;

public class TestNativeLibraryLoads
{
   @Test
   public void testLibraryLoads()
   {
      System.setProperty("org.bytedeco.javacpp.loadLibraries", "false");
      RemoteCapturyNativeLibrary.load();

      int result = Captury_disconnect();

      assertEquals(1, result);
   }
}
