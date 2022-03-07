package com.topNotch.photoEditor.effects.brigtnsAndCntrst;

import java.nio.IntBuffer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class IteratingDarknessPixels extends Thread {
	
	public static int width ;
	public static int height ;
	private int currentDarknessValue ;
	
	public static double[] preCalcValues ;
	
	public 		static int[] 	actualRED ;
	public 		static int[] 	actualGREEN ;
	public 		static int[] 	actualBLUE ;
	public 		static int[] 	elements ;
	
	public static int[] postModificationRED ;
	public static int[] postModificationGREEN ;
	public static int[] postModificationBLUE ;
	public static int[] postModificationARGB ;
	
	public static PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	public static Canvas canvas ;
	public static PixelWriter pxlWriter ;
	
	IteratingDarknessPixels( int currentDarknessValue ){
		
		this.currentDarknessValue = currentDarknessValue ;
	}
	
	@Override
	public void run() {
		
		int newRed = 0 ;
		int newGreen = 0 ;
		
		int redValue = 0 ;
		int greenValue = 0 ;
		int blueValue = 0 ;
		
		for( int i : elements ) {
			
			redValue = actualRED[i] ;
			greenValue = actualGREEN[i] ;
			blueValue = actualBLUE[i] ;
			
			postModificationRED[i] 		= redValue + (int)(currentDarknessValue*preCalcValues[redValue]) ;
			postModificationGREEN[i] 	= greenValue + (int)(currentDarknessValue*preCalcValues[greenValue]) ;
			postModificationBLUE[i] 	= blueValue + (int)(currentDarknessValue*preCalcValues[blueValue]) ;
			
			if( postModificationRED[i] > 255 ) {
				postModificationRED[i] = 255 ;
			}
			if( postModificationRED[i] < 0 ) {
				postModificationRED[i] = 0 ;
			}
			
			if( postModificationGREEN[i] > 255 ) {
				postModificationGREEN[i] = 255 ;
			}
			if( postModificationGREEN[i] < 0 ) {
				postModificationGREEN[i] = 0 ;
			}
			
			if( postModificationBLUE[i] > 255 ) {
				postModificationBLUE[i] = 255 ;
			}
			if( postModificationBLUE[i] < 0 ) {
				postModificationBLUE[i] = 0 ;
			}
			
			newRed = postModificationRED[i]<<16 ;
			newGreen = postModificationGREEN[i]<<8 ;
			
			postModificationARGB[i] = 0xff000000 | newRed | newGreen | postModificationBLUE[i] ;
		}
		
		Platform.runLater( new Runnable(){
			@Override
			public void run() {
				
				pxlWriter.setPixels( 0 , 0 , width , height , intTypePixelFormat , postModificationARGB , 0 , width*1 );
			}
		});
	}
}