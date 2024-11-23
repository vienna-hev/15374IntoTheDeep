package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.CRServo;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DcMotorSimple; //???
        import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class sheaTeleOp extends LinearOpMode {
int num;
    public DcMotor frontRight;
    public DcMotor frontLeft;
    public DcMotor backLeft;
    public DcMotor backRight;
    public DcMotor upMotor;
    public CRServo intakeLeft;
    public CRServo intakeRight;
    public Servo wrist;
    public Servo elbow;
    public Servo bucketL;
    public Servo bucketR;

    @Override
    public void runOpMode() {

        frontRight = hardwareMap.get(DcMotor.class, "right_front");
        frontLeft = hardwareMap.get(DcMotor.class, "left_front");
        backLeft = hardwareMap.get(DcMotor.class, "left_back");
        backRight = hardwareMap.get(DcMotor.class, "right_back");

        upMotor = hardwareMap.get(DcMotor.class, "upMotor");

        intakeLeft = hardwareMap.get(CRServo.class, "intakeServoL");
        intakeRight = hardwareMap.get(CRServo.class, "intakeServoR");
        wrist = hardwareMap.get(Servo.class, "wristServo");
        elbow = hardwareMap.get(Servo.class, "elbowServo");
//        bucketL = hardwareMap.get(Servo.class, "bucketServoL");
//        bucketR = hardwareMap.get(Servo.class, "bucketServoR");

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

            if (gamepad2.dpad_left){
                intakeLeft.setPower(-.5);
                intakeRight.setPower(.5);
            }
            else if (gamepad2.dpad_right){
                intakeLeft.setPower(.3);
                intakeRight.setPower(-.3);
            }
            else {
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
            }

            if (gamepad2.left_stick_y> 0.9){
                wrist.setPosition(.30);
//                num = 1;
            }
            else if (gamepad2.left_stick_y< 0.9){
                wrist.setPosition(.80);
//                num =2;
            }
//            else{
//                if (num == 1){
//                    wrist.setPosition(.85);
//                }
//                else if (num == 2){
//                    wrist.setPosition(.25);
//                }
//                else {
//                    wrist.setPosition(0);
//                }
//            }

            if (gamepad2.right_stick_x > 0.9){
                elbow.setPosition(0.80);
            }
            else{
                elbow.setPosition(0.40);
            }


            if (gamepad2.y){
                upMotor.setPower(0.5);
            }
            else{
                upMotor.setPower(0);
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