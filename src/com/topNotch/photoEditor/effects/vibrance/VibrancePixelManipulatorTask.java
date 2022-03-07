package com.topNotch.photoEditor.effects.vibrance;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class VibrancePixelManipulatorTask extends Task<int[]> {
	
	private int iterations[] ;
	public static Canvas canvas ;
	public static int width ;
	public static int height ;
	
    private int currentValue ;
	
    public static int actualRed[] ;
    public static int actualGreen[] ;
    public static int actualBlue[] ;
    public static int rgbAverage[] ;
	
    public static PixelWriter pxlWriter ;
	public static PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	
	public VibrancePixelManipulatorTask( int currentValue ) {
		
        this.currentValue = currentValue ;
		this.iterations = new int[ width*height ];
	}
	
	@Override
	protected int[] call() {
		
		int i = 0 ;
		
		int currentRedValue = 0 ;
		int currentGreenValue = 0 ;
		int currentBlueValue = 0 ;
		
		int newRed = 0 ;
		int newGreen = 0 ;
		int newBlue = 0 ;
		
		int alpha = 0xff<<24 ;
		
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
				int actualRED = actualRed[i] ;
				int actualGREEN = actualGreen[i] ;
				int actualBLUE = actualBlue[i] ;
				
				if( actualRED >= actualGreen[i] && actualRED >= actualBlue[i] ) {
					 
                    if( actualRED >= 32 && actualRED <= 224 ) {
						
						if( actualRED >= 96 && actualRED <= 160 ) {
							
							newRed = (int)(actualRED + 5*(currentValue/(double)100)) ;
						}
						else {
							
							newRed = (int)(actualRED + 3*(currentValue/(double)100)) ;
						}
					}
					else {
						
						if( actualRED < 255 ) {
							
							newRed = (int)(actualRED + 1*(currentValue/(double)100)) ;
						}
					}
					
					if( actualGreen[i] >= actualBlue[i] ) {
						
						currentBlueValue = (int)( ( ( (actualRED - actualBlue[i] )/(double)actualBlue[i])*( (int)( (actualRED - actualBlue[i])/(double)3) ) )*(currentValue/(double)100) ) ;
						newBlue = (actualBlue[i] - currentBlueValue) ;
						
						newGreen = actualGreen[i] ;
					}
					else {
						
						currentGreenValue = (int)( ( (rgbAverage[i]*( (actualRED - actualGreen[i])/(double)actualGreen[i] ) )/(double)3)*(currentValue/(double)100)) ;
						newGreen = (actualGreen[i] - currentGreenValue) ;
						
						newBlue = actualBlue[i] ;
					}
				}
				
                if( actualGreen[i] >= actualRED && actualGreen[i] >= actualBlue[i] ) {
					
                	if( actualGreen[i] >= 32 && actualGreen[i] <= 224 ) {
						
						if( actualGreen[i] >= 96 && actualGreen[i] <= 160 ) {
							
							newGreen = (int)(actualGreen[i] + 5*(currentValue/(double)100)) ;
						}
						else {
							
							newGreen = (int)(actualGreen[i] + 3*(currentValue/(double)100)) ;
						}
					}
					else {
						
						if( actualGreen[i] < 255 ) {
							
							newGreen = (int)(actualGreen[i] + 1*(currentValue/(double)100)) ;
						}
					}
                	
                	if( actualRed[i] >= actualBlue[i] ) {
                		
						currentBlueValue = (int)( ( (actualGreen[i] - rgbAverage[i])*(actualBlue[i]/(double)rgbAverage[i]) )*(currentValue/(double)100)) ;
						newBlue = (actualBlue[i] - currentBlueValue) ;
						
						newRed = actualRed[i] ;
                	}
                	else {
                		
						currentRedValue = (int)( ( (actualGreen[i] - rgbAverage[i])*(actualRed[i]/(double)rgbAverage[i]) )*(currentValue/(double)100)) ;
						newRed = (actualRed[i] - currentRedValue) ;
						
						newBlue = actualBlue[i] ;
                	}
				}
                
                if( actualBlue[i] >= actualGreen[i] && actualBlue[i] >= actualRed[i] ) {
                	
                	if( actualBlue[i] >= 32 && actualBlue[i] <= 224 ) {
						
						if( actualBlue[i] >= 96 && actualBlue[i] <= 160 ) {
							
							newBlue = (int)(actualBlue[i] + 5*(currentValue/(double)100)) ;
						}
						else {
							
							newBlue = (int)(actualBlue[i] + 3*(currentValue/(double)100)) ;
						}
					}
					else {
						
						if( actualBlue[i] < 255 ) {
							
							newBlue = (int)(actualBlue[i] + 1*(currentValue/(double)100)) ;
						}
					}
					
                	if( actualRed[i] >= actualGreen[i] ) {
                		
						currentGreenValue = (int)( ( (rgbAverage[i]*( (actualBlue[i] - actualGreen[i])/(double)actualGreen[i] ) )/(double)3)*(currentValue/(double)100) ) ;
						newGreen = (actualGreen[i] - currentGreenValue) ;
						
						newRed = actualRed[i] ;
                	}
                	else {
                		
						currentRedValue = (int)( ( ( (actualBlue[i] - actualRed[i])/(double)actualBlue[i])*actualRed[i])*(currentValue/(double)100) ) ;
						newRed = actualRed[i] - currentRedValue ;
						
						newGreen = actualGreen[i] ;
                	}
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