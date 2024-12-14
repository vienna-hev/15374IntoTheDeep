package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 16.5)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-15.25, -62, Math.toRadians(90)))
                        .splineTo(new Vector2d(-56, -52), Math.toRadians(225))
                        .splineTo(new Vector2d(-49.5, -46), Math.toRadians(90))
                        .splineTo(new Vector2d(-56, -52), Math.toRadians(225))
                        .splineTo(new Vector2d(-58, -46), Math.toRadians(90))
                        .splineTo(new Vector2d(-56, -52), Math.toRadians(225))
                        .splineTo(new Vector2d(-63, -46), Math.toRadians(90))
                       // .splineTo(new Vector2d(-56, -52), Math.toRadians(225))
                        .splineTo(new Vector2d(-26, -10), Math.toRadians(360))
                        .build());



//        tile dimensions: 24in by 24in
//        blue/red, NET/OBS side, action?
//        y=60 subject to change (robot specs)
//        15.75 means edge of robot touching edge of 1st tile subject to change
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}