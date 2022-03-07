package com.topNotch.photoEditor.effects.blackAndWhite;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class BlackAndWhiteGreensManipulatorTask extends Task<int[]> {
	
	private int iterations[] ;
	private Canvas canvas ;
	private int width ;
	private int height ;
	
   private int currentValue ;
	
	private int actualRed[] ;
	private int actualGreen[] ;
	private int actualBlue[] ;
	private int rgbAverage[] ;
	
	private PixelWriter pxlWriter ;
	private final PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	
	public BlackAndWhiteGreensManipulatorTask( Canvas canvas , int currentValue ,  int[] actualRed , int[] actualGreen , int[] actualBlue 
            , int[] rgbAverage ) {
		
		this.canvas = canvas ;
		this.width = (int)canvas.getWidth() ;
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
		
		int newRed = 0 ;
		int newGreen = 0 ;
		int newBlue = 0 ;
		
		int differenceBetweenRedAndAverage = 0 ;
		int differenceBetweenGreenAndAverage = 0 ;
		int differenceBetweenBlueAndAverage = 0 ;
		
		int resultant = 0 ;
		
		int alpha = 0xff<<24 ;
		
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
				if( actualGreen[i] >= actualBlue[i] && actualGreen[i] >= actualRed[i] ) { 
					
					if( actualBlue[i] >= actualRed[i] ) {
						
						differenceBetweenGreenAndAverage = actualGreen[i] - rgbAverage[i] ;
						differenceBetweenBlueAndAverage = actualBlue[i] - rgbAverage[i] ;
						resultant = differenceBetweenGreenAndAverage - differenceBetweenBlueAndAverage ;
						
						newRed = (int)(actualRed[i] + (resultant*(currentValue/(double)100))) ;
						newGreen = newRed ;
						newBlue = newRed ;
					}
					else {
						
						differenceBetweenGreenAndAverage = actualGreen[i] - rgbAverage[i] ;
						differenceBetweenRedAndAverage = actualRed[i] - rgbAverage[i] ;
						resultant = differenceBetweenGreenAndAverage - differenceBetweenRedAndAverage ;
						
						newBlue = (int)(actualBlue[i] + (resultant*(currentValue/(double)100))) ;
						newRed = newBlue ;
						newGreen = newBlue ;
					}
				}
				else {
					
					if( actualBlue[i] <= actualRed[i] ) {
						
						if( actualGreen[i] <= actualBlue[i] ) {
							
							newGreen = actualGreen[i] ;
							newRed = newGreen ;
							newBlue = newGreen ;
						}
						else {
							
							newBlue = actualBlue[i] ;
							newGreen = newBlue ;
							newRed = newBlue ;
						}
					}
					else {
						
                        if( actualGreen[i] <= actualRed[i] ) {
							
                        	newGreen = actualGreen[i] ;
							newBlue = newGreen ;
							newRed = newGreen ;
						}
						else {
							
							newRed = actualRed[i] ;
							newGreen = newRed ;
							newBlue = newRed ;
						}
					}
				}
                
                if( newRed < 0 ) {
                	newRed = 0 ;
                }
                
                if( newRed > 255 ) {
                	newRed = 255 ;
                }
                
                if( newGreen < 0 ) {
                	newGreen = 0 ;
                }
                
                if( newGreen > 255 ) {
                	newGreen = 255 ;
                }
                
                if( newBlue < 0 ) {
                	newBlue = 0 ;
                }
                
                if( newBlue > 255 ) {
                	newBlue = 255 ;
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