package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TWOHardware {
    public DcMotor frontLeft, frontRight, backLeft, backRight, liftLeft, liftRight;
    public DcMotorEx intakeExtension;
    public CRServo intakeLeft, intakeRight;
    public Servo foldLeft, flip, bucket;

    public TWOHardware(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotor.class, "FL");
        frontRight = hardwareMap.get(DcMotor.class, "FR");
        backLeft = hardwareMap.get(DcMotor.class, "BL");
        backRight = hardwareMap.get(DcMotor.class, "BR");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        liftLeft = hardwareMap.get(DcMotor.class, "LUM");
        liftRight = hardwareMap.get(DcMotor.class, "RUM");
        intakeLeft = hardwareMap.get(CRServo.class, "IWL");
        intakeRight = hardwareMap.get(CRServo.class, "IWR");
        intakeExtension = hardwareMap.get(DcMotorEx.class, "IE");
        foldLeft = hardwareMap.get(Servo.class, "WL");
        flip = hardwareMap.get(Servo.class, "WF");
        bucket = hardwareMap.get(Servo.class, "BS");

        intakeExtension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeExtension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public void funcIntake(boolean inButton, boolean outButton) {
        if (outButton) {
            intakeLeft.setPower(-1);
            intakeRight.setPower(1);
        } else if (inButton) {
            intakeLeft.setPower(1);
            intakeRight.setPower(-1);
        } else {
            intakeLeft.setPower(0);
            intakeRight.setPower(0);
        }
    }

    public void funcWrist(boolean boolButton) {
        if (boolButton) {
            foldLeft.setPosition(.9);
            flip.setPosition(-1);
        } else {
            foldLeft.setPosition(0.1);
            flip.setPosition(.95);
        }
    }

    public void funcExtend(double extendButton, boolean resetButton) {
        int goalPos = (int)(extendButton * 300); //the coefficient is the range
        int curPos = intakeExtension.getCurrentPosition();
        int tolerance = 10;

        if (resetButton) {
            intakeExtension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        if (curPos < goalPos + tolerance) {
            intakeExtension.setPower(0.5);
        } else if (curPos > goalPos - tolerance) {
            intakeExtension.setPower(-0.5);
        } else {
            intakeExtension.setPower(0);
        }
    }

    public void funcLift(boolean upButton, boolean downButton, double analogDec, boolean digitalDec) {
        double powerDec;
        if (digitalDec) {
            powerDec = 0.7;
        } else {
            powerDec = analogDec;
        }

        if (downButton) {
            liftRight.setPower(1 - powerDec);
            liftLeft.setPower(powerDec - 1);
        } else if (upButton) {
            liftRight.setPower(powerDec - 1);
            liftLeft.setPower(1 - powerDec);
        } else {
            liftRight.setPower(0);
            liftLeft.setPower(0);
        }
    }

    public void funcBucket(boolean boolButton) {
        if (boolButton) {
            bucket.setPosition(.4);
        } else {
            bucket.setPosition(.7);
        }
    }

    public void funcDrive(double xControl, double yControl, double yawControl) {
        double speed = yControl;
        double strafe = xControl;
        double turn = yawControl;

        if (Math.abs(speed) < 0.1) {
            speed = 0;
        }
        if (Math.abs(strafe) < 0.1) {
            strafe = 0;
        }
        if (Math.abs(turn) < 0.1) {
            turn = 0;
        } else if (Math.abs(turn) < 0.5) {
            turn = Math.signum(turn) * 0.5;
        }

        double flPwr = speed - turn - strafe;
        double frPwr = speed + turn + strafe;
        double blPwr = speed - turn + strafe;
        double brPwr = speed + turn - strafe;

        double denominator = Math.max(Math.max(Math.max(flPwr, frPwr), Math.max(blPwr, brPwr)), 1);

        frontLeft.setPower(flPwr / denominator);
        frontRight.setPower(frPwr / denominator);
        backLeft.setPower(blPwr / denominator);
        backRight.setPower(brPwr / denominator);
    }

    public void funcDrive(double xControl, double yControl, double yawControl, double driveSpeed, double strafeSpeed, double turnSpeed) {
        double speed = yControl * driveSpeed;
        double strafe = xControl * strafeSpeed;
        double turn = yawControl;

        if (Math.abs(speed) < 0.1) {
            speed = 0;
        }
        if (Math.abs(strafe) < 0.1) {
            strafe = 0;
        }
        if (Math.abs(turn) < 0.1) {
            turn = 0;
        } else if (Math.abs(turn) < 0.5) {
            turn = Math.signum(turn) * turnSpeed / 2;
        } else {
            turn *= turnSpeed;
        }

        double flPwr = speed - turn - strafe;
        double frPwr = speed + turn + strafe;
        double blPwr = speed - turn + strafe;
        double brPwr = speed + turn - strafe;

        double denominator = Math.max(Math.max(Math.max(flPwr, frPwr), Math.max(blPwr, brPwr)), 1);

        frontLeft.setPower(flPwr / denominator);
        frontRight.setPower(frPwr / denominator);
        backLeft.setPower(blPwr / denominator);
        backRight.setPower(brPwr / denominator);
    }
}
