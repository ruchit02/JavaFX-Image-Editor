package com.topNotch.photoEditor.effects.levels;
import java.nio.IntBuffer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class IteratingRgb0PixelsTask extends Task<int[]> {
	
	private int width ;
	private int height ;
	private int blackValue ;
	private int whiteValue ;
	private int ar0g0b0PixelsArray[] ;
	private PixelWriter pxlWriter ;
	private int argbPixelsArray[] ;
	private final int iterations[] ;
	private final int currentIteration[] ;
	private final int previousIteration[] ;
	private PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	
	PixelFormat<IntBuffer> intTypePxlFormat = PixelFormat.getIntArgbInstance() ;
	
	public IteratingRgb0PixelsTask( final int blackValue , int whiteValue , int[] argbPixelsArray , int[] ar0g0b0PixelsArray , int[] previousIteration , final Canvas canvas ) {
		this.width = (int)canvas.getWidth() ;
		this.height = (int)canvas.getHeight() ;
		this.blackValue = blackValue ;
		this.whiteValue = whiteValue ;
		this.ar0g0b0PixelsArray = ar0g0b0PixelsArray ;
		this.argbPixelsArray = argbPixelsArray ;
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
				
				if( argbPixelsArray[i] <= blackValue ) {
					
					iterations[i] = ar0g0b0PixelsArray[i] ;
					currentIteration[i] = iterations[i] ;
				}
				else {
					
					if( argbPixelsArray[i] < whiteValue ) {
						
						int positionOfPixelValueFromBlackValue = argbPixelsArray[i] - blackValue ;
						float distanceOfPixelValue = (float)positionOfPixelValueFromBlackValue/numOfPositions ;
						
						float newBlackPixelValue = distanceOfPixelValue*argbPixelsArray[i] ;
						float newWhitePixelValue = distanceOfPixelValue*(255-argbPixelsArray[i]) ;
						
						int midtonePixelValue = (int)( newBlackPixelValue + newWhitePixelValue ) ;
						midtonePixelValue = (0xff & midtonePixelValue) ;
						midtonePixelValue = midtonePixelValue<<8 ;
						iterations[i] = ar0g0b0PixelsArray[i] | midtonePixelValue ;
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
