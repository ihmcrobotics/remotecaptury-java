// Targeted by JavaCPP version 1.5.10: DO NOT EDIT THIS FILE

package us.ihmc.remotecaptury;

import java.nio.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

import static us.ihmc.remotecaptury.global.remotecaptury.*;
	// for tracing function calls

// sent to server
@Properties(inherit = us.ihmc.remotecaptury.RemoteCapturyConfig.class)
public class CapturyLogPacket extends Pointer {
    static { Loader.load(); }
    /** Default native constructor. */
    public CapturyLogPacket() { super((Pointer)null); allocate(); }
    /** Native array allocator. Access with {@link Pointer#position(long)}. */
    public CapturyLogPacket(long size) { super((Pointer)null); allocateArray(size); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public CapturyLogPacket(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(long size);
    @Override public CapturyLogPacket position(long position) {
        return (CapturyLogPacket)super.position(position);
    }
    @Override public CapturyLogPacket getPointer(long i) {
        return new CapturyLogPacket((Pointer)this).offsetAddress(i);
    }

	public native int type(); public native CapturyLogPacket type(int setter);	// capturyMessage
	public native int size(); public native CapturyLogPacket size(int setter);	// size of full message including type and size

	public native @Cast("uint8_t") byte logLevel(); public native CapturyLogPacket logLevel(byte setter);
	public native @Cast("char") byte message(int i); public native CapturyLogPacket message(int i, byte setter);
	@MemberGetter public native @Cast("char*") BytePointer message();
}