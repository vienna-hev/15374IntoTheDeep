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
        double pwrDec;

        waitForStart();

        while (opModeIsActive()) {
            hardware.funcIntake(gamepad2.right_bumper, gamepad2.left_bumper);
            hardware.funcWrist(gamepad2.b);
            hardware.funcExtend(gamepad2.right_trigger - gamepad2.left_trigger);
            pwrDec = Math.hypot(gamepad2.left_stick_x, gamepad2.left_stick_y);
            hardware.funcLift(gamepad2.y, gamepad2.a, pwrDec, gamepad2.left_stick_button);
            hardware.funcBucket(gamepad2.x);
            hardware.funcDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x + gamepad1.right_trigger - gamepad1.left_trigger, 1.0, 1.0, 0.6);
        }
    }
}