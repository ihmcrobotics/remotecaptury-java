package us.ihmc.remotecaptury.library;

import us.ihmc.tools.nativelibraries.NativeLibraryDescription;
import us.ihmc.tools.nativelibraries.NativeLibraryLoader;
import us.ihmc.tools.nativelibraries.NativeLibraryWithDependencies;

public class RemoteCapturyNativeLibrary implements NativeLibraryDescription
{
   @Override
   public String getPackage(OperatingSystem os, Architecture arch)
   {
      String archPackage = "";
      if (arch == Architecture.x64)
      {
         archPackage = switch (os)
         {
            case WIN64 -> "windows-x86_64";
            case LINUX64 -> "linux-x86_64";
            default -> "unknown";
         };
      }

      return "native." + archPackage;
   }

   @Override
   public NativeLibraryWithDependencies getLibraryWithDependencies(OperatingSystem os, Architecture arch)
   {
      switch (os)
      {
         case LINUX64 ->
         {
            return NativeLibraryWithDependencies.fromFilename("libjniremotecaptury.so", "libRemoteCaptury.so");
         }
         case WIN64 ->
         {
            return NativeLibraryWithDependencies.fromFilename("jniremotecaptury.dll", "RemoteCaptury.dll");
         }
      }

      System.out.println("Unsupported platform: " + os.name() + "-" + arch.name());

      return null;
   }

   private static boolean loaded = false;

   public static boolean load()
   {
      if (!loaded)
      {
         RemoteCapturyNativeLibrary lib = new RemoteCapturyNativeLibrary();
         loaded = NativeLibraryLoader.loadLibrary(lib);
      }
      return loaded;
   }
}
