package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorDigitalTouch;

@Autonomous
public class AutoTestRL extends LinearOpMode {
    public DcMotor upMotor;
    public CRServo intakeLeft;
    public CRServo intakeRight;
    public Servo wrist;
    public Servo elbow;
    public Servo bucketServo;
    Action driveToHB;
    Action driveToS1;
    Action S1toHB;
//    public TouchSensor touchSensor;
//    TouchSensor touchSensor = hardwareMap.get(TouchSensor.class, "touchSensor");

    public void depositHB() {
        upMotor.setPower(1);
        sleep(3000); //time the slide rises for to reach
        bucketServo.setPosition(1); //position that drops sample
        sleep(2000); //hopefully goes back after this time, position needs to be set again?
        upMotor.setPower(-1);
        sleep(1000); //goes back down for 1 sec
    }
    public void moveIntake(double power) {
//        while (!touchSensor.isPressed()){
//            intakeLeft.setPower(-power);
//            intakeRight.setPower(power);
//        }
        intakeLeft.setPower(-power);
        intakeRight.setPower(power);
    }
    //.4 to go intake, -.2 to outtake
    public void wristUp() {
        wrist.setPosition(.3);
    }
    public void wristDown() {
        wrist.setPosition(.8);
    }
    public void elbowOut() {
        elbow.setPosition(.8);
    }
    public void elbowIn() {
        elbow.setPosition(.4);
    }

    @Override
    public void runOpMode() {
        //start with sample, deposit in HB
        //drive to sample (x3) line, intake sample
        //deposit in HB and park at submersible
        Pose2d initialPose = new Pose2d(-15.25, -62, Math.toRadians(90));
        //x start position is left side aligned with tile side, against the back, facing forward
        PinpointDrive drive = new PinpointDrive(hardwareMap, initialPose);

        Pose2d redHB = new Pose2d(-60, -20.25, Math.toRadians(225));


        driveToHB = drive.actionBuilder(initialPose)
                .splineTo(new Vector2d(-60, -20.25), Math.toRadians(225)) //HB
                //y vector coordinate may be too high
                .build();

        driveToS1 = drive.actionBuilder(redHB)
                .splineTo(new Vector2d(-49.5, -46), Math.toRadians(90)) //S1
                .build();

        S1toHB = drive.actionBuilder(new Pose2d(-49.5, -46, Math.toRadians(90))) //S1
                .splineTo(new Vector2d(-60, -20.25), Math.toRadians(225)) //HB
                .build();

        waitForStart();
        Actions.runBlocking(driveToHB);
        depositHB();
        Actions.runBlocking(driveToS1);
        wristDown();
        elbowOut();
        moveIntake(.4); //intake
        wristUp();
        elbowIn(); //???
        Actions.runBlocking(S1toHB);
        depositHB();
    }
}

//robot dimensions: 17.5 wide x 10 long to center