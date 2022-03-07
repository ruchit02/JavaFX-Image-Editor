package com.topNotch.photoEditor.effects.slctvClr;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class SelectiveColorBlacksManipulatorForYellowsTask extends Task<int[]>{
	
	private int iterations[] ;
	private Canvas canvas ;
	private int width ;
	private int height ;
	
    private int currentBlackValue ;
	
	private int actualRed[] ;
	private int actualGreen[] ;
	private int actualBlue[] ;
	
	private double actualRedInFraction[];
	private double actualGreenInFraction[];
	private double actualBlueInFraction[];
	
	private double counterRedInFraction[];
	private double counterGreenInFraction[];
	private double counterBlueInFraction[];
	
	
	private int differenceBetweenActualRedAndActualBlue[] ;
	private int differenceBetweenActualGreenAndActualBlue[] ;
	
	private PixelWriter pxlWriter ;
	private final PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	
	public SelectiveColorBlacksManipulatorForYellowsTask( Canvas canvas , int currentBlackValue , int[] actualRed , int[] actualGreen
			, int[] actualBlue , double[] actualRedInFraction , double[] actualGreenInFraction , double[] actualBlueInFraction 
			, double[] counterRedInFraction , double[] counterGreenInFraction , double[] counterBlueInFraction 
			, int[] differenceBetweenActualRedAndActualBlue , int[] differenceBetweenActualGreenAndActualBlue ) {
		
		this.canvas = canvas ;
		this.width = (int)canvas.getWidth() ;
		this.height = (int)canvas.getHeight() ;
		
        this.currentBlackValue = currentBlackValue ;
		
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
		
		this.differenceBetweenActualRedAndActualBlue = differenceBetweenActualRedAndActualBlue ;
		this.differenceBetweenActualGreenAndActualBlue = differenceBetweenActualGreenAndActualBlue ;
		
		this.pxlWriter = canvas.getGraphicsContext2D().getPixelWriter() ;
	}
	
	@Override
	protected int[] call() {
		
        int i = 0 ;
		
        double currentRed = 0;
		double currentGreen = 0 ;
		double currentBlue = 0 ;
		
		int newRed = 0 ;
		int newGreen = 0;
		int newBlue = 0 ;
		
		int alpha = 0xff<<24 ;
		
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
				if( actualRed[i] > actualBlue[i] && actualGreen[i] > actualBlue[i] ) {
					
					if( currentBlackValue >= 0 ) {
						
						if( actualRed[i] > actualGreen[i] ) {
							
                            if( actualRed[i] > 127 ) {
								
								currentRed = (differenceBetweenActualGreenAndActualBlue[i]*counterRedInFraction[i])*(currentBlackValue/(double)100) ;
								newRed = (int)(actualRed[i] - currentRed) ;
							}
							else {
								
								currentRed = (differenceBetweenActualGreenAndActualBlue[i]*actualRedInFraction[i])*(currentBlackValue/(double)100) ;
								newRed = (int)(actualRed[i] - currentRed) ;
							}
							
							if( actualGreen[i] > 127 ) {
								
								currentGreen = (differenceBetweenActualGreenAndActualBlue[i]*counterGreenInFraction[i])*(currentBlackValue/(double)100) ;
								newGreen = (int)(actualGreen[i] - currentGreen) ;
							}
							else {
								
								currentGreen = (differenceBetweenActualGreenAndActualBlue[i]*actualGreenInFraction[i])*(currentBlackValue/(double)100) ;
								newGreen = (int)(actualGreen[i] - currentGreen) ;
							}
							
                            if( actualBlue[i] > 127 ) {
								
								currentBlue = (differenceBetweenActualGreenAndActualBlue[i]*counterBlueInFraction[i])*(currentBlackValue/(double)100) ;
								newBlue = (int)(actualBlue[i] - currentBlue) ;
							}
							else {
								
								currentBlue = (differenceBetweenActualGreenAndActualBlue[i]*actualBlueInFraction[i])*(currentBlackValue/(double)100) ;
								newBlue = (int)(actualBlue[i] - currentBlue) ;
							}
						}
						else {
							
                            if( actualRed[i] > 127 ) {
								
								currentRed = (differenceBetweenActualRedAndActualBlue[i]*counterRedInFraction[i])*(currentBlackValue/(double)100) ;
								newRed = (int)(actualRed[i] - currentRed) ;
							}
							else {
								
								currentRed = (differenceBetweenActualRedAndActualBlue[i]*actualRedInFraction[i])*(currentBlackValue/(double)100) ;
								newRed = (int)(actualRed[i] - currentRed) ;
							}
							
							if( actualGreen[i] > 127 ) {
								
								currentGreen = (differenceBetweenActualRedAndActualBlue[i]*counterGreenInFraction[i])*(currentBlackValue/(double)100) ;
								newGreen = (int)(actualGreen[i] - currentGreen) ;
							}
							else {
								
								currentGreen = (differenceBetweenActualRedAndActualBlue[i]*actualGreenInFraction[i])*(currentBlackValue/(double)100) ;
								newGreen = (int)(actualGreen[i] - currentGreen) ;
							}
							
                            if( actualBlue[i] > 127 ) {
								
								currentBlue = (differenceBetweenActualRedAndActualBlue[i]*counterBlueInFraction[i])*(currentBlackValue/(double)100) ;
								newBlue = (int)(actualBlue[i] - currentBlue) ;
							}
							else {
								
								currentBlue = (differenceBetweenActualRedAndActualBlue[i]*actualBlueInFraction[i])*(currentBlackValue/(double)100) ;
								newBlue = (int)(actualBlue[i] - currentBlue) ;
							}
						}
					}
					else {
						
                        if( actualRed[i] > actualGreen[i] ) {
							
                        	currentRed = (differenceBetweenActualGreenAndActualBlue[i]*counterRedInFraction[i])*(currentBlackValue/(double)100) ;
							newRed = (int)(actualRed[i] - currentRed) ;
                        	
							currentGreen = (differenceBetweenActualGreenAndActualBlue[i]*counterGreenInFraction[i])*(currentBlackValue/(double)100) ;
							newGreen = (int)(actualGreen[i] - currentGreen) ;
							
							currentBlue = (differenceBetweenActualGreenAndActualBlue[i]*counterBlueInFraction[i])*(currentBlackValue/(double)100) ;
							newBlue = (int)(actualBlue[i] - currentBlue) ;
						}
						else {
							
							currentRed = (differenceBetweenActualRedAndActualBlue[i]*counterRedInFraction[i])*(currentBlackValue/(double)100) ;
							newRed = (int)(actualRed[i] - currentRed) ;
							
							currentGreen = (differenceBetweenActualRedAndActualBlue[i]*counterGreenInFraction[i])*(currentBlackValue/(double)100) ;
							newGreen = (int)(actualGreen[i] - currentGreen) ;
							
							currentBlue = (differenceBetweenActualRedAndActualBlue[i]*counterBlueInFraction[i])*(currentBlackValue/(double)100) ;
							newBlue = (int)(actualBlue[i] - currentBlue) ;
						}
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
