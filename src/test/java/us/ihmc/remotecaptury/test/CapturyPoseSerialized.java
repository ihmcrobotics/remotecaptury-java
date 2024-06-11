package us.ihmc.remotecaptury.test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.FloatBuffer;

import org.bytedeco.javacpp.FloatPointer;
import us.ihmc.remotecaptury.CapturyPose;
import us.ihmc.remotecaptury.CapturyTransform;
import us.ihmc.remotecaptury.library.RemoteCapturyNativeLibrary;

public class CapturyPoseSerialized extends CapturyPose implements java.io.Serializable {
   static{
      RemoteCapturyNativeLibrary.load();
   }
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
      CapturyTransform serializedTransforms = new CapturyTransform(serializedPose.numTransforms());
      for (int i = 0; i < serializedPose.numTransforms(); i++) {
         serializedTransforms.getPointer(i).translation().put(pose.transforms().getPointer(i).translation());
         serializedTransforms.getPointer(i).rotation().put(pose.transforms().getPointer(i).rotation());
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
      CapturyTransform transform = new CapturyTransform();
      for (int i = 0; i < numTransforms; i++) {
         transform.getPointer(i).put(transforms().getPointer(i));
         FloatBuffer translationBuffer = transform.translation().asBuffer();
         for (int j = 0; j < 3; j++) {
            out.writeDouble(translationBuffer.get(j));
         }
         FloatBuffer rotationBuffer = transform.rotation().asBuffer();
         for (int j = 0; j < 4; j++) {
            out.writeDouble(rotationBuffer.get(j));
         }
      }
      int numBlendShapes = numBlendShapes();
      out.writeInt(numBlendShapes);
      FloatBuffer blendShapesBuffer = blendShapeActivations().asBuffer();
      for (int i = 0; i < numBlendShapes; i++) {
         out.writeFloat(blendShapesBuffer.get(i));
      }
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      int numTransforms = in.readInt();
      CapturyTransform transforms = new CapturyTransform(numTransforms);
      CapturyTransform transform = new CapturyTransform();
      for (int i = 0; i < numTransforms; i++) {
         float[] translationArray = new float[3];
         for (int j = 0; j < 3; j++) {
            translationArray[j] = (float) in.readDouble();
         }
         FloatPointer translation = new FloatPointer(translationArray);

         float[] rotationArray = new float[4];
         for (int j = 0; j < 4; j++) {
            rotationArray[j] = (float) in.readDouble();
         }
         FloatPointer rotation = new FloatPointer(rotationArray);


         transform.translation(i, (int) translation.get());
         transform.rotation(i, (int) rotation.get());
      }
      transforms(transform);

      int numBlendShapes = in.readInt();
      float[] blendShapesArray = new float[numBlendShapes];
      for (int i = 0; i < numBlendShapes; i++) {
         blendShapesArray[i] = in.readFloat();
      }
      FloatPointer blendShapesPointer = new FloatPointer(blendShapesArray);
      blendShapeActivations(blendShapesPointer);
   }

}