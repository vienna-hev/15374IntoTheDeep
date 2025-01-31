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
    Action test;
    public RRHardware hardware;

    @Override
    public void runOpMode() {
        hardware = new RRHardware(hardwareMap);
        //start with sample, deposit in HB
        //drive to sample (x3) line, intake sample
        //deposit in HB and park at submersible
        Pose2d initialPose = new Pose2d(-15, -63.5, Math.toRadians(90));

        //x start position is left side aligned with tile side, against the back, facing forward
        PinpointDrive drive = new PinpointDrive(hardwareMap, initialPose);

        driveToHB = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(-55, -49, Math.toRadians(45)), Math.toRadians(180)) //tangent changed 1/26 (270 --> 295)
                .build();

        HBtoS1 = drive.actionBuilder(new Pose2d(-55, -49, Math.toRadians(45)))
                .splineToLinearHeading(new Pose2d(-48, -48, Math.toRadians(90)), Math.toRadians(45)) //S1
                .strafeTo(new Vector2d(-48, -42))
                .build();

        S1toHB = drive.actionBuilder(new Pose2d(-48, -42, Math.toRadians(90))) //S1
                .splineToLinearHeading(new Pose2d(-52, -49, Math.toRadians(45)), Math.toRadians(225)) //tangent changed 1/26 (180 --> 270)
                .build();

        HBtoS2 = drive.actionBuilder(new Pose2d(-52, -49, Math.toRadians(45)))
                .splineToLinearHeading(new Pose2d(-57.5, -48, Math.toRadians(90)), Math.toRadians(180)) //tangent changed 1/26 (90 --> 180)
                .strafeTo(new Vector2d(-57.5, -42))
                .build();

        S2toHB = drive.actionBuilder(new Pose2d(-57.5, -42, Math.toRadians(90)))
                .splineToLinearHeading(new Pose2d(-54, -48, Math.toRadians(45)), Math.toRadians(225))
                .build();

        HBtoS3 = drive.actionBuilder(new Pose2d(-54, -48, Math.toRadians(45))) //uh oh this will probably break the robot
                .splineToLinearHeading(new Pose2d(-58.5, -55, Math.toRadians(135)), Math.toRadians(135))
                .splineToLinearHeading(new Pose2d(-56, -42, Math.toRadians(90)), Math.toRadians(90)) //tangent more than 90?
                .build();

        S3toHB = drive.actionBuilder(new Pose2d(-56, -42, Math.toRadians(90)))
                .splineToLinearHeading(new Pose2d(-56, -47, Math.toRadians(45)), Math.toRadians(0))
                .build();

        HBtoBack = drive.actionBuilder(new Pose2d(-56, -47, Math.toRadians(45)))
                .splineToLinearHeading(new Pose2d(-28, -24, Math.toRadians(180)), Math.toRadians(45))
                .build();

        test = drive.actionBuilder(initialPose)
                .splineTo(new Vector2d(-25, -53.5), Math.toRadians(0))
//                .splineToLinearHeading(new Pose2d(6, 6, Math.toRadians(180)), Math.toRadians(180))
                .build();

        waitForStart();
        hardware.liftPower(.9); //START moving the lift
        Actions.runBlocking(driveToHB); //drive to the high basket
        hardware.liftPower(.3);
        hardware.bucketServo.setPosition(.3); //drop preloaded sample
        sleep(1100L); //wait for sample to fall
        hardware.wristDown();
        hardware.bucketServo.setPosition(1); //reset bucket (no hit basket)
        hardware.liftPower(-1); //START lowering lift
        hardware.intakeIn();//START intaking before moving to mitigate long delay

        hardware.intakeSlideIn();
        sleep(1000);
        Actions.runBlocking(HBtoS1); //drive to the first sample while lift lowers and intake starts
        sleep(600L); //wait for intake to lower, maybe remove?
//        Actions.runBlocking(forwardAfter); //move forward to eat sample
        hardware.liftPower(0); //stop lift to mitigate loppfasdhrfejgtk
        hardware.wristUp(); //START transfer of sample
        sleep(500L);
        hardware.intakeSlideIn(); //ensure that intake/outtake are aligned
        hardware.intakeStop(); //stop intaking
        sleep(700L);
        hardware.intakeOut();
        sleep(800L);
        hardware.liftPower(1);

        Actions.runBlocking(S1toHB);
        sleep(900L); //this long to wait for lift?
        hardware.liftPower(.3);
        hardware.bucketServo.setPosition(0.3); //drop sample
        sleep(900L); //wait for sample to fall
        hardware.wristDown();
        hardware.bucketServo.setPosition(1); //reset bucket (no hit basket)
        hardware.liftPower(-1); //START moving the lift downward
        hardware.intakeIn();//START intaking before moving to mitigate long delay

        hardware.intakeSlideIn();
        Actions.runBlocking(HBtoS2); //drive to the first sample while lift lowers and intake starts
////        sleep(700L); //wait for intake to lower, maybe remove?
//        Actions.runBlocking(forwardAfter2); //move forward to eat sample
        hardware.liftPower(0); //stop lift to mitigate loppfasdhrfejgtk
        sleep(500L); //time to pick up sample
        hardware.wristUp(); //START transfer of sample
        hardware.intakeSlideIn(); //ensure that intake/outtake are aligned
        hardware.intakeStop(); //stop intaking
        sleep(1000L);
        hardware.intakeOut();
        sleep(800L);
        hardware.liftPower(1);

        Actions.runBlocking(S2toHB);
        sleep(900L); //this long to wait for lift?
        hardware.liftPower(.3);
        hardware.bucketServo.setPosition(0.3); //drop sample
        sleep(900L); //wait for sample to fall
        hardware.wristDown();
        hardware.bucketServo.setPosition(1); //reset bucket (no hit basket)
        hardware.liftPower(-1); //START moving the lift downward
        hardware.intakeIn();//START intaking before moving to mitigate long delay

////        Actions.runBlocking(HBtoBack);
////        hardware.liftPower(1);
////        sleep(500);
////        hardware.liftPower(0);
////        hardware.bucketServo.setPosition(.3);
//
//        hardware.intakeSlideIn();
//        Actions.runBlocking(HBtoS3); //drive to the first sample while lift lowers and intake starts
////        sleep(700L); //wait for intake to lower, maybe remove?
//        hardware.liftPower(0); //stop lift to mitigate loppfasdhrfejgtk
//        sleep(1000L); //time to pick up sample
//        hardware.wristUp(); //START transfer of sample
//        hardware.intakeSlideIn(); //ensure that intake/outtake are aligned
//        hardware.intakeStop(); //stop intaking
//        sleep(400L); //less wait for intake to position for transfer?
//        hardware.intakeOut();
//        sleep(500L);
//        hardware.liftPower(1);
//
//        Actions.runBlocking(S3toHB);
//        sleep(900L); //this long to wait for lift?
//        hardware.leftUpMotor.setPower(0.5);
//        hardware.rightUpMotor.setPower(-0.5);
//        hardware.bucketServo.setPosition(0.3); //drop sample
//        sleep(900L); //wait for sample to fall
//        hardware.bucketServo.setPosition(1); //reset bucket (no hit basket)
//        hardware.liftPower(-1); //START moving the lift downward
//        hardware.intakeIn();//START intaking before moving to mitigate long delay

//        sleep(2000);
//        hardware.liftPower(0);
        // time left:
    }
}

//robot dimensions: 18 wide x 17 long to center (9 wide x 8.5 long to center)
//heading --> looking, tangent --> moving, Pose2D includes (x, y, heading), then tangent in a trajectory