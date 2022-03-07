package com.topNotch.photoEditor.effects.slctvClr;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class SelectiveColorCyansManipulatorForMagentasTask extends Task<int[]> {
	
	private int iterations[] ;
	private Canvas canvas ;
	private int width ;
	private int height ;
	
   private int currentRedValue ;
	
	private int actualRed[] ;
	private int actualGreen[] ;
	private int actualBlue[] ;
	
	private double actualRedInFraction[];
	private double counterRedInFraction[];
	private int differenceBetweenActualBlueAndActualGreen[] ;
	private int differenceBetweenActualRedAndActualGreen[] ;
	
	private PixelWriter pxlWriter ;
	private final PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	
	public SelectiveColorCyansManipulatorForMagentasTask( Canvas canvas , int currentRedValue ,  int[] actualRed , int[] actualGreen , int[] actualBlue 
            , double[] actualRedInFraction , double[] counterRedInFraction , int[] differenceBetweenActualBlueAndActualGreen 
            , int[] differenceBetweenActualRedAndActualGreen ) {
		
		this.canvas = canvas ;
		this.width = (int)canvas.getWidth() ;
		this.height = (int)canvas.getHeight() ;
		
        this.currentRedValue = currentRedValue ;
		
		this.iterations = new int[ width*height ];
		
		this.actualRed      = actualRed;
		this.actualGreen    = actualGreen;
		this.actualBlue     = actualBlue;
		
		this.actualRedInFraction = actualRedInFraction ;
		this.counterRedInFraction = counterRedInFraction ;
		
		this.differenceBetweenActualRedAndActualGreen = differenceBetweenActualRedAndActualGreen ;
		this.differenceBetweenActualBlueAndActualGreen = differenceBetweenActualBlueAndActualGreen ;
		
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
				
				if( actualBlue[i] > actualGreen[i] && actualRed[i] > actualGreen[i] ) {
					
					if( currentRedValue >= 0 ) {
						
						if( actualRed[i] > actualBlue[i] ) {
							
							if( actualRed[i] > 127 ) {
								
								currentRed = (differenceBetweenActualBlueAndActualGreen[i]*counterRedInFraction[i])*(currentRedValue/(double)100) ;
								newRed = (int)(actualRed[i] - currentRed) ;
							}
							else {
								
								currentRed = (differenceBetweenActualBlueAndActualGreen[i]*actualRedInFraction[i])*(currentRedValue/(double)100) ;
								newRed = (int)(actualRed[i] - currentRed) ;
							}
						}
						else {
							
                            if( actualRed[i] > 127 ) {
								
								currentRed = (differenceBetweenActualRedAndActualGreen[i]*counterRedInFraction[i])*(currentRedValue/(double)100) ;
								newRed = (int)(actualRed[i] - currentRed) ;
							}
							else {
								
								currentRed = (differenceBetweenActualRedAndActualGreen[i]*actualRedInFraction[i])*(currentRedValue/(double)100) ;
								newRed = (int)(actualRed[i] - currentRed) ;
							}
						}	
					}
					else {
						
						if( actualRed[i] > actualBlue[i] ) {
							
							currentRed = (differenceBetweenActualBlueAndActualGreen[i]*counterRedInFraction[i])*(currentRedValue/(double)100) ;
							newRed = (int)(actualRed[i] - currentRed) ;
						}
						else {
							
							currentRed = (differenceBetweenActualRedAndActualGreen[i]*counterRedInFraction[i])*(currentRedValue/(double)100) ;
							newRed = (int)(actualRed[i] - currentRed) ;
						}
					}
				}
				else {
					
					newRed = actualRed[i] ;
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
