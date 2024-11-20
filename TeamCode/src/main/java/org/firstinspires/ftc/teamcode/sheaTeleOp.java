package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DcMotorSimple; //???
        import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class sheaTeleOp extends LinearOpMode {

    public DcMotor frontRight;
    public DcMotor frontLeft;
    public DcMotor backLeft;
    public DcMotor backRight;
    public Servo intakeLeft;
    public Servo intakeRight;

    @Override
    public void runOpMode() {

        frontRight = hardwareMap.get(DcMotor.class, "right_front");

        frontLeft = hardwareMap.get(DcMotor.class, "left_front");

        backLeft = hardwareMap.get(DcMotor.class, "left_back");

        backRight = hardwareMap.get(DcMotor.class, "right_back");

        intakeLeft = hardwareMap.get(Servo.class, "intakeServoL");

        intakeRight = hardwareMap.get(Servo.class, "intakeServoR");

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //frontRight.setDirection(DcMotorSimple.Direction.REVERSE); ///undo???
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
       //backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        double drivespeed = -0.5;
        double turnSpeed = -1;


        waitForStart();

        while (opModeIsActive()) {
//            if (gamepad1.right_bumper) {
//                frontLeft.setPower(-drivespeed);
//                frontRight.setPower(drivespeed);
//                backLeft.setPower(-drivespeed);
//                backRight.setPower(drivespeed);
//            } else if (gamepad1.left_bumper) {
//                frontLeft.setPower(drivespeed);
//                frontRight.setPower(-drivespeed);
//                backLeft.setPower(drivespeed);
//                backRight.setPower(-drivespeed);

//            if (gamepad1.right_bumper) {
//                frontLeft.setPower(1);
//                frontRight.setPower(-1);
//                backLeft.setPower(1);
//                backRight.setPower(-1);
//            } else if (gamepad1.left_bumper) {
//                frontLeft.setPower(-1);
//                frontRight.setPower(1);
//                backLeft.setPower(-1);
//                backRight.setPower(1);

            if (gamepad1.right_bumper) {
                frontLeft.setPower(-turnSpeed);
                frontRight.setPower(turnSpeed);
                backLeft.setPower(-turnSpeed);
                backRight.setPower(turnSpeed);
            } else if (gamepad1.left_bumper) {
                frontLeft.setPower(turnSpeed);
                frontRight.setPower(-turnSpeed);
                backLeft.setPower(turnSpeed);
                backRight.setPower(-turnSpeed);
            } else if (gamepad1.left_stick_y > 0.9) {
                frontLeft.setPower(drivespeed);
                frontRight.setPower(drivespeed);
                backLeft.setPower(drivespeed);
                backRight.setPower(drivespeed);
            } else if (gamepad1.left_stick_y < -0.9) {
                frontLeft.setPower(-drivespeed);
                frontRight.setPower(-drivespeed);
                backLeft.setPower(-drivespeed);
                backRight.setPower(-drivespeed);
            } else if (gamepad1.left_stick_x > 0.9) {
                frontLeft.setPower(-drivespeed);
                frontRight.setPower(drivespeed);
                backLeft.setPower(drivespeed);
                backRight.setPower(-drivespeed);
            } else if (gamepad1.left_stick_x < -0.9) {
                frontLeft.setPower(drivespeed);
                frontRight.setPower(-drivespeed);
                backLeft.setPower(-drivespeed);
                backRight.setPower(drivespeed);
            } else {
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(0);
            }

            if (gamepad1.dpad_left){
                intakeLeft.setPower(.5);
            }

            double speed = gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            double strafe = gamepad1.left_stick_x;
            frontLeft.setPower(-speed + turn + strafe);
            frontRight.setPower(-speed - turn - strafe);
            backLeft.setPower(-speed + turn - strafe);
            backRight.setPower(-speed - turn + strafe);
        }
    }
}