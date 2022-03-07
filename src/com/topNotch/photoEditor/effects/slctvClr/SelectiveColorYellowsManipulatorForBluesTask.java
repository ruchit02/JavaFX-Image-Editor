package com.topNotch.photoEditor.effects.slctvClr;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class SelectiveColorYellowsManipulatorForBluesTask extends Task<int[]> {
	
	private int iterations[] ;
	private Canvas canvas ;
	private int width ;
	private int height ;
	
   private int currentGreenValue ;
	
	private int actualRed[] ;
	private int actualGreen[] ;
	private int actualBlue[] ;
	
	private double counterBlueInFraction[];
	private int differenceBetweenActualBlueAndActualGreen[] ;
	
	private PixelWriter pxlWriter ;
	private final PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	
	public SelectiveColorYellowsManipulatorForBluesTask( Canvas canvas , int currentGreenValue ,  int[] actualRed , int[] actualGreen , int[] actualBlue 
            , double[] counterBlueInFraction , int[] differenceBetweenActualBlueAndActualGreen ) {
		
		this.canvas = canvas ;
		this.width = (int)canvas.getWidth() ;
		this.height = (int)canvas.getHeight() ;
		
        this.currentGreenValue = currentGreenValue ;
		
		this.iterations = new int[ width*height ];
		
		this.actualRed      = actualRed;
		this.actualGreen    = actualGreen;
		this.actualBlue     = actualBlue;
		
		this.counterBlueInFraction = counterBlueInFraction ;
		this.differenceBetweenActualBlueAndActualGreen = differenceBetweenActualBlueAndActualGreen ;
		
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
				
				if( actualBlue[i] > actualGreen[i] && actualGreen[i] > actualRed[i] ) {
					
					currentBlue = (differenceBetweenActualBlueAndActualGreen[i]*counterBlueInFraction[i])*(currentGreenValue/(double)100) ;
					newBlue = (int)(actualBlue[i] - currentBlue) ;
				}
				else {
					
					newBlue = actualBlue[i] ;
				}
				
				if( newBlue > 255 ) {
					
					newBlue = 255 ;
				}
				
				if( newBlue < 0) {
					
					newBlue = 0 ;
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
