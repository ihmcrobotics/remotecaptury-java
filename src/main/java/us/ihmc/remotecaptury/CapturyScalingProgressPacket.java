// Targeted by JavaCPP version 1.5.10: DO NOT EDIT THIS FILE

package us.ihmc.remotecaptury;

import java.nio.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

import static us.ihmc.remotecaptury.global.remotecaptury.*;


// sent to client
// as a reply to capturyGetScalingProgress
@Properties(inherit = us.ihmc.remotecaptury.RemoteCapturyConfig.class)
public class CapturyScalingProgressPacket extends Pointer {
    static { Loader.load(); }
    /** Default native constructor. */
    public CapturyScalingProgressPacket() { super((Pointer)null); allocate(); }
    /** Native array allocator. Access with {@link Pointer#position(long)}. */
    public CapturyScalingProgressPacket(long size) { super((Pointer)null); allocateArray(size); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public CapturyScalingProgressPacket(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(long size);
    @Override public CapturyScalingProgressPacket position(long position) {
        return (CapturyScalingProgressPacket)super.position(position);
    }
    @Override public CapturyScalingProgressPacket getPointer(long i) {
        return new CapturyScalingProgressPacket((Pointer)this).offsetAddress(i);
    }

	public native int type(); public native CapturyScalingProgressPacket type(int setter);		// capturyScalingProgress
	public native int size(); public native CapturyScalingProgressPacket size(int setter);

	public native int actor(); public native CapturyScalingProgressPacket actor(int setter);
	public native byte progress(); public native CapturyScalingProgressPacket progress(byte setter);	// value from 0 to 100
}
