package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class AASuperCoolTeleop extends LinearOpMode {
    public DcMotor frontRight, frontLeft, backRight, backLeft;
    public DcMotorEx liftRight, liftLeft;
    public Servo pitch, extendRight, extendLeft, bucket, roll;
    public CRServo intakeRight, intakeLeft;

    @Override
    public void runOpMode() {

        frontRight = hardwareMap.get(DcMotor.class, "FR");
        frontLeft = hardwareMap.get(DcMotor.class, "FL");
        backLeft = hardwareMap.get(DcMotor.class, "BL");
        backRight = hardwareMap.get(DcMotor.class, "BR");

        liftLeft = hardwareMap.get(DcMotorEx.class, "LUM");
        liftRight = hardwareMap.get(DcMotorEx.class, "RUM");
        intakeLeft = hardwareMap.get(CRServo.class, "intakeLeft");
        intakeRight = hardwareMap.get(CRServo.class, "intakeRight");
        extendLeft = hardwareMap.get(Servo.class, "intakeExtensionLeft");
        extendRight = hardwareMap.get(Servo.class, "intakeExtensionRight");
        bucket = hardwareMap.get(Servo.class, "bucketServo");
        pitch = hardwareMap.get(Servo.class, "WL");
        roll = hardwareMap.get(Servo.class, "WF");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        double driveSpeed = 1;
        double strafeSpeed = 1;
        double turnSpeed = 0.5;
        double speed, strafe, turn, flPwr, frPwr, blPwr, brPwr, denominator, tortoisity;

        double exLIn = 0.12; //retracted position for the left slide
        double exLOut = 0.35; //extended position for the left slide
        double exRIn = 0.675; //retracted position for the right slide
        double exROut = 0.465; //extended position for the right slide

        waitForStart();

        //intake in and out
        while (opModeIsActive()) {
            if (gamepad2.dpad_left) {
                    intakeLeft.setPower(-1);
                    intakeRight.setPower(1);
            } else if (gamepad2.dpad_right) {
                intakeLeft.setPower(1);
                intakeRight.setPower(-1);
            } else {
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
            }

            //intake down and up
            if (gamepad2.left_stick_y > 0.5) {
                pitch.setPosition(1);
                roll.setPosition(-1);
            } else {
                pitch.setPosition(0.45);
                roll.setPosition(1);
            }

            //intake extend and retract
            extendLeft.setPosition(exLIn + (exLOut - exLIn) * gamepad2.right_stick_x);
            extendRight.setPosition(exRIn + (exROut - exRIn) * gamepad2.right_stick_x);

            //outtake up and down
            if (gamepad2.left_bumper) {
                tortoisity = 0.5;
            } else {
                tortoisity = gamepad2.left_trigger;
            }

            if (gamepad2.x) {
                liftRight.setPower(1 - tortoisity);
                liftLeft.setPower(tortoisity - 1);
            } else if (gamepad2.y) {
                liftRight.setPower(tortoisity - 1);
                liftLeft.setPower(1 - tortoisity);
            } else {
                liftRight.setPower(0);
                liftLeft.setPower(0);
            }

            //outtake out and in
            if (gamepad2.right_trigger > .5) {
                bucket.setPosition(0);
            } else {
                bucket.setPosition(1);
            }

            speed = gamepad1.left_stick_y * driveSpeed;
            strafe = gamepad1.left_stick_x * strafeSpeed;
            turn = Math.max(gamepad1.right_stick_x, gamepad1.right_trigger - gamepad1.left_trigger);

            if (Math.abs(speed) < 0.1) {
                speed = 0;
            }
            if (Math.abs(turn) < 0.1) {
                turn = 0;
            } else if (Math.abs(turn) < 0.5) {
                turn = turnSpeed / 2;
            } else {
                turn *= turnSpeed;
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