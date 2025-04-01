package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
public class ACsharkMode extends LinearOpMode {
    public DcMotor frontRight, frontLeft, backLeft, backRight;
    public DcMotorEx leftUpMotor, rightUpMotor;
    public CRServo intakeLeft, intakeRight;
    public Servo wristLeft, wristFront;
    public Servo intakeExtensionR, intakeExtensionL;
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

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        double drivespeed = 1;
        double turnSpeed = 1;

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.b) { //extension out
                intakeExtensionL.setPosition(0.35);
                intakeExtensionR.setPosition(0.465);
            } else if (gamepad1.x) { //extension in
                intakeExtensionL.setPosition(0.12);
                intakeExtensionR.setPosition(.675);
            }

            if (gamepad2.a) { //arm rotate up
                wristLeft.setPosition(0.45);
                wristFront.setPosition(1);
            } else if (gamepad2.b) { //arm rotate down
                wristLeft.setPosition(1);
                wristFront.setPosition(-1);
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

            if (gamepad1.right_bumper) { //dump bucket
                bucketServo.setPosition(0);
            } else {
                bucketServo.setPosition(1);
            }

            if (gamepad1.a) {
                    intakeLeft.setPower(1);
                    intakeRight.setPower(-1);
            } else if (gamepad1.y) {
                intakeLeft.setPower(-1);
                intakeRight.setPower(1);
            } else {
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
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