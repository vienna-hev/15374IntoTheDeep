package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class AutoLeft extends LinearOpMode {
    Action driveToHB;
    Action HBtoS1;
    Action S1toHB;
    Action forwardAfter;
    Action forwardAfter2;
    Action HBtoS2;
    Action S2toHB;
    Action HBtoS3;
    Action S3toHB;
    Action HBtoBack;
    public RRHardware hardware;

    @Override
    public void runOpMode() {
        hardware = new RRHardware(hardwareMap);
        //start with sample, deposit in HB
        //drive to sample (x3) line, intake sample
        //deposit in HB and park at submersible
        Pose2d initialPose = new Pose2d(-15.25, -61, Math.toRadians(90));
        //x start position is left side aligned with tile side, against the back, facing forward
        PinpointDrive drive = new PinpointDrive(hardwareMap, initialPose);

        Pose2d redHB = new Pose2d(-53, -50, Math.toRadians(45));

        driveToHB = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(-53, -50, Math.toRadians(45)), Math.toRadians(270))
                .build();

        HBtoS1 = drive.actionBuilder(redHB)
                .splineToLinearHeading(new Pose2d(-48, -52, Math.toRadians(88)), Math.toRadians(45)) //S1
                .build();

        forwardAfter = drive.actionBuilder(new Pose2d(-48, -52, Math.toRadians(88)))
                .turnTo(Math.toRadians(92))
                .turnTo(Math.toRadians(88))
                .turnTo(Math.toRadians(90))
                .lineToY(-42)
                .build();

        S1toHB = drive.actionBuilder(new Pose2d(-42, -49, Math.toRadians(90))) //S1
                .splineToLinearHeading(new Pose2d(-53, -50, Math.toRadians(45)), Math.toRadians(180))
                .build();

        HBtoS2 = drive.actionBuilder(redHB)
                .splineTo(new Vector2d(-58, -49), Math.toRadians(88))
                .build();

        forwardAfter2 = drive.actionBuilder(new Pose2d(-58, -49, Math.toRadians(88)))
//                .turnTo(Math.toRadians(95))
//                .turnTo(Math.toRadians(85))
//                .turnTo(Math.toRadians(90))
                .lineToY(-42)
                .build();

        S2toHB = drive.actionBuilder(new Pose2d(-58, -42, Math.toRadians(90)))
                .splineTo(new Vector2d(-53, -50), Math.toRadians(45))
                .build();

        HBtoS3 = drive.actionBuilder(redHB)
                .splineTo(new Vector2d(-48, -18), Math.toRadians(180))
                .build();

        S3toHB = drive.actionBuilder(new Pose2d(-48, -18, Math.toRadians(180)))
                .strafeTo(new Vector2d(-53, -61))
                .strafeTo(new Vector2d(-53, -55))
                .build();

        HBtoBack = drive.actionBuilder(redHB)
                .splineTo(new Vector2d(-26, -10), Math.toRadians(360))
                .build();

        waitForStart();
        Actions.runBlocking(driveToHB);
        hardware.depositHB();
        Actions.runBlocking(HBtoS1);
        hardware.upMotor.setPower(-1);
        hardware.intakeIn();
        hardware.wristDown();
        sleep(2000);
        Actions.runBlocking(forwardAfter); //intake in MORE after?
        hardware.wristUp();
        hardware.intakeStop();
        hardware.intakeSlideIn();
        sleep(3000);
        hardware.intakeOut();
        Actions.runBlocking(S1toHB);
        hardware.depositHB();
        hardware.intakeStop();
        hardware.upMotor.setPower(-1);
        sleep(1900);
//        Actions.runBlocking(HBtoS2);
//        hardware.intakeIn();
//        hardware.wristDown();
//        Actions.runBlocking(forwardAfter2);
//        hardware.wristUp();
//        hardware.intakeStop();
//        hardware.intakeSlideIn();
//        hardware.intakeOut();
//        hardware.intakeStop();
//        Actions.runBlocking(S2toHB);
//        hardware.depositHB();
        Actions.runBlocking(HBtoS3);
        Actions.runBlocking(S3toHB);
    }
}

//robot dimensions: 17.5 wide x 10 long to center