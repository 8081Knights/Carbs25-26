
package org.firstinspires.ftc.teamcode.Teleop.drivetrain;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.HardwareSoftware;


@TeleOp(name="DriveTest")
public class TestDrive extends OpMode {


    HardwareSoftware hw = new HardwareSoftware();
    boolean isAtBottom = true;
    @Override
    public void init() {

        hw.init(hardwareMap);


    }

    double Slide;
    @Override
    public void loop() {
       /* if(gamepad1.a)
        {
            if(isAtBottom == false) {
                hw.Lift().setTargetPosition(0);
                isAtBottom = true;
            }
            else{
                hw.Lift().setTargetPosition(3000);
                isAtBottom = false;
            }
            hw.Lift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hw.Lift().setVelocity(2000);
        }
        if(gamepad1.b)
        {
            hw.Lift().setTargetPosition(1500);
            isAtBottom = false;
            hw.Lift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hw.Lift().setVelocity(2000);

        }*/

        if(gamepad2.a) {
            hw.FLdrive().setPower(1);
        }
        else{
            hw.FLdrive().setPower(0);
        }

        if(gamepad2.b) {
            hw.FRdrive().setPower(1);
        }
        else{
            hw.FRdrive().setPower(0);
        }
        if(gamepad2.x) {
            hw.BLdrive().setPower(1);
        }
        else{
            hw.BLdrive().setPower(0);
        }

        if(gamepad2.y) {
            hw.BRdrive().setPower(1);
        }
        else{
            hw.BRdrive().setPower(0);
        }

        //telemetry.addData("Lift data:", hw.Lift().getCurrentPosition());
        telemetry.update();

    }

}
