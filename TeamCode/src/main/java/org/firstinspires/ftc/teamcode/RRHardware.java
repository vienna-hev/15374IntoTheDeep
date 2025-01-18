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

        upMotor = hardwareMap.get(DcMotorEx.class, "UM");

        intakeLeft = hardwareMap.get(CRServo.class, "ISL");
        intakeRight = hardwareMap.get(CRServo.class, "ISR");
        wristRight = hardwareMap.get(Servo.class, "WR");
        wristLeft = hardwareMap.get(Servo.class, "WL");
        intakeExtensionL = hardwareMap.get(Servo.class, "IEL");
        intakeExtensionR = hardwareMap.get(Servo.class, "IER");
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
        sleep(2500); //time the slide rises for to reach
        bucketServo.setPosition(.5); //position that drops sample
        sleep(1600); //hopefully goes back after this time, position needs to be set again?
        bucketServo.setPosition(0); //reset the bucket so it does not hit the baskets
        upMotor.setPower(-1);
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
        wristFront.setPosition(1);
        wristLeft.setPosition(-.25);
        wristRight.setPosition(.25);
    }

    public void wristUp() {
        wristFront.setPosition(0);
        wristLeft.setPosition(-.79);
        wristRight.setPosition(.79);
    }

    public void intakeSlideOut() {
        intakeExtensionL.setPosition(.8);
        intakeExtensionR.setPosition(-.8);
    }

    public void intakeSlideIn() {
        intakeExtensionL.setPosition(.4);
        intakeExtensionR.setPosition(-.4);    }
}

//        public void turnDown(double power) {
//        wristFront.setPosition(0);
//        sleep(1000);
//    }
//
//    public void turnUp(double power) {
//        wristFront.setPosition(1);
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