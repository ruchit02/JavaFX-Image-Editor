package com.topNotch.photoEditor.main;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

public class OriginaLImagePixelDataExtractTask extends Task<Void>{
	
	private final Image img ;
	private PixelReader pixelReader ;
	private final ArrayList<Integer> redData;
	private final ArrayList<Integer> greenData ;
	private final ArrayList<Integer> blueData;
	private final ArrayList<Integer> argbData ;
	private final ArrayList< ArrayList<Integer> > imageRedPixelDataArrayList ;
	private final ArrayList< ArrayList<Integer> > imageGreenPixelDataArrayList ;
	private final ArrayList< ArrayList<Integer> > imageBluePixelDataArrayList ;
	private final ArrayList< ArrayList<Integer> > imageArgbPixelDataArrayList ;
	
	
	public OriginaLImagePixelDataExtractTask( Image img , ArrayList<Integer> redData , ArrayList<Integer> greenData 
			                                , ArrayList<Integer> blueData , ArrayList<Integer> argbData 
			                                , ArrayList< ArrayList<Integer> > imageRedPixelDataArrayList
			                                , ArrayList< ArrayList<Integer> > imageGreenPixelDataArrayList
			                                , ArrayList< ArrayList<Integer> > imageBluePixelDataArrayList
			                                , ArrayList< ArrayList<Integer> > imageArgbPixelDataArrayList ){
		
		this.img = img ;
		this.pixelReader = this.img.getPixelReader() ;
		this.redData = redData ;
		this.greenData = greenData ;
		this.blueData = blueData ;
		this.argbData = argbData ;
		this.imageRedPixelDataArrayList = imageRedPixelDataArrayList ;
		this.imageGreenPixelDataArrayList = imageGreenPixelDataArrayList ;
		this.imageBluePixelDataArrayList = imageBluePixelDataArrayList ;
		this.imageArgbPixelDataArrayList = imageArgbPixelDataArrayList ;
	}
	
	@Override
	protected Void call() {
		
		for( int y = 0 ; y < img.getHeight() ; y++ ) {
			for( int x = 0 ; x < img.getWidth() ; x++ ) {
				
				int argbValue = pixelReader.getArgb( x , y );
				
				int alpha = 0xff & argbValue>>24 ;
			    int red = 0xff & argbValue>>16 ;
	            int green = 0xff & argbValue>>8 ;
	            int blue = 0xff & argbValue ;
	            
	            redData.add( red ) ;
	            greenData.add( green ) ;
	            blueData.add( blue ) ;
	            argbData.add(argbValue) ;
			}
		}
		
		Platform.runLater( new Runnable() {
			@Override
			public void run() {
				
				imageRedPixelDataArrayList.add( redData );
				imageGreenPixelDataArrayList.add( greenData );
				imageBluePixelDataArrayList.add( blueData );
				imageArgbPixelDataArrayList.add( argbData );
			}
		});
		return null ;
	}
}
