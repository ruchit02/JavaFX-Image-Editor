package com.topNotch.photoEditor.effects.slctvClr;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class SelectiveColorBlacksManipulatorForRedsTask extends Task<int[]> {
	
	private int iterations[] ;
	private Canvas canvas ;
	private int width ;
	private int height ;
	
    private int currentRedValue ;
	
	private int actualRed[] ;
	private int actualGreen[] ;
	private int actualBlue[] ;
	
	private double counterRedInFraction[];
	private double counterGreenInFraction[];
	private double counterBlueInFraction[];
	
	private double actualRedInFraction[];
	private double actualGreenInFraction[];
	private double actualBlueInFraction[];
	
	private int differenceBetweenActualRedAndActualGreen[] ;
	
	private PixelWriter pxlWriter ;
	private final PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	
	public SelectiveColorBlacksManipulatorForRedsTask( Canvas canvas , int currentRedValue , int[] actualRed , int[] actualGreen
			, int[] actualBlue , double[] actualRedInFraction , double[] actualGreenInFraction , double[] actualBlueInFraction 
			, double[] counterRedInFraction , double[] counterGreenInFraction , double[] counterBlueInFraction 
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
		this.actualGreenInFraction = actualGreenInFraction ;
		this.actualBlueInFraction = actualBlueInFraction ;
		
		this.counterRedInFraction = counterRedInFraction ;
		this.counterGreenInFraction = counterGreenInFraction ;
		this.counterBlueInFraction = counterBlueInFraction ;
		
		this.differenceBetweenActualRedAndActualGreen = differenceBetweenActualRedAndActualGreen ;
		
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
		
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
				int alpha = 0xff<<24 ;
				
				if( actualRed[i] > actualGreen[i] && actualRed[i] > actualBlue[i] ) {
					
					if( currentRedValue >= 0 ) {
						
						if( actualRed[i] > 127 ) {
							
							currentRed = (differenceBetweenActualRedAndActualGreen[i]*counterRedInFraction[i])*(currentRedValue/(double)100) ;
							newRed = (int)(actualRed[i] - currentRed) ;
						}
						else {
							
							currentRed = (differenceBetweenActualRedAndActualGreen[i]*actualRedInFraction[i])*(currentRedValue/(double)100) ;
							newRed = (int)(actualRed[i] - currentRed) ;
						}
						
                        if( actualGreen[i] > 127 ) {
							
							currentGreen = (differenceBetweenActualRedAndActualGreen[i]*counterGreenInFraction[i])*(currentRedValue/(double)100) ;
							newGreen = (int)(actualGreen[i] - currentGreen) ;
						}
						else {
							
							currentGreen = (differenceBetweenActualRedAndActualGreen[i]*actualGreenInFraction[i])*(currentRedValue/(double)100) ;
							newGreen = (int)(actualGreen[i] - currentGreen) ;
						}
                        
                        if( actualBlue[i] > 127 ) {
							
							currentBlue = (differenceBetweenActualRedAndActualGreen[i]*counterBlueInFraction[i])*(currentRedValue/(double)100) ;
							newBlue = (int)(actualBlue[i] - currentBlue) ;
						}
						else {
							
							currentBlue = (differenceBetweenActualRedAndActualGreen[i]*actualBlueInFraction[i])*(currentRedValue/(double)100) ;
							newBlue = (int)(actualBlue[i] - currentBlue) ;
						}
					}
					else {
						
						currentRed = (differenceBetweenActualRedAndActualGreen[i]*counterRedInFraction[i])*(currentRedValue/(double)100) ;
						newRed = (int)(actualRed[i] - currentRed) ;
						
						currentGreen = (differenceBetweenActualRedAndActualGreen[i]*counterGreenInFraction[i])*(currentRedValue/(double)100) ;
						newGreen = (int)(actualGreen[i] - currentGreen) ;
						
						currentBlue = (differenceBetweenActualRedAndActualGreen[i]*counterBlueInFraction[i])*(currentRedValue/(double)100) ;
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
