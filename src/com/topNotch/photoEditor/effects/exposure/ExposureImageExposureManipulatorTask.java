package com.topNotch.photoEditor.effects.exposure;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class ExposureImageExposureManipulatorTask extends Task<int[]> {
	
	private int iterations[] ;
	private Canvas canvas ;
	private int width ;
	private int height ;
	
   private double currentValue ;
	
	private int actualRed[] ;
	private int actualGreen[] ;
	private int actualBlue[] ;
	private int rgbAverage[] ;
	
	private PixelWriter pxlWriter ;
	private final PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	
	public ExposureImageExposureManipulatorTask( Canvas canvas , double currentValue ,  int[] actualRed , int[] actualGreen , int[] actualBlue 
            , int[] rgbAverage ) {
		
		this.canvas = canvas ;
		this.width  = (int)canvas.getWidth() ;
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
		
		double currentRedValue = 0 ;
		double currentGreenValue = 0 ;
		double currentBlueValue = 0 ;
		
		int counterRed = 0 ;
		int counterGreen = 0 ;
		int counterBlue = 0 ;
		
		int newRed = 0 ;
		int newGreen = 0 ;
		int newBlue = 0 ;
		
		int alpha = 0xff<<24 ;
		
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
				if( currentValue >= 0 ) {
					
					newRed = (int)(actualRed[i] + (actualRed[i]*(currentValue*(128/(double)256)))) ;
					newGreen = (int)(actualGreen[i] + (actualGreen[i]*(currentValue*(128/(double)256)))) ;
					newBlue = (int)(actualBlue[i] + (actualBlue[i]*(currentValue*(128/(double)256)))) ;
				}
				else {
					
					if( currentValue < 0 && currentValue >= -1 ) {
						
						newRed = (int)(actualRed[i] + ((actualRed[i]*0.0625)*(currentValue/(double)1))) ;
						newGreen = (int)(actualGreen[i] + ((actualGreen[i]*0.0625)*(currentValue/(double)1))) ;
						newBlue = (int)(actualBlue[i] + ((actualBlue[i]*0.0625)*(currentValue/(double)1))) ;
					}
					else if( currentValue < -1 && currentValue >= -2 ) {
						
						newRed = (int)(actualRed[i] + ((actualRed[i]*0.125)*(currentValue/(double)2))) ;
						newGreen = (int)(actualGreen[i] + ((actualGreen[i]*0.125)*(currentValue/(double)2))) ;
						newBlue = (int)(actualBlue[i] + ((actualBlue[i]*0.125)*(currentValue/(double)2))) ;
					}
	                else if( currentValue < -2 && currentValue >= -3 ) {
						
	                	newRed = (int)(actualRed[i] + ((actualRed[i]*0.1875)*(currentValue/(double)3))) ;
						newGreen = (int)(actualGreen[i] + ((actualGreen[i]*0.1875)*(currentValue/(double)3))) ;
						newBlue = (int)(actualBlue[i] + ((actualBlue[i]*0.1875)*(currentValue/(double)3))) ;
					}
	                else if( currentValue < -3 && currentValue >= -4 ) {
						
	                	newRed = (int)(actualRed[i] + ((actualRed[i]*0.25)*(currentValue/(double)4))) ;
						newGreen = (int)(actualGreen[i] + ((actualGreen[i]*0.25)*(currentValue/(double)4))) ;
						newBlue = (int)(actualBlue[i] + ((actualBlue[i]*0.25)*(currentValue/(double)4))) ;
					}
	                else if( currentValue < -4 && currentValue >= -5 ) {
						
	                	newRed = (int)(actualRed[i] + ((actualRed[i]*0.3125)*(currentValue/(double)5))) ;
						newGreen = (int)(actualGreen[i] + ((actualGreen[i]*0.3125)*(currentValue/(double)5))) ;
						newBlue = (int)(actualBlue[i] + ((actualBlue[i]*0.3125)*(currentValue/(double)5))) ;
						} 
	                else if( currentValue < -5 && currentValue >= -6 ) {
						
	                	newRed = (int)(actualRed[i] + ((actualRed[i]*0.375)*(currentValue/(double)6))) ;
						newGreen = (int)(actualGreen[i] + ((actualGreen[i]*0.375)*(currentValue/(double)6))) ;
						newBlue = (int)(actualBlue[i] + ((actualBlue[i]*0.375)*(currentValue/(double)6))) ;
					}
	                else if( currentValue < -6 && currentValue >= -7 ) {
						
	                	newRed = (int)(actualRed[i] + ((actualRed[i]*0.4375)*(currentValue/(double)7))) ;
						newGreen = (int)(actualGreen[i] + ((actualGreen[i]*0.4375)*(currentValue/(double)7))) ;
						newBlue = (int)(actualBlue[i] + ((actualBlue[i]*0.4375)*(currentValue/(double)7))) ;
					}
	                else if( currentValue < -7 && currentValue >= -8 ) {
						
	                	newRed = (int)(actualRed[i] + ((actualRed[i]*0.5)*(currentValue/(double)8))) ;
						newGreen = (int)(actualGreen[i] + ((actualGreen[i]*0.5)*(currentValue/(double)8))) ;
						newBlue = (int)(actualBlue[i] + ((actualBlue[i]*0.5)*(currentValue/(double)8))) ;
					}
	                else if( currentValue < -8 && currentValue >= -9 ) {
	                	
	                	newRed = (int)(actualRed[i] + ((actualRed[i]*0.625)*(currentValue/(double)9))) ;
						newGreen = (int)(actualGreen[i] + ((actualGreen[i]*0.625)*(currentValue/(double)9))) ;
						newBlue = (int)(actualBlue[i] + ((actualBlue[i]*0.625)*(currentValue/(double)9))) ;
	                }
	                else if( currentValue < -9 && currentValue >= -10 ) {
	                	
	                	newRed = (int)(actualRed[i] + ((actualRed[i]*0.75)*(currentValue/(double)10))) ;
						newGreen = (int)(actualGreen[i] + ((actualGreen[i]*0.75)*(currentValue/(double)10))) ;
						newBlue = (int)(actualBlue[i] + ((actualBlue[i]*0.75)*(currentValue/(double)10))) ;
	                }
	                else if( currentValue < -10 && currentValue >= -11 ) {
	                	
	                	newRed = (int)(actualRed[i] + ((actualRed[i]*0.875)*(currentValue/(double)11))) ;
						newGreen = (int)(actualGreen[i] + ((actualGreen[i]*0.875)*(currentValue/(double)11))) ;
						newBlue = (int)(actualBlue[i] + ((actualBlue[i]*0.875)*(currentValue/(double)11))) ;
	                }
	                else if( currentValue < -11 && currentValue >= -12 ) {
	                	
	                	newRed = (int)(actualRed[i] + ((actualRed[i]*1.0)*(currentValue/(double)12))) ;
						newGreen = (int)(actualGreen[i] + ((actualGreen[i]*1.0)*(currentValue/(double)12))) ;
						newBlue = (int)(actualBlue[i] + ((actualBlue[i]*1.0)*(currentValue/(double)12))) ;
	                }
				}
				
				
				if( newRed > 255 ) {
					newRed = 255 ;
				}
				
				if( newGreen > 255 ) {
					newGreen = 255;
				}
				
				if( newBlue > 255 ) {
					newBlue = 255 ;
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














/*newRed =  (int)(actualRed[i] + ((currentValue*counterRed)/(double)256)) ;
newGreen =  (int)(actualGreen[i] + ((currentValue*counterGreen)/(double)256)) ;
newBlue =  (int)(actualBlue[i] + ((currentValue*counterBlue)/(double)256)) ;*/