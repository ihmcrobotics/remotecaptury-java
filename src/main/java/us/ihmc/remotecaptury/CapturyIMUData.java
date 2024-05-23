// Targeted by JavaCPP version 1.5.10: DO NOT EDIT THIS FILE

package us.ihmc.remotecaptury;

import java.nio.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

import static us.ihmc.remotecaptury.global.remotecaptury.*;


// sent to client
// as a reply to CapturyStreamPacket
@Properties(inherit = us.ihmc.remotecaptury.RemoteCapturyConfig.class)
public class CapturyIMUData extends Pointer {
    static { Loader.load(); }
    /** Default native constructor. */
    public CapturyIMUData() { super((Pointer)null); allocate(); }
    /** Native array allocator. Access with {@link Pointer#position(long)}. */
    public CapturyIMUData(long size) { super((Pointer)null); allocateArray(size); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public CapturyIMUData(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(long size);
    @Override public CapturyIMUData position(long position) {
        return (CapturyIMUData)super.position(position);
    }
    @Override public CapturyIMUData getPointer(long i) {
        return new CapturyIMUData((Pointer)this).offsetAddress(i);
    }

	public native int type(); public native CapturyIMUData type(int setter);	// capturyIMU
	public native int size(); public native CapturyIMUData size(int setter);	// size of full message including type and size

	public native @Cast("uint8_t") byte numIMUs(); public native CapturyIMUData numIMUs(byte setter);
	public native float eulerAngles(int i); public native CapturyIMUData eulerAngles(int i, float setter);
	@MemberGetter public native FloatPointer eulerAngles(); // 3x numIMUs floats
}