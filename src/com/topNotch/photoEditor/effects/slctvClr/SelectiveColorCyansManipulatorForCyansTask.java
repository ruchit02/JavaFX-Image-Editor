package com.topNotch.photoEditor.effects.slctvClr;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class SelectiveColorCyansManipulatorForCyansTask extends Task<int[]> {
	
	private int iterations[] ;
	private Canvas canvas ;
	private int width ;
	private int height ;
	
   private int currentRedValue ;
	
   private int previousIteration[] ;
   
	private int actualRed[] ;
	private int actualGreen[] ;
	private int actualBlue[] ;
	
	private double actualRedInFraction[];
	private double counterRedInFraction[];
	private int differenceBetweenActualGreenAndActualRed[] ;
	
	private PixelWriter pxlWriter ;
	private final PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	
	public SelectiveColorCyansManipulatorForCyansTask( Canvas canvas , int currentRedValue ,  int[] actualRed , int[] actualGreen , int[] actualBlue 
            , double[] actualRedInFraction , double[] counterRedInFraction , int[] differenceBetweenActualGreenAndActualRed , int[] previousIteration ) {
		
		this.canvas = canvas ;
		this.width = (int)canvas.getWidth() ;
		this.height = (int)canvas.getHeight() ;
		
        this.currentRedValue = currentRedValue ;
		
		this.iterations = new int[ width*height ];
		
		this.previousIteration = previousIteration ;
		
		this.actualRed      = actualRed;
		this.actualGreen    = actualGreen;
		this.actualBlue     = actualBlue;
		
		this.actualRedInFraction = actualRedInFraction ;
		this.counterRedInFraction = counterRedInFraction ;
		this.differenceBetweenActualGreenAndActualRed = differenceBetweenActualGreenAndActualRed ;
		
		this.pxlWriter = canvas.getGraphicsContext2D().getPixelWriter() ;
	}
	
	@Override
	protected int[] call() {
		
		int i = 0 ;
		
		double currentRed = 0 ;
		
		int newRed = 0;
		
		int alpha = 0xff<<24 ;
		
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
				if( previousIteration[i] != 0 ) {
					
					int previousRed = (0xff & (previousIteration[i]>>16)) ;
					int previousGreen = (0xff & (previousIteration[i]>>8)) ;
					int previousBlue = (0xff & (previousIteration[i])) ;
					
					if( previousGreen > previousRed && previousBlue > previousRed ) {
						
						if( currentRedValue >= 0 ) {
							
							if( previousRed > 127 ) {
								
								currentRed = ((previousGreen-previousRed)*((255-previousRed)/(double)256))*(currentRedValue/(double)100) ;
								newRed = (int)(previousRed - currentRed) ;
							}
							else {
								
								currentRed = ((previousGreen-previousRed)*(previousRed/(double)256))*(currentRedValue/(double)100) ;
								newRed = (int)(previousRed - currentRed) ;
							}
						}
						else {
							
							currentRed = ((previousGreen-previousRed)*((255-previousRed)/(double)256))*(currentRedValue/(double)100) ;
							newRed = (int)(previousRed - currentRed) ;
						}
					}
					else {
						
						newRed = previousRed ;
					}
					
					iterations[i] = alpha | newRed<<16 | previousGreen<<8 | previousBlue ;
				}
				else {
					
				if( actualGreen[i] > actualRed[i] && actualBlue[i] > actualRed[i] ) {
					
					if( currentRedValue >= 0 ) {
						
						if( actualRed[i] > 127 ) {
							
							currentRed = (differenceBetweenActualGreenAndActualRed[i]*counterRedInFraction[i])*(currentRedValue/(double)100) ;
							newRed = (int)(actualRed[i] - currentRed) ;
						}
						else {
							
							currentRed = (differenceBetweenActualGreenAndActualRed[i]*actualRedInFraction[i])*(currentRedValue/(double)100) ;
							newRed = (int)(actualRed[i] - currentRed) ;
						}
					}
					else {
						
						currentRed = (differenceBetweenActualGreenAndActualRed[i]*counterRedInFraction[i])*(currentRedValue/(double)100) ;
						newRed = (int)(actualRed[i] - currentRed) ;
					}
				}
				else {
					
					newRed   = actualRed[i] ;
				}
				
				iterations[i] = alpha | newRed<<16 | actualGreen[i]<<8 | actualBlue[i] ;
				}
				
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
