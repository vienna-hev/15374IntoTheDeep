package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class RRHardware {
    public DcMotor frontRight;
    public DcMotor frontLeft;
    public DcMotor backLeft;
    public DcMotor backRight;
    public DcMotorEx leftUpMotor;
    public DcMotorEx rightUpMotor;
    public CRServo intakeLeft;
    public CRServo intakeRight;
    public Servo wFold;
    public Servo wTurn;
    public DcMotor intakeExtension;
    public Servo bucketServo;

    public RRHardware(HardwareMap hardwareMap) {
        frontRight = hardwareMap.get(DcMotor.class, "FR");
        frontLeft = hardwareMap.get(DcMotor.class, "FL");
        backLeft = hardwareMap.get(DcMotor.class, "BL");
        backRight = hardwareMap.get(DcMotor.class, "BR");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);


        leftUpMotor = hardwareMap.get(DcMotorEx.class, "LUM");
        rightUpMotor = hardwareMap.get(DcMotorEx.class, "RUM");
        intakeLeft = hardwareMap.get(CRServo.class, "IWL");
        intakeRight = hardwareMap.get(CRServo.class, "IWR");
        intakeExtension = hardwareMap.get(DcMotor.class, "IE");
        bucketServo = hardwareMap.get(Servo.class, "BS");
        wFold = hardwareMap.get(Servo.class, "WL");
        wTurn = hardwareMap.get(Servo.class, "WF");

        intakeExtension.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftUpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightUpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightUpMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftUpMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        intakeExtension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeExtension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double ticks = 384.5D;
    }

    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void bucketDown() {
        bucketServo.setPosition(.4);
    }

    public void depositHB() {
    leftUpMotor.setPower(1);
    rightUpMotor.setPower(1);
    sleep(2500); //time the slide rises for to reach
    bucketServo.setPosition(.5); //position that drops sample
    sleep(1600); //hopefully goes back after this time, position needs to be set again?
    bucketServo.setPosition(0); //reset the bucket so it does not hit the baskets
    leftUpMotor.setPower(-1);
    rightUpMotor.setPower(-1);
    }

    public void liftGoUp() {
        leftUpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightUpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftUpMotor.setPower(1);
        rightUpMotor.setPower(-1);
        leftUpMotor.setTargetPosition(3150);
        rightUpMotor.setTargetPosition(-3150);
        leftUpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightUpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void liftGoDown() {
        leftUpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightUpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftUpMotor.setPower(-1);
        rightUpMotor.setPower(1);
        leftUpMotor.setTargetPosition(-3150);
        rightUpMotor.setTargetPosition(3150);
        leftUpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightUpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void liftGoPark() {
        leftUpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightUpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftUpMotor.setPower(-1);
        rightUpMotor.setPower(1);
        leftUpMotor.setTargetPosition(-2560);
        rightUpMotor.setTargetPosition(2560);
        leftUpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightUpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void liftPower(double liftPwr) {
        leftUpMotor.setPower(liftPwr);
        rightUpMotor.setPower(-liftPwr);
    }

    public void intakeOut() {
        intakeLeft.setPower(-1);
        intakeRight.setPower(1);
    }

    public void intakeIn() {
        intakeLeft.setPower(1);
        intakeRight.setPower(-1);
    }

    public void intakeStop() {
        intakeLeft.setPower(0);
        intakeRight.setPower(0);
    }

    public void wristDown() {
        wFold.setPosition(1);
        wTurn.setPosition(-1);
    }

    public void wristUp() {
        wFold.setPosition(.425);
        wTurn.setPosition(.95);
    }

    public void intakeSlideOutLess() {
        intakeExtension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeExtension.setPower(-1);
        intakeExtension.setTargetPosition(-140);
        intakeExtension.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void intakeSlideIn() {
        intakeExtension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeExtension.setPower(1);
        intakeExtension.setTargetPosition(140);
        intakeExtension.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}