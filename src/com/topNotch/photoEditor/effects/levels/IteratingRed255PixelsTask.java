package com.topNotch.photoEditor.effects.levels;
import java.nio.IntBuffer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class IteratingRed255PixelsTask extends Task<int[]> {
	
	private int width ;
	private int height ;
	private final int whiteValue ;
	private final int blackValue ;
	private int ar255gbPixelsArray[] ;
	private PixelWriter pxlWriter ;
	private int redPixelsArray[] ;
	private final int iterations[] ;
	private final int previousIteration[] ;
	private PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	PixelFormat<IntBuffer> intTypePxlFormat = PixelFormat.getIntArgbInstance() ;
	
	public IteratingRed255PixelsTask( final int whiteValue , int blackValue , int[] redPixelsArray , int[] ar255gbPixelsArray , int[] previousIteration , final Canvas canvas ) {
		this.width = (int)canvas.getWidth() ;
		this.height = (int)canvas.getHeight() ;
		this.whiteValue = whiteValue ;
		this.blackValue = blackValue ;
		this.ar255gbPixelsArray = ar255gbPixelsArray ;
		this.redPixelsArray = redPixelsArray ;
		this.iterations = new int[ width*height ] ;
		this.previousIteration = previousIteration ;
		this.pxlWriter = canvas.getGraphicsContext2D().getPixelWriter() ;
	}
	
	@Override
	protected int[] call() throws Exception {
		
		int i = 0 ;
		int numOfPositions = whiteValue - blackValue ;
	    
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
                if( previousIteration[i] != 0 ) {
					
					int newGreen  = 0xff00ff00 & previousIteration[i] ;
				    int newGreen2 = 0xffff00ff & ar255gbPixelsArray[i] ;
				    ar255gbPixelsArray[i] = newGreen | newGreen2 ;
				    
				    int newBlue  = 0xff0000ff & previousIteration[i] ;
				    int newBlue2 = 0xffffff00 & ar255gbPixelsArray[i] ;
				    ar255gbPixelsArray[i] = newBlue | newBlue2 ;
				}
	
				if( redPixelsArray[i] >= whiteValue ) {
					
					iterations[i] = ar255gbPixelsArray[i] ;
				}
				else {
					
                    if( redPixelsArray[i] > blackValue ) {
						
						int positionOfPixelValueFromBlackValue = redPixelsArray[i] - blackValue ;
						float distanceOfPixelValue = (float)positionOfPixelValueFromBlackValue/numOfPositions ;
						
						float newBlackPixelValue = distanceOfPixelValue*redPixelsArray[i] ;
						float newWhitePixelValue = distanceOfPixelValue*(255-redPixelsArray[i]) ;
						
						int midtonePixelValue = (int)( newBlackPixelValue + newWhitePixelValue ) ;
						midtonePixelValue = (0xff & midtonePixelValue) ;
						midtonePixelValue = midtonePixelValue<<16 ;
						
						midtonePixelValue = 0xff00ffff | midtonePixelValue ;
						
						iterations[i] = ar255gbPixelsArray[i] & midtonePixelValue ;
					}
                    else {
                    	
                    	iterations[i] = previousIteration[i] ;
                    }
                    
				}
				i++ ;
			}
		}
		
		Platform.runLater( new Runnable(){
			@Override
			public void run() {
				
				pxlWriter.setPixels( 0 , 0 , width , height , intTypePixelFormat , iterations , 0 , width*1 );
			}
		});
		
		return iterations ;
	}
}


