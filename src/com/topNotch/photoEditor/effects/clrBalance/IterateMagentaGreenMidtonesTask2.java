package com.topNotch.photoEditor.effects.clrBalance;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class IterateMagentaGreenMidtonesTask2 extends Task<int[]>{
	
	private Canvas canvas ;
	private int width ;
	private int height ;
	
	private int currentGreenValue ;
	
	private int iterations[] ;
	private int actualRed[] ;
	private int actualGreen[] ;
	private int actualBlue[] ;
	
	private double counterRedInFraction[];
	private double counterGreenInFraction[] ;
	private double counterBlueInFraction[] ;
	
	private PixelWriter pxlWriter ;
	private final PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	
	public IterateMagentaGreenMidtonesTask2( Canvas canvas , int currentGreenValue ,  int[] actualRed , int[] actualGreen , int[] actualBlue 
			                       , double[] counterRedInFraction , double[] counterGreenInFraction , double[] counterBlueInFraction ) {
		
		this.canvas = canvas ;
		this.width = (int)canvas.getWidth() ;
		this.height = (int)canvas.getHeight() ;
		this.pxlWriter = canvas.getGraphicsContext2D().getPixelWriter() ;
		
		this.currentGreenValue = currentGreenValue ;
		
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
				
				maxGreen = maxGreen/2 ;
				
				if( actualRed[i] > 196 ) {
					
					maxRed = maxRed/3 ;
				}
                else if( actualRed[i] > 128 && actualRed[i] < 197 ) {
					
					maxRed = (int)(maxRed/(double)2.75) ;
				}
				else if( actualRed[i] > 96 && actualRed[i] < 129 ) {
					
					maxRed = (int)(maxRed/(double)2.5) ;
				}
                else if( actualRed[i] > 32 && actualRed[i] < 97 ) {
					
					maxRed = (int)(maxRed/(double)2.25) ;
				}
				else {
					
					maxRed = maxRed/2 ;
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
				
				int currentRed = (int)((currentGreenValue/(double)100)*maxRed) ;
				int currentGreen = (int)((currentGreenValue/(double)100)*maxGreen) ;
				int currentBlue = (int)((currentGreenValue/(double)100)*maxBlue) ;
				
				int alpha = 0xff<<24 ;
				int newRed = actualRed[i] - currentRed ;
				int newGreen = actualGreen[i] + currentGreen ;
				int newBlue = actualBlue[i] - currentBlue ;
				
				if( newGreen < 0 ) {
					
					newGreen = 0 ;
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

