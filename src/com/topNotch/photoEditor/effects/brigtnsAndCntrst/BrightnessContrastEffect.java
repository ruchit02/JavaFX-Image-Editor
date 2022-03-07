package com.topNotch.photoEditor.effects.brigtnsAndCntrst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.topNotch.photoEditor.main.LinkableEffect;
import com.topNotch.photoEditor.main.PWAOPropertiesTool;
import com.topNotch.photoEditor.main.PrimaryWorkArea;
import com.topNotch.photoEditor.main.PrimaryWorkAreaObject;
import com.topNotch.photoEditor.main.PrimaryWorkAreaObjectStatsDisplayingTools;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BrightnessContrastEffect implements LinkableEffect{
	
	private static List<BrightnessContrastEffect> briConCollection = new ArrayList<BrightnessContrastEffect>() ;
	
	private static final int[] tempArray = new int[ 256 ] ;
	private final double[] cEqBriAlg ; // constant equation/part of the brightness algorithm
	
	private int width ;
	private int height ;
	
	private VBox rootVbox ;
	
	private HBox brightnessHbox ;
	private Label brightnessLabel ;
	private TextField brightnessTextfield ;
	private Slider brightnessSlider ;
	
	private HBox contrastHbox ;
	private Label contrastLabel ;
	private TextField contrastTextfield ;
	private Slider contrastSlider ;
	
	private Canvas canvas ;
	
	private final int[] preModificationRED ;
	private final int[] preModificationGREEN ;
	private final int[] preModificationBLUE ;
	private final int[] preModificationARGB ;
	
	private final int[] postModificationRED ;
	private final int[] postModificationGREEN ;
	private final int[] postModificationBLUE ;
	private final int[] postModificationARGB ;
	
	public final int[] elements ;
	
	private final int th1_EndIndex  ;
	private final int th2_StartIndex ; 
	
	private final int processors = 4 ;
	private final int constant  ;
	
	public BrightnessContrastEffect( LinkableEffect precedingEffect , double width , double height )  {
		
		this.canvas 				= new Canvas( width , height ) ;
		
		this.width 					= (int)width ;
		this.height 				= (int)height ;
		
		System.out.println( "CANVAS WIDTH : " + this.width + " | " + "CANVAS HEIGHT : " + this.height ) ;
		
		this.preModificationARGB  	= precedingEffect.getPostModifiedARGB()		;
		this.preModificationRED   	= precedingEffect.getPostModifiedRED() 		;
		this.preModificationGREEN 	= precedingEffect.getPostModifiedGREEN() 	;
		this.preModificationBLUE  	= precedingEffect.getPostModifiedBLUE()		;
		
		this.postModificationARGB  	= new int[ this.width*this.height ];
		this.postModificationRED   	= new int[ this.width*this.height ];
		this.postModificationGREEN 	= new int[ this.width*this.height ];
		this.postModificationBLUE  	= new int[ this.width*this.height ];
		
		this.elements   			= new int[ this.width*this.height ];
		
		this.th1_EndIndex   = (int)(elements.length/(double)2) ;
 		this.th2_StartIndex = th1_EndIndex + 1 ;
 		this.constant = (int)(elements.length/(double)processors) ;
		
		this.cEqBriAlg   			= new double[ 256 ] ;
		
		System.out.println( "RED ARRAY LENGTH : "   + this.preModificationRED.length 
				          + "GREEN ARRAY LENGTH : " + this.preModificationGREEN.length
				          + "BLUE ARRAY LENGTH : "  + this.preModificationBLUE.length ) ;
		
		putDataInArrays();
		this.createEffectObjects() ;
		briConCollection.add( this ) ;
	}
	
	@Override
	public final int[] getPostModifiedRED() {
		
		return this.postModificationRED ;
	}
	
	@Override
	public final int[] getPostModifiedGREEN() {
		
		return this.postModificationGREEN ;
	}
	
	@Override
	public final int[] getPostModifiedBLUE() {
		
		return this.postModificationBLUE ;
	}
	
	@Override
	public final int[] getPostModifiedARGB() {
		
		return this.postModificationARGB ;
	}
	
	@Override
	public final VBox getRoot() {
	    	
	    return this.rootVbox ;
	}
	
	private void createEffectObjects() {
		
		rootVbox = new VBox() ;
		
		brightnessHbox = new HBox();
		brightnessHbox.setSpacing( 115 );
		rootVbox.getChildren().add( brightnessHbox );
		
		brightnessLabel = new Label();
	    brightnessLabel.setText("Brightness");
		brightnessHbox.getChildren().add( brightnessLabel );
		
		brightnessTextfield = new TextField();
		brightnessTextfield.setText( "0" );
		brightnessTextfield.setMaxWidth(50);
		brightnessTextfield.setEditable(false);
		brightnessHbox.getChildren().add( brightnessTextfield );
		
		brightnessSlider = new Slider();
		//brightnessSlider.setPrefWidth(Double.MAX_VALUE);
		brightnessSlider.setMin(-100);
		brightnessSlider.setMax(100);
		brightnessSlider.setValue( 0 );
		brightnessSlider.setSnapToTicks( true );
		brightnessSlider.setMinorTickCount( 1 );
		rootVbox.getChildren().add( brightnessSlider );
		
		addFunctionalityToBrightnessSlider( brightnessSlider , brightnessTextfield ) ;
		
		PWAOPropertiesTool.getInstance().getPropertyScrollPane().setContent( rootVbox );
		
		PrimaryWorkAreaObject.getObjects().get( PrimaryWorkArea.selectedTabIndex ).getStackPane().getChildren().add( this.canvas ) ;
		System.out.println( "STACK PANE SIZE : " + PrimaryWorkAreaObject.getObjects().get( PrimaryWorkArea.selectedTabIndex ).getStackPane().getChildren().size() );
	}
	
	private void putDataInArrays() {
		
		int i = 0 ;
		
		double sliderConstant = 100 ;
		double pixelConstant = 256 ;
		
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
				postModificationRED[i] 		= preModificationRED[i] ;
				postModificationGREEN[i] 	= preModificationGREEN[i] ;
				postModificationBLUE[i]		= preModificationBLUE[i] ;
				postModificationARGB[i]  	= preModificationARGB[i] ;
				
				elements[i] = i;
				
				i++ ;
			}
			
			System.out.println( "BRI-CON ARGB" + " | " + postModificationRED[y] + " | " + postModificationGREEN[y] + " | " + postModificationBLUE[y]  ); 
		}
		
		this.canvas.getGraphicsContext2D().getPixelWriter().setPixels( 0 , 0 , width , height , PixelFormat.getIntArgbInstance() , postModificationARGB , 0 , width*1 );
		
		displayPixelCo_ordinates( canvas , PrimaryWorkAreaObjectStatsDisplayingTools.getInstance().getTextFieldX() 
                                , PrimaryWorkAreaObjectStatsDisplayingTools.getInstance().getTextFieldY() ) ;
        
		installRGBValueFinder( canvas , PrimaryWorkAreaObjectStatsDisplayingTools.getInstance().getTextFieldR()
                             , PrimaryWorkAreaObjectStatsDisplayingTools.getInstance().getTextFieldG() 
                             , PrimaryWorkAreaObjectStatsDisplayingTools.getInstance().getTextFieldB() ) ;
		
		for( int ind = 0 ; ind < 256 ; ind++ ) {
			
			tempArray[ind] = ind ;
		}
		
		//this is the main algorithm
		for( int newInt : tempArray ) {
			
			cEqBriAlg[newInt] = ( ( ( (255 - newInt)/pixelConstant)*newInt )/sliderConstant ) ;
		}
		
		System.out.println( cEqBriAlg.length );
		
		IteratingBrightnessPixels.preCalcValues = cEqBriAlg ;
		IteratingBrightnessPixels.elements = elements ;
		
		IteratingBrightnessPixels.width = width ;
		IteratingBrightnessPixels.height = height ;
		
		IteratingBrightnessPixels.actualRED = preModificationRED ;
		IteratingBrightnessPixels.actualGREEN = preModificationGREEN ;
		IteratingBrightnessPixels.actualBLUE = preModificationBLUE ;
		
		IteratingBrightnessPixels.postModificationRED = this.postModificationRED ;
		IteratingBrightnessPixels.postModificationGREEN = this.postModificationGREEN ;
		IteratingBrightnessPixels.postModificationBLUE = this.postModificationBLUE ;
		IteratingBrightnessPixels.postModificationARGB = this.postModificationARGB ;
		
		IteratingBrightnessPixels.canvas = this.canvas ;
		IteratingBrightnessPixels.pxlWriter = this.canvas.getGraphicsContext2D().getPixelWriter() ;
		IteratingBrightnessPixels.intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
		
		IteratingDarknessPixels.preCalcValues = cEqBriAlg ;
		IteratingDarknessPixels.elements = elements ;
		
		IteratingDarknessPixels.width = width ;
		IteratingDarknessPixels.height = height ;
		
		IteratingDarknessPixels.actualRED = preModificationRED ;
		IteratingDarknessPixels.actualGREEN = preModificationGREEN ;
		IteratingDarknessPixels.actualBLUE = preModificationBLUE ;
		
		IteratingDarknessPixels.postModificationRED = this.postModificationRED ;
		IteratingDarknessPixels.postModificationGREEN = this.postModificationGREEN ;
		IteratingDarknessPixels.postModificationBLUE = this.postModificationBLUE ;
		IteratingDarknessPixels.postModificationARGB = this.postModificationARGB ;
		
		IteratingDarknessPixels.canvas = this.canvas ;
		IteratingDarknessPixels.pxlWriter = this.canvas.getGraphicsContext2D().getPixelWriter() ;
		IteratingDarknessPixels.intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	}
	
    private void addFunctionalityToBrightnessSlider( Slider brightnessSlider , TextField brightnessTextfield ) {
    	
    	brightnessSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				int value = newVal.intValue() ;
				
				long startTime3 = System.nanoTime() ;
				
				try {
					
					int start = 0 ;
					int end = 0 ;
					
					for( int i = 1 ; i <= processors ; i++ ) {
						
						if( end == 0 ) { start = end ; }
						else           { start = end + 1 ; }
							
						
						if( i != processors ) { end   = constant*i ; }
						else                  { end = elements.length ; } 
						
						final int startIndex = start ;
						final int endIndex = end ;
						
						Thread th = new Thread( new Runnable() {
							
							   @Override
							   public void run() {
								   
								   int redValue 	= 0 ;
								   int greenValue 	= 0 ;
								   int blueValue 	= 0 ;
									
								   int newRed 		= 0 ;
								   int newGreen	= 0 ;
								   
								   for( int i = startIndex ; i < endIndex ; i++ ) {
										
										redValue 	= preModificationRED[i] ;
										greenValue 	= preModificationGREEN[i] ;
										blueValue 	= preModificationBLUE[i] ;
										
										postModificationRED[i] 		= redValue + (int)(value*cEqBriAlg[redValue]) ;
										postModificationGREEN[i] 	= greenValue + (int)(value*cEqBriAlg[greenValue]) ;
										postModificationBLUE[i] 	= blueValue + (int)(value*cEqBriAlg[blueValue]) ;
										
										if( postModificationRED[i] > 255 ) {
											postModificationRED[i] = 255 ;
										}
										if( postModificationRED[i] < 0 ) {
											postModificationRED[i] = 0 ;
										}
										
										if( postModificationGREEN[i] > 255 ) {
											postModificationGREEN[i] = 255 ;
										}
										if( postModificationGREEN[i] < 0 ) {
											postModificationGREEN[i] = 0 ;
										}
										
										if( postModificationBLUE[i] > 255 ) {
											postModificationBLUE[i] = 255 ;
										}
										if( postModificationBLUE[i] < 0 ) {
											postModificationBLUE[i] = 0 ;
										}
										
										
										newRed = postModificationRED[i]<<16 ;
										newGreen = postModificationGREEN[i]<<8 ;
										
										postModificationARGB[i] = 0xff000000 | newRed | newGreen | postModificationBLUE[i] ;
									}
							   }
						}) ;
						
						th.start();
					}
					
				}finally {
					
					canvas.getGraphicsContext2D().getPixelWriter().setPixels( 0 , 0 , width , height , PixelFormat.getIntArgbInstance() , postModificationARGB , 0 , width*1 );
					long endTime3 = System.nanoTime() - startTime3 ;
					System.out.println( "2 Threads Time " + endTime3 + postModificationARGB[40230] );
				}
				
				brightnessTextfield.setText( String.valueOf( value ) );
			}
		});
    }
    
	private void displayPixelCo_ordinates( Canvas canvas , TextField tfX , TextField tfY ) {
		
		 EventHandler<MouseEvent> pixelCo_ordinates = new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseMovedEvent ) {
				
			    tfX.setText( Double.toString( mouseMovedEvent.getX() ) );
				tfY.setText( Double.toString( mouseMovedEvent.getY() ) ) ;  
				
				mouseMovedEvent.consume();
			}
		};
		
		canvas.addEventHandler( MouseEvent.MOUSE_MOVED , pixelCo_ordinates );
	}
	
   private void installRGBValueFinder( Canvas canvas , TextField textFieldRed , TextField textFieldGreen , TextField textFieldBlue ) {
		
		EventHandler<MouseEvent> canvasColorComponent = new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mcEvent ) {
				
				WritableImage wImage = canvas.snapshot( new SnapshotParameters() , null );
				int ARGBobtained = wImage.getPixelReader().getArgb(  (int)mcEvent.getX() , (int)mcEvent.getY() ) ;
				
				int red   = ( ARGBobtained >> 16 ) & 0xff ;
				int green = ( ARGBobtained >> 8 ) & 0xff ;
				int blue  = ( ARGBobtained  ) & 0xff ;
				
				textFieldRed.setText( Integer.toString(red) );
				textFieldGreen.setText( Integer.toString(green));
				textFieldBlue.setText( Integer.toString(blue));
			}
		};
		
		canvas.addEventHandler( MouseEvent.MOUSE_MOVED , canvasColorComponent );
	}
   
   private class Brightner extends Thread {
	   
	   private int startIndex ;
	   private int endIndex ;
	   private int value ;
	   
	   private Brightner( int startIndex , int endIndex , int value ) {
		   
		   this.startIndex = startIndex ;
		   this.endIndex = endIndex ;
		   this.value = value ;
	   }
	   

   }
}