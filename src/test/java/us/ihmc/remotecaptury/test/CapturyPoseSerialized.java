package us.ihmc.remotecaptury.test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.FloatBuffer;

import org.bytedeco.javacpp.FloatPointer;
import us.ihmc.remotecaptury.CapturyPose;
import us.ihmc.remotecaptury.CapturyTransform;

public class CapturyPoseSerialized extends CapturyPose implements java.io.Serializable {

   private static final long serialVersionUID = 1L;

   public CapturyPoseSerialized() {
      super();
   }
   public static CapturyPoseSerialized convertToSerializedPose(CapturyPose pose) {
      CapturyPoseSerialized serializedPose = new CapturyPoseSerialized();

      serializedPose.actor(pose.actor());
      serializedPose.timestamp(pose.timestamp());
      serializedPose.numTransforms(pose.numTransforms());

      // Copy the transforms
      CapturyTransform originalTransforms = pose.transforms();
      CapturyTransform serializedTransforms = new CapturyTransform(serializedPose.numTransforms());
      for (int i = 0; i < serializedPose.numTransforms(); i++) {
         serializedTransforms.position(i).put(originalTransforms.position(i));
         serializedTransforms.put(originalTransforms);
      }
      serializedPose.transforms(serializedTransforms);

      serializedPose.flags(pose.flags());
      serializedPose.numBlendShapes(pose.numBlendShapes());

      // Copy the blend shape activations
      FloatBuffer originalBlendShapes = pose.blendShapeActivations().asBuffer();
      FloatPointer serializedBlendShapes = new FloatPointer(originalBlendShapes.capacity());
      for (int i = 0; i < originalBlendShapes.capacity(); i++) {
         serializedBlendShapes.put(i, originalBlendShapes.get(i));
      }
      serializedPose.blendShapeActivations(serializedBlendShapes);

      return serializedPose;
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.defaultWriteObject();
      int numBlendShapes = numBlendShapes();
      out.writeInt(numBlendShapes);
      float[] blendShapesArray = new float[numBlendShapes];
      blendShapeActivations().get(blendShapesArray);
      for (float blendShape : blendShapesArray) {
         out.writeFloat(blendShape);
      }
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      int numBlendShapes = in.readInt();
      float[] blendShapesArray = new float[numBlendShapes];
      for (int i = 0; i < numBlendShapes; i++) {
         blendShapesArray[i] = in.readFloat();
      }
      FloatPointer floatPointer = new FloatPointer(blendShapesArray);
      blendShapeActivations(floatPointer);
   }
}
