package com.topNotch.photoEditor.effects.photoFltr;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class PhotoFilterWarmingFilterTask extends Task<int[]>{
	
	private int iterations[] ;
	private Canvas canvas ;
	private int width ;
	private int height ;
	
	private int actualRed[] ;
	private int actualGreen[] ;
	private int actualBlue[] ;
	
	private int rgbAverage[] ;
	
	private int filterRed ;
	private int filterGreen ;
	private int filterBlue ;
	
	private int currentValue ;
	
	private PixelWriter pxlWriter ;
	
	private final PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	
	public PhotoFilterWarmingFilterTask( Canvas canvas , int currentValue , int[] actualRed , int[] actualGreen , int[] actualBlue
			, int[] rgbAverage , int filterRed , int filterGreen , int filterBlue ) {
		
		this.canvas = canvas ;
		this.width = (int)this.canvas.getWidth() ;
		this.height = (int)this.canvas.getHeight() ;
		this.iterations = new int[ width*height ];
		
		this.actualRed = actualRed ;
		this.actualGreen = actualGreen ;
		this.actualBlue = actualBlue ;
		
		this.rgbAverage = rgbAverage ;
		
		this.filterRed = filterRed ;
		this.filterGreen = filterGreen ;
		this.filterBlue = filterBlue ;
		
		this.currentValue = currentValue ;
		
		this.pxlWriter = canvas.getGraphicsContext2D().getPixelWriter() ;
	}
	
	@Override
	protected int[] call() {
		
		int i = 0;
		
		int currentRed1 = 0 ;
		int currentGreen1 = 0 ;
		int currentBlue1 = 0 ;
		
		int currentRed2 = 0 ;
		int currentGreen2 = 0 ;
		int currentBlue2 = 0 ;
		
		int currentRed3 = 0 ;
		int currentGreen3 = 0 ;
		int currentBlue3 = 0 ;
		
		int newRed = 0 ;
		int newGreen = 0 ;
		int newBlue = 0 ;
		
		int alpha = 0xff<<24 ;
		
		int extraGreen1 = 0 ;
		int extraBlue1 = 0 ;
		
		int extraRed2 = 0 ;
		int extraBlue2 = 0 ;
		
		int extraRed3 = 0 ;
		int extraGreen3 = 0 ;
		
		int diffBetweenFilterRedAndActualRed = 0 ;
		int diffBetweenFilterGreenAndActualGreen = 0 ;
		int diffBetweenFilterBlueAndActualBlue = 0 ;
		
		int diffBetweenFilterRedAndRgbAverage = 0 ;
		int diffBetweenFilterGreenAndRgbAverage = 0 ;
		int diffBetweenFilterBlueAndRgbAverage = 0 ;
		
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
				diffBetweenFilterRedAndActualRed = filterRed - actualRed[i] ;
				diffBetweenFilterGreenAndActualGreen = filterGreen - actualGreen[i] ;
				diffBetweenFilterBlueAndActualBlue = filterBlue - actualBlue[i] ; 
				
				diffBetweenFilterRedAndRgbAverage = filterRed - rgbAverage[i] ;
				diffBetweenFilterGreenAndRgbAverage = filterGreen - rgbAverage[i] ;
				diffBetweenFilterBlueAndRgbAverage = filterBlue - rgbAverage[i] ;
				
				if( filterRed >= filterGreen && filterRed >= filterBlue ) {
					
					if( filterGreen >= filterBlue ) {
						
						currentRed1 = (int)((actualRed[i]/(double)256)*filterRed) ;
						extraGreen1 = actualGreen[i] - rgbAverage[i] ;
						currentGreen1 = (int)((extraGreen1/(double)256)*diffBetweenFilterGreenAndActualGreen) ;
						extraBlue1 = actualBlue[i] - rgbAverage[i] ;
						currentBlue1 = (int)((extraBlue1/(double)256)*diffBetweenFilterBlueAndActualBlue) ;
						newRed = actualRed[i] - (int)((actualRed[i] - (currentRed1 + currentGreen1 - currentBlue1))*(currentValue/(double)100)) ;
						
						currentGreen2 = (int)((actualGreen[i]/(double)256)*filterGreen) ;
	    				extraRed2 = actualRed[i] - rgbAverage[i] ;
	    				currentRed2 = (int)((extraRed2/(double)256)*diffBetweenFilterRedAndActualRed) ; 
	    				extraBlue2 = actualBlue[i] - rgbAverage[i] ;
	    				currentBlue2 = (int)((extraBlue2/(double)256)*diffBetweenFilterBlueAndActualBlue) ;
	    				newGreen = actualGreen[i] - (int)((actualGreen[i] - (currentRed2 + currentGreen2 - currentBlue2))*(currentValue/(double)100)) ;
	    				
	    				currentBlue3 = (int)((actualBlue[i]/(double)256)*filterBlue) ;
	    				extraGreen3 = actualGreen[i] - rgbAverage[i] ;
	    				currentGreen3 = (int)((extraGreen3/(double)256)*diffBetweenFilterGreenAndActualGreen) ;
	    				extraRed3 = actualRed[i] - rgbAverage[i] ;
	    				currentRed3 = (int)((extraRed3/(double)256)*diffBetweenFilterRedAndRgbAverage) ; 
	    				newBlue = actualBlue[i] - (int)((actualBlue[i] - (currentBlue3 - currentRed3 - currentGreen3))*(currentValue/(double)100)) ;
					}
					else {
						
						currentRed1 = (int)((actualRed[i]/(double)256)*filterRed) ;
						extraGreen1 = actualGreen[i] - rgbAverage[i] ;
						currentGreen1 = (int)((extraGreen1/(double)256)*diffBetweenFilterGreenAndActualGreen) ;
						extraBlue1 = actualBlue[i] - rgbAverage[i] ;
						currentBlue1 = (int)((extraBlue1/(double)256)*diffBetweenFilterBlueAndActualBlue) ;
						newRed = actualRed[i] - (int)((actualRed[i] - (currentRed1 + currentBlue1 - currentGreen1))*(currentValue/(double)100)) ;
						
						currentBlue3 = (int)((actualBlue[i]/(double)256)*filterBlue) ;
	    				extraRed3 = actualRed[i] - rgbAverage[i] ;
	    				currentRed3 = (int)((extraRed3/(double)256)*diffBetweenFilterRedAndActualRed) ; 
	    				extraGreen3 = actualGreen[i] - rgbAverage[i] ;
	    				currentGreen3 = (int)((extraGreen3/(double)256)*diffBetweenFilterGreenAndActualGreen) ;
	    				newBlue = actualBlue[i] - (int)((actualBlue[i] - (currentRed3 + currentBlue3 - currentGreen3))*(currentValue/(double)100)) ;
	    				
	    				currentGreen2 = (int)((actualGreen[i]/(double)256)*filterGreen) ;
	    				extraBlue2 = actualBlue[i] - rgbAverage[i] ;
	    				currentBlue2 = (int)((extraBlue2/(double)256)*diffBetweenFilterBlueAndActualBlue) ;
	    				extraRed2 = actualRed[i] - rgbAverage[i] ;
	    				currentRed2 = (int)((extraRed2/(double)256)*diffBetweenFilterRedAndRgbAverage) ; 
	    				newGreen = actualGreen[i] - (int)((actualGreen[i] - (currentGreen2 - currentRed2 - currentBlue2))*(currentValue/(double)100)) ;
					}
				}
				
				if( filterGreen >= filterBlue && filterGreen >= filterRed ) {
    				
					if( filterRed >= filterBlue ) {
						
						currentGreen2 = (int)((actualGreen[i]/(double)256)*filterGreen) ;
	    				extraRed2 = actualRed[i] - rgbAverage[i] ;
	    				currentRed2 = (int)((extraRed2/(double)256)*diffBetweenFilterRedAndActualRed) ; 
	    				extraBlue2 = actualBlue[i] - rgbAverage[i] ;
	    				currentBlue2 = (int)((extraBlue2/(double)256)*diffBetweenFilterBlueAndActualBlue) ;
	    				newGreen = actualGreen[i] - (int)((actualGreen[i] - (currentGreen2 + currentRed2 - currentBlue2))*(currentValue/(double)100)) ;
						
						currentRed1 = (int)((actualRed[i]/(double)256)*filterRed) ;
						extraGreen1 = actualGreen[i] - rgbAverage[i] ;
						currentGreen1 = (int)((extraGreen1/(double)256)*diffBetweenFilterGreenAndActualGreen) ;
						extraBlue1 = actualBlue[i] - rgbAverage[i] ;
						currentBlue1 = (int)((extraBlue1/(double)256)*diffBetweenFilterBlueAndActualBlue) ;
						newRed = actualRed[i] - (int)((actualRed[i] - (currentGreen1 + currentRed1 - currentBlue1))*(currentValue/(double)100)) ;
						
						currentBlue3 = (int)((actualBlue[i]/(double)256)*filterBlue) ;
	    				extraRed3 = actualRed[i] - rgbAverage[i] ;
	    				currentRed3 = (int)((extraRed3/(double)256)*diffBetweenFilterRedAndActualRed) ; 
	    				extraGreen3 = actualGreen[i] - rgbAverage[i] ;
	    				currentGreen3 = (int)((extraGreen3/(double)256)*diffBetweenFilterGreenAndRgbAverage) ;
	    				newBlue = actualBlue[i] - (int)((actualBlue[i] - (currentBlue3 - currentGreen3 - currentRed3))*(currentValue/(double)100)) ;
					}
					else {
						
						currentGreen2 = (int)((actualGreen[i]/(double)256)*filterGreen) ;
	    				extraRed2 = actualRed[i] - rgbAverage[i] ;
	    				currentRed2 = (int)((extraRed2/(double)256)*diffBetweenFilterRedAndActualRed) ; 
	    				extraBlue2 = actualBlue[i] - rgbAverage[i] ;
	    				currentBlue2 = (int)((extraBlue2/(double)256)*diffBetweenFilterBlueAndActualBlue) ;
	    				newGreen = actualGreen[i] - (int)((actualGreen[i] - (currentGreen2 + currentBlue2 - currentRed2))*(currentValue/(double)100)) ;
						
						currentBlue3 = (int)((actualBlue[i]/(double)256)*filterBlue) ;
	    				extraRed3 = actualRed[i] - rgbAverage[i] ;
	    				currentRed3 = (int)((extraRed3/(double)256)*diffBetweenFilterRedAndActualRed) ; 
	    				extraGreen3 = actualGreen[i] - rgbAverage[i] ;
	    				currentGreen3 = (int)((extraGreen3/(double)256)*diffBetweenFilterGreenAndActualGreen) ;
	    				newBlue = actualBlue[i] - (int)((actualBlue[i] - (currentGreen3 + currentBlue3 - currentRed3))*(currentValue/(double)100)) ;
	    				
	    				currentRed1 = (int)((actualRed[i]/(double)256)*filterRed) ;
						extraBlue1 = actualBlue[i] - rgbAverage[i] ;
						currentBlue1 = (int)((extraBlue1/(double)256)*diffBetweenFilterBlueAndActualBlue) ;
						extraGreen1 = actualGreen[i] - rgbAverage[i] ;
						currentGreen1 = (int)((extraGreen1/(double)256)*diffBetweenFilterGreenAndRgbAverage) ;
						newRed = actualRed[i] - (int)((actualRed[i] - (currentRed1 - currentGreen1 - currentBlue1))*(currentValue/(double)100)) ;
					}
				}
				
				if( filterBlue >= filterRed && filterBlue >= filterGreen ) {
					
					if( filterRed >= filterGreen ) {
						
						currentBlue3 = (int)((actualBlue[i]/(double)256)*filterBlue) ;
	    				extraRed3 = actualRed[i] - rgbAverage[i] ;
	    				currentRed3 = (int)((extraRed3/(double)256)*diffBetweenFilterRedAndActualRed) ; 
	    				extraGreen3 = actualGreen[i] - rgbAverage[i] ;
	    				currentGreen3 = (int)((extraGreen3/(double)256)*diffBetweenFilterGreenAndActualGreen) ;
	    				newBlue = actualBlue[i] - (int)((actualBlue[i] - (currentBlue3 + currentRed3 - currentGreen3))*(currentValue/(double)100)) ;
						
						currentRed1 = (int)((actualRed[i]/(double)256)*filterRed) ;
						extraGreen1 = actualGreen[i] - rgbAverage[i] ;
						currentGreen1 = (int)((extraGreen1/(double)256)*diffBetweenFilterGreenAndActualGreen) ;
						extraBlue1 = actualBlue[i] - rgbAverage[i] ;
						currentBlue1 = (int)((extraBlue1/(double)256)*diffBetweenFilterBlueAndActualBlue) ;
						newRed = actualRed[i] - (int)((actualRed[i] - (currentBlue1 + currentRed1 - currentGreen1))*(currentValue/(double)100)) ;
						
						currentGreen2 = (int)((actualGreen[i]/(double)256)*filterGreen) ;
	    				extraRed2 = actualRed[i] - rgbAverage[i] ;
	    				currentRed2 = (int)((extraRed2/(double)256)*diffBetweenFilterRedAndActualRed) ; 
	    				extraBlue2 = actualBlue[i] - rgbAverage[i] ;
	    				currentBlue2 = (int)((extraBlue2/(double)256)*diffBetweenFilterBlueAndRgbAverage) ;
	    				newGreen = actualGreen[i] - (int)((actualGreen[i] - (currentGreen2 - currentBlue2 - currentRed2))*(currentValue/(double)100)) ;
					}
					else {
						
						currentBlue3 = (int)((actualBlue[i]/(double)256)*filterBlue) ;
	    				extraRed3 = actualRed[i] - rgbAverage[i] ;
	    				currentRed3 = (int)((extraRed3/(double)256)*diffBetweenFilterRedAndActualRed) ; 
	    				extraGreen3 = actualGreen[i] - rgbAverage[i] ;
	    				currentGreen3 = (int)((extraGreen3/(double)256)*diffBetweenFilterGreenAndActualGreen) ;
	    				newBlue = actualBlue[i] - (int)((actualBlue[i] - (currentBlue3 + currentGreen3 - currentRed3))*(currentValue/(double)100)) ;
						
						currentGreen2 = (int)((actualGreen[i]/(double)256)*filterGreen) ;
	    				extraRed2 = actualRed[i] - rgbAverage[i] ;
	    				currentRed2 = (int)((extraRed2/(double)256)*diffBetweenFilterRedAndActualRed) ; 
	    				extraBlue2 = actualBlue[i] - rgbAverage[i] ;
	    				currentBlue2 = (int)((extraBlue2/(double)256)*diffBetweenFilterBlueAndActualBlue) ;
	    				newGreen = actualGreen[i] - (int)((actualGreen[i] - (currentBlue2 + currentGreen2 - currentRed2))*(currentValue/(double)100)) ;
	    				
	    				currentRed1 = (int)((actualRed[i]/(double)256)*filterRed) ;
						extraGreen1 = actualGreen[i] - rgbAverage[i] ;
						currentGreen1 = (int)((extraGreen1/(double)256)*diffBetweenFilterGreenAndActualGreen) ;
						extraBlue1 = actualBlue[i] - rgbAverage[i] ;
						currentBlue1 = (int)((extraBlue1/(double)256)*diffBetweenFilterBlueAndRgbAverage) ;
						newRed = actualRed[i] - (int)((actualRed[i] - (currentRed1 - currentBlue1 - currentGreen1))*(currentValue/(double)100)) ;
					}
				}
                
                if( newRed < 0 ) {
					newRed = 0 ;
				}
                
                if( newGreen < 0 ) {
					newGreen = 0 ;
				}
                
                if( newBlue < 0 ) {
                	newBlue = 0 ;
                }
                
                if( newRed > 255 ) {
					newRed = 255 ;
				}
                
                if( newGreen > 255 ) {
					newGreen = 255 ;
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























/*if( filterRed != 0 ) {

currentRed1 = (int)((actualRed[i]/(double)256)*filterRed) ;

extraGreen1 = actualGreen[i] - rgbAverage[i] ;
currentGreen1 = (int)((extraGreen1/(double)256)*diffBetweenFilterGreenAndActualGreen) ;

extraBlue1 = actualBlue[i] - rgbAverage[i] ;
currentBlue1 = (int)((extraBlue1/(double)256)*diffBetweenFilterBlueAndActualBlue);

newRed = currentRed1 + currentGreen1 - currentBlue1 ;
}
else {

newRed = 0 ;
}

if( filterGreen != 0 ) {

kcurrentGreen2 = (int)((actualGreen[i]/(double)256)*filterGreen) ;

extraRed2 = actualRed[i] - rgbAverage[i] ;
currentRed2 = (int)((extraRed2/(double)256)*diffBetweenFilterRedAndActualRed) ; 

extraBlue2 = actualBlue[i] - rgbAverage[i] ;
currentBlue2 = (int)((extraBlue2/(double)256)*diffBetweenFilterBlueAndActualBlue);

newGreen = currentGreen2 - currentBlue2 + currentRed2 ;
}
else {

newGreen = 0 ;
}

if( filterBlue != 0 ) {

currentBlue3 = (int)((actualBlue[i]/(double)256)*filterBlue) ;

extraRed3 = actualRed[i] - rgbAverage[i] ;
currentRed3 = (int)((extraRed3/(double)256)*diffBetweenFilterRedAndActualRed) ; 

extraGreen3 = actualGreen[i] - rgbAverage[i] ;
currentGreen3 = (int)((extraGreen3/(double)256)*diffBetweenFilterGreenAndActualGreen) ;

newBlue = currentBlue3 + currentRed3 + currentGreen3 ;
}
else {

newBlue = 0 ;
}*/
