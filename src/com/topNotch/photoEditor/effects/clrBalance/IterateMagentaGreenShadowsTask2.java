package com.topNotch.photoEditor.effects.clrBalance;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class IterateMagentaGreenShadowsTask2 extends Task<int[]>{
	
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
	
	public IterateMagentaGreenShadowsTask2( Canvas canvas , int currentRedValue ,  int[] actualRed , int[] actualGreen , int[] actualBlue 
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
		int currentGreen = 0 ;
		int newGreen = 0 ;
		
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
				int alpha = 0xff<<24 ;
				///
				if( actualGreen[i] <= currentRedValue ) {
					
					newGreen = 0 ;
				}
				else {
					
					currentGreen = (int)((currentRedValue/(double)actualGreen[i])*100) ; 
					newGreen = actualGreen[i] + currentGreen ;
				}
				///
				if( newGreen < 0 ) {
					
					newGreen = 0 ;
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