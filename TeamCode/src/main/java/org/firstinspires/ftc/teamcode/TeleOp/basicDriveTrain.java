
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

    int ArmTargetPos = 1600;

    int ArmToBucketPos = 200;

    boolean ClawOpen = true;
    boolean Wrist = true;
    boolean Bucket = true;
    int liftPosHigh = -3800;
    int liftPosLow = -10;
    int liftPosMid = -1000;
    double clawPosOpen = 0.2;
    double clawPosClosed = 0.45;   //originally .4
    double bucketScorePos = 0.5;     //high bucket score
    double bucketResetPos = 0.1;   //bucket rest
    double wristUp = 0.6;     //originally .9
    double wristDown = 0.1;   //originally .4




    int shoulderToBucket = 1240;

  /*  public void MoveLift(int yPos)
    {
        hw.Lift().setTargetPosition(yPos);
        hw.Lift().setVelocity(1500);
        hw.Lift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
  */  /*public void OpenClaw()
    {
        hw.Claw().setPosition(clawPosOpen);
    }
    public void CloseClaw()
    {
        hw.Claw().setPosition(clawPosClosed);
    }
    public void BucketScore()
    {
        hw.Bucket().setPosition(bucketScorePos);
    }
    public void ResetBucket()
    {
        hw.Bucket().setPosition(bucketResetPos);
    }
    public void MoveWristUp()
    {
        hw.Wrist().setPosition(wristUp);
    }
    public void MoveWristDown()
    {
        hw.Wrist().setPosition(wristDown);
    }
    public void MoveArm(int extendPos)
    {
        hw.Arm().setTargetPosition(extendPos);
        hw.Arm().setVelocity(-1000);
        if (hw.Arm().getCurrentPosition() == extendPos) {
            hw.Arm().setVelocity(0);
        }
    }*/
    /* public void PrimeArm()
     {
         hw.Arm().setTargetPosition(ArmTargetPos);
         while(hw.Arm().getCurrentPosition() != ArmTargetPos) {
             hw.Arm().setVelocity(1000);
         }
     }

     */
   /* public void MoveShoulder(int ShoulderTarget)
    {
        while(hw.Shoulder().getCurrentPosition() != ShoulderTarget) {
            if (hw.Shoulder().getCurrentPosition() < ShoulderTarget){
                hw.Shoulder().setPower(-0.3);
            }
            else {
                hw.Shoulder().setPower(0.3);
            }
        }
    }
*/

    @Override
    public void loop() {


        y = gamepad1.left_stick_y;
        rx = gamepad1.right_stick_x;
        x = gamepad1.left_stick_x;

        shoulder_y = gamepad2.left_stick_y / 3;

        hw.FLdrive().setPower(-(y - rx - x));
        hw.FRdrive().setPower(-(y + rx + x));
        hw.BLdrive().setPower(-(y - rx + x));
        hw.BRdrive().setPower(-(y + rx - x));

   //     hw.Shoulder().setPower(shoulder_y);


// lift
        /*if (gamepad2.a) {
            MoveLift(liftPosHigh);
        }

        if (gamepad2.b) {
            MoveLift(liftPosLow);
        }
        if (gamepad1.b) {
            MoveLift(liftPosMid);
        }*/

        // claw open/close
       /* if (gamepad2.x && !ClawOpen) {
            OpenClaw();
            ClawOpen = true;
        }
        if (!gamepad2.x && ClawOpen) {
            CloseClaw();
            ClawOpen = false;
        }*/

//        if (Claw) {
//            OpenClaw();
//        }
//        else {
//            CloseClaw();
//        }

        // arm down
       /* if(gamepad2.left_trigger > 0.5) {
            hw.Arm().setTargetPosition(0);
            if(hw.Arm().getCurrentPosition() > 0)
                hw.Arm().setVelocity(-1000);
            else
                hw.Arm().setVelocity(0);
        }

        // arm up
        else if(gamepad2.right_trigger > 0.5)
        {
            hw.Arm().setTargetPosition(ArmTargetPos);
            if(hw.Arm().getCurrentPosition() < ArmTargetPos)
                hw.Arm().setVelocity(1000);
            else{
                hw.Arm().setVelocity(0);
            }
        }
        else {
            hw.Arm().setVelocity(0);
        }

        // bucket
        if(gamepad2.right_bumper || gamepad2.left_bumper && Bucket) {
            if(hw.Bucket().getPosition() != bucketScorePos)
                BucketScore();
            else
                ResetBucket();
            Bucket = false;
        }
        if(!gamepad2.right_bumper && !gamepad2.left_bumper)
            Bucket = true;*/
        //sample of how to make a button correctly do mulitple things

           /* if(gamepad2.right_bumper)
            {
                BucketScore();
            }
            if(gamepad2.left_bumper)
            {
                ResetBucket();
            }*/
        // wrist
        /*if(gamepad2.left_bumper && Wrist) {
            if (hw.Wrist().getPosition() == wristDown) {
                MoveWristUp();
            }
            else {
                MoveWristDown();
            }
            Wrist = false;
        }
        if (!gamepad2.left_bumper) {
            Wrist = true;
        }
*/
     /*   if (gamepad2.x) {
            MoveWristDown();
        }
        if (gamepad2.y) {
            MoveWristUp();
        }*/

      /*  if (gamepad2.dpad_up) {
            MoveArm(ArmToBucketPos);
            MoveWristUp();
            MoveShoulder(shoulderToBucket);
            //OpenClaw();
        }

        if (gamepad2.dpad_down) {
            BucketScore();
            MoveLift(liftPosLow);
            ResetBucket();
        }


        put specimen on bar

        while (hw.Shoulder().getCurrentPosition() != SpecPos) {
            hw.Shoulder().setPower(0.3);
        }
        hw.Arm().setVelocity(1000);

        shoulder rotate slightly/arm retract to secure hook on bar??
        claw release
        arm go down



        pick up a thing

        arm extend
        claw open
        shoulder rotate
        claw close
        shoulder rotate
         */



     /*   telemetry.addData("Lodom", hw.FRdrive().getCurrentPosition());
        telemetry.addData("Rodom", hw.FLdrive().getCurrentPosition());
        telemetry.addData("Bodom", hw.BRdrive().getCurrentPosition());
        telemetry.addData("Lift", hw.Lift().getCurrentPosition());
        telemetry.addData("Claw", hw.Claw().getPosition());
        telemetry.addData("Arm", hw.Arm().getCurrentPosition());
        telemetry.addData("Wrist", hw.Wrist().getPosition());
        telemetry.addData("Lift Target", hw.Lift().getTargetPosition());
        telemetry.addData("Bucket Pos", hw.Bucket().getPosition());
        telemetry.addData("Shoulder Pos", hw.Shoulder().getCurrentPosition());*/

        telemetry.update();

    }

}

