package org.firstinspires.ftc.teamcode;

import android.graphics.Color;
import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

/*
 * This OpMode illustrates how to use a video source (camera) as a color sensor
 *
 * A "color sensor" will typically determine the color of the object that it is pointed at.
 *
 * This sample performs the same function, except it uses a video camera to inspect an object or scene.
 * The user may choose to inspect all, or just a Region of Interest (ROI), of the active camera view.
 * The user must also provide a list of "acceptable colors" (Swatches) from which the closest matching color will be selected.
 *
 * To perform this function, a VisionPortal runs a PredominantColorProcessor process.
 *   The PredominantColorProcessor process is created first, and then the VisionPortal is built to use this process.
 *   The PredominantColorProcessor analyses the ROI and splits the colored pixels into several color-clusters.
 *   The largest of these clusters is then considered to be the "Predominant Color"
 *   The process then matches the Predominant Color with the closest Swatch and returns that match.
 *
 * To aid the user, a colored rectangle is drawn on the camera preview to show the RegionOfInterest,
 * The Predominant Color is used to paint the rectangle border, so the user can verify that the color is reasonable.
 */

@TeleOp(name = "webcamtesty1.0")
public class webcamidentify extends LinearOpMode {

    public WebcamName webcam;

    @Override
    public void runOpMode() {
        webcam = hardwareMap.get(WebcamName.class, "webcam");

        PredominantColorProcessor colorSensor = new PredominantColorProcessor.Builder()
                .setRoi(ImageRegion.asUnityCenterCoordinates(-0.1, 0.1, 0.1, -0.1))
                .setSwatches(
                        PredominantColorProcessor.Swatch.RED,
                        PredominantColorProcessor.Swatch.BLUE,
                        PredominantColorProcessor.Swatch.YELLOW,
                        PredominantColorProcessor.Swatch.BLACK,
                        PredominantColorProcessor.Swatch.WHITE)
                .build();

        /*
         * Build a vision portal to run the Color Sensor process.
         *
         *  - Add the colorSensor process created above.
         *  - Set the desired video resolution.
         *      Since a high resolution will not improve this process, choose a lower resolution that is
         *      supported by your camera.  This will improve overall performance and reduce latency.
         *  - Choose your video source.  This may be
         *      .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))  .....   for a webcam
         *  or
         *      .setCamera(BuiltinCameraDirection.BACK)    ... for a Phone Camera
         */
        VisionPortal portal = new VisionPortal.Builder()
                .addProcessor(colorSensor)
                .setCameraResolution(new Size(1280, 720))
                .setCamera(webcam)
                .build();

        telemetry.setMsTransmissionInterval(50);  // Speed up telemetry updates, Just use for debugging.

        // WARNING:  To be able to view the stream preview on the Driver Station, this code runs in INIT mode.
        while (opModeIsActive() || opModeInInit())
        {
            telemetry.addData("DS preview on/off", "3 dots, Camera Stream\n");

            // Request the most recent color analysis.
            // This will return the closest matching colorSwatch and the predominant RGB color.
            // Note: to take actions based on the detected color, simply use the colorSwatch in a comparison or switch.
            //  eg:
            //      if (result.closestSwatch == PredominantColorProcessor.Swatch.RED) {... some code  ...}
            PredominantColorProcessor.Result result = colorSensor.getAnalysis();

            // Display the Color Sensor result.
            telemetry.addData("Best Match:", result.closestSwatch);
            telemetry.addLine(String.format("R %3d, G %3d, B %3d", Color.red(result.rgb), Color.green(result.rgb), Color.blue(result.rgb)));
            telemetry.update();

            sleep(20);
        }
    }
}

//ZACH CODE
// package org.firstinspires.ftc.teamcode;
//
//import static android.os.SystemClock.sleep;
//
//import com.acmerobotics.roadrunner.Action;
//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.Vector2d;
//import com.acmerobotics.roadrunner.ftc.Actions;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.core.Rect;
//import org.opencv.core.Scalar;
//import org.opencv.imgproc.Imgproc;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.easyopencv.OpenCvPipeline;
//import org.openftc.easyopencv.OpenCvWebcam;
//
//import java.util.Objects;
//
//
//@Autonomous
//public class openCVwebcam extends OpMode {
//
//    OpenCvWebcam webcam = null;
//    String position = "none";
////    String startposition = "none";
//
//
//    @Override
//    public void init() {
//        WebcamName webcamName = hardwareMap.get(WebcamName.class, "webcam");
//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id",
//                hardwareMap.appContext.getPackageName());
//        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"),
//                cameraMonitorViewId);
//
//        webcam.setPipeline(new examplePipeline());
//
//        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
//            public void onOpened() {
//                webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
//
//            }
//
//            public void onError(int errorCode) {
//                telemetry.addData("Error", errorCode);
//                telemetry.update();
//            }
//        });
//    }
//
//    @Override
//    public void loop() {
//
//    }
//
//    class examplePipeline extends OpenCvPipeline {
//        Mat YCbCr = new Mat();
//        Mat leftCrop;
//        Mat centerCrop;
//        Mat rightCrop;
//        double leftavgfin;
//        double centeravgfin;
//        double rightavgfin;
//        Mat outPut = new Mat();
//        Scalar rectColor = new Scalar(255.0, 0.0, 0.0);
//
//        public Mat processFrame(Mat input) {
//            Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2YCrCb);
//            telemetry.addLine("pipeline running :)");
//
//            Rect centerRect = new Rect(290, 30, 50, 50);
//            Rect rightRect = new Rect(1, 1, 30, 50);
//            Rect leftRect = new Rect(609, 1, 30, 50);
//
//            input.copyTo(outPut);
//            Imgproc.rectangle(outPut, leftRect, rectColor, 2);
//            Imgproc.rectangle(outPut, centerRect, rectColor, 2);
//            Imgproc.rectangle(outPut, rightRect, rectColor, 2);
//
//            leftCrop = YCbCr.submat(leftRect);
//            centerCrop = YCbCr.submat(centerRect);
//            rightCrop = YCbCr.submat(rightRect);
//
//            Core.extractChannel(leftCrop, leftCrop, 1);
//            Core.extractChannel(centerCrop, centerCrop, 1);
//            Core.extractChannel(rightCrop, rightCrop, 1);
//
//            Scalar leftavg = Core.mean(leftCrop);
//            Scalar centeravg = Core.mean(centerCrop);
//            Scalar rightavg = Core.mean(rightCrop);
//
//            leftavgfin = leftavg.val[0];
//            centeravgfin = centeravg.val[0];
//            rightavgfin = rightavg.val[0];
//
//            if (leftavgfin > rightavgfin && leftavgfin > centeravgfin) {
//                telemetry.addLine("Left");
//                position = "left";
//            }
//            if (rightavgfin > centeravgfin && rightavgfin > leftavgfin) {
//                telemetry.addLine("Right");
//                position = "right";
//            }
//            if (centeravgfin > rightavgfin && centeravgfin > leftavgfin) {
//                telemetry.addLine("Center :)");
//                position = "center";
//            }
//
//            return (outPut);
//        }
//    }
//}
