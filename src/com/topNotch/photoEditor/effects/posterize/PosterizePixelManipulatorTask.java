package com.topNotch.photoEditor.effects.posterize;

import java.nio.IntBuffer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class PosterizePixelManipulatorTask extends Task<int[]>{
	
	private int iterations[] ;
	private Canvas canvas ;
	private int width ;
	private int height ;
	
	private int actualRed[] ;
	private int actualGreen[] ;
	private int actualBlue[] ;
	
	private int currentLevelValue ;
	
	private PixelWriter pxlWriter ;
	
	private final PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	
	public PosterizePixelManipulatorTask( Canvas canvas , int currentLevelValue , int[] actualRed , int[] actualGreen , int[] actualBlue ) {
		
		this.canvas = canvas ;
		this.width = (int)this.canvas.getWidth() ;
		this.height = (int)this.canvas.getHeight() ;
		this.iterations = new int[ width*height ];
		
		this.actualRed = actualRed ;
		this.actualGreen = actualGreen ;
		this.actualBlue = actualBlue ;
		
		this.currentLevelValue = currentLevelValue ;
		
		this.pxlWriter = canvas.getGraphicsContext2D().getPixelWriter() ;
	}
	
	@Override
	protected int[] call() {
		
		int i = 0;
		
		int currentRed = 0 ;
		int currentGreen = 0 ;
		int currentBlue = 0 ;
		
		int newRed = 0 ;
		int newGreen = 0 ;
		int newBlue = 0 ;
		
		double value = 255/(double)(currentLevelValue) ;
		double counterValue = 255 - value ;
		
		int calculatedValue1 = (int)(255/(currentLevelValue-1)) ;
		int calculatedValue2 = (int)(255/(currentLevelValue-1))*2 ;
		int calculatedValue3 = (int)(255/(currentLevelValue-1))*3 ;
		int calculatedValue4 = (int)(255/(currentLevelValue-1))*4 ;
		int calculatedValue5 = (int)(255/(currentLevelValue-1))*5 ;
		int calculatedValue6 = (int)(255/(currentLevelValue-1))*6 ;
		int calculatedValue7 = (int)(255/(currentLevelValue-1))*7 ;
		int calculatedValue8 = (int)(255/(currentLevelValue-1))*8 ;
		
		int alpha = 0xff<<24 ;
		
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
				if( actualRed[i] > value ) {
					
					if( actualRed[i] < counterValue ) {
						
						if( actualRed[i] < 2*value ) {
							
							newRed = calculatedValue1;
						}
						else {
							
                            if( actualRed[i] < 3*value ) {
								
                            	newRed = calculatedValue2 ;
							}
							else {
								
								if( actualRed[i] < 4*value ) {
									
									newRed = calculatedValue3 ;
								}
								else {
									
									if( actualRed[i] < 5*value ) {
										
										newRed = calculatedValue4 ;
									}
									else {
										
										if( actualRed[i] < 6*value ) {
											
											newRed = calculatedValue5 ;
										}
										else {
											
											if( actualRed[i] < 7*value ) {
												
												newRed = calculatedValue6 ;
											}
											else {
												
												if( actualRed[i] < 8*value ) {
													
													newRed = calculatedValue7 ;
												}
												else {
													
													if( actualRed[i] < 9*value ) {
														
														newRed = calculatedValue8 ;
													}
													else {
														
														newRed = actualRed[i];
													}
												}
											}
										}
									}
								}
							}
						}
					}
					else {
						
						newRed = 255 ;
					}
				}
				else {
					
					newRed = 0 ;
				}
				
				/////////////
                if( actualGreen[i] > value ) {
					
					if( actualGreen[i] < counterValue ) {
						
						if( actualGreen[i] < 2*value ) {
							
							newGreen = calculatedValue1;
						}
						else {
							
                            if( actualGreen[i] < 3*value ) {
								
                            	newGreen = calculatedValue2 ;
							}
							else {
								
								if( actualGreen[i] < 4*value ) {
									
									newGreen = calculatedValue3 ;
								}
								else {
									
									if( actualGreen[i] < 5*value ) {
										
										newGreen = calculatedValue4 ;
									}
									else {
										
										if( actualGreen[i] < 6*value ) {
											
											newGreen = calculatedValue5 ;
										}
										else {
											
											if( actualGreen[i] < 7*value ) {
												
												newGreen = calculatedValue6 ;
											}
											else {
												
												if( actualGreen[i] < 8*value ) {
													
													newGreen = calculatedValue7 ;
												}
												else {
													
													if( actualGreen[i] < 9*value ) {
														
														newGreen = calculatedValue8 ;
													}
													else {
														
														newGreen = actualGreen[i];
													}
												}
											}
										}
									}
								}
							}
						}
					}
					else {
						
						newGreen = 255 ;
					}
				}
				else {
					
					newGreen = 0 ;
				}
				
				//////////////
                if( actualBlue[i] > value ) {
					
					if( actualBlue[i] < counterValue ) {
						
						if( actualBlue[i] < 2*value ) {
							
							newBlue = calculatedValue1;
						}
						else {
							
                            if( actualBlue[i] < 3*value ) {
								
                            	newBlue = calculatedValue2 ;
							}
							else {
								
								if( actualBlue[i] < 4*value ) {
									
									newBlue = calculatedValue3 ;
								}
								else {
									
									if( actualBlue[i] < 5*value ) {
										
										newBlue = calculatedValue4 ;
									}
									else {
										
										if( actualBlue[i] < 6*value ) {
											
											newBlue = calculatedValue5 ;
										}
										else {
											
											if( actualBlue[i] < 7*value ) {
												
												newBlue = calculatedValue6 ;
											}
											else {
												
												if( actualBlue[i] < 8*value ) {
													
													newBlue = calculatedValue7 ;
												}
												else {
													
													if( actualBlue[i] < 9*value ) {
														
														newBlue = calculatedValue8 ;
													}
													else {
														
														newBlue = actualBlue[i];
													}
												}
											}
										}
									}
								}
							}
						}
					}
					else {
						
						newBlue = 255 ;
					}
				}
				else {
					
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
