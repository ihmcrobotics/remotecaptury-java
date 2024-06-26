// Targeted by JavaCPP version 1.5.10: DO NOT EDIT THIS FILE

package us.ihmc.remotecaptury;

import java.nio.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

import static us.ihmc.remotecaptury.global.remotecaptury.*;


@Properties(inherit = us.ihmc.remotecaptury.RemoteCapturyConfig.class)
public class CapturyARTag extends Pointer {
    static { Loader.load(); }
    /** Default native constructor. */
    public CapturyARTag() { super((Pointer)null); allocate(); }
    /** Native array allocator. Access with {@link Pointer#position(long)}. */
    public CapturyARTag(long size) { super((Pointer)null); allocateArray(size); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public CapturyARTag(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(long size);
    @Override public CapturyARTag position(long position) {
        return (CapturyARTag)super.position(position);
    }
    @Override public CapturyARTag getPointer(long i) {
        return new CapturyARTag((Pointer)this).offsetAddress(i);
    }

	public native int id(); public native CapturyARTag id(int setter);

	public native @ByRef CapturyTransform transform(); public native CapturyARTag transform(CapturyTransform setter);
}
