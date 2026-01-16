
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class HardwareSoftware {

    private HardwareMap hw = null;

    public DcMotorEx FRdrive = null;
    public DcMotorEx BRdrive = null;
    public DcMotorEx BLdrive = null;
    public DcMotorEx FLdrive = null;
    public DcMotorEx Intake = null;
    public DcMotorEx FlywheelL = null;
    public DcMotorEx FlywheelR = null;
    public Servo Angler = null;
    public Servo RightGate = null;
    public Servo LeftGate = null;
    public Servo Flapper = null;

    public WebcamName Camera = null;



    public double openLGate = 0.33;
    public double openRGate = 0.63;
    public double closeLGate = 0.56;
    //.57 to far to the right
    //.51 to far to the left
    public double closeRGate = 0.54;
    public double AnglerOpen = 0.5;
    public double AnglerFar = 0.375;
    public double TPS = 0.6;
    public double FlapperEnter = 0.8;
    public double FlapperStart = 0.6; //.55 changed value to make it push less .60 works for auto
    public double FlywheelFast = 3300;
    public double FlywheelSlow = 2600;

    //.69 not high enough

    public double FlapperLaunch = 0;

    public void init(HardwareMap ahw) {


        hw = ahw;

        FLdrive = hw.get(DcMotorEx.class, "FLdrive");
        FRdrive = hw.get(DcMotorEx.class, "FRdrive");
        BLdrive = hw.get(DcMotorEx.class, "BLdrive");
        BRdrive = hw.get(DcMotorEx.class, "BRdrive");
        Intake = hw.get(DcMotorEx.class, "Intake");
        FlywheelL = hw.get(DcMotorEx.class, "FlywheelL");
        FlywheelR = hw.get(DcMotorEx.class, "FlywheelR");
        Angler = hw.get(Servo.class, "Angler");
        RightGate = hw.get(Servo.class, "RightGate");
        LeftGate = hw.get(Servo.class, "LeftGate");
        Flapper = hw.get(Servo.class, "Flapper");
        Camera = hw.get(WebcamName.class, "Webcam 1");

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
        BRdrive.setDirection(DcMotorEx.Direction.REVERSE);
        FlywheelR.setDirection(DcMotor.Direction.REVERSE);
        /* Arm.setDirection(DcMotorSimple.Direction.REVERSE);*/

        FLdrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BRdrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FRdrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BLdrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FlywheelL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FlywheelR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }


}