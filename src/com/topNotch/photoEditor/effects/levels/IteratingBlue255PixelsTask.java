package com.topNotch.photoEditor.effects.levels;
import java.nio.IntBuffer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class IteratingBlue255PixelsTask extends Task<int[]> {
	
	private int width ;
	private int height ;
	private final int whiteValue ;
	private final int blackValue ;
	private int argb255PixelsArray[] ;
	private PixelWriter pxlWriter ;
	private int bluePixelsArray[] ;
	private final int iterations[] ;
	private final int previousIteration[] ;
	private final int currentIteration[] ;
	private PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	PixelFormat<IntBuffer> intTypePxlFormat = PixelFormat.getIntArgbInstance() ;
	
	public IteratingBlue255PixelsTask( final int whiteValue , int blackValue , int[] bluePixelsArray , int[] argb255PixelsArray , int[] previousIteration , final Canvas canvas ) {
		this.width = (int)canvas.getWidth() ;
		this.height = (int)canvas.getHeight() ;
		this.whiteValue = whiteValue ;
		this.blackValue = blackValue ;
		this.argb255PixelsArray = argb255PixelsArray ;
		this.bluePixelsArray = bluePixelsArray ;
		this.iterations = new int[ width*height ] ;
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
				    int newRed2 = 0xff00ffff & argb255PixelsArray[i] ;
				    argb255PixelsArray[i] = newRed | newRed2 ;
				    
				    int newGreen  = 0xff00ff00 & previousIteration[i] ;
				    int newGreen2 = 0xffff00ff & argb255PixelsArray[i] ;
				    argb255PixelsArray[i] = newGreen | newGreen2 ;
				}
				
				if( bluePixelsArray[i] >= whiteValue ) {
					
					iterations[i] = argb255PixelsArray[i] ;
					currentIteration[i] = iterations[i] ;
				}
				else {
					
                    if( bluePixelsArray[i] > blackValue ) {
						
						int positionOfPixelValueFromBlackValue = bluePixelsArray[i] - blackValue ;
						float distanceOfPixelValue = (float)positionOfPixelValueFromBlackValue/numOfPositions ;
						
						float newBlackPixelValue = distanceOfPixelValue*bluePixelsArray[i] ;
						float newWhitePixelValue = distanceOfPixelValue*(255-bluePixelsArray[i]) ;
						
						int midtonePixelValue = (int)( newBlackPixelValue + newWhitePixelValue ) ;
						midtonePixelValue = (0xff & midtonePixelValue) ;
						
						midtonePixelValue = 0xffffff00 | midtonePixelValue ;
						
						iterations[i] = argb255PixelsArray[i] & midtonePixelValue ;
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

