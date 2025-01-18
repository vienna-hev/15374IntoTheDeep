package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class AAsharkMode extends LinearOpMode {
    public DcMotor frontRight;
    public DcMotor frontLeft;
    public DcMotor backLeft;
    public DcMotor backRight;
    public DcMotorEx upMotor;
    public CRServo intakeServo;
    public Servo wristRight;
    public Servo wristLeft;
    public Servo wristFront;
    public Servo intakeExtensionR;
    public Servo intakeExtensionL;
    public Servo bucketServo;

    @Override
    public void runOpMode() {

        frontRight = hardwareMap.get(DcMotor.class, "FR");
        frontLeft = hardwareMap.get(DcMotor.class, "FL");
        backLeft = hardwareMap.get(DcMotor.class, "BL");
        backRight = hardwareMap.get(DcMotor.class, "BR");

        upMotor = hardwareMap.get(DcMotorEx.class, "UM");
        bucketServo = hardwareMap.get(Servo.class, "BS");

        intakeServo = hardwareMap.get(CRServo.class, "IS");
        intakeExtensionR = hardwareMap.get(Servo.class, "IER");
        intakeExtensionL = hardwareMap.get(Servo.class, "IEL");
        wristRight = hardwareMap.get(Servo.class, "WR");
        wristLeft = hardwareMap.get(Servo.class, "WL");
        wristFront = hardwareMap.get(Servo.class, "WF");

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        upMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        double drivespeed = 1;
        double turnSpeed = 1;


        waitForStart();

        while (opModeIsActive()) {
            //game pad spots will be changed
            if (gamepad2.dpad_left) { //arm rotate up and probably intake
                wristLeft.setPosition(1);
                wristRight.setPosition(1);
                wristFront.setPosition(0);
                intakeServo.setPower(.5);
            } else if (gamepad2.dpad_right) { //arm rotate down and probably outtake
                wristLeft.setPosition(.1);
                wristRight.setPosition(.1);
                wristFront.setPosition(1);
                intakeServo.setPower(-.5);
            } else {
                intakeServo.setPower(0);
            }

            if (gamepad2.right_stick_x > 0.9) { //arm out
                intakeExtensionL.setPosition(0.80);
                intakeExtensionR.setPosition(-0.80);
            } else {
                intakeExtensionL.setPosition(0.40);
                intakeExtensionR.setPosition(-0.40);
            }

            if (gamepad2.y) { //bucket rise
                upMotor.setPower(1);
            } else if (gamepad2.x) { //bucket fall
                upMotor.setPower(-1);
            } else {
                upMotor.setPower(0);
            }

            if (gamepad2.right_trigger > .9) { //dump bucket
                bucketServo.setPosition(.5);
            } else {
                bucketServo.setPosition(0);
            }

            double speed = gamepad1.left_stick_y * drivespeed;
            double turn = (gamepad1.right_trigger - gamepad1.left_trigger) * turnSpeed;
            double strafe = gamepad1.left_stick_x * drivespeed;

            if (Math.abs(speed) < 0.1) {
                speed = 0;
            }
            if (Math.abs(turn) < 0.1) {
                turn = 0;
            }
            if (Math.abs(strafe) < 0.1) {
                strafe = 0;
            }

            frontLeft.setPower(-speed + turn + strafe);
            frontRight.setPower(-speed - turn - strafe);
            backLeft.setPower(-speed + turn - strafe);
            backRight.setPower(-speed - turn + strafe);
        }
    }
}