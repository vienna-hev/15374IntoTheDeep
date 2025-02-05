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
    public TOHardware hardware;

    @Override
    public void runOpMode() {
        hardware = new TOHardware(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            hardware.funcIntake(gamepad2.a, gamepad2.b);
            hardware.funcWrist(gamepad2.right_bumper);
            hardware.funcExtend(gamepad2.right_trigger);
            hardware.funcLift(gamepad2.dpad_up, gamepad2.dpad_down, gamepad2.left_trigger, gamepad2.left_bumper);
            hardware.funcBucket(gamepad2.x);
            hardware.funcDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x + gamepad1.right_trigger - gamepad1.left_trigger, 1.0, 1.0, 0.5);
        }
    }
}