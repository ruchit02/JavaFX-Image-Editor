package com.topNotch.photoEditor.effects.levels;
import java.nio.IntBuffer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class IteratingBlue0PixelsTask extends Task<int[]> {
	
	private int width ;
	private int height ;
	private int blackValue ;
	private int whiteValue ;
	private int argb0PixelsArray[] ;
	private PixelWriter pxlWriter ;
	private int bluePixelsArray[] ;
	private final int iterations[] ;
	private final int currentIteration[] ;
	private final int previousIteration[] ;
	private PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	
	PixelFormat<IntBuffer> intTypePxlFormat = PixelFormat.getIntArgbInstance() ;
	
	public IteratingBlue0PixelsTask( final int blackValue , int whiteValue , int[] bluePixelsArray , int[] argb0PixelsArray , int[] previousIteration , final Canvas canvas ) {
		this.width = (int)canvas.getWidth() ;
		this.height = (int)canvas.getHeight() ;
		this.blackValue = blackValue ;
		this.whiteValue = whiteValue ;
		this.argb0PixelsArray = argb0PixelsArray ;
		this.bluePixelsArray = bluePixelsArray ;
		this.iterations = new int[ width*height ];
		this.currentIteration = new int[ width*height ];
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
					
					int newRed  = 0xffff0000 & previousIteration[i] ;
				    int newRed2 = 0xff00ffff & argb0PixelsArray[i] ;
				    argb0PixelsArray[i] = newRed | newRed2 ;
				    
				    int newGreen  = 0xff00ff00 & previousIteration[i] ;
				    int newGreen2 = 0xffff00ff & argb0PixelsArray[i] ;
				    argb0PixelsArray[i] = newGreen | newGreen2 ;
				}
				
				if( bluePixelsArray[i] <= blackValue ) {
					
					iterations[i] = argb0PixelsArray[i] ;
					currentIteration[i] = iterations[i] ;
				}
				else {
					
					if( bluePixelsArray[i] < whiteValue ) {
						
						int positionOfPixelValueFromBlackValue = bluePixelsArray[i] - blackValue ;
						float distanceOfPixelValue = (float)positionOfPixelValueFromBlackValue/numOfPositions ;
						
						float newBlackPixelValue = distanceOfPixelValue*bluePixelsArray[i] ;
						float newWhitePixelValue = distanceOfPixelValue*(255-bluePixelsArray[i]) ;
						
						int midtonePixelValue = (int)( newBlackPixelValue + newWhitePixelValue ) ;
						midtonePixelValue = (0xff & midtonePixelValue) ;
						//midtonePixelValue = midtonePixelValue<<8 ;
						
						iterations[i] = argb0PixelsArray[i] | midtonePixelValue ;
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

