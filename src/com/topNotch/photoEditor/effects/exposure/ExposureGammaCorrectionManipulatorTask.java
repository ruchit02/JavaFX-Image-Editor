package com.topNotch.photoEditor.effects.exposure;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class ExposureGammaCorrectionManipulatorTask extends Task<int[]> {
	
	private int iterations[] ;
	private Canvas canvas ;
	private int width ;
	private int height ;
	
   private double currentValue ;
	
	private int actualRed[] ;
	private int actualGreen[] ;
	private int actualBlue[] ;
	private int rgbAverage[] ;
	
	private PixelWriter pxlWriter ;
	private final PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	
	public ExposureGammaCorrectionManipulatorTask( Canvas canvas , double currentValue ,  int[] actualRed , int[] actualGreen , int[] actualBlue 
            , int[] rgbAverage ) {
		
		this.canvas = canvas ;
		this.width  = (int)canvas.getWidth() ;
		this.height = (int)canvas.getHeight() ;
		
        this.currentValue = currentValue ;
		
		this.iterations = new int[ width*height ];
		
		this.actualRed      = actualRed;
		this.actualGreen    = actualGreen;
		this.actualBlue     = actualBlue;
		this.rgbAverage     = rgbAverage ;
		
		this.pxlWriter = canvas.getGraphicsContext2D().getPixelWriter() ;
	}
	
	@Override
	protected int[] call() {
		
		int i = 0 ;
		
		double currentRedValue = 0 ;
		double currentGreenValue = 0 ;
		double currentBlueValue = 0 ;
		
		int counterRed = 0 ;
		int counterGreen = 0 ;
		int counterBlue = 0 ;
		
		int newRed = 0 ;
		int newGreen = 0 ;
		int newBlue = 0 ;
		
		int alpha = 0xff<<24 ;
		
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
				
				
				iterations[i] = alpha | newRed<<16 | newGreen<<8 | newBlue ;
				
				i++ ;
			}
		}
		
		Platform.runLater( new Runnable() {
			
			@Override
			public void run() {
				
				pxlWriter.setPixels( 0 , 0 , width , height , intTypePixelFormat , iterations , 0 , width*1 );
			}
		});
		
		return iterations ;
	}
}














/*newRed =  (int)(actualRed[i] + ((currentValue*counterRed)/(double)256)) ;
newGreen =  (int)(actualGreen[i] + ((currentValue*counterGreen)/(double)256)) ;
newBlue =  (int)(actualBlue[i] + ((currentValue*counterBlue)/(double)256)) ;*/