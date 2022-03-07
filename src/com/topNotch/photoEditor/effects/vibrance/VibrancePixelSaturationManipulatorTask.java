package com.topNotch.photoEditor.effects.vibrance;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class VibrancePixelSaturationManipulatorTask extends Task<int[]> {
	
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
	
	public VibrancePixelSaturationManipulatorTask( Canvas canvas , int currentValue ,  int[] actualRed , int[] actualGreen , int[] actualBlue 
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
		
		double amountOfRedInThePixel = 0 ;
		double amountOfGreenInThePixel = 0 ;
		double amountOfBlueInThePixel = 0 ;
		
		int abundantRedValue = 0 ;
		int abundantGreenValue = 0;
		int abundantBlueValue = 0 ;
		
		double abundantRedValueInFraction = 0 ;
		double abundantGreenValueInFraction = 0;
		double abundantBlueValueInFraction = 0 ;
		
		int newRed = 0 ;
		int newGreen = 0 ;
		int newBlue = 0 ;
		
		int differenceBetweenRedAndBlue = 0 ;
		int differenceBetweenRedAndGreen = 0 ;
		int differenceBetweenGreenAndBlue = 0 ;
		
		int differenceBetweenBlueAndRed = 0 ;
		int differenceBetweenBlueAndGreen = 0 ;
		int differenceBetweenGreenAndRed = 0 ;
		
		double divideDifferenceByRed = 0 ;
		double divideDifferenceByGreen = 0 ;
		double divideDifferenceByBlue = 0 ;
		
		int abunantRedValue = 0 ;
		int abunantGreenValue = 0 ;
		int abunantBlueValue = 0 ;
		
		int alpha = 0xff<<24 ;
		
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
				if( actualRed[i] >= actualGreen[i] && actualRed[i] >= actualBlue[i] ) {
					
					if( actualGreen[i] > actualBlue[i] ) {
						
						differenceBetweenRedAndGreen = (actualRed[i] - actualGreen[i]) ;
						divideDifferenceByRed = differenceBetweenRedAndGreen/(double)actualRed[i] ;
						abunantRedValue = (int)((divideDifferenceByRed*rgbAverage[i])*(currentValue/(double)100)) ;
						newRed = actualRed[i] + abunantRedValue ;
						
						amountOfGreenInThePixel = actualGreen[i] - rgbAverage[i] ;
						abundantGreenValueInFraction = amountOfGreenInThePixel/(double)256 ;
						abundantGreenValue = (int)((abundantGreenValueInFraction*rgbAverage[i])*(currentValue/(double)100)) ;
						newGreen = actualGreen[i] + abundantGreenValue ;
						
						differenceBetweenRedAndBlue = (actualRed[i] - actualBlue[i]) ;
						divideDifferenceByBlue = differenceBetweenRedAndBlue/(double)actualBlue[i] ;
						abunantBlueValue = (int)((divideDifferenceByBlue*rgbAverage[i])*(currentValue/(double)100)) ;
						newBlue = actualBlue[i] - abunantBlueValue ;
					}
					else {
						
						differenceBetweenRedAndBlue = (int)(actualRed[i] - actualBlue[i]) ;
						divideDifferenceByRed = differenceBetweenRedAndBlue/(double)actualRed[i] ;
						abunantRedValue = (int)((divideDifferenceByRed*rgbAverage[i])*(currentValue/(double)100)) ;
						newRed = actualRed[i] + abunantRedValue ;
						
						amountOfBlueInThePixel = actualBlue[i] - rgbAverage[i] ;
						abundantBlueValueInFraction = amountOfBlueInThePixel/(double)256 ;
						abundantBlueValue = (int)((abundantBlueValueInFraction*rgbAverage[i])*(currentValue/(double)100)) ;
						newBlue = actualBlue[i] + abundantBlueValue ;
						
						differenceBetweenRedAndGreen = (actualRed[i] - actualGreen[i]) ;
						abunantGreenValue = (int)((differenceBetweenRedAndGreen/(double)3)*(currentValue/(double)100)) ;
						newGreen = actualGreen[i] - abunantGreenValue ;
					}
				}
				
                if( actualGreen[i] >= actualRed[i] && actualGreen[i] >= actualBlue[i] ) {
                	
                    if( actualRed[i] > actualBlue[i] ) {
                    	
                    	differenceBetweenGreenAndRed = (int)(actualGreen[i] - actualRed[i]) ;
						divideDifferenceByGreen = differenceBetweenGreenAndRed/(double)actualGreen[i] ;
						abunantGreenValue = (int)((divideDifferenceByGreen*rgbAverage[i])*(currentValue/(double)100)) ;
						newGreen = actualGreen[i] + abunantGreenValue ;
						
						amountOfRedInThePixel = actualRed[i] - rgbAverage[i] ;
						abundantRedValueInFraction = amountOfRedInThePixel/(double)256 ;
						abundantRedValue = (int)((abundantRedValueInFraction*rgbAverage[i])*(currentValue/(double)100)) ;
						newRed = actualRed[i] + abundantRedValue ;
						
						differenceBetweenGreenAndBlue = (actualGreen[i] - actualBlue[i]) ;
						divideDifferenceByBlue = differenceBetweenGreenAndBlue/(double)actualBlue[i] ;
						abunantBlueValue = (int)((divideDifferenceByBlue*rgbAverage[i])*(currentValue/(double)100)) ;
						newBlue = actualBlue[i] - abunantBlueValue ;
					}
					else {
						
						differenceBetweenGreenAndBlue = (int)(actualGreen[i] - actualBlue[i]) ;
						divideDifferenceByGreen = differenceBetweenGreenAndBlue/(double)actualGreen[i] ;
						abunantGreenValue = (int)((divideDifferenceByGreen*rgbAverage[i])*(currentValue/(double)100)) ;
						newGreen = actualGreen[i] + abunantGreenValue ;
						
						amountOfBlueInThePixel = actualBlue[i] - rgbAverage[i] ;
						abundantBlueValueInFraction = amountOfBlueInThePixel/(double)256 ;
						abundantBlueValue = (int)((abundantBlueValueInFraction*rgbAverage[i])*(currentValue/(double)100)) ;
						newBlue = actualBlue[i] + abundantBlueValue ;
						
						differenceBetweenGreenAndRed = (actualGreen[i] - actualRed[i]) ;
						divideDifferenceByRed = differenceBetweenGreenAndRed/(double)actualRed[i] ;
						abunantRedValue = (int)((divideDifferenceByRed*rgbAverage[i])*(currentValue/(double)100)) ;
						newRed = actualRed[i] - abunantRedValue ;
					}
				}
                
                if( actualBlue[i] >= actualRed[i] && actualBlue[i] >= actualGreen[i] ) {
                	
                    if( actualRed[i] > actualGreen[i] ) {
						
                    	differenceBetweenBlueAndRed = (int)(actualBlue[i] - actualRed[i]) ;
						divideDifferenceByBlue = differenceBetweenBlueAndRed/(double)actualBlue[i] ;
						abunantBlueValue = (int)((divideDifferenceByBlue*rgbAverage[i])*(currentValue/(double)100)) ;
						newBlue = actualBlue[i] + abunantBlueValue ;
						
						amountOfRedInThePixel = actualRed[i] - rgbAverage[i] ;
						abundantRedValueInFraction = amountOfRedInThePixel/(double)256 ;
						abundantRedValue = (int)(abundantRedValueInFraction*rgbAverage[i]) ;
						newRed = (int)(actualRed[i] + (abundantRedValue*(currentValue/(double)100))) ;
						
						differenceBetweenBlueAndGreen = (actualBlue[i] - actualGreen[i]) ;
						abunantGreenValue = (int)((divideDifferenceByGreen/(double)3)*(currentValue/(double)100)) ;
						newGreen = actualGreen[i] - abunantGreenValue ;
						
						/*differenceBetweenBlueAndGreen = (actualBlue[i] - actualGreen[i]) ;
						divideDifferenceByGreen = differenceBetweenBlueAndGreen/(double)actualGreen[i] ;
						abunantGreenValue = (int)((divideDifferenceByGreen*rgbAverage[i])*(currentValue/(double)100)) ;
						newGreen = actualGreen[i] - abunantGreenValue ;*/
					}
					else {
						
						differenceBetweenBlueAndGreen = (int)(actualBlue[i] - actualGreen[i]) ;
						divideDifferenceByBlue = differenceBetweenBlueAndGreen/(double)actualBlue[i] ;
						abunantBlueValue = (int)((divideDifferenceByBlue*rgbAverage[i])*(currentValue/(double)100)) ;
						newBlue = actualBlue[i] + abunantBlueValue ;
						
						amountOfGreenInThePixel = actualGreen[i] - rgbAverage[i] ;
						abundantGreenValueInFraction = amountOfGreenInThePixel/(double)256 ;
						abundantGreenValue = (int)((abundantGreenValueInFraction*rgbAverage[i])*(currentValue/(double)100)) ;
						newGreen = actualGreen[i] + abundantGreenValue ;
						
						differenceBetweenBlueAndRed = (actualBlue[i] - actualRed[i]) ;
						divideDifferenceByRed = differenceBetweenBlueAndRed/(double)actualRed[i] ;
						abunantRedValue = (int)((divideDifferenceByRed*rgbAverage[i])*(currentValue/(double)100)) ;
						newRed = actualRed[i] - abunantRedValue ;
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









/*if( actualRed[i] > actualGreen[i] && actualRed[i] > actualBlue[i] ) {
					 
					if( actualRed[i] > 127 ) {
						
						amountOfRedInThePixel = (255-actualRed[i])/(double)256 ;
						abundantRedValue = (int)((rgbAverage[i]*amountOfRedInThePixel)*(currentValue/(double)100)) ;
						newRed = actualRed[i] + abundantRedValue ;
					}
					else {
						
						amountOfRedInThePixel = actualRed[i]/(double)256 ;
						abundantRedValue = (int)((rgbAverage[i]*amountOfRedInThePixel)*(currentValue/(double)100)) ;
						newRed = actualRed[i] + abundantRedValue ;
					}
					
					if( actualGreen[i] > actualBlue[i] ) {
						
						amountOfGreenInThePixel = actualGreen[i] - rgbAverage[i] ;
						abundantGreenValueInFraction = amountOfGreenInThePixel/(double)256 ;
						abundantGreenValue = (int)((abundantGreenValueInFraction*rgbAverage[i])*(currentValue/(double)100)) ;
						newGreen = actualGreen[i] + abundantGreenValue ;
						
						if( actualBlue[i] > 127 ) {
							
							amountOfBlueInThePixel = actualBlue[i]/(double)256 ;
							abundantBlueValue = (int)((rgbAverage[i] - rgbAverage[i]*amountOfBlueInThePixel)*(currentValue/(double)100)) ;
							newBlue = actualBlue[i] - abundantBlueValue ;
						}
						else {
							
							amountOfBlueInThePixel = (255-actualBlue[i])/(double)256 ;
							abundantBlueValue = (int)((rgbAverage[i] - rgbAverage[i]*amountOfBlueInThePixel)*(currentValue/(double)100)) ;
							newBlue = actualBlue[i] - abundantBlueValue ;
						}
					}
					else {
						
						amountOfBlueInThePixel = actualBlue[i] - rgbAverage[i] ;
						abundantBlueValueInFraction = amountOfBlueInThePixel/(double)256 ;
						abundantBlueValue = (int)((abundantBlueValueInFraction*rgbAverage[i])*(currentValue/(double)100)) ;
						newBlue = actualBlue[i] + abundantBlueValue ;
						
						if( actualGreen[i] > 127 ) {
							
							amountOfGreenInThePixel = actualGreen[i]/(double)256 ;
							abundantGreenValue = (int)((rgbAverage[i] - rgbAverage[i]*amountOfGreenInThePixel)*(currentValue/(double)100)) ;
							newGreen = actualGreen[i] - abundantGreenValue ;
						}
						else {
							
							amountOfGreenInThePixel = (255-actualGreen[i])/(double)256 ;
							abundantGreenValue = (int)((rgbAverage[i] - rgbAverage[i]*amountOfGreenInThePixel)*(currentValue/(double)100)) ;
							newGreen = actualGreen[i] - abundantGreenValue ;
						}
					}
				}
				
                if( actualGreen[i] > actualRed[i] && actualGreen[i] > actualBlue[i] ) {
					
                	if( actualGreen[i] > 127 ) {
                		
                		amountOfGreenInThePixel = (255-actualGreen[i])/(double)256 ;
						abundantGreenValue = (int)((rgbAverage[i]*amountOfGreenInThePixel)*(currentValue/(double)100)) ;
						newGreen = actualGreen[i] + abundantGreenValue ;
                	}
                	else {
                		
                		amountOfGreenInThePixel = actualGreen[i]/(double)256 ;
						abundantGreenValue = (int)((rgbAverage[i]*amountOfGreenInThePixel)*(currentValue/(double)100)) ;
						newGreen = actualGreen[i] + abundantGreenValue ;
                	}
                	
                    if( actualRed[i] > actualBlue[i] ) {
                    	
                    	amountOfRedInThePixel = actualRed[i] - rgbAverage[i] ;
						abundantRedValueInFraction = amountOfRedInThePixel/(double)256 ;
						abundantRedValue = (int)((abundantRedValueInFraction*rgbAverage[i])*(currentValue/(double)100)) ;
						newRed = actualRed[i] + abundantRedValue ;
						
                    	if( actualBlue[i] > 127 ) {
                    		
                    		amountOfBlueInThePixel = actualBlue[i]/(double)256 ;
							abundantBlueValue = (int)((rgbAverage[i] - rgbAverage[i]*amountOfBlueInThePixel)*(currentValue/(double)100)) ;
							newBlue = actualBlue[i] - abundantBlueValue ;
                    	}
                    	else {
                    		
                    		amountOfBlueInThePixel = (255-actualBlue[i])/(double)256 ;
							abundantBlueValue = (int)((rgbAverage[i] - rgbAverage[i]*amountOfBlueInThePixel)*(currentValue/(double)100)) ;
							newBlue = actualBlue[i] - abundantBlueValue ;
                    	}
					}
					else {
						
						amountOfBlueInThePixel = actualBlue[i] - rgbAverage[i] ;
						abundantBlueValueInFraction = amountOfBlueInThePixel/(double)256 ;
						abundantBlueValue = (int)((abundantBlueValueInFraction*rgbAverage[i])*(currentValue/(double)100)) ;
						newBlue = actualBlue[i] + abundantBlueValue ;
						
						if( actualRed[i] > 127 ) {
							
							amountOfRedInThePixel = actualRed[i]/(double)256 ;
							abundantRedValue = (int)((rgbAverage[i] - rgbAverage[i]*amountOfRedInThePixel)*(currentValue/(double)100)) ;
							newRed = actualRed[i] - abundantRedValue ;
						}
						else {
							
							amountOfRedInThePixel = (255-actualRed[i])/(double)256 ;
							abundantRedValue = (int)((rgbAverage[i] - rgbAverage[i]*amountOfRedInThePixel)*(currentValue/(double)100)) ;
							newRed = actualRed[i] - abundantRedValue ;
						}
					}
				}
                
                if( actualBlue[i] > actualRed[i] && actualBlue[i] > actualGreen[i] ) {
                	
                	if( actualBlue[i] > 127 ) {
                		
                		amountOfBlueInThePixel = (255-actualBlue[i])/(double)256 ;
						abundantBlueValue = (int)((rgbAverage[i]*amountOfBlueInThePixel)*(currentValue/(double)100)) ;
						newBlue = actualBlue[i] + abundantBlueValue ;
                	}
                	else {
                		
                		amountOfBlueInThePixel = actualBlue[i]/(double)256 ;
						abundantBlueValue = (int)((rgbAverage[i]*amountOfBlueInThePixel)*(currentValue/(double)100)) ;
						newBlue = actualBlue[i] + abundantBlueValue ;
                	}
                	
                    if( actualRed[i] > actualGreen[i] ) {
						
                    	amountOfRedInThePixel = actualRed[i] - rgbAverage[i] ;
						abundantRedValueInFraction = amountOfRedInThePixel/(double)256 ;
						abundantRedValue = (int)((abundantRedValueInFraction*rgbAverage[i])*(currentValue/(double)100)) ;
						newRed = actualRed[i] + abundantRedValue ;
                    	
                    	if( actualGreen[i] > 127 ) {
                    		
                    		amountOfGreenInThePixel = actualGreen[i]/(double)256 ;
    						abundantGreenValue = (int)((rgbAverage[i] - rgbAverage[i]*amountOfGreenInThePixel)*(currentValue/(double)100)) ;
    						newGreen = actualGreen[i] - abundantGreenValue ;
                    	}
                    	else {
                    		
                    		amountOfGreenInThePixel = (255-actualGreen[i])/(double)256 ;
    						abundantGreenValue = (int)((rgbAverage[i] - rgbAverage[i]*amountOfGreenInThePixel)*(currentValue/(double)100)) ;
    						newGreen = actualGreen[i] - abundantGreenValue ;
                    	}
					}
					else {
						
						amountOfGreenInThePixel = actualGreen[i] - rgbAverage[i] ;
						abundantGreenValueInFraction = amountOfGreenInThePixel/(double)256 ;
						abundantGreenValue = (int)((abundantGreenValueInFraction*rgbAverage[i])*(currentValue/(double)100)) ;
						newGreen = actualGreen[i] + abundantGreenValue ;
						
						if( actualRed[i] > 127 ) {
							
							amountOfRedInThePixel = actualRed[i]/(double)256 ;
							abundantRedValue = (int)((rgbAverage[i] - rgbAverage[i]*amountOfRedInThePixel)*(currentValue/(double)100)) ;
							newRed = actualRed[i] - abundantRedValue ;
						}
						else {
							
							amountOfRedInThePixel = (255-actualRed[i])/(double)256 ;
							abundantRedValue = (int)((rgbAverage[i] - rgbAverage[i]*amountOfRedInThePixel)*(currentValue/(double)100)) ;
							newRed = actualRed[i] - abundantRedValue ;
						}
					}
                }
                
                iterations[i] = alpha | newRed<<16 | newGreen<<8 | newBlue ;
                
				i++ ;
*/