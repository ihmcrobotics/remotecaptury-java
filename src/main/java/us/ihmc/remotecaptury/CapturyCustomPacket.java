// Targeted by JavaCPP version 1.5.10: DO NOT EDIT THIS FILE

package us.ihmc.remotecaptury;

import java.nio.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

import static us.ihmc.remotecaptury.global.remotecaptury.*;


// sent in both directions
// a CapturyRequestPacket = capturyCustomAck is always sent in reply
@Properties(inherit = us.ihmc.remotecaptury.RemoteCapturyConfig.class)
public class CapturyCustomPacket extends Pointer {
    static { Loader.load(); }
    /** Default native constructor. */
    public CapturyCustomPacket() { super((Pointer)null); allocate(); }
    /** Native array allocator. Access with {@link Pointer#position(long)}. */
    public CapturyCustomPacket(long size) { super((Pointer)null); allocateArray(size); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public CapturyCustomPacket(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(long size);
    @Override public CapturyCustomPacket position(long position) {
        return (CapturyCustomPacket)super.position(position);
    }
    @Override public CapturyCustomPacket getPointer(long i) {
        return new CapturyCustomPacket((Pointer)this).offsetAddress(i);
    }

	public native int type(); public native CapturyCustomPacket type(int setter);	// capturyCustom
	public native int size(); public native CapturyCustomPacket size(int setter);

	public native @Cast("char") byte name(int i); public native CapturyCustomPacket name(int i, byte setter);
	@MemberGetter public native @Cast("char*") BytePointer name();
	public native @Cast("char") byte data(int i); public native CapturyCustomPacket data(int i, byte setter);
	@MemberGetter public native @Cast("char*") BytePointer data();
}
