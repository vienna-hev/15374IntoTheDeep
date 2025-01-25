package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class AAunnovatedAuto extends LinearOpMode {
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

        Pose2d redHB = new Pose2d(-54, -46, Math.toRadians(45));

        driveToHB = drive.actionBuilder(initialPose)
                .splineToLinearHeading(redHB, Math.toRadians(270))
                .build();

        HBtoS1 = drive.actionBuilder(redHB)
//                .setTangent(Math.toRadians(45))
                .splineToLinearHeading(new Pose2d(-48, -46, Math.toRadians(90)), Math.toRadians(45)) //S1
                .build();

        forwardAfter = drive.actionBuilder(new Pose2d(-48, -46, Math.toRadians(88)))
                .turnTo(Math.toRadians(90))
                .lineToY(-42)
                .build();

        S1toHB = drive.actionBuilder(new Pose2d(-48, -50, Math.toRadians(90))) //S1
                .splineToLinearHeading(redHB, Math.toRadians(180))
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
                .splineTo(new Vector2d(-54, -46), Math.toRadians(45))
                .build();

        HBtoS3 = drive.actionBuilder(redHB)
                .splineTo(new Vector2d(-48, -18), Math.toRadians(180))
                .build();

        S3toHB = drive.actionBuilder(new Pose2d(-48, -18, Math.toRadians(180)))
                .strafeTo(new Vector2d(-55, -61))
                .strafeTo(new Vector2d(-55, -45))
                .build();

        HBtoBack = drive.actionBuilder(redHB)
                .splineTo(new Vector2d(-26, -10), Math.toRadians(360))
                .build();

        waitForStart();
        hardware.leftUpMotor.setPower(1); //START moving the lift upward before moving
        hardware.rightUpMotor.setPower(-1);
        Actions.runBlocking(driveToHB); //drive to the high basket while lift lifts
        hardware.leftUpMotor.setPower(0.5);
        hardware.rightUpMotor.setPower(-0.5);
        hardware.bucketServo.setPosition(0.3); //drop the preloaded sample
        sleep(1100L); //wait for the sample to fall
        hardware.bucketServo.setPosition(1); //reset the bucket to avoid hitting the baskets
        hardware.wristDown(); //lower the intake (after moving to stop it from dragging)
        hardware.leftUpMotor.setPower(-1); //START moving the lift downward
        hardware.rightUpMotor.setPower(1);
        hardware.intakeIn();//START intaking before moving to mitigate long delay
        hardware.intakeSlideIn();
        Actions.runBlocking(HBtoS1); //drive to the first sample while lift lowers and intake starts
        sleep(700L); //wait for the intake to lower
        Actions.runBlocking(forwardAfter); //move forward to eat the sample
        hardware.leftUpMotor.setPower(0);
        hardware.rightUpMotor.setPower(0); //stop the lift from lowering to mitigate the loppfasdhrfejgtk
        sleep(800L);
        hardware.wristUp(); //start lifting the intake
        hardware.intakeStop(); //stop intaking
        hardware.intakeSlideIn(); //ensure that the intake is aligned with the outtake
        sleep(3000L); //wait for intake to get positioned for transfer to outtake
        hardware.intakeOut();
        sleep(3000L);

    }
}

//robot dimensions: 17.5 wide x 10 long to center