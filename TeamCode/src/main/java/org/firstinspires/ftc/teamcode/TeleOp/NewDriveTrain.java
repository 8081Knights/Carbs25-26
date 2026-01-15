
package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HardwareSoftware;

@TeleOp(name="New Drive")
public class NewDriveTrain extends OpMode {

    HardwareSoftware hw = new HardwareSoftware();
    @Override
    public void init() {

        hw.init(hardwareMap);
        //  hw.Wrist().setPosition(0.0);

    }
    double y = 0;
    double rx = 0;
    double shoulder_y = 0;

    double x = 0;

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
// fly wheel fast speed
        if(gamepad2.right_trigger > .5){
            hw.FlywheelL.setVelocity(hw.TPS * hw.FlywheelFast);
            hw.FlywheelR.setVelocity(hw.TPS * hw.FlywheelFast);
        }
// fly wheel slow speed
        else if(gamepad2.left_trigger > .5){
            hw.FlywheelL.setVelocity(hw.TPS * hw.FlywheelSlow);
            hw.FlywheelR.setVelocity(hw.TPS * hw.FlywheelSlow);
        }
        else {
            hw.FlywheelL.setVelocity(0);
            hw.FlywheelR.setVelocity(0);
        }

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

