package us.ihmc.remotecaptury;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CapturyActorSerialized implements java.io.Serializable {

   private static final long serialVersionUID = 1L;

   private String name;
   private int id;
   private int numJoints;
   private CapturyJoint joints;
   private int numBlobs;
   private CapturyBlob blobs;
   private int numBlendShapes;
   private CapturyBlendShape blendShapes;

   public CapturyActorSerialized() {
   }

   public CapturyActorSerialized(CapturyActor actor) {
      this.name = actor.name().getString();
      this.id = actor.id();
      this.numJoints = actor.numJoints();
      this.joints = new CapturyJoint(numJoints);
      for (int i = 0; i < numJoints; i++) {
         this.joints.getPointer(i).put(actor.joints().getPointer(i));
      }
      this.numBlobs = actor.numBlobs();
      this.blobs = new CapturyBlob(numBlobs);
      for (int i = 0; i < numBlobs; i++) {
         this.blobs.getPointer(i).put(actor.blobs().getPointer(i));
      }
      this.numBlendShapes = actor.numBlendShapes();
      this.blendShapes = new CapturyBlendShape(numBlendShapes);
      for (int i = 0; i < numBlendShapes; i++) {
         this.blendShapes.getPointer(i).put(actor.blendShapes().getPointer(i));
      }
   }

   public String name() {
      return name;
   }

   public int id() {
      return id;
   }

   public int numJoints() {
      return numJoints;
   }

   public CapturyJoint joints() {
      return joints;
   }

   public int numBlobs() {
      return numBlobs;
   }

   public CapturyBlob blobs() {
      return blobs;
   }

   public int numBlendShapes() {
      return numBlendShapes;
   }

   public CapturyBlendShape blendShapes() {
      return blendShapes;
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.writeUTF(name);
      out.writeInt(id);
      out.writeInt(numJoints);
      for (int i = 0; i < numJoints; i++) {
         CapturyJoint joint = joints().getPointer(i);
         out.writeUTF(joint.name().getString());
         out.writeInt(joint.parent());
         out.writeFloat(joint.offset(0));
         out.writeFloat(joint.offset(1));
         out.writeFloat(joint.offset(2));
         out.writeFloat(joint.orientation(0));
         out.writeFloat(joint.orientation(1));
         out.writeFloat(joint.orientation(2));
         out.writeFloat(joint.scale(0));
         out.writeFloat(joint.scale(1));
         out.writeFloat(joint.scale(2));
      }
      out.writeInt(numBlobs);
      for (int i = 0; i < numBlobs; i++) {
         CapturyBlob blob = blobs().getPointer(i);
         out.writeFloat(blob.position(0).position());
         out.writeFloat(blob.position(1).position());
         out.writeFloat(blob.position(2).position());
         out.writeFloat(blob.size());
      }
      out.writeInt(numBlendShapes);
      for (int i = 0; i < numBlendShapes; i++) {
         CapturyBlendShape blendShape = blendShapes().getPointer(i);
         out.writeUTF(blendShape.name().getString());
      }
   }

   private void readObject(ObjectInputStream in) throws IOException {
      name = in.readUTF();
      id = in.readInt();
      numJoints = in.readInt();
      joints = new CapturyJoint(numJoints);
      for (int i = 0; i < numJoints; i++) {
         CapturyJoint joint = joints.getPointer(i);
         joint.name().put(in.readUTF().getBytes());
         joint.parent(in.readInt());
         float[] offset = new float[3];
         for (int j = 0; j < 3; j++) {
            offset[j] = in.readFloat();
         }
         joint.offset().put(offset);
         float[] orientation = new float[3];
         for (int j = 0; j < 3; j++) {
            orientation[j] = in.readFloat();
         }
         joint.orientation().put(orientation);
         float[] scale = new float[3];
         for (int j = 0; j < 3; j++) {
            scale[j] = in.readFloat();
         }
         joint.scale().put(scale);
      }
      numBlobs = in.readInt();
      blobs = new CapturyBlob(numBlobs);
      for (int i = 0; i < numBlobs; i++) {
         CapturyBlob blob = blobs.getPointer(i);
         float[] position = new float[3];
         for (int j = 0; j < 3; j++) {
            position[j] = in.readFloat();
         }
         blob.size(in.readFloat());
      }
      numBlendShapes = in.readInt();
      blendShapes = new CapturyBlendShape(numBlendShapes);
      for (int i = 0; i < numBlendShapes; i++) {
         CapturyBlendShape blendShape = blendShapes.getPointer(i);
         blendShape.name().put(in.readUTF().getBytes());
      }
   }
}