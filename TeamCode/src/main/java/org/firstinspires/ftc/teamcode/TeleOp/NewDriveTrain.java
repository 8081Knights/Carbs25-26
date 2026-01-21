
package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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

    double y = 0;
    double rx = 0;
    double shoulder_y = 0;
    int flywheelvelocity = 1560;

    double x = 0;
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
        //  hw.Wrist().setPosition(0.0);

    }

    //TODO: 1. Test direction of flywheels done done done run run we run the town
    //      2. Test intake direction done done done i dont talk but i bite full of venom
    //      3. test da set positions
    //      4. Find 2 set positions for launching 1. near and 2. far
    //      5. Test odometrey sensor

    @Override
    public void loop() {


        y = gamepad1.left_stick_y;
        rx = gamepad1.right_stick_x;
        x = gamepad1.left_stick_x;

        shoulder_y = gamepad2.left_stick_y / 3;

        hw.FLdrive.setPower(-(y - rx - x));
        hw.FRdrive.setPower(-(y + rx + x));
        hw.BLdrive.setPower(-(y - rx + x));
        hw.BRdrive.setPower(-(y + rx - x));
// release ball
        if(gamepad1.b){
            hw.Intake.setPower(.97);
        }
// pickup ball
        else if(gamepad1.a){
            hw.Intake.setPower(-.97);
        }
        else {
            hw.Intake.setPower(0);
        }
        if(gamepad2.right_trigger > 0.5){
            hw.FlywheelL.setVelocity(flywheelvelocity);
            hw.FlywheelR.setVelocity(flywheelvelocity);
        } else {
            hw.FlywheelL.setVelocity(0);
            hw.FlywheelR.setVelocity(0);
        }


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


// fly wheel fast speed
        if(gamepad2.right_stick_y > .5){
 //           hw.FlywheelL.setVelocity(hw.TPS * hw.FlywheelFast);
 //           hw.FlywheelR.setVelocity(hw.TPS * hw.FlywheelFast);
            flywheelvelocity += 1;
        }
        if(gamepad2.right_stick_y < -.5){
            flywheelvelocity -= 1;
        }

        telemetry.addData("Flywheel Velocity", flywheelvelocity);
        telemetry.addData("CurrentVR", hw.FlywheelL.getVelocity());
        telemetry.addData("CurrentVL", hw.FlywheelR.getVelocity());
        if (AutoAim.getClosestTag(aprilTag.getDetections()) != null){
            telemetry.addData("Range (ft)", "%.2f", AutoAim.getClosestTag(aprilTag.getDetections()).ftcPose.range);
        }
        telemetry.update();

//0.39, 0.5
// fly wheel open position
        if(gamepad2.a){
            hw.Angler.setPosition(hw.AnglerOpen);
        }
// fly wheel close position (far away)
        if(gamepad2.b){
            hw.Angler.setPosition(hw.AnglerFar);
        }
// right and left gates
        if(gamepad1.right_bumper){
            hw.RightGate.setPosition(hw.openRGate);
        }
        else {
            hw.RightGate.setPosition(hw.closeRGate);
        }
        if(gamepad1.left_bumper){
            hw.LeftGate.setPosition(hw.openLGate);
        }
        else {
            hw.LeftGate.setPosition(hw.closeLGate);
        }
// flapper to get ball to fly wheel
        if(gamepad2.left_bumper){   //to slightly push the ball forward
            hw.Flapper.setPosition(hw.FlapperStart);
            if(gamepad2.right_bumper){  ////tpush the ball to launch
                hw.Flapper.setPosition(hw.FlapperLaunch);
        }}
        else {
            hw.Flapper.setPosition(hw.FlapperEnter);
        }
        //telemetry.addData("Shoulder Pos", hw.Shoulder().getCurrentPosition());

        telemetry.update();

    }

}

