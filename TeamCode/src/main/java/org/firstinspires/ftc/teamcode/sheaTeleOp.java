package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple; //???
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class sheaTeleOp extends LinearOpMode {
    public DcMotor frontRight;
    public DcMotor frontLeft;
    public DcMotor backLeft;
    public DcMotor backRight;
    public DcMotorEx upMotor;
    public CRServo intakeLeft;
    public CRServo intakeRight;
    public Servo wristRight;
    public Servo wristLeft;
    public Servo wristFront;
    public Servo elbow;
    public Servo bucketServo;

    @Override
    public void runOpMode() {

        frontRight = hardwareMap.get(DcMotor.class, "FR");
        frontLeft = hardwareMap.get(DcMotor.class, "FL");
        backLeft = hardwareMap.get(DcMotor.class, "BL");
        backRight = hardwareMap.get(DcMotor.class, "BR");

        upMotor = hardwareMap.get(DcMotorEx.class, "UM");

        intakeLeft = hardwareMap.get(CRServo.class, "ISL");
        intakeRight = hardwareMap.get(CRServo.class, "ISR");
        wristRight = hardwareMap.get(Servo.class, "WR");
        wristLeft = hardwareMap.get(Servo.class, "WL");
        elbow = hardwareMap.get(Servo.class, "ES");
        bucketServo = hardwareMap.get(Servo.class, "BS");
        wristFront = hardwareMap.get(Servo.class, "WF");


        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        upMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);


        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        //frontRight.setDirection(DcMotorSimple.Direction.REVERSE); ///undo???
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        //backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        double drivespeed = -0.5;
        double turnSpeed = -1;


        waitForStart();

        while (opModeIsActive()) {
//            if (gamepad1.right_bumper) {
//                frontLeft.setPower(-drivespeed);
//                frontRight.setPower(drivespeed);
//                backLeft.setPower(-drivespeed);
//                backRight.setPower(drivespeed);
//            } else if (gamepad1.left_bumper) {
//                frontLeft.setPower(drivespeed);
//                frontRight.setPower(-drivespeed);
//                backLeft.setPower(drivespeed);
//                backRight.setPower(-drivespeed);

//            if (gamepad1.right_bumper) {
//                frontLeft.setPower(1);
//                frontRight.setPower(-1);
//                backLeft.setPower(1);
//                backRight.setPower(-1);
//            } else if (gamepad1.left_bumper) {
//                frontLeft.setPower(-1);
//                frontRight.setPower(1);
//                backLeft.setPower(-1);
//                backRight.setPower(1);

            if (gamepad1.left_bumper) {
                frontLeft.setPower(turnSpeed);
                frontRight.setPower(-turnSpeed);
                backLeft.setPower(turnSpeed);
                backRight.setPower(-turnSpeed);
            } else if (gamepad1.right_bumper) {
                frontLeft.setPower(-turnSpeed);
                frontRight.setPower(turnSpeed);
                backLeft.setPower(-turnSpeed);
                backRight.setPower(turnSpeed);

            }
            if (gamepad1.left_stick_y > 0.9) {
                frontLeft.setPower(drivespeed);
                frontRight.setPower(drivespeed);
                backLeft.setPower(drivespeed);
                backRight.setPower(drivespeed);
            } else if (gamepad1.left_stick_y < -0.9) {
                frontLeft.setPower(-drivespeed);
                frontRight.setPower(-drivespeed);
                backLeft.setPower(-drivespeed);
                backRight.setPower(-drivespeed);
            } else if (gamepad1.left_stick_x > 0.9) {
                frontLeft.setPower(-drivespeed);
                frontRight.setPower(drivespeed);
                backLeft.setPower(drivespeed);
                backRight.setPower(-drivespeed);
            } else if (gamepad1.left_stick_x < -0.9) {
                frontLeft.setPower(drivespeed);
                frontRight.setPower(-drivespeed);
                backLeft.setPower(-drivespeed);
                backRight.setPower(drivespeed);
            } else {
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(0);
            }

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

            if (gamepad2.left_stick_y > 0.9) {
                wristLeft.setPosition(.1);
                wristRight.setPosition(.1);
                wristFront.setPosition(1);
            }
            else if (gamepad2.left_stick_y < 0.9) {
                wristLeft.setPosition(1);
                wristRight.setPosition(1);
                wristFront.setPosition(0);
            }

            if (gamepad2.right_stick_x > 0.9) {
                elbow.setPosition(0.80);
            } else {
                elbow.setPosition(0.40);
            }

//            if (gamepad1.x) {
//                wristFront.setPosition(1);
//            }
//            else {
//                wristFront.setPosition(0);
//            }

            if (gamepad2.y) {
                upMotor.setPower(1);
            } else if (gamepad2.x) {
                upMotor.setPower(-0.6);
            } else {
                upMotor.setPower(0);
            }

            //bucket drop
            if (gamepad2.right_trigger > .9) {
                bucketServo.setPosition(1);
            } else {
                bucketServo.setPosition(0.3);
            }


            double speed = gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            double strafe = gamepad1.left_stick_x;
            frontLeft.setPower(-speed + turn + strafe);
            frontRight.setPower(-speed - turn - strafe);
            backLeft.setPower(-speed + turn - strafe);
            backRight.setPower(-speed - turn + strafe);
        }
    }
}