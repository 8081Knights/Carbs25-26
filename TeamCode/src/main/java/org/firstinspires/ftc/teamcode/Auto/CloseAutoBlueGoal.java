/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HardwareSoftware;

/*
 * This OpMode illustrates the concept of driving a path based on encoder counts.
 * The code is structured as a LinearOpMode
 *
 * The code REQUIRES that you DO have encoders on the wheels,
 *   otherwise you would use: hwAutoDriveByTime;
 *
 *  This code ALSO requires that the drive Motors have been configured such that a positive
 *  power command moves them forward, and causes the encoders to count UP.
 *
 *   The desired path in this example is:
 *   - Drive forward for 48 inches
 *   - Spin right for 12 Inches
 *   - Drive Backward for 24 inches
 *   - Stop and close the claw.
 *
 *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
 *  that performs the actual movement.
 *  This method assumes that each movement is relative to the last stopping place.
 *  There are other ways to perform encoder based moves, but this method is probably the simplest.
 *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@Autonomous(name="hw: CloseAutoBlueGoal", group="hw")

public class CloseAutoBlueGoal extends LinearOpMode {
    //  HardwareSoftware hw = new HardwareSoftware();

    /* Declare OpMode members. */
    private DcMotor         FLdrive   = null;
    private DcMotor         BLdrive   = null;
    private DcMotor         FRdrive  = null;
    private DcMotor         BRdrive  = null;

    private ElapsedTime     runtime = new ElapsedTime();

    // Calculate the COUNTS_PER_INCH for your specific drive train.
    // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
    // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
    // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
    // This is gearing DOWN for less speed and more torque.
    // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
    static final double     COUNTS_PER_INCH    = 40 ;    // eg: TETRIX Motor Encoder

    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;
    HardwareSoftware hw = new HardwareSoftware();
    @Override
    public void runOpMode() {

        // Initialize the drive system variables.
        hw.init(hardwareMap);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Starting at",  "%7d :%7d",
                hw.FLdrive.getCurrentPosition(),
                hw.BLdrive.getCurrentPosition(),
                hw.FRdrive.getCurrentPosition(),
                hw.BRdrive.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)

        encoderDrive(DRIVE_SPEED,  47,  47, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout

        // Flappers push into fly wheel
        hw.Flapper.setPosition(hw.FlapperStart);
        sleep(700);
        // Ball 1 Launch
        hw.Flapper.setPosition(hw.FlapperLaunch);
        sleep(300);
        hw.Flapper.setPosition(hw.FlapperEnter);
        // Ball 2 Entering Fly wheel
        hw.Intake.setPower(-.97);
        sleep(500);
        hw.Intake.setPower(0);
        hw.Flapper.setPosition(hw.FlapperStart);
        sleep(700);
        // Ball 2 Launch
        hw.Flapper.setPosition(hw.FlapperLaunch);
        sleep(300);
        hw.Flapper.setPosition(hw.FlapperEnter);
        // Ball 3 Entering Fly Wheel
        hw.Intake.setPower(-.97);
        sleep(500);
        hw.Flapper.setPosition(hw.FlapperStart);
        sleep(700);
        // Ball 3 Launch
        hw.Flapper.setPosition(hw.FlapperLaunch);
        sleep(300);
        hw.Flapper.setPosition(hw.FlapperEnter);
        // Robot Sleep
        sleep(300);
        hw.Intake.setPower(0);
        hw.FlywheelL.setVelocity(0);
        hw.FlywheelR.setVelocity(0);

        // collecting next two balls
        // old values: 26, 36
        hw.Intake.setPower(-.97);
        encoderDrive(TURN_SPEED, 24.5, -24.5, 4.0);
        encoderDrive(DRIVE_SPEED, driveForward(29),6);
        // first ball going in
        hw.FlywheelR.setVelocity(0);
        hw.FlywheelL.setVelocity(0);
        sleep(500);
        encoderDrive(DRIVE_SPEED, driveForward(9),6);
        // second ball going in
        sleep(350);
        hw.Intake.setPower(0);

        //robot gets back to shoot position with two balls, old values: 41, 36
        encoderDrive(TURN_SPEED, 35, -35, 3.5);
        encoderDrive(DRIVE_SPEED, driveForward(38),4.0);
        encoderDrive(TURN_SPEED, 15.8, -15.8, 4.0);
        sleep(300);
        // Flappers push into fly wheel
        hw.Flapper.setPosition(hw.FlapperStart);
        sleep(700);
        // Ball 1 Launch
        hw.Flapper.setPosition(hw.FlapperLaunch);
        sleep(300);
        hw.Flapper.setPosition(hw.FlapperEnter);
        // Ball 2 Entering Fly wheel
        hw.Intake.setPower(-.97);
        sleep(500);
        hw.Intake.setPower(0);
        hw.Flapper.setPosition(hw.FlapperStart);
        sleep(700);
        // Ball 2 Launch
        hw.Flapper.setPosition(hw.FlapperLaunch);
        sleep(300);
        hw.Flapper.setPosition(hw.FlapperEnter);
        // Ball 3 going through gate
        //hw.RightGate.setPosition(hw.openRGate);
        //sleep(500);
        //hw.Intake.setPower(-.97);
        //sleep(800);
        //hw.RightGate.setPosition(hw.closeRGate);
        //sleep(800);
        // Flappers push into fly wheel
        //hw.Flapper.setPosition(hw.FlapperStart);
        //sleep(800);
        // Ball 3 Launch
        //hw.Flapper.setPosition(hw.FlapperLaunch);
        //sleep(500);
        //hw.Flapper.setPosition(hw.FlapperEnter);
        encoderDrive(DRIVE_SPEED, driveRight(28), 5);
        // Robot Sleep
        sleep(1000);
        hw.Intake.setPower(0);
        hw.FlywheelL.setVelocity(0);
        hw.FlywheelR.setVelocity(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);  // pause to display final telemetry message.
    }

    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the OpMode running.
     */
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {
              hw.LeftGate.setPosition(hw.closeLGate);
            hw.RightGate.setPosition(hw.closeRGate);
            // angling launcher and turn it on
              hw.Angler.setPosition(hw.AnglerFar);
            // Start Fly Wheels
              hw.FlywheelL.setVelocity(hw.TPS * 2600);
               hw.FlywheelR.setVelocity(hw.TPS * 2600);
            sleep(800);
            // Determine new target position, and pass to motor controller
            newLeftTarget = hw.FLdrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newLeftTarget = hw.BLdrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = hw.FRdrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            newRightTarget = hw.BRdrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            hw.FLdrive.setTargetPosition(newLeftTarget);
            hw.BLdrive.setTargetPosition(newLeftTarget);
            hw.FRdrive.setTargetPosition(newRightTarget);
            hw.BRdrive.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            hw.FLdrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hw.BLdrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hw.FRdrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hw.BRdrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            hw.FLdrive.setPower(Math.abs(speed));
            hw.BLdrive.setPower(Math.abs(speed));
            hw.FRdrive.setPower(Math.abs(speed));
            hw.BRdrive.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the hw will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the hw continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (hw.FLdrive.isBusy() && hw.FRdrive.isBusy()) &&
                    (hw.BLdrive.isBusy() && hw.BRdrive.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Running to",  " %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Currently at",  " at %7d :%7d",
                        hw.FLdrive.getCurrentPosition(), hw.FRdrive.getCurrentPosition(),
                        hw.BLdrive.getCurrentPosition(), hw.BRdrive.getCurrentPosition());


                telemetry.update();
            }

            // Stop all motion;
            hw.FLdrive.setPower(0);
            hw.BLdrive.setPower(0);
            hw.FRdrive.setPower(0);
            hw.BRdrive.setPower(0);

            // Turn off RUN_TO_POSITION
            hw.FLdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            hw.BLdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            hw.FRdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            hw.BRdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move.
        }
    }

    public void encoderDrive(double speed,
                             double[] inches,
                             double timeoutS) {
        int newFLeftTarget;
        int newFRightTarget;
        int newBRightTarget;
        int newBLeftTarget;

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFLeftTarget = hw.FLdrive.getCurrentPosition() + (int) (inches[0] * COUNTS_PER_INCH);
            newFRightTarget = hw.FRdrive.getCurrentPosition() + (int) (inches[1] * COUNTS_PER_INCH);
            newBLeftTarget = hw.BLdrive.getCurrentPosition() + (int) (inches[2] * COUNTS_PER_INCH);
            newBRightTarget = hw.BRdrive.getCurrentPosition() + (int) (inches[3] * COUNTS_PER_INCH);

            hw.FLdrive.setTargetPosition(newFLeftTarget);
            hw.FRdrive.setTargetPosition(newFRightTarget);
            hw.BLdrive.setTargetPosition(newBLeftTarget);
            hw.BRdrive.setTargetPosition(newBRightTarget);

            // Turn On RUN_TO_POSITION
            hw.BLdrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hw.BRdrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hw.FLdrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hw.FRdrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            hw.FLdrive.setPower(Math.abs(speed));
            hw.FRdrive.setPower(Math.abs(speed));
            hw.BLdrive.setPower(Math.abs(speed));
            hw.BRdrive.setPower(Math.abs(speed));


            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the hw will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the hw continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (hw.FLdrive.isBusy() && hw.FRdrive.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Running to", " %7d :%7d", newFLeftTarget, newFRightTarget);
                telemetry.addData("Currently at", " at %7d :%7d",
                        hw.FLdrive.getCurrentPosition(), hw.FRdrive.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            hw.BRdrive.setPower(0);
            hw.FRdrive.setPower(0);
            hw.FLdrive.setPower(0);
            hw.BLdrive.setPower(0);


            // Turn off RUN_TO_POSITION
            hw.FLdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            hw.FRdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            hw.BRdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            hw.BLdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move.
        }
    }
    public double[] driveLeft(double inches) {
        return new double[]{-inches, inches, inches, -inches};
    }

    public double[] driveRight(double inches) {
        return new double[]{inches, -inches, -inches, inches};
    }


    public double[] driveForward(double inches) {
        return new double[]{inches, inches, inches, inches};
    }

    public double[] driveBackward(double inches) {
        return new double[]{-inches, -inches, -inches, -inches};
    }

    public double[] turnRight(double inches) {
        return new double[]{inches, -inches, -inches, inches};
    }

    public double[] turnLeft(double inches) {
        return new double[]{-inches, inches, inches, -inches};
    }
}
