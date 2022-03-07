package com.topNotch.photoEditor.effects.clrBalance;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class IterateCyanRedMidtonesTask2 extends Task<int[]>{
	
	private Canvas canvas ;
	private int width ;
	private int height ;
	
	private int currentRedValue ;
	
	private int iterations[] ;
	private int actualRed[] ;
	private int actualGreen[] ;
	private int actualBlue[] ;
	
	private double counterRedInFraction[];
	private double counterGreenInFraction[] ;
	private double counterBlueInFraction[] ;
	
	private PixelWriter pxlWriter ;
	private final PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	
	public IterateCyanRedMidtonesTask2( Canvas canvas , int currentRedValue ,  int[] actualRed , int[] actualGreen , int[] actualBlue 
			                       , double[] counterRedInFraction , double[] counterGreenInFraction , double[] counterBlueInFraction ) {
		
		this.canvas = canvas ;
		this.width = (int)canvas.getWidth() ;
		this.height = (int)canvas.getHeight() ;
		this.pxlWriter = canvas.getGraphicsContext2D().getPixelWriter() ;
		
		this.currentRedValue = currentRedValue ;
		
		this.iterations = new int[ width*height ];
		
		this.actualRed      = actualRed;
		this.actualGreen    = actualGreen;
		this.actualBlue     = actualBlue;
		
		this.counterRedInFraction = counterRedInFraction ;
		this.counterGreenInFraction = counterGreenInFraction ;
		this.counterBlueInFraction = counterBlueInFraction ;
	}
	
	@Override
	protected int[] call() {
		
		int i = 0 ;
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
				int maxRed = (int)(actualRed[i]*counterRedInFraction[i]) ;
				int maxGreen = (int)(actualGreen[i]*counterGreenInFraction[i]) ;
				int maxBlue = (int)(actualBlue[i]*counterBlueInFraction[i]) ;
 				
                maxRed = maxRed/2 ;
				
				if( actualGreen[i] > 196 ) {
					
					maxGreen = maxGreen/3 ;
				}
                else if( actualGreen[i] > 128 && actualGreen[i] < 197 ) {
					
					maxGreen = (int)(maxGreen/(double)2.75) ;
				}
				else if( actualGreen[i] > 96 && actualGreen[i] < 129 ) {
					
					maxGreen = (int)(maxGreen/(double)2.5) ;
				}
				else if( actualGreen[i] > 32 && actualGreen[i] < 97 ) {
					
					maxGreen = (int)(maxGreen/(double)2.25) ;
				}
				else {
					
					maxGreen = maxGreen/2 ;
				}
				
				if( actualBlue[i] > 196 ) {
					
					maxBlue = maxBlue/3 ;
				}
				else if( actualBlue[i] > 128 && actualBlue[i] < 197 ) {
					
					maxBlue = (int)(maxBlue/(double)2.75) ;
				}
                else if( actualBlue[i] > 96 && actualBlue[i] < 129 ) {
                	
                	maxBlue = (int)(maxBlue/(double)2.5) ;
                }
                else if( actualBlue[i] > 32 && actualBlue[i] < 97 ) {
                	
                	maxBlue = (int)(maxBlue/(double)2.25) ;
                }
                else {
                	
                	maxBlue = maxBlue/2 ;
                }
				
				int currentRed = (int)((currentRedValue/(double)100)*maxRed) ;
				int currentGreen = (int)((currentRedValue/(double)100)*maxGreen) ;
				int currentBlue = (int)((currentRedValue/(double)100)*maxBlue) ;
				
				int alpha = 0xff<<24 ;
				int newRed = actualRed[i] + currentRed ;
				int newGreen = actualGreen[i] - currentGreen ;
				int newBlue = actualBlue[i] - currentBlue ;
				
				if( newRed < 0 ) {
					
					newRed = 0 ;
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
