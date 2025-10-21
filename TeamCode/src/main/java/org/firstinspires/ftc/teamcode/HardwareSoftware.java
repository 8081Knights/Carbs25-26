
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HardwareSoftware {

    private HardwareMap hw = null;

    DcMotorEx FRdrive    = null;
    DcMotorEx BRdrive     = null;
    DcMotorEx BLdrive      = null;
    DcMotorEx FLdrive     = null;
/*    DcMotorEx Arm = null;
    DcMotorEx Lift = null;
    DcMotorEx Shoulder = null;
    Servo Wrist = null;
    Servo Claw = null;
    Servo Bucket = null;*/


    public void init(HardwareMap ahw){


        hw = ahw;

        FLdrive = hw.get(DcMotorEx.class, "FLdrive");
        FRdrive = hw.get(DcMotorEx.class, "FRdrive");
        BLdrive = hw.get(DcMotorEx.class, "BLdrive");
        BRdrive = hw.get(DcMotorEx.class, "BRdrive");


     //   Arm = hw.get(DcMotorEx.class, "Arm");
    //    Lift = hw.get(DcMotorEx.class, "Lift");
        //    Shoulder = hw.get(DcMotorEx.class, "Shoulder");


    //    Wrist = hw.get(Servo.class, "Wrist");
    //    Claw = hw.get(Servo.class, "Claw");
    //    Bucket = hw.get(Servo.class, "Bucket");



        FLdrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        BRdrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        FRdrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        BLdrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        FLdrive.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        BRdrive.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        FRdrive.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        BLdrive.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

      /*  Shoulder.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Arm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        Lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/

        FLdrive.setDirection(DcMotorEx.Direction.REVERSE);
        FRdrive.setDirection(DcMotorEx.Direction.FORWARD);
        BLdrive.setDirection(DcMotorEx.Direction.REVERSE);
        BRdrive.setDirection(DcMotorEx.Direction.FORWARD);
       /* Arm.setDirection(DcMotorSimple.Direction.REVERSE);*/

        FLdrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BRdrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FRdrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BLdrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
     /*   Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Shoulder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
*/



    }

    public DcMotorEx FLdrive(){
        return FLdrive;
    }

    public DcMotorEx FRdrive(){
        return FRdrive;
    }

    public DcMotorEx BLdrive(){
        return BLdrive;
    }

    public DcMotorEx BRdrive(){
        return BRdrive;
    }

    /*public DcMotorEx Arm(){
        return Arm;
    }

    public DcMotorEx Lift(){
        return Lift;
    }

    public DcMotorEx Shoulder(){
        return Shoulder;
    }

    public Servo Claw(){
        return Claw;
    }

    public Servo Wrist(){
        return Wrist;
    }

    public Servo Bucket(){return Bucket;};
*/


}
