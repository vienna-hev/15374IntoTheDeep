package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class RRHardware {
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

    public RRHardware(HardwareMap hardwareMap) {
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
        sleep(3000); //time the slide rises for to reach
        bucketServo.setPosition(1); //position that drops sample
        sleep(2000); //hopefully goes back after this time, position needs to be set again?
        upMotor.setPower(-1);
        sleep(1000); //goes back down for 1 sec
    }
//    public void moveIntake(double power) {
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
    public void wristUp() {
        wristLeft.setPosition(-.3);
        wristRight.setPosition(.3);
    }
    public void wristDown() {
        wristLeft.setPosition(.3);
        wristRight.setPosition(-.3);
    }
    public void elbowOut() {
        elbow.setPosition(.8);
    }
    public void elbowIn() {
        elbow.setPosition(.4);
    }
}
