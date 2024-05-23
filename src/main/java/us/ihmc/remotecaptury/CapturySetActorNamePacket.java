// Targeted by JavaCPP version 1.5.10: DO NOT EDIT THIS FILE

package us.ihmc.remotecaptury;

import java.nio.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

import static us.ihmc.remotecaptury.global.remotecaptury.*;


@Properties(inherit = us.ihmc.remotecaptury.RemoteCapturyConfig.class)
public class CapturySetActorNamePacket extends Pointer {
    static { Loader.load(); }
    /** Default native constructor. */
    public CapturySetActorNamePacket() { super((Pointer)null); allocate(); }
    /** Native array allocator. Access with {@link Pointer#position(long)}. */
    public CapturySetActorNamePacket(long size) { super((Pointer)null); allocateArray(size); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public CapturySetActorNamePacket(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(long size);
    @Override public CapturySetActorNamePacket position(long position) {
        return (CapturySetActorNamePacket)super.position(position);
    }
    @Override public CapturySetActorNamePacket getPointer(long i) {
        return new CapturySetActorNamePacket((Pointer)this).offsetAddress(i);
    }

	public native int type(); public native CapturySetActorNamePacket type(int setter); // capturySetActorName
	public native int size(); public native CapturySetActorNamePacket size(int setter);
	public native int actor(); public native CapturySetActorNamePacket actor(int setter);
	public native @Cast("char") byte name(int i); public native CapturySetActorNamePacket name(int i, byte setter);
	@MemberGetter public native @Cast("char*") BytePointer name();
}
