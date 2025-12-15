package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HardwareSoftware;

import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import java.util.List;

@TeleOp(name = "StolenAprilTagII", group = "TeleOp")
public class StolenAprilTagII extends OpMode {

    // =============================
    // Hardware
    // =============================
    HardwareSoftware robot = new HardwareSoftware();

    // =============================
    // Vision
    // =============================
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;

    // =============================
    // Control constants
    // =============================
    private static final double YAW_TOLERANCE_DEG = 2.0;
    private static final double TURN_KP = 0.02;
    private static final double MAX_TURN_POWER = 0.4;

    @Override
    public void init() {

        robot.init(hardwareMap);

        aprilTag = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(aprilTag)
                .build();

        telemetry.addLine("AprilTag Auto-Aim Ready");
        telemetry.update();
    }

    @Override
    public void loop() {

        boolean autoAim = gamepad1.left_trigger > 0.5;
        List<AprilTagDetection> detections = aprilTag.getDetections();

        if (autoAim && !detections.isEmpty()) {

            AprilTagDetection targetTag = getClosestTag(detections);

            if (targetTag != null) {
                aimAtTag(targetTag);
            } else {
                stopDrive();
            }

        } else {
            stopDrive();
        }

        // =============================
        // Telemetry
        // =============================
        if (!detections.isEmpty()) {
            AprilTagDetection tag = getClosestTag(detections);
            telemetry.addData("Tag ID", tag.id);
            telemetry.addData("Yaw (deg)", "%.2f", tag.ftcPose.yaw);
            telemetry.addData("Range (ft)", "%.2f", tag.ftcPose.range);
        } else {
            telemetry.addLine("No Tags Detected");
        }

        telemetry.addData("Auto Aim", autoAim ? "ON" : "OFF");
        telemetry.update();
    }

    // =====================================================
    // AUTO AIM LOGIC
    // =====================================================

    private void aimAtTag(AprilTagDetection tag) {

        double yawError = -tag.ftcPose.yaw;   // degrees (its negative because the wheels are inversed)

        if (Math.abs(yawError) <= YAW_TOLERANCE_DEG) {
            stopDrive();
            telemetry.addLine("AIM LOCKED");
            return;
        }

        double turnPower = yawError * TURN_KP;
        turnPower = Math.max(-MAX_TURN_POWER, Math.min(MAX_TURN_POWER, turnPower));

        // Rotate in place
        robot.FLdrive.setPower( turnPower);
        robot.BLdrive.setPower( turnPower);
        robot.FRdrive.setPower(-turnPower);
        robot.BRdrive.setPower(-turnPower);
    }

    private void stopDrive() {
        robot.FLdrive.setPower(0);
        robot.FRdrive.setPower(0);
        robot.BLdrive.setPower(0);
        robot.BRdrive.setPower(0);
    }

    // =====================================================
    // TAG SELECTION
    // =====================================================

    private AprilTagDetection getClosestTag(List<AprilTagDetection> detections) {

        AprilTagDetection closest = null;
        double minRange = Double.MAX_VALUE;

        for (AprilTagDetection tag : detections) {
            if (tag.ftcPose.range < minRange) {
                minRange = tag.ftcPose.range;
                closest = tag;
            }
        }
        return closest;
    }
}
