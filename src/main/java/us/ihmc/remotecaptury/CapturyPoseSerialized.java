package us.ihmc.remotecaptury;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.FloatBuffer;

public class CapturyPoseSerialized implements java.io.Serializable {

   private static final long serialVersionUID = 1L;

   private int actor;
   private long timestamp;
   private int numTransforms;
   private CapturyTransform transforms;
   private int flags;
   private int numBlendShapes;
   private float[] blendShapeActivations;

   public CapturyPoseSerialized() {
   }

   public CapturyPoseSerialized(CapturyPose pose) {
      this.actor = pose.actor();
      this.timestamp = pose.timestamp();
      this.numTransforms = pose.numTransforms();
      this.transforms = new CapturyTransform(numTransforms);
      for (int i = 0; i < numTransforms; i++) {
         this.transforms.getPointer(i).put(pose.transforms().getPointer(i));
      }
      this.flags = pose.flags();
      this.numBlendShapes = pose.numBlendShapes();
      this.blendShapeActivations = new float[numBlendShapes];
      FloatBuffer buffer = pose.blendShapeActivations().asBuffer();
      for (int i = 0; i < numBlendShapes; i++) {
         this.blendShapeActivations[i] = buffer.get(i);
      }
   }

   public int actor() {
      return actor;
   }

   public long timestamp() {
      return timestamp;
   }

   public int numTransforms() {
      return numTransforms;
   }

   public CapturyTransform transforms() {
      return transforms;
   }

   public int flags() {
      return flags;
   }

   public int numBlendShapes() {
      return numBlendShapes;
   }

   public float[] blendShapeActivations() {
      return blendShapeActivations;
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.writeInt(actor);
      out.writeLong(timestamp);
      out.writeInt(numTransforms);
      for (int i = 0; i < numTransforms; i++) {
         CapturyTransform transform = transforms().getPointer(i);
         out.writeFloat(transform.translation().get(0));
         out.writeFloat(transform.translation().get(1));
         out.writeFloat(transform.translation().get(2));
         out.writeFloat(transform.rotation().get(0));
         out.writeFloat(transform.rotation().get(1));
         out.writeFloat(transform.rotation().get(2));
      }
      out.writeInt(flags);
      out.writeInt(numBlendShapes);
      for (int i = 0; i < numBlendShapes; i++) {
         out.writeFloat(blendShapeActivations[i]);
      }
   }

   private void readObject(ObjectInputStream in) throws IOException {
      actor = in.readInt();
      timestamp = in.readLong();
      numTransforms = in.readInt();
      transforms = new CapturyTransform(numTransforms);
      for (int i = 0; i < numTransforms; i++) {
         float[] translation = new float[3];
         float[] rotation = new float[3];
         for (int j = 0; j < 3; j++) {
            translation[j] = in.readFloat();
         }
         for(int k = 0; k < 3; k++)
         {
            rotation[k] = in.readFloat();
         }
         transforms.getPointer(i).translation().put(translation);
         transforms.getPointer(i).rotation().put(rotation);
      }
      flags = in.readInt();
      numBlendShapes = in.readInt();
      blendShapeActivations = new float[numBlendShapes];
      for (int i = 0; i < numBlendShapes; i++) {
         blendShapeActivations[i] = in.readFloat();
      }
   }
}