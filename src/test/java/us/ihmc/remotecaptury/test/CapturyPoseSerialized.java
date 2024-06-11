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
         CapturyTransform originalTransform = originalTransforms.position(i);
         CapturyTransform serializedTransform = serializedTransforms.position(i);
         serializedTransform.translation().put(originalTransform.translation(i));
         serializedTransform.rotation().put(originalTransform.rotation(i));
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
      int numTransforms = numTransforms();
      out.writeInt(numTransforms);
      for (int i = 0; i < numTransforms; i++) {
         CapturyTransform transform = transforms().position(i);
         float[] translationArray = transform.translation().asBuffer().array();
         for (double d : translationArray) {
            out.writeDouble(d);
         }
         float[] rotationArray = transform.rotation().asBuffer().array();
         for (double d : rotationArray) {
            out.writeDouble(d);
         }
      }
      int numBlendShapes = numBlendShapes();
      out.writeInt(numBlendShapes);
      float[] blendShapesArray = new float[numBlendShapes];
      blendShapeActivations().get(blendShapesArray);
      for (float blendShape : blendShapesArray) {
         out.writeFloat(blendShape);
      }
   }
   private float[] toFloatArray(double[] doubleArray) {
      float[] floatArray = new float[doubleArray.length];
      for (int i = 0; i < doubleArray.length; i++) {
         floatArray[i] = (float) doubleArray[i];
      }
      return floatArray;
   }
   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      int numTransforms = in.readInt();
      CapturyTransform transforms = new CapturyTransform(numTransforms);
      for (int i = 0; i < numTransforms; i++) {
         double[] translationArray = new double[3];
         for (int j = 0; j < 3; j++) {
            translationArray[j] = in.readDouble();
         }
         FloatPointer translation = new FloatPointer(toFloatArray(translationArray));
         double[] rotationArray = new double[4];
         for (int j = 0; j < 4; j++) {
            rotationArray[j] = in.readDouble();
         }
         FloatPointer rotation = new FloatPointer(toFloatArray(rotationArray));
         transforms.position(i).translation((int) translation.get());
         transforms.position(i).rotation((int) rotation.get());
      }
      transforms(transforms);
      int numBlendShapes = in.readInt();
      float[] blendShapesArray = new float[numBlendShapes];
      for (int i = 0; i < numBlendShapes; i++) {
         blendShapesArray[i] = in.readFloat();
      }
      FloatPointer floatPointer = new FloatPointer(blendShapesArray);
      blendShapeActivations(floatPointer);
   }
}