// Targeted by JavaCPP version 1.5.10: DO NOT EDIT THIS FILE

package us.ihmc.remotecaptury;

import java.nio.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

import static us.ihmc.remotecaptury.global.remotecaptury.*;


// sent to server
@Properties(inherit = us.ihmc.remotecaptury.RemoteCapturyConfig.class)
public class CapturyStreamPacket extends Pointer {
    static { Loader.load(); }
    /** Default native constructor. */
    public CapturyStreamPacket() { super((Pointer)null); allocate(); }
    /** Native array allocator. Access with {@link Pointer#position(long)}. */
    public CapturyStreamPacket(long size) { super((Pointer)null); allocateArray(size); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public CapturyStreamPacket(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(long size);
    @Override public CapturyStreamPacket position(long position) {
        return (CapturyStreamPacket)super.position(position);
    }
    @Override public CapturyStreamPacket getPointer(long i) {
        return new CapturyStreamPacket((Pointer)this).offsetAddress(i);
    }

	public native int type(); public native CapturyStreamPacket type(int setter);		// capturyStream
	public native int size(); public native CapturyStreamPacket size(int setter);		// size of full message including type and size

	public native int what(); public native CapturyStreamPacket what(int setter);		// CAPTURY_STREAM_POSES or CAPTURY_STREAM_NOTHING
	public native int cameraId(); public native CapturyStreamPacket cameraId(int setter);	// valid if what & CAPTURY_STREAM_IMAGES
}
