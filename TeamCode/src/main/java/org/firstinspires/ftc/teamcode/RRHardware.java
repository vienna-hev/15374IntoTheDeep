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
    public DcMotorEx upMotor;
    public CRServo intakeLeft;
    public CRServo intakeRight;
    public Servo wristRight;
    public Servo wristLeft;
    public Servo wristFront;
    public Servo elbow;
    public Servo bucketServo;

    public RRHardware(HardwareMap hardwareMap) {
        frontRight = hardwareMap.get(DcMotor.class, "FR");
        frontLeft = hardwareMap.get(DcMotor.class, "FL");
        backLeft = hardwareMap.get(DcMotor.class, "BL");
        backRight = hardwareMap.get(DcMotor.class, "BR");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        upMotor = hardwareMap.get(DcMotorEx.class, "UM");

        intakeLeft = hardwareMap.get(CRServo.class, "ISL");
        intakeRight = hardwareMap.get(CRServo.class, "ISR");
        wristRight = hardwareMap.get(Servo.class, "WR");
        wristLeft = hardwareMap.get(Servo.class, "WL");
        elbow = hardwareMap.get(Servo.class, "ES");
        bucketServo = hardwareMap.get(Servo.class, "BS");
        wristFront = hardwareMap.get(Servo.class, "WF");

        upMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        upMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void depositHB() {
        upMotor.setPower(1);
        sleep(1900); //time the slide rises for to reach
        bucketServo.setPosition(.5); //position that drops sample
        sleep(1600); //hopefully goes back after this time, position needs to be set again?
        bucketServo.setPosition(0); //reset the bucket so it does not hit the baskets
        upMotor.setPower(-1);
        sleep(2000); //goes back down for 1 sec
    }

    public void intakeIn() {
        intakeLeft.setPower(-1);
        intakeRight.setPower(1);
        sleep(1500);
    }

    public void intakeOut() {
        intakeLeft.setPower(1);
        intakeRight.setPower(-1);
        sleep(1500);
    }

    public void turnDown(double power) {
        wristFront.setPosition(0);
        sleep(1000);
    }

    public void turnUp(double power) {
        wristFront.setPosition(1);
        sleep(1000);
    }

    public void wristDown() {
        wristFront.setPosition(1);
        wristLeft.setPosition(-.3);
        wristRight.setPosition(.3);
        sleep(2000);
    }

    public void wristUp() {
        wristFront.setPosition(0);
        wristLeft.setPosition(-.77);
        wristRight.setPosition(.77);
        sleep(3000);
    }

    public void intakeSlideOut() {
        elbow.setPosition(.8);
    }

    public void intakeSlideIn() {
        elbow.setPosition(.4);
    }
}


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