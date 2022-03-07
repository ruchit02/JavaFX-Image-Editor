package com.topNotch.photoEditor.effects.slctvClr;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class SelectiveColorBlacksManipulatorForBluesTask extends Task<int[]> {
	
	private int iterations[] ;
	private Canvas canvas ;
	private int width ;
	private int height ;
	
    private int currentBlueValue ;
	
	private int actualRed[] ;
	private int actualGreen[] ;
	private int actualBlue[] ;
	
	private double counterRedInFraction[];
	private double counterGreenInFraction[];
	private double counterBlueInFraction[];
	
	private double actualRedInFraction[];
	private double actualGreenInFraction[];
	private double actualBlueInFraction[];
	
	private int differenceBetweenActualBlueAndActualGreen[] ;
	
	private PixelWriter pxlWriter ;
	private final PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	
	public SelectiveColorBlacksManipulatorForBluesTask( Canvas canvas , int currentBlueValue , int[] actualRed , int[] actualGreen
			, int[] actualBlue , double[] actualRedInFraction , double[] actualGreenInFraction , double[] actualBlueInFraction 
			, double[] counterRedInFraction , double[] counterGreenInFraction , double[] counterBlueInFraction 
			, int[] differenceBetweenActualBlueAndActualGreen ) {
		
		this.canvas = canvas ;
		this.width = (int)canvas.getWidth() ;
		this.height = (int)canvas.getHeight() ;
		
        this.currentBlueValue = currentBlueValue ;
		
		this.iterations = new int[ width*height ];
		
		this.actualRed      = actualRed;
		this.actualGreen    = actualGreen;
		this.actualBlue     = actualBlue;
		
		this.actualRedInFraction = actualRedInFraction ;
		this.actualGreenInFraction = actualGreenInFraction ;
		this.actualBlueInFraction = actualBlueInFraction ;
		
		this.counterRedInFraction = counterRedInFraction ;
		this.counterGreenInFraction = counterGreenInFraction ;
		this.counterBlueInFraction = counterBlueInFraction ;
		
		this.differenceBetweenActualBlueAndActualGreen = differenceBetweenActualBlueAndActualGreen ;
		
		this.pxlWriter = canvas.getGraphicsContext2D().getPixelWriter() ;
	}
	
	@Override
	protected int[] call() {
		
		int i = 0 ;
		
		double currentRed = 0 ;
		double currentGreen = 0;
		double currentBlue = 0 ;
		
		int newRed = 0 ;
		int newGreen = 0 ;
		int newBlue = 0 ;
		
		int alpha = 0xff<<24 ;
		
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
				if( actualBlue[i] > actualRed[i] && actualBlue[i] > actualGreen[i] ) {
					
					if( currentBlueValue >= 0 ) {
						
						if( actualRed[i] > 127 ) {
							
							currentRed = (differenceBetweenActualBlueAndActualGreen[i]*counterRedInFraction[i])*(currentBlueValue/(double)100) ;
							newRed = (int)(actualRed[i] - currentRed) ;
						}
						else {
							
							currentRed = (differenceBetweenActualBlueAndActualGreen[i]*actualRedInFraction[i])*(currentBlueValue/(double)100) ;
							newRed = (int)(actualRed[i] - currentRed) ;
						}
						
                        if( actualGreen[i] > 127 ) {
							
							currentGreen = (differenceBetweenActualBlueAndActualGreen[i]*counterGreenInFraction[i])*(currentBlueValue/(double)100) ;
							newGreen = (int)(actualGreen[i] - currentGreen) ;
						}
						else {
							
							currentGreen = (differenceBetweenActualBlueAndActualGreen[i]*actualGreenInFraction[i])*(currentBlueValue/(double)100) ;
							newGreen = (int)(actualGreen[i] - currentGreen) ;
						}
                        
                        if( actualBlue[i] > 127 ) {
							
							currentBlue = (differenceBetweenActualBlueAndActualGreen[i]*counterBlueInFraction[i])*(currentBlueValue/(double)100) ;
							newBlue = (int)(actualBlue[i] - currentBlue) ;
						}
						else {
							
							currentBlue = (differenceBetweenActualBlueAndActualGreen[i]*actualBlueInFraction[i])*(currentBlueValue/(double)100) ;
							newBlue = (int)(actualBlue[i] - currentBlue) ;
						}
					}
					else {
						
						currentRed = (differenceBetweenActualBlueAndActualGreen[i]*counterRedInFraction[i])*(currentBlueValue/(double)100) ;
						newRed = (int)(actualRed[i] - currentRed) ;
						
						currentGreen = (differenceBetweenActualBlueAndActualGreen[i]*counterGreenInFraction[i])*(currentBlueValue/(double)100) ;
						newGreen = (int)(actualGreen[i] - currentGreen) ;
						
						currentBlue = (differenceBetweenActualBlueAndActualGreen[i]*counterBlueInFraction[i])*(currentBlueValue/(double)100) ;
						newBlue = (int)(actualBlue[i] - currentBlue) ;
					}
				}
				else {
					
					newRed = actualRed[i] ;
					newGreen = actualGreen[i] ;
					newBlue = actualBlue[i] ;
				}
				
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
