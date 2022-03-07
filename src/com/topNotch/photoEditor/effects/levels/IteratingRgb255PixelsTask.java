package com.topNotch.photoEditor.effects.levels;
import java.nio.IntBuffer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class IteratingRgb255PixelsTask extends Task<int[]> {
	
	private int width ;
	private int height ;
	private final int whiteValue ;
	private final int blackValue ;
	private int ar255g255b255PixelsArray[] ;
	private PixelWriter pxlWriter ;
	private int argbPixelsArray[] ;
	private final int iterations[] ;
	private final int previousIteration[] ;
	private PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	PixelFormat<IntBuffer> intTypePxlFormat = PixelFormat.getIntArgbInstance() ;
	
	public IteratingRgb255PixelsTask( final int whiteValue , int blackValue , int[] argbPixelsArray , int[] ar255g255b255PixelsArray , int[] previousIteration , final Canvas canvas ) {
		this.width = (int)canvas.getWidth() ;
		this.height = (int)canvas.getHeight() ;
		this.whiteValue = whiteValue ;
		this.blackValue = blackValue ;
		this.ar255g255b255PixelsArray = ar255g255b255PixelsArray ;
		this.argbPixelsArray = argbPixelsArray ;
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
				
				if( argbPixelsArray[i] >= whiteValue ) {
					
					iterations[i] = ar255g255b255PixelsArray[i] ;
				}
				else {
					
                    if( argbPixelsArray[i] > blackValue ) {
						
						int positionOfPixelValueFromBlackValue = argbPixelsArray[i] - blackValue ;
						float distanceOfPixelValue = (float)positionOfPixelValueFromBlackValue/numOfPositions ;
						
						float newBlackPixelValue = distanceOfPixelValue*argbPixelsArray[i] ;
						float newWhitePixelValue = distanceOfPixelValue*(255-argbPixelsArray[i]) ;
						
						int midtonePixelValue = (int)( newBlackPixelValue + newWhitePixelValue ) ;
						
						int alpha = 0xff & midtonePixelValue>>24 ;
						int red   = 0xff & midtonePixelValue>>16 ;
						int green = 0xff & midtonePixelValue>>8  ;
						int blue  = 0xff & midtonePixelValue     ;
						
						/*int m1 = 0xff | 0;
						m1 = m1<<24 ;
						
						int m3 = 0xff | 0 ;
						m3 = m3<<8 ;
						
						int m4 = 0xff ;
						
						midtonePixelValue = m1 | midtonePixelValue | m3  | m4 ;
						
						iterations[i] = ar255g255b255PixelsArray[i] & midtonePixelValue ;*/
						
						iterations[i] = alpha | red | green | blue ;
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


