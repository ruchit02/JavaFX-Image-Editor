package com.topNotch.photoEditor.effects.slctvClr;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class SelectiveColorMagentasManipulatorForGreensTask extends Task<int[]> {
	
	private int iterations[] ;
	private Canvas canvas ;
	private int width ;
	private int height ;
	
   private int currentGreenValue ;
	
	private int actualRed[] ;
	private int actualGreen[] ;
	private int actualBlue[] ;
	
	private double actualGreenInFraction[]; 
	private double counterGreenInFraction[];
	private int differenceBetweenActualGreenAndActualRed[] ;
	
	private PixelWriter pxlWriter ;
	private final PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	
	public SelectiveColorMagentasManipulatorForGreensTask( Canvas canvas , int currentGreenValue ,  int[] actualRed , int[] actualGreen , int[] actualBlue 
            , double[] actualGreenInFraction , double[] counterGreenInFraction , int[] differenceBetweenActualGreenAndActualRed ) {
		
		this.canvas = canvas ;
		this.width = (int)canvas.getWidth() ;
		this.height = (int)canvas.getHeight() ;
		
        this.currentGreenValue = currentGreenValue ;
		
		this.iterations = new int[ width*height ];
		
		this.actualRed      = actualRed;
		this.actualGreen    = actualGreen;
		this.actualBlue     = actualBlue;
		
		this.actualGreenInFraction = actualGreenInFraction ;
		this.counterGreenInFraction = counterGreenInFraction ;
		this.differenceBetweenActualGreenAndActualRed = differenceBetweenActualGreenAndActualRed ;
		
		this.pxlWriter = canvas.getGraphicsContext2D().getPixelWriter() ;
	}
	
	@Override
	protected int[] call() {
		
		int i = 0 ;
		
		double currentGreen = 0 ;
		
		int newGreen = 0;
		
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
				int alpha = 0xff<<24 ;
				
				if( actualGreen[i] > actualRed[i] && actualGreen[i] > actualBlue[i] ) {
					
					if( currentGreenValue >= 0 ) {
						
						if( actualGreen[i] > 127 ) {
							
							currentGreen = (differenceBetweenActualGreenAndActualRed[i]*counterGreenInFraction[i])*(currentGreenValue/(double)100) ;
							newGreen = (int)(actualGreen[i] - currentGreen) ;
						}
						else {
							
							currentGreen = (differenceBetweenActualGreenAndActualRed[i]*actualGreenInFraction[i])*(currentGreenValue/(double)100) ;
							newGreen = (int)(actualGreen[i] - currentGreen) ;
						}
					}
					else {
						
						currentGreen = (differenceBetweenActualGreenAndActualRed[i]*counterGreenInFraction[i])*(currentGreenValue/(double)100) ;
						newGreen = (int)(actualGreen[i] - currentGreen) ;
					}
				}
				else {
					
					newGreen = actualGreen[i] ;
				}
				
				iterations[i] = alpha | actualRed[i]<<16 | newGreen<<8 | actualBlue[i] ;
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

