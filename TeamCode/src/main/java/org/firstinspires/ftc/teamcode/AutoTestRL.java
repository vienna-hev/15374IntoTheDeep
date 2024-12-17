package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@Autonomous
public class AutoTestRL extends LinearOpMode {
    public DcMotor upMotor;
    public CRServo intakeLeft;
    public CRServo intakeRight;
    public Servo wrist;
    public Servo elbow;
    public Servo bucketServo;
    Action driveToHB;
    Action HBtoS1;
    Action S1toHB;
    Action HBtoS2;
    Action S2toHB;
    //    Action HBtoS3;
//    Action S3toHB;
    Action HBtoPark;
    public TouchSensor touchSensor;
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
        for (int x = 8; x > 0; x--){
            if(!touchSensor.isPressed()){
                intakeLeft.setPower(-power);
                intakeRight.setPower(power);
                sleep(1000);
            }
            else {
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
                break;
            }
        }

//        while (!touchSensor.isPressed()){
//            intakeLeft.setPower(-power);
//            intakeRight.setPower(power);
//        }
    }
    //.4 to intake, -.2 to outtake
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

        Pose2d redHB = new Pose2d(-56, -52, Math.toRadians(225));

        driveToHB = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(-56, -52, Math.toRadians(90)), Math.toRadians(225)) //HB
                //y vector coordinate may be too high
                .build();

        HBtoS1 = drive.actionBuilder(redHB)
                .splineTo(new Vector2d(-49.5, -46), Math.toRadians(90)) //S1
                .build();

        S1toHB = drive.actionBuilder(new Pose2d(-49.5, -46, Math.toRadians(90))) //S1
                .splineTo(new Vector2d(-56, -52), Math.toRadians(225)) //HB
                .build();

        HBtoS2 = drive.actionBuilder(redHB)
                .splineTo(new Vector2d(-58, -46), Math.toRadians(90))
                .build();

        S2toHB = drive.actionBuilder(new Pose2d(-58, -46, Math.toRadians(90)))
                .splineTo(new Vector2d(-56, -52), Math.toRadians(225))
                .build();

//        HBtoS3 = drive.actionBuilder(redHB)
//                .splineTo(new Vector2d(-63, -46), Math.toRadians(90))
//                .build();
//
//        S3toHB = drive.actionBuilder(new Pose2d(-63, -46, Math.toRadians(90)))
//                .splineTo(new Vector2d(-56, -52), Math.toRadians(225))
//                .build();

        HBtoPark = drive.actionBuilder(redHB)
                .splineTo(new Vector2d(-26, -10), Math.toRadians(360))
                .build();

        waitForStart();
        Actions.runBlocking(driveToHB);
        depositHB(); //error
        Actions.runBlocking(HBtoS1);
        wristDown();
        elbowOut();
        moveIntake(.4); //intake
        wristUp();
        elbowIn(); //???
        Actions.runBlocking(S1toHB);
        depositHB();
        Actions.runBlocking(HBtoS2);
        wristDown();
        elbowOut();
        moveIntake(.4); //intake
        wristUp();
        elbowIn(); //???
        Actions.runBlocking(S2toHB);
        depositHB();
        Actions.runBlocking(HBtoPark);
    }
}

//robot dimensions: 17.5 wide x 10 long to center