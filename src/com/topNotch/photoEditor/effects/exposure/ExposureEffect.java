package com.topNotch.photoEditor.effects.exposure;

import com.topNotch.photoEditor.main.MyEffects;

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
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ExposureEffect extends MyEffects {
	
	private Blend blend ;
	private Canvas canvas ;
	private int width ;
	private int height ;
	private ColorAdjust colorAdjust ;
	
	private TabPane tabPane ;
	private GridPane gridPane ;
	private ScrollPane scrollPane ;
	
	private HBox toggleButtonsHbox ;
	private ToggleGroup toggleGroup ;
	private ToggleButton exposureToggleButton ;
	private ToggleButton masksToggleButton ;
	
	private VBox rootVbox ;
	private VBox masksVbox ;
	
	private HBox exposureHbox ;
	private Label exposureLabel ;
	private TextField exposureTextfield ;
	private Slider exposureSlider ;
	
	private HBox offsetHbox ;
	private Label offsetLabel ;
	private TextField offsetTextfield ;
	private Slider offsetSlider ;
	
	private HBox gammaCorrectionHbox ;
	private Label gammaCorrectionLabel ;
	private TextField gammaCorrectionTextfield ;
	private Slider gammaCorrectionSlider ;
	
	private int actualRed[] ;
	private int actualGreen[] ;
	private int actualBlue[] ;
	private int rgbAverage[] ;
	
	private double actualRedInFraction[] ;
	private double actualGreenInFraction[] ;
	private double actualBlueInFraction[] ;
	private double rgbAverageInFraction[] ;
	
	private int counterRed[];
	private int counterGreen[];
	private int counterBlue[];
	
	private double counterRedInFraction[];
	private double counterGreenInFraction[];
	private double counterBlueInFraction[];
	
	public ExposureEffect( Image img ) {
		
		super( img );
		this.blend = this.getBlend();
		this.canvas = this.getCanvas();
		this.width = (int)this.canvas.getWidth();
		this.height = (int)this.canvas.getHeight() ;
		this.colorAdjust = this.getColorAdjust();
		
		actualRed = new int[ (int)(width*height) ] ; 
		actualGreen = new int[ (int)(width*height) ] ; 
		actualBlue  = new int[ (int)(width*height) ] ; 
		
		counterRed = new int[ (int)(width*height) ] ; 
		counterGreen = new int[ (int)(width*height) ] ; 
		counterBlue = new int[ (int)(width*height) ] ; 
		
		actualRedInFraction = new double[ (int)(width*height) ] ; 
		actualGreenInFraction = new double[ (int)(width*height) ] ; 
		actualBlueInFraction = new double[ (int)(width*height) ] ;
		
		counterRedInFraction = new double[ (int)(width*height) ] ; 
		counterGreenInFraction = new double[ (int)(width*height) ] ; 
		counterBlueInFraction = new double[ (int)(width*height) ] ; 
		
		rgbAverage = new int[ (int)(width*height) ];
		
		rgbAverageInFraction = new double[ (int)(width*height) ];
		
		this.createEffectObjects();
	}
	
	public ExposureEffect( WritableImage wImg ) {
		
		super( wImg );
		this.blend = this.getBlend();
		this.canvas = this.getCanvas();
		this.width = (int)this.canvas.getWidth();
		this.height = (int)this.canvas.getHeight() ;
		this.colorAdjust = this.getColorAdjust();
		
		actualRed = new int[ (int)(width*height) ] ; 
		actualGreen = new int[ (int)(width*height) ] ; 
		actualBlue  = new int[ (int)(width*height) ] ; 
		
		counterRed = new int[ (int)(width*height) ] ; 
		counterGreen = new int[ (int)(width*height) ] ; 
		counterBlue = new int[ (int)(width*height) ] ; 
		
		actualRedInFraction = new double[ (int)(width*height) ] ; 
		actualGreenInFraction = new double[ (int)(width*height) ] ; 
		actualBlueInFraction = new double[ (int)(width*height) ] ;
		
		counterRedInFraction = new double[ (int)(width*height) ] ; 
		counterGreenInFraction = new double[ (int)(width*height) ] ; 
		counterBlueInFraction = new double[ (int)(width*height) ] ; 
		
		rgbAverage = new int[ (int)(width*height) ];
		
		rgbAverageInFraction = new double[ (int)(width*height) ];
		
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
		
		exposureToggleButton = new ToggleButton();
		exposureToggleButton.setText("Exposure");
		exposureToggleButton.setToggleGroup( toggleGroup );
		exposureToggleButton.setSelected( true );
		toggleButtonsHbox.getChildren().add( exposureToggleButton );
		
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
		rootVbox.setMaxWidth( 235 );
		scrollPane.setContent( rootVbox );
		
		exposureHbox = new HBox();
		exposureHbox.setSpacing( 105 );
		rootVbox.getChildren().add( exposureHbox );
		
		exposureLabel = new Label();
		exposureLabel.setText("Exposure");
		exposureHbox.getChildren().add( exposureLabel );
		
		exposureTextfield = new TextField();
		exposureTextfield.setMaxWidth( 50 );
		exposureTextfield.setText("0");
		exposureHbox.getChildren().add( exposureTextfield );
		
		exposureSlider = new Slider();
		exposureSlider.setMin( -12 );
		exposureSlider.setMax( 12 );
		exposureSlider.setValue( 0 );
		exposureSlider.setMaxWidth( Double.MAX_VALUE );
		rootVbox.getChildren().add( exposureSlider );
		
		addFunctionalityToExposureSlider( exposureSlider , exposureTextfield );
		
		offsetHbox = new HBox();
		offsetHbox.setSpacing( 135 );
		rootVbox.getChildren().add( offsetHbox );
		
		offsetLabel = new Label();
		offsetLabel.setText("Offset");
		offsetHbox.getChildren().add( offsetLabel );
		
		offsetTextfield = new TextField();
		offsetTextfield.setMaxWidth( 50 );
		offsetTextfield.setText("0");
		offsetHbox.getChildren().add( offsetTextfield );
		
		offsetSlider = new Slider();
		offsetSlider.setMin( -0.5 );
		offsetSlider.setMax( 0.5 );
		offsetSlider.setMajorTickUnit( 0.1 );
		offsetSlider.setMinorTickCount( 100 );
		offsetSlider.setMaxWidth( Double.MAX_VALUE );
		rootVbox.getChildren().add( offsetSlider );
		
		addFunctionalityToOffsetSlider( offsetSlider , offsetTextfield );
		
		/*gammaCorrectionHbox = new HBox();
		gammaCorrectionHbox.setSpacing( 65 );
		rootVbox.getChildren().add( gammaCorrectionHbox );
		
		gammaCorrectionLabel = new Label();
		gammaCorrectionLabel.setText("Gamma Correction");
		gammaCorrectionHbox.getChildren().add( gammaCorrectionLabel );
		
		gammaCorrectionTextfield = new TextField();
		gammaCorrectionTextfield.setMaxWidth( 50 );
		gammaCorrectionTextfield.setText("4");
		gammaCorrectionHbox.getChildren().add( gammaCorrectionTextfield );
		
		gammaCorrectionSlider = new Slider();
		gammaCorrectionSlider.setMin( 0 );
		gammaCorrectionSlider.setMax( 10 );
		gammaCorrectionSlider.setValue( 1 );
		gammaCorrectionSlider.setMaxWidth( Double.MAX_VALUE );
		rootVbox.getChildren().add( gammaCorrectionSlider ) ;
		
		addFunctionalityToGammaCorrectionSlider( gammaCorrectionSlider , gammaCorrectionTextfield );*/
		
		putDataInArrays();
	}
	
	private void putDataInArrays() {
		
		int i = 0 ;
    	final WritableImage wImg = canvas.snapshot( new SnapshotParameters() , null );
    	PixelReader pixelReader = wImg.getPixelReader() ;
		
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
                int ARGBobtained = pixelReader.getArgb( x , y );
				
				int red                   = (0xff & (ARGBobtained >> 16)) ;
				int green                 = (0xff & (ARGBobtained >> 8))  ;
				int blue                  = (0xff & (ARGBobtained));	
				
				actualRed[i]              = red   ;
				actualGreen[i]            = green ;
				actualBlue[i]             = blue  ;
				
				counterRed[i]             = 255 - actualRed[i] ; 
				counterGreen[i]           = 255 - actualGreen[i] ;
				counterBlue[i]            = 255 - actualBlue[i] ;
				
				actualRedInFraction[i]    = actualRed[i]/(double)256 ;
				actualGreenInFraction[i]  = actualGreen[i]/(double)256 ;
				actualBlueInFraction[i]   = actualBlue[i]/(double)256 ;
				
				counterRedInFraction[i]   = counterRed[i]/(double)256 ;
				counterGreenInFraction[i] = counterGreen[i]/(double)256 ;
				counterBlueInFraction[i]  = counterBlue[i]/(double)256 ;
				
				rgbAverage[i]             = ((actualRed[i] + actualGreen[i] + actualBlue[i])/3) ;
				
				rgbAverageInFraction[i] = rgbAverage[i]/(double)256 ;
				
				i++ ;
			}
		}
	}
	
	private void addFunctionalityToExposureSlider( Slider exposureSlider , TextField exposureTextfield ) {
		
		exposureSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				double value = newVal.doubleValue() ;
				
				exposureImageExposureTask( value );
				
				exposureTextfield.setText( Double.toString( value ));
			}
		});
	}
	
	private void exposureImageExposureTask( double value ) {
		
		ExposureImageExposureManipulatorTask task = new ExposureImageExposureManipulatorTask( canvas , value , actualRed , actualGreen 
				, actualBlue , rgbAverage ) ;
		Thread th = new Thread(task);
		th.setDaemon( true );
		th.start();
	}
	
	private void addFunctionalityToOffsetSlider( Slider offsetSlider , TextField offsetTextfield ) {
		
		offsetSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				double value = newVal.doubleValue() ;
				
				exposureOffsetTask( value );
				
			    offsetTextfield.setText( Double.toString( value ));
			}
		});
	}
	
	private void exposureOffsetTask( double value ) {
		
		ExposureOffsetManipulatorTask task = new ExposureOffsetManipulatorTask( canvas , value , actualRed , actualGreen 
				, actualBlue , rgbAverage ) ;
		Thread th = new Thread(task);
		th.setDaemon( true );
		th.start();
	}
	
    private void addFunctionalityToGammaCorrectionSlider( Slider gammaCorrectionSlider , TextField gammaCorrectionTextfield ) {
		
    	gammaCorrectionSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				float value = newVal.floatValue() ;
				
				//exposureGammaCorrectionTask( value ) ;
				
				gammaCorrectionTextfield.setText( Float.toString( value ));
			}
		});
	}
    
    private void exposureGammaCorrectionTask( float value ) {
		
		ExposureOffsetManipulatorTask task = new ExposureOffsetManipulatorTask( canvas , value , actualRed , actualGreen 
				, actualBlue , rgbAverage ) ;
		Thread th = new Thread(task);
		th.setDaemon( true );
		th.start();
	}
}
