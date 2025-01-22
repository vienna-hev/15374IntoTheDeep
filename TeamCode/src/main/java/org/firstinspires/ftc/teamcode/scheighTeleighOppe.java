package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class scheighTeleighOppe extends LinearOpMode {
    public DcMotor frontRight, frontLeft, backRight, backLeft;
    public DcMotorEx liftRight, liftLeft;
    public Servo pitchRight, pitchLeft, extendRight, extendLeft, bucket, roll;
    public CRServo intakeRight, intakeLeft;

    @Override
    public void runOpMode() {

        frontRight = hardwareMap.get(DcMotor.class, "FR");
        frontLeft = hardwareMap.get(DcMotor.class, "FL");
        backRight = hardwareMap.get(DcMotor.class, "BR");
        backLeft = hardwareMap.get(DcMotor.class, "BL");

        liftRight = hardwareMap.get(DcMotorEx.class, "RUM");
        liftLeft = hardwareMap.get(DcMotorEx.class, "LUM");
        intakeRight = hardwareMap.get(CRServo.class, "intakeServoRight"); //STILL NEED TO CONFIGURE
        intakeLeft = hardwareMap.get(CRServo.class, "intakeServoLeft"); //STILL NEED TO CONFIGURE
        pitchRight = hardwareMap.get(Servo.class, "intakeFoldRight");
        pitchLeft = hardwareMap.get(Servo.class, "intakeFoldLeft");
        extendRight = hardwareMap.get(Servo.class, "intakeExtensionRight");
        extendLeft = hardwareMap.get(Servo.class, "intakeExtensionLeft");
        bucket = hardwareMap.get(Servo.class, "bucketServo");
        roll = hardwareMap.get(Servo.class, "intakeTurn");

        double drivespeed = 1;
        double strafespeed = 1;
        double turnSpeed = 1;
        double speed, strafe, turn, flPwr, frPwr, blPwr, brPwr, denominator;


        waitForStart();

        while (opModeIsActive()) {
            if (gamepad2.dpad_left) {
                intakeLeft.setPower(-.5);
                intakeRight.setPower(.5);
            } else if (gamepad2.dpad_right) {
                intakeLeft.setPower(.6);
                intakeRight.setPower(-.6);
            } else {
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
            }

            if (gamepad2.left_stick_y > 0.5) {
                pitchLeft.setPosition(.1);
                pitchRight.setPosition(.1);
                roll.setPosition(1);
            } else {
                pitchLeft.setPosition(1);
                pitchRight.setPosition(1);
                roll.setPosition(0);
            }

            if (gamepad2.right_stick_x > 0.5) {
                extendRight.setPosition(0.80);
                extendLeft.setPosition(-0.80);
            } else {
                extendRight.setPosition(0.40);
                extendLeft.setPosition(-0.40);
            }

            if (gamepad2.y) {
                liftRight.setPower(1 - gamepad2.left_trigger);
                liftLeft.setPower(gamepad2.left_trigger - 1);
            } else if (gamepad2.x) {
                liftRight.setPower(gamepad2.left_trigger - 1);
                liftLeft.setPower(1 - gamepad2.left_trigger);
            } else {
                liftRight.setPower(0);
                liftLeft.setPower(0);
            }

            if (gamepad2.right_trigger > .5) {
                bucket.setPosition(.5);
            } else {
                bucket.setPosition(0);
            }

            speed = gamepad1.left_stick_y * drivespeed;
            strafe = gamepad1.left_stick_x * strafespeed;
            turn = (gamepad1.right_trigger - gamepad1.left_trigger) * turnSpeed;

            if (Math.abs(speed) < 0.1) {
                speed = 0;
            }
            if (Math.abs(turn) < 0.1) {
                turn = 0;
            }
            if (Math.abs(strafe) < 0.1) {
                strafe = 0;
            }

            flPwr = speed - turn - strafe;
            frPwr = speed + turn + strafe;
            blPwr = speed - turn + strafe;
            brPwr = speed + turn - strafe;

            denominator = Math.max(Math.max(Math.max(flPwr, frPwr), Math.max(blPwr, brPwr)), 1);
            
            frontLeft.setPower(flPwr / denominator);
            frontRight.setPower(frPwr / denominator);
            backLeft.setPower(blPwr / denominator);
            backRight.setPower(brPwr / denominator);
        }
    }
}