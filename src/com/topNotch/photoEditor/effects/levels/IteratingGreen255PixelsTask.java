package com.topNotch.photoEditor.effects.levels;
import java.nio.IntBuffer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class IteratingGreen255PixelsTask extends Task<int[]> {
	
	private int width ;
	private int height ;
	private final int whiteValue ;
	private final int blackValue ;
	private int arg255bPixelsArray[] ;
	private PixelWriter pxlWriter ;
	private int greenPixelsArray[] ;
	private final int iterations[] ;
	private final int previousIteration[] ;
	private final int currentIteration[] ;
	private PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	PixelFormat<IntBuffer> intTypePxlFormat = PixelFormat.getIntArgbInstance() ;
	
	public IteratingGreen255PixelsTask( final int whiteValue , int blackValue , int[] greenPixelsArray , int[] arg255bPixelsArray , int[] previousIteration , final Canvas canvas ) {
		this.width = (int)canvas.getWidth() ;
		this.height = (int)canvas.getHeight() ;
		this.whiteValue = whiteValue ;
		this.blackValue = blackValue ;
		this.arg255bPixelsArray = arg255bPixelsArray ;
		this.greenPixelsArray = greenPixelsArray ;
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
				    int newRed2 = 0xff00ffff & arg255bPixelsArray[i] ;
				    arg255bPixelsArray[i] = newRed | newRed2 ;
				    
				    int newBlue  = 0xff0000ff & previousIteration[i] ;
				    int newBlue2 = 0xffffff00 & arg255bPixelsArray[i] ;
				    arg255bPixelsArray[i] = newBlue | newBlue2 ;
				}
				
				if( greenPixelsArray[i] >= whiteValue ) {
					
					iterations[i] = arg255bPixelsArray[i] ;
					currentIteration[i] = iterations[i] ;
				}
				else {
					
                    if( greenPixelsArray[i] > blackValue ) {
						
						int positionOfPixelValueFromBlackValue = greenPixelsArray[i] - blackValue ;
						float distanceOfPixelValue = (float)positionOfPixelValueFromBlackValue/numOfPositions ;
						
						float newBlackPixelValue = distanceOfPixelValue*greenPixelsArray[i] ;
						float newWhitePixelValue = distanceOfPixelValue*(255-greenPixelsArray[i]) ;
						
						int midtonePixelValue = (int)( newBlackPixelValue + newWhitePixelValue ) ;
						midtonePixelValue = (0xff & midtonePixelValue) ;
						midtonePixelValue = midtonePixelValue<<8 ;
						
						midtonePixelValue = 0xffff00ff | midtonePixelValue ;
						
						iterations[i] = arg255bPixelsArray[i] & midtonePixelValue ;
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
