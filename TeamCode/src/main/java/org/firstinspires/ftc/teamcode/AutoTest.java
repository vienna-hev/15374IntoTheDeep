package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Disabled
public class AutoTest extends OpMode {
    PinpointDrive drive = new PinpointDrive(hardwareMap, new Pose2d(1,1, Math.toRadians(90) ));
    Pose2d initialpose;

    @Override
    public void init() {
        initialpose = new Pose2d(2,3,Math.toRadians(90));
    }

    @Override
    public void loop() {
        TrajectoryActionBuilder traj1 = drive.actionBuilder(initialpose)
                ;

    }
}
