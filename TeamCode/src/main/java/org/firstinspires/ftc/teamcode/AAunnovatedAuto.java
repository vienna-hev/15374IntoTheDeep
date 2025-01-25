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

        Pose2d redHB = new Pose2d(-53, -47, Math.toRadians(45));

        driveToHB = drive.actionBuilder(initialPose)
                .splineToLinearHeading(redHB, Math.toRadians(270))
                .build();

        HBtoS1 = drive.actionBuilder(redHB)
                .splineToLinearHeading(new Pose2d(-48, -50, Math.toRadians(90)), Math.toRadians(45)) //S1
                .build();

        forwardAfter = drive.actionBuilder(new Pose2d(-48, -50, Math.toRadians(90)))
                .lineToY(-42)
                .build();

        S1toHB = drive.actionBuilder(new Pose2d(-48, -42, Math.toRadians(90))) //S1
                .splineToLinearHeading(redHB, Math.toRadians(180))
                .build();

        HBtoS2 = drive.actionBuilder(redHB)
                .splineToLinearHeading(new Pose2d(-58, -49, Math.toRadians(90)), Math.toRadians(90))
                .build();

        forwardAfter2 = drive.actionBuilder(new Pose2d(-58, -49, Math.toRadians(90)))
                .lineToY(-42)
                .build();

        S2toHB = drive.actionBuilder(new Pose2d(-58, -42, Math.toRadians(90)))
                .splineToLinearHeading(redHB, Math.toRadians(225))
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

        //more time before going up asfter intaking, too fast doesnt pick up
        waitForStart();
        hardware.liftPower(1); //START moving the lift
        Actions.runBlocking(driveToHB); //drive to the high basket
        hardware.leftUpMotor.setPower(0.5);
        hardware.rightUpMotor.setPower(-0.5);
        hardware.bucketServo.setPosition(0.3); //drop preloaded sample
        sleep(1000L); //wait for sample to fall
        hardware.bucketServo.setPosition(1); //reset bucket
        hardware.wristDown();
        hardware.liftPower(-1); //START moving the lift downward
        hardware.intakeIn();//START intaking before moving to mitigate long delay

        Actions.runBlocking(HBtoS1); //drive to the first sample while lift lowers and intake starts
        hardware.intakeSlideIn();
//        sleep(700L); //wait for intake to lower, maybe remove?
        Actions.runBlocking(forwardAfter); //move forward to eat the sample
        sleep(400L); //why sleep? intake?
        hardware.liftPower(0); //stop the lift to mitigate the loppfasdhrfejgtk
        hardware.wristUp(); //start lifting the intake
        hardware.intakeSlideIn(); //ensure that the intake is aligned with the outtake
        hardware.intakeStop(); //stop intaking
        sleep(400L); //wait for intake to position for transfer
        hardware.intakeOut();
        sleep(500L);
        hardware.liftPower(1);

        Actions.runBlocking(S1toHB);
        sleep(800L); //wait for lift?
        hardware.leftUpMotor.setPower(0.5);
        hardware.rightUpMotor.setPower(-0.5);
        hardware.bucketServo.setPosition(0.3); //drop sample
        sleep(1000L); //wait for sample to fall, maybe less?
        hardware.bucketServo.setPosition(1); //reset bucket (no hit basket)
        hardware.liftPower(-1); //START moving the lift downward
        hardware.intakeIn();//START intaking before moving to mitigate long delay

        Actions.runBlocking(HBtoS2); //drive to second sample while lift lowers and intake starts
        hardware.intakeSlideIn();
        hardware.wristDown();
//        sleep(700L); //why?
        Actions.runBlocking(forwardAfter2); //move forward to eat sample
        sleep(400L); //why
        hardware.liftPower(0); //stop lift to mitigate the loppfasdhrfejgtk
        hardware.wristUp(); //start lifting intake to bucket
        hardware.intakeSlideIn(); //ensure that the intake is aligned with the outtake
        hardware.intakeStop(); //stop intaking
        sleep(800L); //wait for intake to get positioned for transfer to outtake
        hardware.intakeOut();
        sleep(500L);
        hardware.liftPower(1);

        Actions.runBlocking(S2toHB);
        sleep(800L);
        hardware.leftUpMotor.setPower(0.5);
        hardware.rightUpMotor.setPower(-0.5);
        hardware.bucketServo.setPosition(0.3); //drop the preloaded sample
        sleep(1000L); //wait for the sample to fall
        hardware.bucketServo.setPosition(1); //reset the bucket to avoid hitting the baskets
        hardware.wristDown(); //lower the intake (after moving to stop it from dragging)
        hardware.liftPower(-1); //START moving the lift downward
//        hardware.intakeIn();//START intaking before moving to mitigate long delay
        hardware.intakeSlideIn();

        sleep(2000); //maybe the end?
    }
}

//robot dimensions: 17.5 wide x 10 long to center