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

    private double telescopicity;
    private final double exLIn = 0.1;
    private final double exLOut = 0.5;
    private final double exRIn = 0.73;
    private final double exROut = 0.5;

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

    public void funcIntake(boolean inBoolButton, boolean outBoolButton) {
        if (outBoolButton) {
            intakeLeft.setPower(-1);
            intakeRight.setPower(1);
        } else if (inBoolButton) {
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
        telescopicity = doubleButton;
        if (telescopicity > 0) {
            slideLeft.setPosition(exLIn + (exLOut - exLIn) * telescopicity);
            slideRight.setPosition(exRIn + (exROut - exRIn) * telescopicity);
        } else {
            slideLeft.setPosition(exLIn);
            slideRight.setPosition(exRIn);
        }
    }
}
