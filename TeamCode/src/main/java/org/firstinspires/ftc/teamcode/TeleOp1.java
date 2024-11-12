package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp

public class TeleOp1 extends LinearOpMode {

        public DcMotor frontRight;
        public DcMotor frontLeft;
        public DcMotor backLeft;
        public DcMotor backRight;

        @Override
        public void runOpMode() {

                frontRight = hardwareMap.get(DcMotor.class, "frontRight");

                frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");

                backLeft = hardwareMap.get(DcMotor.class, "backLeft");

                backRight = hardwareMap.get(DcMotor.class, "backRight");

                frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

                double drivespeed = 0.8;


                waitForStart();

                while (opModeIsActive()) {
            if (gamepad1.right_bumper) {
                frontLeft.setPower(-drivespeed);
                frontRight.setPower(drivespeed);
                backLeft.setPower(-drivespeed);
                backRight.setPower(drivespeed);
            }
            else if (gamepad1.left_bumper) {
                frontLeft.setPower(drivespeed);
                frontRight.setPower(-drivespeed);
                backLeft.setPower(drivespeed);
                backRight.setPower(-drivespeed);
            }

            else if (gamepad1.left_stick_y > 0.9) {
                frontLeft.setPower(drivespeed);
                frontRight.setPower(drivespeed);
                backLeft.setPower(drivespeed);
                backRight.setPower(drivespeed);
            }
            else if (gamepad1.left_stick_y < -0.9) {
                frontLeft.setPower(-drivespeed);
                frontRight.setPower(-drivespeed);
                backLeft.setPower(-drivespeed);
                backRight.setPower(-drivespeed);
            }

            else if (gamepad1.left_stick_x > 0.9) {
                frontLeft.setPower(-drivespeed);
                frontRight.setPower(drivespeed);
                backLeft.setPower(drivespeed);
                backRight.setPower(-drivespeed);
            }
            else if (gamepad1.left_stick_x < -0.9) {
                frontLeft.setPower(drivespeed);
                frontRight.setPower(-drivespeed);
                backLeft.setPower(-drivespeed);
                backRight.setPower(drivespeed);
            }
            else {
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