package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ZZOneDrive extends LinearOpMode {
    public TWOHardware hardware;

    @Override
    public void runOpMode() {
        hardware = new TWOHardware(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            hardware.funcIntake(gamepad1.a, gamepad1.b);
            hardware.funcWrist(gamepad1.right_bumper);
            hardware.funcExtend(gamepad1.right_trigger, gamepad1.y);
            hardware.funcLift(gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.left_trigger, gamepad1.left_bumper);
            hardware.funcBucket(gamepad1.x);
            hardware.funcDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, 1.0, 1.0, 0.5);
        }
    }
}