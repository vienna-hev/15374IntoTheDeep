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
    public DcMotorEx leftUpMotor;
    public DcMotorEx rightUpMotor;
    public CRServo intakeLeft;
    public CRServo intakeRight;
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

        leftUpMotor = hardwareMap.get(DcMotorEx.class, "LUM");
        rightUpMotor = hardwareMap.get(DcMotorEx.class, "RUM");
        intakeLeft = hardwareMap.get(CRServo.class, "intakeLeft");
        intakeRight = hardwareMap.get(CRServo.class, "intakeRight");
        intakeExtensionL = hardwareMap.get(Servo.class, "intakeExtensionLeft");
        intakeExtensionR = hardwareMap.get(Servo.class, "intakeExtensionRight");
        bucketServo = hardwareMap.get(Servo.class, "bucketServo");
        wristLeft = hardwareMap.get(Servo.class, "WL");
        wristFront = hardwareMap.get(Servo.class, "WF");

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftUpMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightUpMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        double drivespeed = 1;
        double turnSpeed = 1;

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.b) {
                intakeExtensionL.setPosition(-.1);
                intakeExtensionR.setPosition(.1);
            } else if (gamepad1.x) {
                intakeExtensionL.setPosition(-.9);
                intakeExtensionR.setPosition(.9);
            }

            //game pad spots will be changed
            if (gamepad2.dpad_left) { //arm rotate up and probably outtake
                wristLeft.setPosition(1);
                wristFront.setPosition(0);
                intakeLeft.setPower(.5);
                intakeRight.setPower(.5);
            } else if (gamepad2.dpad_right) { //arm rotate down
                wristLeft.setPosition(.1);
                wristFront.setPosition(1);
            } else {
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
            }

            if (gamepad2.right_stick_x > 0.9) { //arm out
                intakeExtensionL.setPosition(0.90);
                intakeExtensionR.setPosition(-0.90);
            } else {
                intakeExtensionL.setPosition(0.10);
                intakeExtensionR.setPosition(-0.10);
            }

            if (gamepad2.y) { //bucket rise
                leftUpMotor.setPower(-1);
                rightUpMotor.setPower(1);
            } else if (gamepad2.x) { //bucket fall
                leftUpMotor.setPower(1);
                rightUpMotor.setPower(-1);
            } else {
                leftUpMotor.setPower(0);
                rightUpMotor.setPower(0);
            }

            if (gamepad2.right_trigger > .9) { //dump bucket
                bucketServo.setPosition(.9);
            } else {
                bucketServo.setPosition(.1);
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

//left extension: 0 extended, left in
//right extension: 0 extended, right in