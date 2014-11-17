/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author xgy
 */
import java.awt.*;

import java.io.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String arg[]) throws InterruptedException, IOException {
        // Load the native library.  
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
      //or ...     System.loadLibrary("opencv_java244");       

        //make the JFrame
        JFrame frame = new JFrame("WebCam Capture - Face detection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FaceDetector faceDetector = new FaceDetector();
        FacePanel facePanel = new FacePanel();
        frame.setSize(400, 400); //give the frame some arbitrary size 
        frame.setBackground(Color.BLUE);
        frame.add(facePanel, BorderLayout.CENTER);
        frame.setVisible(true);

        //Open and Read from the video stream  
        Mat webcam_image = new Mat();
        VideoCapture webCam = new VideoCapture(0);
        int i = 0;
        if (webCam.isOpened()) {
            Thread.sleep(500); /// This one-time delay allows the Webcam to initialize itself  
            while (i == 0) {
                webCam.read(webcam_image);
                if (!webcam_image.empty()) {
                    Thread.sleep(200); /// This delay eases the computational load .. with little performance leakage
                    frame.setSize(webcam_image.width() + 40, webcam_image.height() + 60);
                    //Apply the classifier to the captured image  
                    webcam_image = faceDetector.detect(webcam_image);
                    //Display the image  
                    facePanel.matToBufferedImage(webcam_image);
                    facePanel.repaint();

                    MatOfByte mb = new MatOfByte();
                    Highgui.imencode(".jpg", webcam_image, mb);
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(mb.toArray())); 
                    File file=new File("D:/test.jpg");
                    ImageIO.write(image,"JPEG",file);
                    i++;
                } else {
                    System.out.println(" --(!) No captured frame from webcam !");
                    break;
                }
            }
        }
        webCam.release(); //release the webcam
    }

}
