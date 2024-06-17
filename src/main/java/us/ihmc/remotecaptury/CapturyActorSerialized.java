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
         // implement writing joint data
      }
      out.writeInt(numBlobs);
      for (int i = 0; i < numBlobs; i++) {
         // implement writing blob data
      }
      out.writeInt(numBlendShapes);
      for (int i = 0; i < numBlendShapes; i++) {
         // implement writing blend shape data
      }
   }

   private void readObject(ObjectInputStream in) throws IOException {
      name = in.readUTF();
      id = in.readInt();
      numJoints = in.readInt();
      joints = new CapturyJoint(numJoints);
      for (int i = 0; i < numJoints; i++) {
         // implement reading joint data
      }
      numBlobs = in.readInt();
      blobs = new CapturyBlob(numBlobs);
      for (int i = 0; i < numBlobs; i++) {
         // implement reading blob data
      }
      numBlendShapes = in.readInt();
      blendShapes = new CapturyBlendShape(numBlendShapes);
      for (int i = 0; i < numBlendShapes; i++) {
         // implement reading blend shape data
      }
   }
}