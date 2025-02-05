package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TOHardware {
    public DcMotor frontLeft, frontRight, backLeft, backRight, liftLeft, liftRight;
    public CRServo intakeLeft, intakeRight;
    public Servo foldLeft, flip, slideLeft, slideRight, bucket;

    final double exLIn = 0.1;
    final double exLOut = 0.5;
    final double exRIn = 0.73;
    final double exROut = 0.5;

    public TOHardware(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotor.class, "FL");
        frontRight = hardwareMap.get(DcMotor.class, "FR");
        backLeft = hardwareMap.get(DcMotor.class, "BL");
        backRight = hardwareMap.get(DcMotor.class, "BR");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        liftLeft = hardwareMap.get(DcMotor.class, "LUM");
        liftRight = hardwareMap.get(DcMotor.class, "RUM");
        intakeLeft = hardwareMap.get(CRServo.class, "intakeLeft");
        intakeRight = hardwareMap.get(CRServo.class, "intakeRight");
        slideLeft = hardwareMap.get(Servo.class, "intakeExtensionLeft");
        slideRight = hardwareMap.get(Servo.class, "intakeExtensionRight");
        foldLeft = hardwareMap.get(Servo.class, "WL");
        flip = hardwareMap.get(Servo.class, "WF");
        bucket = hardwareMap.get(Servo.class, "bucketServo");
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
            foldLeft.setPosition(1);
            flip.setPosition(-1);
        } else {
            foldLeft.setPosition(0.4);
            flip.setPosition(0.95);
        }
    }

    public void funcExtend(double doubleButton) {
        if (doubleButton > 0.1) {
            slideLeft.setPosition(exLIn + (exLOut - exLIn) * doubleButton);
            slideRight.setPosition(exRIn + (exROut - exRIn) * doubleButton);
        } else {
            slideLeft.setPosition(exLIn);
            slideRight.setPosition(exRIn);
        }
    }

    public void funcLift(boolean upButton, boolean downButton, double analogDec, boolean digitalDec) {
        double tortoisity;
        if (digitalDec) {
            tortoisity = 0.7;
        } else {
            tortoisity = analogDec;
        }

        if (downButton) {
            liftRight.setPower(1 - tortoisity);
            liftLeft.setPower(tortoisity - 1);
        } else if (upButton) {
            liftRight.setPower(tortoisity - 1);
            liftLeft.setPower(1 - tortoisity);
        } else {
            liftRight.setPower(0);
            liftLeft.setPower(0);
        }
    }

    public void funcBucket(boolean boolButton) {
        if (boolButton) {
            bucket.setPosition(0);
        } else {
            bucket.setPosition(1);
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
