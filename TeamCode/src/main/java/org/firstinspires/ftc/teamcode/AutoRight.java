package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Disabled
public class AutoRight extends LinearOpMode {
    Action driveToPark;
    public RRHardware hardware;

    @Override
    public void runOpMode() {
        hardware = new RRHardware(hardwareMap);
        Pose2d initialPose = new Pose2d(15.25, -62, Math.toRadians(90));
        //x start position is left side aligned with tile side, against the back, facing forward
        PinpointDrive drive = new PinpointDrive(hardwareMap, initialPose);

        driveToPark = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(52,-61))
                .build();

        waitForStart();
        Actions.runBlocking(driveToPark); //parks on the side in territory
    }
}