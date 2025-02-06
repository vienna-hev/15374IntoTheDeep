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
    public TOHardware hardware;

    @Override
    public void runOpMode() {
        hardware = new TOHardware(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            hardware.funcIntake(gamepad2.dpad_right, gamepad2.dpad_left);
            hardware.funcWrist(gamepad2.left_stick_y > 0.5);
            hardware.funcExtend(gamepad2.right_stick_x);
            hardware.funcLift(gamepad2.y, gamepad2.x, gamepad2.left_trigger, gamepad2.left_bumper);
            hardware.funcBucket(gamepad2.right_trigger > 0.5);
            hardware.funcDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, (gamepad1.right_stick_x + gamepad1.right_trigger) - gamepad1.left_trigger,
                    1.0, 1.0, 0.6);
        }
    }
}