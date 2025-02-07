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
    public Servo intakeExtensionR;
    public Servo intakeExtensionL;
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
        intakeLeft = hardwareMap.get(CRServo.class, "intakeLeft");
        intakeRight = hardwareMap.get(CRServo.class, "intakeRight");
        intakeExtensionL = hardwareMap.get(Servo.class, "intakeExtensionLeft");
        intakeExtensionR = hardwareMap.get(Servo.class, "intakeExtensionRight");
        bucketServo = hardwareMap.get(Servo.class, "bucketServo");
        wFold = hardwareMap.get(Servo.class, "WL");
        wTurn = hardwareMap.get(Servo.class, "WF");

        leftUpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightUpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightUpMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftUpMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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
        bucketServo.setPosition(.1);
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
        leftUpMotor.setTargetPosition(-2500);
        rightUpMotor.setTargetPosition(2500);
        leftUpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightUpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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

    public void intakeSlideOut() {
        intakeExtensionL.setPosition(0.52);
        intakeExtensionR.setPosition(0.44);
    }

    public void intakeSlideOutLess() {
        intakeExtensionL.setPosition(0.40);
        intakeExtensionR.setPosition(0.56);
    }

    public void intakeSlideIn() {
        intakeExtensionL.setPosition(0.3);
        intakeExtensionR.setPosition(.66);
    }
}

//        public void turnDown(double power) {
//        wTurn.setPosition(0);
//        sleep(1000);
//    }
//
//    public void turnUp(double power) {
//        wTurn.setPosition(1);
//        sleep(1000);
//    }

//public void moveIntake(double power) {
//        for (int x = 8; x > 0; x--){
//            if(!touchSensor.isPressed()){
//                intakeLeft.setPower(-power);
//                intakeRight.setPower(power);
//                sleep(1000);
//            }
//            else {
//                intakeLeft.setPower(0);
//                intakeRight.setPower(0);
//                break;
//            }
//        }
//
////        while (!touchSensor.isPressed()){
////            intakeLeft.setPower(-power);
////            intakeRight.setPower(power);
////        }
//    }
    //.4 to intake, -.2 to outtake