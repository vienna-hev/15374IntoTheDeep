package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple; //???
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class VariablePowerExample extends LinearOpMode {
    public DcMotor frontRight;
    public DcMotor frontLeft;
    public DcMotor backLeft;
    public DcMotor backRight;
    public DcMotor upMotor;
    public CRServo intakeLeft;
    public CRServo intakeRight;
    public Servo wristRight;
    public Servo wristLeft;
    public Servo wristFront;
    public Servo elbow;
    public Servo bucketServo;

    @Override
    public void runOpMode() {

        frontRight = hardwareMap.get(DcMotor.class, "FR");
        frontLeft = hardwareMap.get(DcMotor.class, "FL");
        backLeft = hardwareMap.get(DcMotor.class, "BL");
        backRight = hardwareMap.get(DcMotor.class, "BR");

        upMotor = hardwareMap.get(DcMotor.class, "UM");

        intakeLeft = hardwareMap.get(CRServo.class, "ISL");
        intakeRight = hardwareMap.get(CRServo.class, "ISR");
        wristRight = hardwareMap.get(Servo.class, "WR");
        wristLeft = hardwareMap.get(Servo.class, "WL");
        elbow = hardwareMap.get(Servo.class, "ES");
        bucketServo = hardwareMap.get(Servo.class, "BS");
        wristFront = hardwareMap.get(Servo.class, "WF");


        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        upMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        //frontRight.setDirection(DcMotorSimple.Direction.REVERSE); ///undo???
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        //backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        double drivespeed = -0.5;
        double turnSpeed = -1;

        double rotationPwr;


        waitForStart();

        while (opModeIsActive()) {
            rotationPwr = gamepad2.right_trigger - gamepad2.left_trigger;

            frontLeft.setPower(rotationPwr);
            frontRight.setPower(-rotationPwr);
            backLeft.setPower(rotationPwr);
            backRight.setPower(-rotationPwr);
        }
    }
}