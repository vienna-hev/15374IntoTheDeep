package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ABAlternateTeleop extends LinearOpMode {
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

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        liftLeft = hardwareMap.get(DcMotorEx.class, "LUM");
        liftRight = hardwareMap.get(DcMotorEx.class, "RUM");
        intakeLeft = hardwareMap.get(CRServo.class, "intakeLeft");
        intakeRight = hardwareMap.get(CRServo.class, "intakeRight");
        extendLeft = hardwareMap.get(Servo.class, "intakeExtensionLeft");
        extendRight = hardwareMap.get(Servo.class, "intakeExtensionRight");
        bucket = hardwareMap.get(Servo.class, "bucketServo");
        pitch = hardwareMap.get(Servo.class, "WL");
        roll = hardwareMap.get(Servo.class, "WF");

        double driveSpeed = 1;
        double strafeSpeed = 1;
        double turnSpeed = 0.5;
        double speed, strafe, turn, flPwr, frPwr, blPwr, brPwr, denominator, tortoisity, telescopicity;

        double exLIn = 0.14; //retracted position for the left slide
        double exLOut = 0.35; //extended position for the left slide
        double exRIn = 1; //retracted position for the right slide
        double exROut = 0.475; //extended position for the right slide

        waitForStart();

        //intake in and out
        while (opModeIsActive()) {
            if (gamepad2.b) {
                intakeLeft.setPower(-1);
                intakeRight.setPower(1);
            } else if (gamepad2.a) {
                intakeLeft.setPower(1);
                intakeRight.setPower(-1);
            } else {
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
            }

            //intake down and up
            if (gamepad2.right_bumper) {
                pitch.setPosition(1);
                roll.setPosition(-1);
            } else {
                pitch.setPosition(0.4);
                roll.setPosition(0.95);
            }

            //intake extend and retract
            telescopicity = gamepad2.right_trigger;
            if (telescopicity > 0) {
                extendLeft.setPosition(exLIn + (exLOut - exLIn) * telescopicity);
                extendRight.setPosition(exRIn + (exROut - exRIn) * telescopicity);
            } else {
                extendLeft.setPosition(exLIn);
                extendRight.setPosition(exRIn);
            }

            //outtake up and down
            if (gamepad2.left_bumper) {
                tortoisity = 0.7;
            } else {
                tortoisity = gamepad2.left_trigger;
            }

            if (gamepad2.dpad_down) {
                liftRight.setPower(1 - tortoisity);
                liftLeft.setPower(tortoisity - 1);
            } else if (gamepad2.dpad_up) {
                liftRight.setPower(tortoisity - 1);
                liftLeft.setPower(1 - tortoisity);
            } else {
                liftRight.setPower(0);
                liftLeft.setPower(0);
            }

            //outtake out and in
            if (gamepad2.x) {
                bucket.setPosition(0);
            } else {
                bucket.setPosition(1);
            }

            speed = gamepad1.left_stick_y * driveSpeed;
            strafe = gamepad1.left_stick_x * strafeSpeed;
            turn = gamepad1.right_stick_x + gamepad1.right_trigger - gamepad1.left_trigger;

            if (Math.abs(speed) < 0.1) {
                speed = 0;
            }
            if (Math.abs(turn) < 0.1) {
                turn = 0;
            } else if (Math.abs(turn) < 0.5) {
                turn = Math.signum(turn) * turnSpeed / 2;
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