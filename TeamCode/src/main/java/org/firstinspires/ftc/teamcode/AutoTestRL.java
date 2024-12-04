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

    public void depositHB() {
        upMotor.setPower(1);
        sleep(3000); //time the slide rises for to reach
        bucketServo.setPosition(1); //position that drops sample
        sleep(2000); //hopefully goes back after this time, position needs to be set again?
        upMotor.setPower(-1);
        sleep(1000); //goes back down for 1 sec
    }

    @Override
    public void runOpMode() {
        //start with sample, deposit in HB
        //drive to sample (x3) line, intake sample
        //deposit in HB and park?
        Pose2d initialPose = new Pose2d(-15.25, -62, Math.toRadians(90));
        //x start position is left side aligned with tile side, against the back, facing forward
        PinpointDrive drive = new PinpointDrive(hardwareMap, initialPose);

        Pose2d dropRedHB = new Pose2d(-60, -20.25, Math.toRadians(225));

        driveToHB = drive.actionBuilder(initialPose)
                .splineTo(new Vector2d(-60, -20.25), Math.toRadians(225))
                //y vector coordinate may be too high
                .build();

        driveToS1 = drive.actionBuilder(dropRedHB)
                        .build();

        waitForStart();
        Actions.runBlocking(driveToHB);
        depositHB();
    }
}

//17.5 wide x 10 long to center