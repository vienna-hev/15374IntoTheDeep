package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class scheighTeleighOppe extends LinearOpMode {
    public DcMotor frontRight;
    public DcMotor frontLeft;
    public DcMotor backLeft;
    public DcMotor backRight;
    public DcMotorEx lift;
    public CRServo clawLeft;
    public CRServo clawRight;
    public Servo pitchRight;
    public Servo pitchLeft;
    public Servo roll;
    public Servo extend;
    public Servo bucket;

    @Override
    public void runOpMode() {

        frontRight = hardwareMap.get(DcMotor.class, "FR");
        frontLeft = hardwareMap.get(DcMotor.class, "FL");
        backRight = hardwareMap.get(DcMotor.class, "BR");
        backLeft = hardwareMap.get(DcMotor.class, "BL");

        lift = hardwareMap.get(DcMotorEx.class, "UM");
        clawRight = hardwareMap.get(CRServo.class, "ISR");
        clawLeft = hardwareMap.get(CRServo.class, "ISL");
        pitchRight = hardwareMap.get(Servo.class, "WR");
        pitchLeft = hardwareMap.get(Servo.class, "WL");
        extend = hardwareMap.get(Servo.class, "ES");
        bucket = hardwareMap.get(Servo.class, "BS");
        roll = hardwareMap.get(Servo.class, "WF");
        
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        
        double drivespeed = 1;
        double strafespeed = 1;
        double turnSpeed = 1;
        double speed, strafe, turn, flPwr, frPwr, blPwr, brPwr, denominator;


        waitForStart();

        while (opModeIsActive()) {
            if (gamepad2.dpad_left) {
                clawLeft.setPower(-.5);
                clawRight.setPower(.5);
            } else if (gamepad2.dpad_right) {
                clawLeft.setPower(.6);
                clawRight.setPower(-.6);
            } else {
                clawLeft.setPower(0);
                clawRight.setPower(0);
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
                extend.setPosition(0.80);
            } else {
                extend.setPosition(0.40);
            }

            if (gamepad2.y) {
                lift.setPower(1 - gamepad2.left_trigger);
            } else if (gamepad2.x) {
                lift.setPower(gamepad2.left_trigger - 1);
            } else {
                lift.setPower(0);
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