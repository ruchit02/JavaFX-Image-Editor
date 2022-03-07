package com.topNotch.photoEditor.effects.slctvClr;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class SelectiveColorYellowsManipulatorForMagentasTask extends Task<int[]> {
	
	private int iterations[] ;
	private Canvas canvas ;
	private int width ;
	private int height ;
	
   private int currentBlueValue ;
	
	private int actualRed[] ;
	private int actualGreen[] ;
	private int actualBlue[] ;
	
	private double actualBlueInFraction[] ;
	private double counterBlueInFraction[];
	
	private int differenceBetweenActualBlueAndActualGreen[] ;
	private int differenceBetweenActualRedAndActualGreen[];
	
	private PixelWriter pxlWriter ;
	private final PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	
	public SelectiveColorYellowsManipulatorForMagentasTask( Canvas canvas , int currentBlueValue ,  int[] actualRed , int[] actualGreen , int[] actualBlue 
            , double[] actualBlueInFraction , double[] counterBlueInFraction , int[] differenceBetweenActualBlueAndActualGreen 
            , int[] differenceBetweenActualRedAndActualGreen ) {
		
		this.canvas = canvas ;
		this.width = (int)canvas.getWidth() ;
		this.height = (int)canvas.getHeight() ;
		
        this.currentBlueValue = currentBlueValue ;
		
		this.iterations = new int[ width*height ];
		
		this.actualRed      = actualRed;
		this.actualGreen    = actualGreen;
		this.actualBlue     = actualBlue;
		
		this.actualBlueInFraction = actualBlueInFraction ;
		this.counterBlueInFraction = counterBlueInFraction ;
		
		this.differenceBetweenActualBlueAndActualGreen = differenceBetweenActualBlueAndActualGreen ;
		this.differenceBetweenActualRedAndActualGreen = differenceBetweenActualRedAndActualGreen ;
		
		this.pxlWriter = canvas.getGraphicsContext2D().getPixelWriter() ;
	}
	
	@Override
	protected int[] call() {
		
		int i = 0 ;
		
		double currentBlue = 0 ;
		
		int newBlue = 0;
		
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
				int alpha = 0xff<<24 ;
				
				if( actualBlue[i] > actualGreen[i] && actualRed[i] > actualGreen[i]  ) {
					
					if( currentBlueValue >= 0 ) {
						
						if( actualRed[i] > actualBlue[i] ) {
							
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
							
                            if( actualBlue[i] > 127 ) {
								
								currentBlue = (differenceBetweenActualRedAndActualGreen[i]*counterBlueInFraction[i])*(currentBlueValue/(double)100) ;
								newBlue = (int)(actualBlue[i] - currentBlue) ;
							}
							else {
								
								currentBlue = (differenceBetweenActualRedAndActualGreen[i]*actualBlueInFraction[i])*(currentBlueValue/(double)100) ;
								newBlue = (int)(actualBlue[i] - currentBlue) ;
							}
						}
					}
					else {
						
						if( actualRed[i] > actualBlue[i] ) {
							
							currentBlue = (differenceBetweenActualBlueAndActualGreen[i]*counterBlueInFraction[i])*(currentBlueValue/(double)100) ;
							newBlue = (int)(actualBlue[i] - currentBlue) ;
						}
						else {
							
							currentBlue = (differenceBetweenActualRedAndActualGreen[i]*counterBlueInFraction[i])*(currentBlueValue/(double)100) ;
							newBlue = (int)(actualBlue[i] - currentBlue) ;
						}
					}
				}
				else {
					
					newBlue = actualBlue[i] ;
				}
				
				iterations[i] = alpha | actualRed[i]<<16 | actualGreen[i]<<8 | newBlue ;
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
