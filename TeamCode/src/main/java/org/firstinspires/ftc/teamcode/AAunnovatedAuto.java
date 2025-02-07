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
    Action forwardAfter;
    Action S1toHB;
    Action HBtoS2;
    Action S2toHB;
    Action HBtoS3;
    Action S3toSpin;
    Action S3toHB;
    Action HBtoPark;
    public RRHardware hardware;

    @Override
    public void runOpMode() {
        hardware = new RRHardware(hardwareMap);
        //start with sample, deposit in HB
        //drive to sample (x3) line, intake sample
        //deposit in HB and park at submersible
        Pose2d initialPose = new Pose2d(-15, -63.5, Math.toRadians(90));

        //x start position is left side aligned with tile side, against the back, facing forward
        PinpointDrive drive = new PinpointDrive(hardwareMap, initialPose); //all HB pos: (x-1, y-1)

        driveToHB = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(-60, -50, Math.toRadians(45)), Math.toRadians(180)) //tangent changed 1/26 (270 --> 295)
                .build();

        HBtoS1 = drive.actionBuilder(new Pose2d(-60, -50, Math.toRadians(45)))
                .splineToLinearHeading(new Pose2d(-47.7, -50, Math.toRadians(90)), Math.toRadians(45)) //S1
                .build();

        forwardAfter = drive.actionBuilder(new Pose2d(-47.7, -50, Math.toRadians(90)))
                .lineToY(-45)
                .build();

        S1toHB = drive.actionBuilder(new Pose2d(-47.7, -50, Math.toRadians(90))) //S1
                .splineToLinearHeading(new Pose2d(-57, -51, Math.toRadians(45)), Math.toRadians(225)) //tangent changed 1/26 (180 --> 270)
                .build();

        HBtoS2 = drive.actionBuilder(new Pose2d(-57, -51, Math.toRadians(45)))
                .splineToLinearHeading(new Pose2d(-59, -47, Math.toRadians(91)), Math.toRadians(180)) //tangent changed 1/26 (90 --> 180)
                .strafeTo(new Vector2d(-59, -44))
                .build();

        S2toHB = drive.actionBuilder(new Pose2d(-59, -44, Math.toRadians(91)))
                .splineToLinearHeading(new Pose2d(-55, -54, Math.toRadians(45)), Math.toRadians(225))
                .build();

        HBtoS3 = drive.actionBuilder(new Pose2d(-55, -54, Math.toRadians(45))) //uh oh this will probably break the robot
                .splineToLinearHeading(new Pose2d(-54.5, -53, Math.toRadians(120)), Math.toRadians(120))
                .build();

        S3toSpin = drive.actionBuilder(new Pose2d(-54.5, -53, Math.toRadians(118)))
                .turnTo(Math.toRadians(115))
                .turnTo(Math.toRadians(118))
                .build();

        S3toHB = drive.actionBuilder(new Pose2d(-54.5, -52, Math.toRadians(118)))
                .splineToLinearHeading(new Pose2d(-58, -55, Math.toRadians(35)), Math.toRadians(0))
                .build();

        HBtoPark = drive.actionBuilder(new Pose2d(-58, -55, Math.toRadians(35)))
                .splineToLinearHeading(new Pose2d(-20, -26, Math.toRadians(180)), Math.toRadians(360))
                .lineToX(-7)
                .build();

        waitForStart();
        hardware.bucketServo.setPosition(.3);
        hardware.bucketServo.setPosition(.7);
        hardware.liftGoUp(); //START moving the lift
        Actions.runBlocking(driveToHB); //drive to the high basket
        hardware.bucketDown();
        sleep(800L); //wait for sample to fall
        hardware.bucketServo.setPosition(.7); //reset bucket (no hit basket)
        hardware.wristDown();
        hardware.liftGoDown(); //START moving the lift downward
        hardware.intakeIn();//START intaking before moving to mitigate long delay

        hardware.intakeSlideIn();
        sleep(500);
        Actions.runBlocking(HBtoS1); //drive to the first sample while lift lowers and intake starts
        Actions.runBlocking(forwardAfter);
        sleep(300L); //wait for intake to lower, maybe remove?
        hardware.wristUp(); //START transfer of sample
        hardware.intakeSlideIn(); //ensure that intake/outtake are aligned
        hardware.intakeStop(); //stop intaking
        sleep(900L);
        hardware.intakeOut();
        sleep(900L);
        hardware.intakeStop();
        hardware.liftGoUp();

        Actions.runBlocking(S1toHB);
        sleep(700L); //this long to wait for lift?
        hardware.bucketServo.setPosition(0.3); //drop sample
        sleep(1000L); //wait for sample to fall
        hardware.wristDown();
        hardware.bucketServo.setPosition(.7); //reset bucket (no hit basket)
        hardware.liftGoDown(); //START moving the lift downward
        hardware.intakeIn();//START intaking before moving to mitigate long delay

        hardware.intakeSlideIn();
        sleep(500L);
        Actions.runBlocking(HBtoS2); //drive to the first sample while lift lowers and intake starts
////        sleep(700L); //wait for intake to lower, maybe remove?
//        Actions.runBlocking(forwardAfter2); //move forward to eat sample
        hardware.liftPower(0); //stop lift to mitigate loppfasdhrfejgtk
        sleep(400L); //time to pick up sample
        hardware.wristUp(); //START transfer of sample
        hardware.intakeSlideIn(); //ensure that intake/outtake are aligned
        hardware.intakeStop(); //stop intaking
        sleep(900L);
        hardware.intakeOut();
        sleep(900L);
        hardware.intakeStop();
        hardware.liftGoUp();

        Actions.runBlocking(S2toHB);
        sleep(700L); //this long to wait for lift?
        hardware.liftPower(.3);
        hardware.bucketServo.setPosition(0.3); //drop sample
        sleep(900L); //wait for sample to fall
        hardware.wristDown();
        hardware.bucketServo.setPosition(.7); //reset bucket (no hit basket)
        hardware.liftGoDown(); //START moving the lift downward
        hardware.intakeIn();//START intaking before moving to mitigate long delay
//
        hardware.intakeSlideIn();
        Actions.runBlocking(HBtoS3); //drive to the first sample while lift lowers and intake starts
        hardware.intakeSlideOutLess();
        sleep(500L); //time to pick up sample
        Actions.runBlocking(S3toSpin);
        sleep(500);
        hardware.intakeSlideIn(); //ensure that intake/outtake are aligned
        hardware.wristUp(); //START transfer of sample
        hardware.intakeStop(); //stop intaking
        sleep(900L);
        hardware.intakeOut();
        sleep(900L);
        hardware.intakeStop();
        hardware.liftGoUp();

        Actions.runBlocking(S3toHB);
        sleep(700L); //this long to wait for lift?
        hardware.bucketServo.setPosition(0.3); //drop sample
        sleep(900L); //wait for sample to fall
        hardware.bucketServo.setPosition(.6); //reset bucket (no hit basket)
        hardware.liftGoPark();

        Actions.runBlocking(HBtoPark);

//         time left: 0 seconds!!!
    }
}

//robot dimensions: 18 wide x 17 long to center (9 wide x 8.5 long to center)
//heading --> looking, tangent --> moving, Pose2D includes (x, y, heading), then tangent in a trajectory