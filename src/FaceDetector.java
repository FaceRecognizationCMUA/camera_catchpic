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

public class FaceDetector {
    private CascadeClassifier face_cascade;  
     // Create a constructor method  
     public FaceDetector(){  
         // face_cascade=new CascadeClassifier("./cascades/lbpcascade_frontalface_alt.xml");  
         //..didn't have not much luck with the lbp
         
        face_cascade=new CascadeClassifier("outlib\\haarcascade_frontalface_alt_tree.xml"); 
//        System.out.print(getClass().getResource("haarcascade_frontalface_alt.xml").getPath());
          if(face_cascade.empty())  
          {  
               System.out.println("--(!)Error loading A\n");  
                return;  
          }  
          else  
          {  
                     System.out.println("Face classifier loooaaaaaded up");  
          }  
     }  
     
     
     public Mat detect(Mat inputframe){  
          Mat mRgba=new Mat();  
          Mat mGrey=new Mat();  
          MatOfRect faces = new MatOfRect();  
          inputframe.copyTo(mRgba);  
          inputframe.copyTo(mGrey);  
          Imgproc.cvtColor( mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);  
          Imgproc.equalizeHist( mGrey, mGrey );  
          face_cascade.detectMultiScale(mGrey, faces);  
//          System.out.println(String.format("Detected %s faces", faces.toArray().length));  
          for(Rect rect:faces.toArray())  
          {  
               Point center= new Point(rect.x + rect.width*0.5, rect.y + rect.height*0.5 );  
               //draw a blue eclipse around face
               
            
               
               
               
               Core.ellipse( mRgba, center, new Size( rect.width*0.5, rect.height*0.5), 0, 0, 360, new Scalar( 255, 0, 255 ), 4, 8, 0 );
          }  
          return mRgba;  
     }  
}
