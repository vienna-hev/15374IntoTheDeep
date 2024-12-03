package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class AutoTest extends LinearOpMode {
    public DcMotor frontRight;
    public DcMotor frontLeft;
    public DcMotor backLeft;
    public DcMotor backRight;
    public DcMotor upMotor;
    public CRServo intakeLeft;
    public CRServo intakeRight;
    public Servo wrist;
    public Servo elbow;
    public Servo bucketServoL;

    public void depositHB() {
        upMotor.setPower(1);
        sleep(3000); //time the slide rises for to reach
        bucketServoL.setPosition(1); //position that drops sample
        sleep(2000); //hopefully goes back after this time, position needs to be set again?
        upMotor.setPower(-1);
        sleep(1000); //goes back down for 1 sec
    }

    @Override
    public void runOpMode() {
    //start with sample, deposit in HB
    //drive to sample (x3) line, intake sample
    //deposit in HB and park?
    }
}