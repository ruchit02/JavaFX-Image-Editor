package com.topNotch.photoEditor.effects.clrBalance;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class IterateCyanRedHighlightsTask extends Task<int[]>{
	
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
	
	public IterateCyanRedHighlightsTask( Canvas canvas , int currentRedValue ,  int[] actualRed , int[] actualGreen , int[] actualBlue 
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
		int currentRed = 0 ;
		int newRed = 0 ;
		
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
				int alpha = 0xff<<24 ;
				///
				if( actualRed[i] >= 256 - currentRedValue ) {
					
					newRed = 255 ;
				}
				else {
					
					currentRed = (int)((currentRedValue/(255-(double)actualRed[i]))*actualRed[i]) ; 
					newRed = actualRed[i] + currentRed ;
				}
				///
				if( newRed > 255 ) {
					
					newRed = 255 ;
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
