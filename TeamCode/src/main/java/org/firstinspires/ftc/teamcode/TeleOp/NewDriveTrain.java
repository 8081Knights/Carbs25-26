package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HardwareSoftware;
import org.firstinspires.ftc.teamcode.Subsystems.AutoAim;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@TeleOp(name="New Drive")
public class NewDriveTrain extends OpMode {

    HardwareSoftware hw = new HardwareSoftware();
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;

    // ---------- FLAPPER ENUM & STATE ----------
    public enum FlapperState {
        REST,
        PRIMED,
        LAUNCHED
    }
    FlapperState flapperState = FlapperState.REST;
    ElapsedTime flapperTimer = new ElapsedTime();

    // ---------- OTHER VARIABLES ----------
    double y = 0;
    double rx = 0;
    double x = 0;
    double shoulder_y = 0;

    @Override
    public void init() {
        hw.init(hardwareMap);

        aprilTag = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hw.Camera)
                .addProcessor(aprilTag)
                .build();

        AutoAim.setHardwareSoftware(hw);
    }

    @Override
    public void loop() {

        // ---------- DRIVE ----------
        y = gamepad1.left_stick_y;
        rx = gamepad1.right_stick_x;
        x = gamepad1.left_stick_x;

        shoulder_y = gamepad2.left_stick_y / 3;

        hw.FLdrive.setPower(-(y - rx - x));
        hw.FRdrive.setPower(-(y + rx + x));
        hw.BLdrive.setPower(-(y - rx + x));
        hw.BRdrive.setPower(-(y + rx - x));

        // ---------- INTAKE ----------
        if(gamepad1.b){
            hw.Intake.setPower(.97);
        } else if(gamepad1.a){
            hw.Intake.setPower(-.97);
        } else {
            hw.Intake.setPower(0);
        }

        // ---------- FLYWHEEL ----------
        if(gamepad2.right_trigger > 0.5){
            AprilTagDetection tag = AutoAim.getClosestTag(aprilTag.getDetections());
            if(tag != null) {
                hw.FlywheelL.setVelocity(getVelocity(tag.ftcPose.range));
                hw.FlywheelR.setVelocity(getVelocity(tag.ftcPose.range));
            }
        } else {
            hw.FlywheelL.setVelocity(0);
            hw.FlywheelR.setVelocity(0);
        }

        // ---------- AUTO AIM ----------
        List<AprilTagDetection> detections = aprilTag.getDetections();
        boolean autoAim = gamepad2.left_trigger > 0.5;

        if (autoAim && !detections.isEmpty()) {
            AprilTagDetection targetTag = AutoAim.getClosestTag(detections);
            if (targetTag != null) {
                AutoAim.aimAtTag(targetTag);
            } else {
                AutoAim.stopDrive();
            }
        }

        // ---------- FLYWHEEL ANGLE ----------
        if(gamepad2.a){
            hw.Angler.setPosition(hw.AnglerOpen);
        }
        if(gamepad2.b){
            hw.Angler.setPosition(hw.AnglerFar);
        }

        // ---------- FLAPPER LOGIC ----------
        if (gamepad2.left_bumper && flapperState == FlapperState.REST) {
            hw.Flapper.setPosition(hw.FlapperStart); // primed
            flapperTimer.reset();
            flapperState = FlapperState.PRIMED;
        }

        if (flapperState == FlapperState.PRIMED && flapperTimer.milliseconds() >= 1100) {
            hw.Flapper.setPosition(hw.FlapperLaunch); // launch
            flapperTimer.reset();
            flapperState = FlapperState.LAUNCHED;
        }

        if (flapperState == FlapperState.LAUNCHED && flapperTimer.milliseconds() >= 450) {
            hw.Flapper.setPosition(hw.FlapperEnter); // back to rest
            flapperState = FlapperState.REST;
        }

        // ---------- TELEMETRY ----------
        telemetry.addData("CurrentVL", hw.FlywheelL.getVelocity());
        telemetry.addData("CurrentVR", hw.FlywheelR.getVelocity());
        telemetry.addData("Flapper State", flapperState);
        if(AutoAim.getClosestTag(detections) != null) {
            telemetry.addData("Range", AutoAim.getClosestTag(detections).ftcPose.range);
        }
        telemetry.update();
    }


    public double getVelocity(double distance){
        // y=-0.000357528x^{3}+0.127712x^{2}-11.52989x+1860.2731
        return -0.000357528 * Math.pow(distance,3) + 0.127712 * Math.pow(distance,2) - 11.52989 * distance + 1860.2731;

    }
}
