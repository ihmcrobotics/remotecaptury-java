// Targeted by JavaCPP version 1.5.10: DO NOT EDIT THIS FILE

package us.ihmc.remotecaptury;

import java.nio.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

import static us.ihmc.remotecaptury.global.remotecaptury.*;


// sent to server - old version needs to stay around for old clients
@Properties(inherit = us.ihmc.remotecaptury.RemoteCapturyConfig.class)
public class CapturyStreamPacket0 extends Pointer {
    static { Loader.load(); }
    /** Default native constructor. */
    public CapturyStreamPacket0() { super((Pointer)null); allocate(); }
    /** Native array allocator. Access with {@link Pointer#position(long)}. */
    public CapturyStreamPacket0(long size) { super((Pointer)null); allocateArray(size); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public CapturyStreamPacket0(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(long size);
    @Override public CapturyStreamPacket0 position(long position) {
        return (CapturyStreamPacket0)super.position(position);
    }
    @Override public CapturyStreamPacket0 getPointer(long i) {
        return new CapturyStreamPacket0((Pointer)this).offsetAddress(i);
    }

	public native int type(); public native CapturyStreamPacket0 type(int setter);		// capturyStream
	public native int size(); public native CapturyStreamPacket0 size(int setter);		// size of full message including type and size

	public native int what(); public native CapturyStreamPacket0 what(int setter);		// CAPTURY_STREAM_POSES or CAPTURY_STREAM_NOTHING
}