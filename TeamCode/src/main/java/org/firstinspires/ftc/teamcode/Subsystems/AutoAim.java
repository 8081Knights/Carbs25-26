package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.HardwareSoftware;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class AutoAim {
    private static VisionPortal visionPortal;
    private static AprilTagProcessor aprilTag;
    private static final double YAW_TOLERANCE_DEG = 1.5;
    private static final double TURN_KP = 0.02;
    private static final double MAX_TURN_POWER = 0.4;

    static HardwareSoftware robot = null;




    public static void setHardwareSoftware(HardwareSoftware roBot){
        robot = roBot;
    }
    public static boolean aimAtTag(AprilTagDetection tag) {
    boolean isDone = false;
        double yawError = -tag.ftcPose.bearing;   // degrees (its negative because the wheels are inversed)

        if (Math.abs(yawError) <= YAW_TOLERANCE_DEG) {
            stopDrive();
            isDone = true;
            return isDone;
        }

        double turnPower = yawError * TURN_KP;
        turnPower = Math.max(-MAX_TURN_POWER, Math.min(MAX_TURN_POWER, turnPower));

        // Rotate in place
        robot.FLdrive.setPower( turnPower);
        robot.BLdrive.setPower( turnPower);
        robot.FRdrive.setPower(-turnPower);
        robot.BRdrive.setPower(-turnPower);
        return isDone;
    }

    public static void stopDrive() {
        robot.FLdrive.setPower(0);
        robot.FRdrive.setPower(0);
        robot.BLdrive.setPower(0);
        robot.BRdrive.setPower(0);
    }



    public static AprilTagDetection getClosestTag(List<AprilTagDetection> detections) {

        AprilTagDetection closest = null;
        double minRange = Double.MAX_VALUE;
        if(!detections.isEmpty()) {
            for (AprilTagDetection tag : detections) {
                if(tag != null) {
                    if (tag.ftcPose.range < minRange) {
                        minRange = tag.ftcPose.range;
                        closest = tag;
                    }
                }
            }
        }
        return closest;
    }
}
