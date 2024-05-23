// Targeted by JavaCPP version 1.5.10: DO NOT EDIT THIS FILE

package us.ihmc.remotecaptury.global;

import us.ihmc.remotecaptury.*;

import java.nio.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

public class remotecaptury extends us.ihmc.remotecaptury.RemoteCapturyConfig {
    static { Loader.load(); }

// Parsed from captury/PublicStructs.h

// #pragma once

// #include <stdint.h>

// #pragma pack(push, 1)
// Targeting ..\CapturyJoint.java


// Targeting ..\CapturyBlendShape.java


// Targeting ..\CapturyBlob.java


// Targeting ..\CapturyActor.java


// Targeting ..\CapturyTransform.java



/** enum CapturyPoseFlags */
public static final int CAPTURY_LEFT_FOOT_ON_GROUND = 0x01, CAPTURY_RIGHT_FOOT_ON_GROUND = 0x02;
// Targeting ..\CapturyPose.java


// Targeting ..\CapturyIMUPose.java


// Targeting ..\CapturyConstraint.java


// Targeting ..\CapturyCamera.java


// Targeting ..\CapturyImage.java


// Targeting ..\CapturyDepthImage.java


// Targeting ..\CapturyARTag.java


// Targeting ..\CapturyCornerDetection.java


// Targeting ..\CapturyLatencyInfo.java



// #pragma pack(pop)


// Parsed from RemoteCaptury.h

// #pragma once

// #include <stdint.h>
// #include "captury/PublicStructs.h"

// #ifdef WIN32
// #define CAPTURY_DLL_EXPORT __declspec(dllexport)
// #else
// #define CAPTURY_DLL_EXPORT
// #endif

// #ifdef __cplusplus
// #endif




// returns 1 if successful, 0 otherwise
// the default port is 2101
public static native int Captury_connect(@Cast("const char*") BytePointer ip, @Cast("unsigned short") short port);
public static native int Captury_connect(String ip, @Cast("unsigned short") short port);
// in case you need to set the local port because of firewalls, etc.
// use 0 for localPort and localStreamPort if you don't care
// if async != 0, the function will return immediately and perform the connection attempt asynchronously
public static native int Captury_connect2(@Cast("const char*") BytePointer ip, @Cast("unsigned short") short port, @Cast("unsigned short") short localPort, @Cast("unsigned short") short localStreamPort, int async);
public static native int Captury_connect2(String ip, @Cast("unsigned short") short port, @Cast("unsigned short") short localPort, @Cast("unsigned short") short localStreamPort, int async);

// returns 1 if successful, 0 otherwise
public static native int Captury_disconnect();

public static final int CAPTURY_DISCONNECTED =			0; // not connected
public static final int CAPTURY_CONNECTING =			1; // trying to connect
public static final int CAPTURY_CONNECTED =			2; // not connected
// returns one of the above
public static native int Captury_getConnectionStatus();






// returns the number of actors
// on exit *cameras points to an array of CapturyCamera
// the array is owned by the library - do not free
public static native int Captury_getActors(@Cast("const CapturyActor**") PointerPointer actors);
public static native int Captury_getActors(@Const @ByPtrPtr CapturyActor actors);

// returns the actor or NULL if it is not known
// the struct is owned by the library - do not free
public static native @Const CapturyActor Captury_getActor(int actorId);

// returns the number of cameras
// on exit *cameras points to an array of CapturyCamera
// the array is owned by the library - do not free
public static native int Captury_getCameras(@Cast("const CapturyCamera**") PointerPointer cameras);
public static native int Captury_getCameras(@Const @ByPtrPtr CapturyCamera cameras);


public static final int CAPTURY_LEFT_KNEE_FLEXION_EXTENSION =		1;
public static final int CAPTURY_LEFT_KNEE_VARUS_VALGUS =			2;
public static final int CAPTURY_LEFT_KNEE_ROTATION =			3; // both internal and external
public static final int CAPTURY_LEFT_HIP_FLEXION_EXTENSION =		4;
public static final int CAPTURY_LEFT_HIP_ABADDUCTION =			5; // both ab- and adduction
public static final int CAPTURY_LEFT_HIP_ROTATION =			6; // both internal and external
public static final int CAPTURY_LEFT_ANKLE_FLEXION_EXTENSION =		7;
public static final int CAPTURY_LEFT_ANKLE_PRONATION_SUPINATION =		8;
public static final int CAPTURY_LEFT_ANKLE_ROTATION =			9;
public static final int CAPTURY_LEFT_SHOULDER_FLEXION_EXTENSION =		10;
public static final int CAPTURY_LEFT_SHOULDER_TOTAL_FLEXION =		11;
public static final int CAPTURY_LEFT_SHOULDER_ABADDUCTION =		12; // both ab- and adduction
public static final int CAPTURY_LEFT_SHOULDER_ROTATION =			13;
public static final int CAPTURY_LEFT_ELBOW_FLEXION_EXTENSION =		14;
public static final int CAPTURY_LEFT_FOREARM_PRONATION_SUPINATION =	15;
public static final int CAPTURY_LEFT_WRIST_FLEXION_EXTENSION =		16;
public static final int CAPTURY_LEFT_WRIST_RADIAL_ULNAR_DEVIATION =	17;
public static final int CAPTURY_RIGHT_KNEE_FLEXION_EXTENSION =		18;
public static final int CAPTURY_RIGHT_KNEE_VARUS_VALGUS =			19;
public static final int CAPTURY_RIGHT_KNEE_ROTATION =			20; // both internal and external
public static final int CAPTURY_RIGHT_HIP_FLEXION_EXTENSION =		21;
public static final int CAPTURY_RIGHT_HIP_ABADDUCTION =			22; // both ab- and adduction
public static final int CAPTURY_RIGHT_HIP_ROTATION =			23; // both internal and external
public static final int CAPTURY_RIGHT_ANKLE_FLEXION_EXTENSION =		24;
public static final int CAPTURY_RIGHT_ANKLE_PRONATION_SUPINATION =	25;
public static final int CAPTURY_RIGHT_ANKLE_ROTATION =			26;
public static final int CAPTURY_RIGHT_SHOULDER_FLEXION_EXTENSION =	27;
public static final int CAPTURY_RIGHT_SHOULDER_TOTAL_FLEXION =		28;
public static final int CAPTURY_RIGHT_SHOULDER_ABADDUCTION =		29; // both ab- and adduction
public static final int CAPTURY_RIGHT_SHOULDER_ROTATION =			30;
public static final int CAPTURY_RIGHT_ELBOW_FLEXION_EXTENSION =		31;
public static final int CAPTURY_RIGHT_FOREARM_PRONATION_SUPINATION =	32;
public static final int CAPTURY_RIGHT_WRIST_FLEXION_EXTENSION =		33;
public static final int CAPTURY_RIGHT_WRIST_RADIAL_ULNAR_DEVIATION =	34;
public static final int CAPTURY_NECK_FLEXION_EXTENSION =			35;
public static final int CAPTURY_NECK_ROTATION =				36;
public static final int CAPTURY_NECK_LATERAL_BENDING =			37;
public static final int CAPTURY_CENTER_OF_GRAVITY_X =			38;
public static final int CAPTURY_CENTER_OF_GRAVITY_Y =			39;
public static final int CAPTURY_CENTER_OF_GRAVITY_Z =			40;
public static final int CAPTURY_HEAD_ROTATION =				41;
public static final int CAPTURY_TORSO_ROTATION =				42;
public static final int CAPTURY_TORSO_INCLINATION =			43;
public static final int CAPTURY_HEAD_INCLINATION =			44;
public static final int CAPTURY_TORSO_FLEXION =				45;



public static final int CAPTURY_STREAM_NOTHING =		0x0000;
public static final int CAPTURY_STREAM_POSES =		0x0001;
public static final int CAPTURY_STREAM_GLOBAL_POSES =	0x0001;
public static final int CAPTURY_STREAM_LOCAL_POSES =	0x0003;
public static final int CAPTURY_STREAM_ARTAGS =		0x0004;
public static final int CAPTURY_STREAM_IMAGES =		0x0008;
public static final int CAPTURY_STREAM_META_DATA =	0x0010;	// only valid when streaming poses
public static final int CAPTURY_STREAM_IMU_DATA =		0x0020;
public static final int CAPTURY_STREAM_LATENCY_INFO =	0x0040;
public static final int CAPTURY_STREAM_FOOT_CONTACT =	0x0080;
public static final int CAPTURY_STREAM_COMPRESSED =	0x0100;
public static final int CAPTURY_STREAM_ANGLES =		0x0200;
public static final int CAPTURY_STREAM_SCALES =		0x0400;
public static final int CAPTURY_STREAM_BLENDSHAPES =	0x0800;

// returns 1 if successful, 0 otherwise
public static native int Captury_startStreaming(int what);

// if you want to stream images use this function rather than Captury_startStreaming()
// returns 1 if successfull otherwise 0
public static native int Captury_startStreamingImages(int what, int cameraId);

// if you want to stream images use this function rather than Captury_startStreaming()
// returns 1 if successfull otherwise 0
public static native int Captury_startStreamingImagesAndAngles(int what, int cameraId, int numAngles, @Cast("uint16_t*") ShortPointer angles);
public static native int Captury_startStreamingImagesAndAngles(int what, int cameraId, int numAngles, @Cast("uint16_t*") ShortBuffer angles);
public static native int Captury_startStreamingImagesAndAngles(int what, int cameraId, int numAngles, @Cast("uint16_t*") short[] angles);


// equivalent to Captury_startStreaming(CAPTURY_STREAM_NOTHING)
// returns 1 if successful, 0 otherwise
public static native int Captury_stopStreaming(int _wait/*=1*/);
public static native int Captury_stopStreaming();

// #pragma pack(push, 1)
// Targeting ..\CapturyAngleData.java


// #pragma pack(pop)

// fills the pose with the current pose for the given actor
// returns the current pose. Captury_freePose() after use
public static native CapturyPose Captury_getCurrentPoseForActor(int actorId);
public static native CapturyPose Captury_getCurrentPoseAndTrackingConsistencyForActor(int actorId, IntPointer tc);
public static native CapturyPose Captury_getCurrentPoseAndTrackingConsistencyForActor(int actorId, IntBuffer tc);
public static native CapturyPose Captury_getCurrentPoseAndTrackingConsistencyForActor(int actorId, int[] tc);
public static native CapturyPose Captury_getCurrentPose(int actorId);
public static native CapturyPose Captury_getCurrentPoseAndTrackingConsistency(int actorId, IntPointer tc);
public static native CapturyPose Captury_getCurrentPoseAndTrackingConsistency(int actorId, IntBuffer tc);
public static native CapturyPose Captury_getCurrentPoseAndTrackingConsistency(int actorId, int[] tc);
// *numAngles = number of angles returned
public static native CapturyAngleData Captury_getCurrentAngles(int actorId, IntPointer numAngles);
public static native CapturyAngleData Captury_getCurrentAngles(int actorId, IntBuffer numAngles);
public static native CapturyAngleData Captury_getCurrentAngles(int actorId, int[] numAngles);

// simple function for releasing memory of a pose
public static native void Captury_freePose(CapturyPose pose);
// Targeting ..\CapturyNewPoseCallback.java



// register callback that will be called when a new pose is received
// the callback will be run in a different thread than the main application
// try to be quick in the callback
// returns 1 if successful otherwise 0
public static native int Captury_registerNewPoseCallback(CapturyNewPoseCallback callback);
// Targeting ..\CapturyNewAnglesCallback.java



// register callback that will be called when new physiological angle data is received
// the callback will be run in a different thread than the main application
// try to be quick in the callback
// returns 1 if successful otherwise 0
public static native int Captury_registerNewAnglesCallback(CapturyNewAnglesCallback callback);

/** enum CapturyActorStatus */
public static final int ACTOR_SCALING = 0, ACTOR_TRACKING = 1, ACTOR_STOPPED = 2, ACTOR_DELETED = 3, ACTOR_UNKNOWN = 4;

// Targeting ..\CapturyActorChangedCallback.java


// returns CapturyActorStatus if the actorId is not known returns ACTOR_UNKNOWN
// this retrieves the local status. it causes no network traffic and should be fast.
public static native int Captury_getActorStatus(int actorId);

// register callback that will be called when a new actor is found or
// the status of an existing actor changes
// status can be one of CapturyActorStatus
// returns 1 if successful otherwise 0
public static native int Captury_registerActorChangedCallback(CapturyActorChangedCallback callback);
// Targeting ..\CapturyARTagCallback.java



// register callback that will be called when an artag is detected
// pass NULL if you want to deregister the callback
// returns 1 if successful otherwise 0
public static native int Captury_registerARTagCallback(CapturyARTagCallback callback);

// returns an array of artags followed by one where the id is -1
// Captury_freeARTags() after use
public static native CapturyARTag Captury_getCurrentARTags();

public static native void Captury_freeARTags(CapturyARTag artags);
// Targeting ..\CapturyImageCallback.java



// register callback that will be called when a new frame was streamed from this particular camera
// pass NULL to deregister
// returns 1 if successfull otherwise 0
public static native int Captury_registerImageStreamingCallback(CapturyImageCallback callback);

// may return NULL if no image has been received yet
// use Captury_freeImage to free after use


// requests an update of the texture for the given actor. non-blocking
// returns 1 if successful otherwise 0
public static native int Captury_requestTexture(int actorId);

// returns the timestamp of the constraint or 0
public static native @Cast("uint64_t") long Captury_getMarkerTransform(int actorId, int joint, CapturyTransform trafo);

// get the scaling status (0 - 100)
public static native int Captury_getScalingProgress(int actorId);

// get the tracking quality (0 - 100)
public static native int Captury_getTrackingQuality(int actorId);

// change the name of the actor
public static native int Captury_setActorName(int actorId, @Cast("const char*") BytePointer name);
public static native int Captury_setActorName(int actorId, String name);

// returns a texture image of the specified actor. free after use with Captury_freeImage().
public static native CapturyImage Captury_getTexture(int actorId);

// simple function for releasing memory of a pose
public static native void Captury_freeImage(CapturyImage image);

// synchronizes time with Captury Live
// this function should be called once before calling Captury_getTime()
// returns the current time in microseconds
public static native @Cast("uint64_t") long Captury_synchronizeTime();

// start a thread that continuously synchronizes the time with Captury Live
// if this is running it is not necessary to call Captury_synchronizeTime()
public static native void Captury_startTimeSynchronizationLoop();

// returns the current time as measured by Captury Live in microseconds
public static native @Cast("uint64_t") long Captury_getTime();

// returns the difference between the local and the remote time in microseconds
// offset = CapturyLive.time - local.time
public static native @Cast("int64_t") long Captury_getTimeOffset();


// get the last error message
public static native @Cast("char*") BytePointer Captury_getLastErrorMessage();
public static native void Captury_freeErrorMessage(@Cast("char*") BytePointer msg);
public static native void Captury_freeErrorMessage(@Cast("char*") ByteBuffer msg);
public static native void Captury_freeErrorMessage(@Cast("char*") byte[] msg);



// tries to snap an actor at the specified location
// x and z are in mm
// heading is in degrees measured from the x-axis around the y-axis (270 is facing the z-axis)
// use a value larger than 360 to indicate that heading is not known
// poll Captury_getActors to get the new actor id
// returns 1 if the request was successfully received
public static native int Captury_snapActor(float x, float z, float heading);

// tries to snap an actor at the specified location just like Captury_snapActor()
// the additional parameters allow specifying which skeleton should be snapped (the name should match the name in the drop down)
// snapMethod should be one of CapturySnapMethod
// if quickScaling != 0 snapping uses only a single frame
// returns 1 on success, 0 otherwise
/** enum CapturySnapMethod */
public static final int SNAP_BACKGROUND_LOCAL = 0, SNAP_BACKGROUND_GLOBAL = 1, SNAP_BODYPARTS_LOCAL = 2, SNAP_BODYPARTS_GLOBAL = 3, SNAP_BODYPARTS_JOINTS = 4, SNAP_DEFAULT = 5;
public static native int Captury_snapActorEx(float x, float z, float radius, float heading, @Cast("const char*") BytePointer skeletonName, int snapMethod, int quickScaling);
public static native int Captury_snapActorEx(float x, float z, float radius, float heading, String skeletonName, int snapMethod, int quickScaling);

// (re-)start tracking the actor at the given location
// x and z are in mm
// heading is in degrees measured from the x-axis around the y-axis (270 is facing the z-axis)
// use a value larger than 360 to indicate that heading is not known
// returns 1 on success, 0 otherwise
public static native int Captury_startTracking(int actorId, float x, float z, float heading);

// stops tracking the specified actor
// returns 1 on success, 0 otherwise
public static native int Captury_stopTracking(int actorId);

// stops tracking the actor and deletes the corresponding internal data in CapturyLive
// returns 1 on success, 0 otherwise
public static native int Captury_deleteActor(int actorId);

// rescale actor
// returns 1 on success, 0 otherwise
public static native int Captury_rescaleActor(int actorId);

// recolor actor
// returns 1 on success, 0 otherwise
public static native int Captury_recolorActor(int actorId);

// recolor actor
// returns 1 on success, 0 otherwise
public static native int Captury_updateActorColors(int actorId);

public static final int CAPTURY_CONSTRAINT_HALF_PLANE =	1;
public static final int CAPTURY_CONSTRAINT_ROTATION =	2;
public static final int CAPTURY_CONSTRAINT_FIXED_AXIS =	4;
public static final int CAPTURY_CONSTRAINT_DISTANCE =	8;
public static final int CAPTURY_CONSTRAINT_OFFSET =	16;

// constrain point attached to joint to stay on one side of the half plane defined by normal, offset
// weight should nornally be 1
// returns 1 if successful, 0 otherwise
public static native int Captury_setHalfplaneConstraint(int actorId, int jointIndex, FloatPointer originOffset, FloatPointer normal, float offset, @Cast("uint64_t") long timestamp, float weight);
public static native int Captury_setHalfplaneConstraint(int actorId, int jointIndex, FloatBuffer originOffset, FloatBuffer normal, float offset, @Cast("uint64_t") long timestamp, float weight);
public static native int Captury_setHalfplaneConstraint(int actorId, int jointIndex, float[] originOffset, float[] normal, float offset, @Cast("uint64_t") long timestamp, float weight);

// constrain bone to point in specific direction given by x,y,z Euler angles in *rotation
// weight should nornally be 1
// returns 1 if successful, 0 otherwise
public static native int Captury_setRotationConstraint(int actorId, int jointIndex, FloatPointer rotation, @Cast("uint64_t") long timestamp, float weight);
public static native int Captury_setRotationConstraint(int actorId, int jointIndex, FloatBuffer rotation, @Cast("uint64_t") long timestamp, float weight);
public static native int Captury_setRotationConstraint(int actorId, int jointIndex, float[] rotation, @Cast("uint64_t") long timestamp, float weight);

// constrain axis attached to joint to point in the direction of targetAxis
// unlike the rotation constraint this constraint allows the bone to rotate around the targetAxis
// weight should nornally be 1
// returns 1 if successful, 0 otherwise
public static native int Captury_setFixedAxisConstraint(int actorId, int jointIndex, FloatPointer axis, FloatPointer targetAxis, @Cast("uint64_t") long timestamp, float weight);
public static native int Captury_setFixedAxisConstraint(int actorId, int jointIndex, FloatBuffer axis, FloatBuffer targetAxis, @Cast("uint64_t") long timestamp, float weight);
public static native int Captury_setFixedAxisConstraint(int actorId, int jointIndex, float[] axis, float[] targetAxis, @Cast("uint64_t") long timestamp, float weight);

// constrain a point attached to a joint of originActorId to maintain an offset to a point attached to a joint on targetActorId
// the offset is specified in local coordinates of the origin actor
// this constraint could for example be used to constrain the hands of a cyclist to maintain their relative position on the handle bars
// originActor and targetActor are allowed to be identical
// weight should nornally be 1
// returns 1 if successful, 0 otherwise
public static native int Captury_setOffsetConstraint(int originActorId, int originJointIndex, FloatPointer originOffset, int targetActorId, int targetJointIndex, FloatPointer targetOffset, FloatPointer offset, @Cast("uint64_t") long timestamp, float weight);
public static native int Captury_setOffsetConstraint(int originActorId, int originJointIndex, FloatBuffer originOffset, int targetActorId, int targetJointIndex, FloatBuffer targetOffset, FloatBuffer offset, @Cast("uint64_t") long timestamp, float weight);
public static native int Captury_setOffsetConstraint(int originActorId, int originJointIndex, float[] originOffset, int targetActorId, int targetJointIndex, float[] targetOffset, float[] offset, @Cast("uint64_t") long timestamp, float weight);

// constrain a point attached to a joint of originActorId to maintain a given distance to a point attached to a joint on targetActorId
// originActor and targetActor are allowed to be identical
// this constraint could for example be used to constrain the feet of a cyclist to maintain a constant distance
// weight should nornally be 1
// returns 1 if successful, 0 otherwise
public static native int Captury_setDistanceConstraint(int originActorId, int originJointIndex, FloatPointer originOffset, int targetActorId, int targetJointIndex, FloatPointer targetOffset, float distance, @Cast("uint64_t") long timestamp, float weight);
public static native int Captury_setDistanceConstraint(int originActorId, int originJointIndex, FloatBuffer originOffset, int targetActorId, int targetJointIndex, FloatBuffer targetOffset, float distance, @Cast("uint64_t") long timestamp, float weight);
public static native int Captury_setDistanceConstraint(int originActorId, int originJointIndex, float[] originOffset, int targetActorId, int targetJointIndex, float[] targetOffset, float distance, @Cast("uint64_t") long timestamp, float weight);

// constrain a point attached to a joint of originActorId to maintain a relative rotation to a point attached to a joint on targetActorId
// originActor and targetActor are allowed to be identical
// weight should nornally be 1
// returns 1 if successful, 0 otherwise
public static native int Captury_setRelativeRotationConstraint(int originActorId, int originJointIndex, int targetActorId, int targetJointIndex, FloatPointer rotation, @Cast("uint64_t") long timestamp, float weight);
public static native int Captury_setRelativeRotationConstraint(int originActorId, int originJointIndex, int targetActorId, int targetJointIndex, FloatBuffer rotation, @Cast("uint64_t") long timestamp, float weight);
public static native int Captury_setRelativeRotationConstraint(int originActorId, int originJointIndex, int targetActorId, int targetJointIndex, float[] rotation, @Cast("uint64_t") long timestamp, float weight);




// fills the pointers with the current day, session, shot tuple that is used in CapturyLive to identify a shot
// the strings are owned by the library - do not free or overwrite
// returns 1 if successful, 0 otherwise
public static native int Captury_getCurrentDaySessionShot(@Cast("const char**") PointerPointer day, @Cast("const char**") PointerPointer session, @Cast("const char**") PointerPointer shot);
public static native int Captury_getCurrentDaySessionShot(@Cast("const char**") @ByPtrPtr BytePointer day, @Cast("const char**") @ByPtrPtr BytePointer session, @Cast("const char**") @ByPtrPtr BytePointer shot);
public static native int Captury_getCurrentDaySessionShot(@Cast("const char**") @ByPtrPtr ByteBuffer day, @Cast("const char**") @ByPtrPtr ByteBuffer session, @Cast("const char**") @ByPtrPtr ByteBuffer shot);
public static native int Captury_getCurrentDaySessionShot(@Cast("const char**") @ByPtrPtr byte[] day, @Cast("const char**") @ByPtrPtr byte[] session, @Cast("const char**") @ByPtrPtr byte[] shot);

// sets the shot name for the next recording
// returns 1 if successful, 0 otherwise
public static native int Captury_setShotName(@Cast("const char*") BytePointer name);
public static native int Captury_setShotName(String name);

// you have to set the shot name before starting to record - or make sure that it has been set using CapturyLive
// returns the timestamp when recording starts (on the CapturyLive machine) if successful, 0 otherwise
public static native @Cast("int64_t") long Captury_startRecording();

// returns 1 if successful, 0 otherwise
public static native int Captury_stopRecording();

// returns 1 if successful, 0 otherwise
public static native int Captury_getCurrentLatency(CapturyLatencyInfo latencyInfo);
// Targeting ..\CapturyCustomPacketCallback.java



// send packet with any data to Captury Live
// the packet will be handled by a plugin that registered for receiving packets with this name
// the name must be at most 16 characters
// size is the size of the raw data without the name of the plugin
// returns 1 if successful, 0 otherwise


// register callback that will be called when a packet with the given name is received
// the name must be at most 16 characters including a terminating 0
// the callback will be run in a different thread than the main application
// try to be quick in the callback
// returns 1 if successful, 0 otherwise
public static native int Captury_registerCustomPacketCallback(@Cast("const char*") BytePointer pluginName, CapturyCustomPacketCallback callback);
public static native int Captury_registerCustomPacketCallback(String pluginName, CapturyCustomPacketCallback callback);

// convert the pose given in global coordinates into local coordinates
public static native void Captury_convertPoseToLocal(CapturyPose pose, int actorId);
// Targeting ..\CapturyBackgroundFinishedCallback.java



public static native int Captury_captureBackground(CapturyBackgroundFinishedCallback callback, Pointer userData);
public static native int Captury_getBackgroundQuality();

public static native @Cast("const char*") BytePointer Captury_getStatus(); // do not free.

public static native void Captury_enablePrintf(int on); // 0 to turn off
public static native void Captury_enableRemoteLogging(int on); // 0 to turn off
public static native @Cast("const char*") BytePointer Captury_getNextLogMessage(); // do free.

//
// it is safe to ignore everything below this line
//
/** enum CapturyPacketTypes */
public static final int capturyActors = 1, capturyActor = 2,
	       capturyCameras = 3, capturyCamera = 4,
	       capturyStream = 5, capturyStreamAck = 6, capturyPose = 7,
	       capturyDaySessionShot = 8, capturySetShot = 9, capturySetShotAck = 10,
	       capturyStartRecording = 11, capturyStartRecordingAck = 12,
	       capturyStopRecording = 13, capturyStopRecordingAck = 14,
	       capturyConstraint = 15,
	       capturyGetTime = 16, capturyTime = 17,
	       capturyCustom = 18, capturyCustomAck = 19,
	       capturyGetImage = 20, capturyImageHeader = 21, capturyImageData = 22,
	       capturyGetImageData = 23,
	       capturyActorContinued = 24,
	       capturyGetMarkerTransform = 25, capturyMarkerTransform = 26,
	       capturyGetScalingProgress = 27, capturyScalingProgress = 28,
	       capturyConstraintAck = 29,
	       capturySnapActor = 30, capturyStopTracking = 31, capturyDeleteActor = 32,
	       capturySnapActorAck = 33, capturyStopTrackingAck = 34, capturyDeleteActorAck = 35,
	       capturyActorModeChanged = 36, capturyARTag = 37,
	       capturyGetBackgroundQuality = 38, capturyBackgroundQuality = 39,
	       capturyCaptureBackground = 40, capturyCaptureBackgroundAck = 41, capturyBackgroundFinished = 42,
	       capturySetActorName = 43, capturySetActorNameAck = 44,
	       capturyStreamedImageHeader = 45, capturyStreamedImageData = 46,
	       capturyGetStreamedImageData = 47, capturyRescaleActor = 48, capturyRecolorActor = 49,
	       capturyRescaleActorAck = 50, capturyRecolorActorAck = 51,
	       capturyStartTracking = 52, capturyStartTrackingAck = 53,
	       capturyPose2 = 54,
	       capturyGetStatus = 55, capturyStatus = 56,
	       capturyUpdateActorColors = 57,
	       capturyPoseCont = 58,
	       capturyActor2 = 59, capturyActorContinued2 = 60,
	       capturyLatency = 61,
	       capturyActors2 = 62, capturyActor3 = 63, capturyActorContinued3 = 64,
	       capturyCompressedPose = 65, capturyCompressedPose2 = 66,
	       capturyCompressedPoseCont = 67,
	       capturyGetTime2 = 68, capturyTime2 = 69,
	       capturyAngles = 70,
	       capturyStartRecording2 = 71, capturyStartRecordingAck2 = 72,
	       capturyHello = 73, // handshake finished
	       capturyActorBlendShapes = 74,
	       capturyMessage = 75,
	       capturyEnableRemoteLogging = 76,
	       capturyDisableRemoteLogging = 77,
	       capturyError = 0;

// returns a string for nicer error messages


// make sure structures are laid out without padding
// #pragma pack(push, 1)
// Targeting ..\CapturyRequestPacket.java


// Targeting ..\CapturyActorsPacket.java


// Targeting ..\CapturyJointPacket.java


// Targeting ..\CapturyJointPacket2.java


// Targeting ..\CapturyJointPacket3.java


// Targeting ..\CapturyActorPacket.java


// Targeting ..\CapturyActorBlendShapesPacket.java


// Targeting ..\CapturyActorContinuedPacket.java


// Targeting ..\CapturyCamerasPacket.java


// Targeting ..\CapturyCameraPacket.java


// Targeting ..\CapturyStreamPacket0.java


// Targeting ..\CapturyStreamPacket.java


// Targeting ..\CapturyStreamPacket1.java


// Targeting ..\CapturyStreamPacketTcp.java


// Targeting ..\CapturyStreamPacket1Tcp.java


// Targeting ..\CapturyDaySessionShotPacket.java


// Targeting ..\CapturySetShotPacket.java


// Targeting ..\CapturyPosePacket.java


// Targeting ..\CapturyPosePacket2.java


// Targeting ..\CapturyPoseCont.java


// Targeting ..\CapturyAnglesPacket.java


// Targeting ..\CapturyConstraintPacket.java


// Targeting ..\CapturyTimePacket.java


// Targeting ..\CapturyTimePacket2.java


// Targeting ..\CapturyGetMarkerTransformPacket.java


// Targeting ..\CapturyMarkerTransformPacket.java


// Targeting ..\CapturyGetScalingProgressPacket.java


// Targeting ..\CapturyScalingProgressPacket.java


// Targeting ..\CapturyCustomPacket.java


// Targeting ..\CapturyGetImagePacket.java


// Targeting ..\CapturyGetImageDataPacket.java


// Targeting ..\CapturyImageHeaderPacket.java


// Targeting ..\CapturyImageDataPacket.java


// Targeting ..\CapturySnapActorPacket.java


// Targeting ..\CapturySnapActorPacket2.java


// Targeting ..\CapturyStartTrackingPacket.java


// Targeting ..\CapturyStopTrackingPacket.java


// Targeting ..\CapturyActorModeChangedPacket.java


// Targeting ..\CapturyARTagPacket.java


// Targeting ..\CapturyBackgroundQualityPacket.java


// Targeting ..\CapturySetActorNamePacket.java


// Targeting ..\CapturyStatusPacket.java


// Targeting ..\CapturyIMUData.java


// Targeting ..\CapturyLatencyPacket.java




public static final int CAPTURY_LOG_FATAL =	0;	// this is definitely causing a crash
public static final int CAPTURY_LOG_ERROR =	1;	// for things that went so wrong
					// that the program will probably not work
public static final int CAPTURY_LOG_WARNING =	2;	// when things went wrong but the program
					// is probably going to work anyhow
public static final int CAPTURY_LOG_IMPORTANT =	3;	// the program is running normally but some
					// important messages needs to be passed to the user
public static final int CAPTURY_LOG_INFO =	4;	// the program is running normally but some
					// interesting points have been reached
public static final int CAPTURY_LOG_DEBUG =	5;	// debugging messages
public static final int CAPTURY_LOG_TRACE =	6;
// Targeting ..\CapturyLogPacket.java



// #pragma pack(pop)

// #ifndef FOURCC
// #define FOURCC(a,b,c,d)		(((d)<<24)|((c)<<16)|((b)<<8)|(a))
// #endif

public static native @MemberGetter int FOURCC_RGB();
public static final int FOURCC_RGB = FOURCC_RGB(); // uncompressed RGB

// #ifdef __cplusplus // extern "C"
// #endif


}
