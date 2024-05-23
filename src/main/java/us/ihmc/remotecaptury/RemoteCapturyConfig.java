package us.ihmc.remotecaptury;

import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.tools.Info;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

@Properties(value = {
      @Platform(
            includepath = "include",
            include = {"captury/PublicStructs.h",
                       "RemoteCaptury.h"},
            linkpath = "lib",
            link = "RemoteCaptury"
      )
},
      target = "us.ihmc.remotecaptury",
      global = "us.ihmc.remotecaptury.global.remotecaptury"
)
public class RemoteCapturyConfig implements InfoMapper
{
   @Override
   public void map(InfoMap infoMap)
   {
      infoMap.put(new Info("CAPTURY_DLL_EXPORT").cppTypes().annotations());
   }
}
