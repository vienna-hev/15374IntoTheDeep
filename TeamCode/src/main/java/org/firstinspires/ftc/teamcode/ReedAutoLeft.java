package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.opencv.core.Mat;

@Autonomous
public class ReedAutoLeft extends LinearOpMode {
    public class Arm{
        private Servo elbow;
        private Servo wrist;

        public Arm(HardwareMap hardwareMap){
            elbow = hardwareMap.get(Servo.class, "elbowServo");
            wrist = hardwareMap.get(Servo.class, "wristServo");
        }

        public class ExtendArm implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                elbow.setPosition(1);
                wrist.setPosition(1);
                return false;
            }
        }
        public Action extendArm(){
            return new ExtendArm();
        }

        public class RetractArm implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                elbow.setPosition(0);
                wrist.setPosition(0);
                return false;
            }
        }
        public Action retractArm(){
            return new RetractArm();
        }

        public class Ascend implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                elbow.setPosition(0);
                wrist.setPosition(0.5);
                return false;
            }
        }
        public Action ascend(){
            return new Ascend();
        }
    }

    public class Claw{
        private Servo leftWheel;
        private Servo rightWheel;

        public Claw(HardwareMap hardwareMap){
            leftWheel = hardwareMap.get(Servo.class, "intakeServoL");
            rightWheel = hardwareMap.get(Servo.class, "intakeServoR");
        }

        public class SpinIn implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                leftWheel.setPosition(0);
                rightWheel.setPosition(0);
                return false;
            }
        }
        public Action spinIn(){
            return new SpinIn();
        }

        public class SpinOut implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                leftWheel.setPosition(1);
                rightWheel.setPosition(1);
                return false;
            }
        }
        public Action spinOut(){
            return new SpinOut();
        }

        public class SpinStop implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                leftWheel.setPosition(0.5);
                rightWheel.setPosition(0.5);
                return false;
            }
        }
        public Action spinStop(){
            return new SpinStop();
        }
    }

    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(-24, -61, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        Arm arm = new Arm(hardwareMap);
        Claw claw = new Claw(hardwareMap);

        Action place0 = drive.actionBuilder(new Pose2d(-24, -61, Math.toRadians(180)))
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-52, -56), Math.toRadians(180))
                .build();
        Action align1 = drive.actionBuilder(new Pose2d(-52, -56, Math.toRadians(180)))
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(-48, -48), Math.toRadians(90))
                .build();
        Action grab1 = drive.actionBuilder(new Pose2d(-48, -48, Math.toRadians(90)))
                .setTangent(Math.toRadians(90))
                .lineToY(-40)
                .build();
        Action place1 = drive.actionBuilder(new Pose2d(-48, -40, Math.toRadians(90)))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(-56, -52, Math.toRadians(270)), Math.toRadians(180))
                .build();
        Action align2 = drive.actionBuilder(new Pose2d(-56, -52, Math.toRadians(270)))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(-40, -24, Math.toRadians(180)), Math.toRadians(90))
                .build();
        Action grab2 = drive.actionBuilder(new Pose2d(-40, -24, Math.toRadians(180)))
                .setTangent(Math.toRadians(180))
                .lineToX(-46)
                .build();
        Action place2 = drive.actionBuilder(new Pose2d(-46, -24, Math.toRadians(180)))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(-56, -52, Math.toRadians(270)), Math.toRadians(180))
                .build();
        Action align3 = drive.actionBuilder(new Pose2d(-56, -52, Math.toRadians(270)))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(-48, -24, Math.toRadians(180)), Math.toRadians(180))
                .build();
        Action grab3 = drive.actionBuilder(new Pose2d(-48, -24, Math.toRadians(180)))
                .setTangent(Math.toRadians(180))
                .lineToX(-56)
                .build();
        Action place3 = drive.actionBuilder(new Pose2d(-56, -24, Math.toRadians(180)))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(-56, -52, Math.toRadians(270)), Math.toRadians(270))
                .build();
        Action park = drive.actionBuilder(new Pose2d(-56, -52, Math.toRadians(270)))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-36, -10, Math.toRadians(0)), Math.toRadians(0))
                .lineToX(-26)
                .build();
        Action pause = drive.actionBuilder(new Pose2d(0, 0, Math.toRadians(0)))
                .waitSeconds(3)
                .build();

        Actions.runBlocking(
                new SequentialAction(
                        place0,
                        claw.spinOut(),
                        pause,
                        claw.spinStop(),
                        align1,
                        arm.extendArm(),
                        claw.spinIn(),
                        grab1,
                        pause,
                        claw.spinStop(),
                        place1,
                        claw.spinOut(),
                        pause,
                        claw.spinStop(),
                        arm.retractArm(),
                        align2,
                        arm.extendArm(),
                        claw.spinIn(),
                        grab2,
                        pause,
                        claw.spinStop(),
                        place2,
                        claw.spinOut(),
                        pause,
                        claw.spinStop(),
                        arm.retractArm(),
                        align3,
                        arm.extendArm(),
                        claw.spinIn(),
                        grab3,
                        pause,
                        claw.spinStop(),
                        place3,
                        claw.spinOut(),
                        pause,
                        claw.spinStop(),
                        arm.retractArm(),
                        park,
                        arm.ascend()
                )
        );
    }
}
