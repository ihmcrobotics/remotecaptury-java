// Targeted by JavaCPP version 1.5.10: DO NOT EDIT THIS FILE

package us.ihmc.remotecaptury;

import java.nio.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

import static us.ihmc.remotecaptury.global.remotecaptury.*;

@Properties(inherit = us.ihmc.remotecaptury.RemoteCapturyConfig.class)
public class CapturyActorChangedCallback extends FunctionPointer {
    static { Loader.load(); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public    CapturyActorChangedCallback(Pointer p) { super(p); }
    protected CapturyActorChangedCallback() { allocate(); }
    private native void allocate();
    public native void call(int actorId, int mode);
}
