// Targeted by JavaCPP version 1.5.10: DO NOT EDIT THIS FILE

package us.ihmc.remotecaptury;

import java.nio.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

import static us.ihmc.remotecaptury.global.remotecaptury.*;


// sent to server
@Properties(inherit = us.ihmc.remotecaptury.RemoteCapturyConfig.class)
public class CapturyRequestPacket extends Pointer {
    static { Loader.load(); }
    /** Default native constructor. */
    public CapturyRequestPacket() { super((Pointer)null); allocate(); }
    /** Native array allocator. Access with {@link Pointer#position(long)}. */
    public CapturyRequestPacket(long size) { super((Pointer)null); allocateArray(size); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public CapturyRequestPacket(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(long size);
    @Override public CapturyRequestPacket position(long position) {
        return (CapturyRequestPacket)super.position(position);
    }
    @Override public CapturyRequestPacket getPointer(long i) {
        return new CapturyRequestPacket((Pointer)this).offsetAddress(i);
    }

	public native int type(); public native CapturyRequestPacket type(int setter);		// from capturyActors, capturyCameras, capturyDaySessionShot, capturySetShot, capturyStartRecording, capturyStopRecording
	public native int size(); public native CapturyRequestPacket size(int setter);		// size of full message including type and size
}
