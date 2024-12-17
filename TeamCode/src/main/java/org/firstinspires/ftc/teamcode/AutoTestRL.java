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

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Autonomous
public class AutoTestRL extends LinearOpMode {
    Action driveToHB;
    Action HBtoS1;
    Action S1toHB;
    Action HBtoS2;
    Action S2toHB;
    Action depositHB;
    //    Action HBtoS3;
//    Action S3toHB;
    Action HBtoPark;
    public RRHardware hardware;

    @Override
    public void runOpMode() {
        hardware = new RRHardware(hardwareMap);
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
        hardware.depositHB(); //error
        Actions.runBlocking(HBtoS1);
        hardware.wristDown();
        hardware.elbowOut();
     //   moveIntake(.4); //intake
        hardware.wristUp();
        hardware.elbowIn(); //???
        Actions.runBlocking(S1toHB);
        hardware.depositHB();
        Actions.runBlocking(HBtoS2);
        hardware.wristDown();
        hardware.elbowOut();
     //   moveIntake(.4); //intake
        hardware.wristUp();
        hardware.elbowIn();
        Actions.runBlocking(S2toHB);
        hardware.depositHB();
        Actions.runBlocking(HBtoPark);
    }
}

//robot dimensions: 17.5 wide x 10 long to center