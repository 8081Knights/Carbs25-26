package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HardwareSoftware;

import org.firstinspires.ftc.teamcode.Subsystems.AutoAim;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Subsystems.AutoAim.*;

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
    private static final double YAW_TOLERANCE_DEG = 1.0;
    private static final double TURN_KP = 0.02;
    private static final double MAX_TURN_POWER = 0.4;

    @Override
    public void init() {

 //       HardwareSoftware hw = new HardwareSoftware();
        robot.init(hardwareMap);

        aprilTag = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(robot.Camera)
                .addProcessor(aprilTag)
                .build();

        AutoAim.setHardwareSoftware(robot);

        telemetry.addLine("AprilTag Auto-Aim Ready");
        telemetry.update();
    }

    @Override
    public void loop() {

        boolean autoAim = gamepad1.left_trigger > 0.5;
        List<AprilTagDetection> detections = aprilTag.getDetections();

        if (autoAim && !detections.isEmpty()) {

            AprilTagDetection targetTag = AutoAim.getClosestTag(detections);

            if (targetTag != null) {
              AutoAim.aimAtTag(targetTag);
            } else {
               AutoAim.stopDrive();
            }

        } else {
            AutoAim.stopDrive();
        }

        // =============================
        // Telemetry
        // =============================
        if (!detections.isEmpty()) {
            AprilTagDetection tag = AutoAim.getClosestTag(detections);
            telemetry.addData("Tag ID", tag.id);
            telemetry.addData("Yaw (deg)", "%.2f", tag.ftcPose.yaw);
            telemetry.addData("Range (ft)", "%.2f", tag.ftcPose.range);
        } else {
            telemetry.addLine("No Tags Detected");
        }

        telemetry.addData("Auto Aim", autoAim ? "ON" : "OFF");
        telemetry.update();
    }


}
