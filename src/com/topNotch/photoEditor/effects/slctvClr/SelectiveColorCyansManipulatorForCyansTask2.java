package com.topNotch.photoEditor.effects.slctvClr;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class SelectiveColorCyansManipulatorForCyansTask2 extends Task<int[]> {
	
	private int iterations[] ;
	private Canvas canvas ;
	private int width ;
	private int height ;
	
   private int currentRedValue ;
	
	private int actualRed[] ;
	private int actualGreen[] ;
	private int actualBlue[] ;
	
	private double actualRedInFraction[];
	private int differenceBetweenActualGreenAndActualRed[] ;
	
	private PixelWriter pxlWriter ;
	private final PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	
	public SelectiveColorCyansManipulatorForCyansTask2( Canvas canvas , int currentRedValue ,  int[] actualRed , int[] actualGreen , int[] actualBlue 
            , double[] actualRedInFraction , int[] differenceBetweenActualGreenAndActualRed ) {
		
		this.canvas = canvas ;
		this.width = (int)canvas.getWidth() ;
		this.height = (int)canvas.getHeight() ;
		
        this.currentRedValue = currentRedValue ;
		
		this.iterations = new int[ width*height ];
		
		this.actualRed      = actualRed;
		this.actualGreen    = actualGreen;
		this.actualBlue     = actualBlue;
		
		this.actualRedInFraction = actualRedInFraction ;
		this.differenceBetweenActualGreenAndActualRed = differenceBetweenActualGreenAndActualRed ;
		
		this.pxlWriter = canvas.getGraphicsContext2D().getPixelWriter() ;
	}
	
	@Override
	protected int[] call() {
		
		int i = 0 ;
		
		double currentRed = 0 ;
		
		int newRed = 0;
		
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
				int alpha = 0xff<<24 ;
				
				if( actualRed[i] < actualBlue[i] ) {
					
					currentRed = (differenceBetweenActualGreenAndActualRed[i]*actualRedInFraction[i])*(currentRedValue/(double)100) ;
					newRed = (int)(actualRed[i] - currentRed) ;
				}
				else {
					
					newRed = actualRed[i] ;
				}
				
				if( newRed > 255 ) {
					
					newRed = 255 ;
				}
				
				if( newRed < 0) {
					
					newRed = 0 ;
				}
				
				iterations[i] = alpha | newRed<<16 | actualGreen[i]<<8 | actualBlue[i] ;
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
