
package org.firstinspires.ftc.teamcode.TeleOp;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HardwareSoftware;
@TeleOp(name="Drive")
public class basicDriveTrain extends OpMode {

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

    //TODO: 1. Test direction of flywheels
    //      2. Test intake direction
    //      3. Program servo to go to set positions
    //      4. Find 2 set positions for launching 1. near and 2. far

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

        if(gamepad1.a){
            hw.Intake.setPower(.97);
        }
            else if(gamepad1.b){
                hw.Intake.setPower(-.97);
            }
            else {
                hw.Intake.setPower(0);
            }
        if(gamepad1.x){
            hw.FlywheelL.setPower(.97);
            hw.FlywheelR.setPower(-.97);
        }
            else {
                hw.FlywheelL.setPower(0);
                hw.FlywheelR.setPower(0);
            }
        if(gamepad2.x){
            hw.Angler.setPosition(.54);
        }
        if(gamepad2.y){
            hw.Angler.setPosition(.56);
        }
        if(gamepad2.a){
            hw.Angler.setPosition(.5);
        }
        if(gamepad2.b){
            hw.Angler.setPosition(.52);
        }
        //telemetry.addData("Shoulder Pos", hw.Shoulder().getCurrentPosition());

        telemetry.update();

    }

}

