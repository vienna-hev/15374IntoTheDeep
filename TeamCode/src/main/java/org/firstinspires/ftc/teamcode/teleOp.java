package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple; //???

@TeleOp

public class teleOp extends LinearOpMode {

    public DcMotor frontRight;
    public DcMotor frontLeft;
    public DcMotor backLeft;
    public DcMotor backRight;
//hh
    @Override
    public void runOpMode() {

        frontRight = hardwareMap.get(DcMotor.class, "right_front");

        frontLeft = hardwareMap.get(DcMotor.class, "left_front");

        backLeft = hardwareMap.get(DcMotor.class, "left_back");

        backRight = hardwareMap.get(DcMotor.class, "right_back");

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // frontRight.setDirection(DcMotorSimple.Direction.REVERSE); // ???

        double drivespeed = 0.8;


        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.right_bumper) {
                frontLeft.setPower(-drivespeed);
                frontRight.setPower(drivespeed);
                backLeft.setPower(-drivespeed);
                backRight.setPower(drivespeed);
            } else if (gamepad1.left_bumper) {
                frontLeft.setPower(drivespeed);
                frontRight.setPower(-drivespeed);
                backLeft.setPower(drivespeed);
                backRight.setPower(-drivespeed);
            } else if (gamepad1.left_stick_y > 0.9) {
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