// Targeted by JavaCPP version 1.5.10: DO NOT EDIT THIS FILE

package us.ihmc.remotecaptury;

import java.nio.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

import static us.ihmc.remotecaptury.global.remotecaptury.*;


// sent to server
@Properties(inherit = us.ihmc.remotecaptury.RemoteCapturyConfig.class)
public class CapturyGetImagePacket extends Pointer {
    static { Loader.load(); }
    /** Default native constructor. */
    public CapturyGetImagePacket() { super((Pointer)null); allocate(); }
    /** Native array allocator. Access with {@link Pointer#position(long)}. */
    public CapturyGetImagePacket(long size) { super((Pointer)null); allocateArray(size); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public CapturyGetImagePacket(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(long size);
    @Override public CapturyGetImagePacket position(long position) {
        return (CapturyGetImagePacket)super.position(position);
    }
    @Override public CapturyGetImagePacket getPointer(long i) {
        return new CapturyGetImagePacket((Pointer)this).offsetAddress(i);
    }

	public native int type(); public native CapturyGetImagePacket type(int setter);	// capturyGetImage
	public native int size(); public native CapturyGetImagePacket size(int setter);

	public native int actor(); public native CapturyGetImagePacket actor(int setter);
}
