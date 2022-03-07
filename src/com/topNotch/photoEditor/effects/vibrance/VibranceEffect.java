package com.topNotch.photoEditor.effects.vibrance;

import java.util.concurrent.ExecutionException;

import com.topNotch.photoEditor.main.MyEffects;

import javafx.scene.input.MouseButton;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.Blend;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage ;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VibranceEffect extends MyEffects {
	
	private Blend blend ;
	private Canvas canvas ;
	private ColorAdjust colorAdjust ;
	private int width ;
	private int height ;
	
	private TabPane tabPane ;
	private GridPane gridPane ;
	private ScrollPane scrollPane ;
	
	private VBox rootVbox ;
	private VBox masksVbox ;
	
	private HBox toggleButtonsHbox ;
	private ToggleGroup toggleGroup ;
	private ToggleButton vibranceToggleButton ;
	private ToggleButton masksToggleButton ;
	
	private HBox vibranceHbox ;
	private Label vibranceLabel ;
	private TextField vibranceTextfield ;
	private Slider vibranceSlider ;
	
	private HBox saturationHbox ;
	private Label saturationLabel ;
	private TextField saturationTextfield ;
	private Slider saturationSlider ;
	
	private int actualRed[];
	private int actualGreen[];
	private int actualBlue[];
	
	private int saturationRed[] ;
	private int saturationGreen[] ;
	private int saturationBlue[] ;
	
	private int vibranceRed[] ;
	private int vibranceGreen[] ;
	private int vibranceBlue[] ;
	
	private int rgbAverage[] ;
	private int rgbVibranceAverage[] ;
	private int rgbSaturationAverage[] ;
	
	private int iterationsArray[] ;
	
	public VibranceEffect( Image img ) {
		
		super( img );
		this.blend = this.getBlend();
		this.canvas = this.getCanvas();
		this.width = (int)this.canvas.getWidth();
		this.height = (int)this.canvas.getHeight() ;
		this.colorAdjust = this.getColorAdjust();
		
		this.actualRed = new int[ width*height ];
		this.actualGreen = new int[ width*height ];
		this.actualBlue = new int[ width*height ];
		
		this.rgbAverage = new int[ width*height ];
		this.rgbVibranceAverage = new int[ width*height ];
		this.rgbSaturationAverage = new int[ width*height ];
		
		this.saturationRed = new int[ width*height ] ;
		this.saturationGreen = new int[ width*height ] ;
		this.saturationBlue = new int[ width*height ] ;
		
		this.vibranceRed = new int[ width*height ] ;
		this.vibranceGreen = new int[ width*height ] ;
		this.vibranceBlue = new int[ width*height ] ;
		
		this.iterationsArray =  new int[ width*height ] ;
		
		this.createEffectObjects();
	}
	
	public VibranceEffect( WritableImage wImg ) {
		
		super( wImg );
		this.blend = this.getBlend();
		this.canvas = this.getCanvas();
		this.width = (int)this.canvas.getWidth();
		this.height = (int)this.canvas.getHeight() ;
		this.colorAdjust = this.getColorAdjust();
		
		this.actualRed = new int[ width*height ];
		this.actualGreen = new int[ width*height ];
		this.actualBlue = new int[ width*height ];
		
		this.rgbAverage = new int[ width*height ];
		this.rgbVibranceAverage = new int[ width*height ];
		this.rgbSaturationAverage = new int[ width*height ];
		
		this.saturationRed = new int[ width*height ] ;
		this.saturationGreen = new int[ width*height ] ;
		this.saturationBlue = new int[ width*height ] ;
		
		this.vibranceRed = new int[ width*height ] ;
		this.vibranceGreen = new int[ width*height ] ;
		this.vibranceBlue = new int[ width*height ] ;
		
		this.iterationsArray =  new int[ width*height ] ;
		
		this.createEffectObjects();
	}
	
	public Blend getBlend() {
		
		return super.getBlend();
	}
	
	public Canvas getCanvas() {
		
		return super.getCanvas();
	}
	
	public ColorAdjust getColorAdjust() {
		
		return super.getColorAdjust();
	}
	
	public TabPane getTabPane() {
		
		return tabPane ;
	}
	
	@Override
	public ScrollPane getScrollPane() {
		
		return scrollPane ;
	}
	
	private void createEffectObjects() {
		
        tabPane = new TabPane();
		
		Tab propertiesTab = new Tab();
		propertiesTab.setText("Properties");
		propertiesTab.setClosable( false );
		tabPane.getTabs().add( propertiesTab );
		
		gridPane = new GridPane();
		propertiesTab.setContent( gridPane );
		
		toggleButtonsHbox = new HBox();
		toggleButtonsHbox.setTranslateX( 5 );
		toggleButtonsHbox.setSpacing( 5 );
		toggleButtonsHbox.setTranslateY( 5 );
		gridPane.getChildren().add( toggleButtonsHbox );
		
		toggleGroup = new ToggleGroup();
		
		vibranceToggleButton = new ToggleButton();
		vibranceToggleButton.setText("Vibrance");
		vibranceToggleButton.setToggleGroup( toggleGroup );
		vibranceToggleButton.setSelected( true );
		toggleButtonsHbox.getChildren().add( vibranceToggleButton );
		
		masksToggleButton = new ToggleButton();
		masksToggleButton.setText("Masks");
		masksToggleButton.setToggleGroup( toggleGroup );
		toggleButtonsHbox.getChildren().add( masksToggleButton );
		
		toggleGroup.selectedToggleProperty().addListener( new ChangeListener<Toggle>() {
			@Override
			public void changed( ObservableValue< ? extends Toggle > obv , Toggle oldVal , Toggle newVal ) {
				
				if( newVal == masksToggleButton ) {
					
					scrollPane.setContent( masksVbox );
				}
				else {
					
					scrollPane.setContent( rootVbox );
				}
			}
		});
		
		scrollPane = new ScrollPane();
		scrollPane.setTranslateX( 2 );
		scrollPane.setTranslateY( 36 );
		scrollPane.setMinWidth( 246 );
		scrollPane.setMaxSize( 246 , 175 );
		scrollPane.setVbarPolicy( ScrollBarPolicy.ALWAYS );
		scrollPane.setHbarPolicy( ScrollBarPolicy.NEVER );
		gridPane.getChildren().add( scrollPane );
		
		rootVbox = new VBox();
		rootVbox.setPadding( new Insets( 5,5,5,5 ) );
		rootVbox.setSpacing( 5 );
		scrollPane.setContent( rootVbox );
		
		vibranceHbox = new HBox();
		vibranceHbox.setSpacing( 120 );
		rootVbox.getChildren().add( vibranceHbox );
		
		vibranceLabel = new Label();
		vibranceLabel.setText("Vibrance");
		vibranceHbox.getChildren().add( vibranceLabel );
		
		vibranceTextfield = new TextField();
		vibranceTextfield.setMaxWidth( 50 );
		vibranceTextfield.setText("0");
		vibranceHbox.getChildren().add( vibranceTextfield );
		
		vibranceSlider = new Slider();
		vibranceSlider.setMin( -100 );
		vibranceSlider.setMax( 100 );
		rootVbox.getChildren().add( vibranceSlider );
		
		vibranceSlider.setOnMouseReleased( new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent e ) {
				
				int i = 0 ;
				for( int y = 0 ; y < height ; y++ ) {
					for( int x = 0 ; x < width ; x++ ) {
					    
					    vibranceRed[i] = ((iterationsArray[i]>>16) & 0xff) ;
					    vibranceGreen[i] = ((iterationsArray[i]>>8) & 0xff) ;
					    vibranceBlue[i] = (iterationsArray[i] & 0xff) ;
					    
					    rgbVibranceAverage[i] = (int)((vibranceRed[i] + vibranceGreen[i] + vibranceBlue[i])/(double)3) ;
					    
					    i++ ;
					}
				}
			}
		});
		
		addFunctionalityToVibranceSlider( vibranceSlider , vibranceTextfield );
		vibranceSlider.setValue( 0 );
		
		saturationHbox = new HBox();
		saturationHbox.setSpacing( 112 );
		rootVbox.getChildren().add( saturationHbox );
		
		saturationLabel = new Label();
		saturationLabel.setText("Saturation");
		saturationHbox.getChildren().add( saturationLabel );
		
		saturationTextfield = new TextField();
		saturationTextfield.setMaxWidth( 50 );
		saturationTextfield.setText("0");
		saturationHbox.getChildren().add( saturationTextfield );
		
		saturationSlider = new Slider();
		saturationSlider.setMin( -100 );
		saturationSlider.setMax( 100 );
		rootVbox.getChildren().add( saturationSlider );
		
		saturationSlider.setOnMouseReleased( new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent e ) {
				
				int i = 0 ;
				for( int y = 0 ; y < height ; y++ ) {
					for( int x = 0 ; x < width ; x++ ) {
					    
					    saturationRed[i] = ((iterationsArray[i]>>16) & 0xff) ;
					    saturationGreen[i] = ((iterationsArray[i]>>8) & 0xff) ;
					    saturationBlue[i] = (iterationsArray[i] & 0xff) ;
					    
					    rgbSaturationAverage[i] = (int)((saturationRed[i] + saturationGreen[i] + saturationBlue[i])/(double)3) ;
					    
					    i++ ;
					}
				}
			}
		});
		
		addFunctionalityToSaturationSlider( saturationSlider , saturationTextfield );
		saturationSlider.setValue( 0 );
		
		putDataInArrays();
	}
	
	private void putDataInArrays() {
		
		int i = 0 ;
    	WritableImage wImg = canvas.snapshot( new SnapshotParameters() , null );
    	PixelReader pxlReader = wImg.getPixelReader() ;
    	
    	for( int y = 0 ; y < height ; y++ ) {
    		for( int x = 0 ; x < width ; x++ ) {
    			
    			int argb = pxlReader.getArgb( x , y );
    			
    			int red   = ( 0xff & (argb>>16) );
    			int green = ( 0xff & (argb>>8) );
    			int blue  = ( 0xff & (argb) );
    			 
    			actualRed[i] = red ;
    			actualGreen[i] = green ;
    			actualBlue[i] = blue ;
    			
    			saturationRed[i] = actualRed[i] ;
    			saturationGreen[i] = actualGreen[i] ;
    			saturationBlue[i] = actualBlue[i] ;
    			
    			vibranceRed[i] = actualRed[i] ;
    			vibranceGreen[i] = actualGreen[i] ;
    			vibranceBlue[i] = actualBlue[i] ;
    			
    			rgbAverage[i] = (int)((red + green + blue)/(double)3) ;
    			rgbVibranceAverage[i] = (int)((red + green + blue)/(double)3) ;
    			rgbSaturationAverage[i] = (int)((red + green + blue)/(double)3) ;
    			
    			i++ ;
    		}
    	}
    	
    	VibrancePixelManipulatorTask.width = width ;
    	VibrancePixelManipulatorTask.height = height ;
		
    	VibrancePixelManipulatorTask.actualRed = actualRed ;
    	VibrancePixelManipulatorTask.actualGreen = actualGreen ;
		VibrancePixelManipulatorTask.actualBlue = actualBlue ;
		VibrancePixelManipulatorTask.rgbAverage = rgbSaturationAverage ;
		
		VibrancePixelManipulatorTask.canvas = canvas ;
		VibrancePixelManipulatorTask.pxlWriter = canvas.getGraphicsContext2D().getPixelWriter() ;
		VibrancePixelManipulatorTask.intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
	}
	
	private void addFunctionalityToVibranceSlider( Slider vibranceSlider , TextField vibranceTextfield ) {
		
		vibranceSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				int value = newVal.intValue() ;
				
				callVibranceTask( value );
				
				vibranceTextfield.setText( Integer.toString( value ));
			}
		});
	}
	
    private void callVibranceTask( int value ) {
    	
    	VibrancePixelManipulatorTask task = new VibrancePixelManipulatorTask( value );
    	Thread th = new Thread(task);
    	th.setDaemon( true );
    	th.start();
    	
    	try {
			iterationsArray = task.get() ;
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
    private void addFunctionalityToSaturationSlider( Slider saturationSlider , TextField saturationTextfield ) {
		
    	saturationSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				int value = newVal.intValue() ;
				
				callSaturationTask(value);
				
				saturationTextfield.setText( Integer.toString( value ));
			}
		});
	}
    
    private void callSaturationTask( int value ) {
    	
    	VibrancePixelSaturationManipulatorTask task = new VibrancePixelSaturationManipulatorTask( canvas , value , vibranceRed , vibranceGreen , vibranceBlue 
    			                           , rgbVibranceAverage );
    	Thread th = new Thread(task);
    	th.setDaemon( true );
    	th.start();
    	
    	try {
			iterationsArray = task.get() ;
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
