package com.topNotch.photoEditor.effects.levels;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.topNotch.photoEditor.main.MyEffects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
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
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LevelsEffectNew extends MyEffects{
	
	private ColorAdjust colorAdjust ;
	private Blend blend ;
	private Image img ;
	private WritableImage wImg ;
	
    private int argbData[] ;
	
	private Canvas canvas ;
	
	private int width ;
	private int height ;
	
    private int iterationsArrayA[] ;
	
	private int argbPixelsArray[]  ;     
	private int colorPixelsArray[] ;
	
	private int argb255PixelsArray[]       ;   
	private int arg255bPixelsArray[]       ;  
	private int ar255gbPixelsArray[]       ; 
	private int colorWhitePixelsArray[]    ;
	private int ar255g255b255PixelsArray[] ;
	
	private int argb0PixelsArray[]      ;  
	private int arg0bPixelsArray[]      ;  
	private int ar0gbPixelsArray[]      ;  
	private int ar0g0b0PixelsArray[]    ;
	private int colorBlackPixelsArray[] ;
	
	private int alphaPixelsArray[]  ; 
	private int redPixelsArray[]    ;  
	private int greenPixelsArray[]  ;  
	private int bluePixelsArray[]   ;   
	
	private int redPixelsData[]    ;
	private int greenPixelsData[]  ;
	private int bluePixelsData[]   ;
	
	private ScrollPane levelsScrollPane ;
	private VBox levelsVbox ;
	private HBox channelsHbox ;
	private Label channelsLabel ;
	private ComboBox<String> channelsComboBox ;
	private VBox blackAndMidtonesAndWhiteSlidersContainer ;
	
	private HBox blacksHbox ;
	private Slider blacksSlider ;
	private TextField blacksTextfield ;
	
	private HBox midtonesHbox ;
	private Slider midtonesSlider ;
	private TextField midtonesTextfield ;
	
	private HBox whitesHbox ;
	private Slider whitesSlider ;
	private TextField whitesTextfield ;
	
	private NumberAxis xAxis ;
	private NumberAxis yAxis ;
	private LineChart lineChart ;
	private XYChart.Series< NumberAxis , NumberAxis > redSeries ;
	private XYChart.Series< NumberAxis , NumberAxis > greenSeries ;
	private XYChart.Series< NumberAxis , NumberAxis > blueSeries  ;
	
	private int redSliderBlackValue ;
	private int redSliderWhiteValue ;
	private int greenSliderBlackValue ;
	private int greenSliderWhiteValue ;
	private int blueSliderBlackValue ;
	private int blueSliderWhiteValue ;
	
	private TabPane tabPane ;
	private GridPane gridPane ;
	private ScrollPane scrollPane ;
	private VBox rootVbox ;
	private VBox masksVbox ;
	
	private HBox toggleButtonsHbox ;
	private ToggleGroup toggleGroup ;
	private ToggleButton levelsToggleButton ;
	private ToggleButton masksToggleButton ;
	
	public LevelsEffectNew( Image img ) {
		
		super( img );
		this.img = img ;
		this.canvas = this.getCanvas() ;
		this.blend = this.getBlend() ;
		this.colorAdjust = this.getColorAdjust() ;
		
		this.width = (int)this.canvas.getWidth() ;
		this.height = (int)this.canvas.getHeight() ;
		
		this.createEffectObjects();
	}
	
	public LevelsEffectNew( WritableImage wImg ) {
		
		super( wImg );
		this.wImg = wImg ;
		this.canvas = this.getCanvas() ;
		this.blend = this.getBlend() ;
		this.colorAdjust = this.getColorAdjust() ;
		
		this.width = (int)this.canvas.getWidth() ;
		this.height = (int)this.canvas.getHeight() ;
		
		this.createEffectObjects();
	}
	
	@Override
	public Canvas getCanvas() {
		
		return super.getCanvas() ;
	}
	
	@Override
	public Blend getBlend() {
		
		return super.getBlend() ;
	}
	
	@Override
	public ColorAdjust getColorAdjust() {
		
		return super.getColorAdjust() ;
	}
	
	@Override
	public ScrollPane getScrollPane() {
		
		return this.levelsScrollPane ;
	}
	
	public TabPane getTabPane() {
		
		return tabPane ;
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
		
		levelsToggleButton = new ToggleButton();
		levelsToggleButton.setText("Levels");
		levelsToggleButton.setToggleGroup( toggleGroup );
		levelsToggleButton.setSelected( true );
		toggleButtonsHbox.getChildren().add( levelsToggleButton );
		
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
    	
		channelsHbox = new HBox();
    	channelsHbox.setSpacing( 10 );
    	channelsHbox.setTranslateX( 17 );
		
    	channelsLabel = new Label();
		channelsLabel.setText( "Channel" );
		channelsLabel.setFont( Font.font( "Comic Sans MS" , FontWeight.BOLD , 15 ) );
		channelsHbox.getChildren().add( channelsLabel );				
		
		ObservableList<String> pixelType = FXCollections.observableArrayList( "RGB" , "Red" , "Green" , "Blue" ); 
		
		channelsComboBox = new ComboBox<String>();
		channelsComboBox.setItems( pixelType );
		channelsComboBox.setMinWidth( 125 );
		channelsHbox.getChildren().add( channelsComboBox );
    	
		blackAndMidtonesAndWhiteSlidersContainer = new VBox();
		blackAndMidtonesAndWhiteSlidersContainer.setSpacing( 5 )      ;
		blackAndMidtonesAndWhiteSlidersContainer.setTranslateX( 14 )  ;
		
		blacksHbox = new HBox();
		blacksHbox.setSpacing( 17 )                 ;
		blackAndMidtonesAndWhiteSlidersContainer.getChildren().add( blacksHbox ) ;
		
		blacksSlider = new Slider();
		blacksSlider.setMax( 255 )                  ;
		blacksSlider.setMin( 0 )                    ;
		blacksSlider.setValue( 0 )                  ;
		blacksHbox.getChildren().add( blacksSlider ) ;
		
		blacksTextfield = new TextField();
		blacksTextfield.setMaxWidth( 40 )              ;
		blacksTextfield.setText("0")                   ;
		blacksHbox.getChildren().add( blacksTextfield ) ;
		
		midtonesHbox = new HBox();
		midtonesHbox.setSpacing( 17 )                 ;
		blackAndMidtonesAndWhiteSlidersContainer.getChildren().add( midtonesHbox ) ;
		
		midtonesSlider = new Slider();
		midtonesSlider.setMax( 255 );
		midtonesSlider.setMin( 0 );
		midtonesSlider.setValue( 127 );
		midtonesHbox.getChildren().add( midtonesSlider );
		
		midtonesTextfield = new TextField();
		midtonesTextfield.setMaxWidth( 40 );
		midtonesTextfield.setText("128");
		midtonesHbox.getChildren().add( midtonesTextfield );
		
		whitesHbox = new HBox();
		whitesHbox.setSpacing( 17 );
		blackAndMidtonesAndWhiteSlidersContainer.getChildren().add( whitesHbox );
		
		whitesSlider = new Slider();
		whitesSlider.setMax( 255 );
		whitesSlider.setMin( 0 );
		whitesSlider.setValue( 255 );
		whitesHbox.getChildren().add( whitesSlider );
		
		whitesTextfield = new TextField();
		whitesTextfield.setMaxWidth( 40 );
		whitesTextfield.setText("255");
		whitesHbox.getChildren().add( whitesTextfield );
		
		addFunctionToBlackSlider( blacksSlider , midtonesSlider , whitesSlider , blacksTextfield )    ;
		addFunctionToMidtonesSlider( blacksSlider , midtonesSlider , whitesSlider , midtonesTextfield ) ;
		addFunctionToWhiteSlider( blacksSlider , midtonesSlider , whitesSlider , whitesTextfield )    ;
		
		putDataInArrays() ;
		
		rootVbox.getChildren().addAll( channelsHbox , lineChart , blackAndMidtonesAndWhiteSlidersContainer );
    }
    
    private void addFunctionToBlackSlider( Slider blackSlider , Slider midtonesSlider , Slider whiteSlider , TextField tfBlack ) {
    	
    	blackSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				if( newVal.intValue() < whiteSlider.getValue()-2 ) {
					
					if( colorPixelsArray == redPixelsArray ) {
						
						createRed0PixelsThread( newVal.intValue() , (int)whiteSlider.getValue() ) ;
					}
					else if( colorPixelsArray == greenPixelsArray ) {
						
						createGreen0PixelsThread( newVal.intValue() , (int)whiteSlider.getValue() ) ;
					}
					else if( colorPixelsArray == bluePixelsArray ) {
						
						createBlue0PixelsThread( newVal.intValue() , (int)whiteSlider.getValue() ) ;
					}
					else {
						
						createRgb0PixelsThread( newVal.intValue() , (int)whiteSlider.getValue() ) ;
					}
				    
				    midtonesSlider.setValue( Math.ceil( ((blackSlider.getValue() + whiteSlider.getValue())/2) ) );
				    tfBlack.setText( Integer.toString( newVal.intValue() ) );
				}
				else {
					
					midtonesSlider.setValue( Math.ceil( ((blackSlider.getValue() + whiteSlider.getValue())/2) ) );
					blackSlider.setValue( whiteSlider.getValue()-2 );
					tfBlack.setText( Integer.toString( (int)(whiteSlider.getValue()-2 )) );
				}
			}
		});
    }
    
    private void addFunctionToMidtonesSlider( Slider blackSlider , Slider midtonesSlider , Slider whiteSlider , TextField tfMidtones ) {
    	
    	midtonesSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				midtonesSlider.setValue( Math.ceil( ((blackSlider.getValue() + whiteSlider.getValue())/2) ) );
				tfMidtones.setText( Integer.toString( (int)(midtonesSlider.getValue()) ) );
			}
		});
    }
    
    private void addFunctionToWhiteSlider( Slider blackSlider , Slider midtonesSlider , Slider whiteSlider , TextField tfWhite ) {
    	
    	whiteSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
                if( newVal.intValue() > blackSlider.getValue()+2 ) {
					
                    if( colorPixelsArray == redPixelsArray ) {
						
						createRed255PixelsThread( newVal.intValue() , (int)blackSlider.getValue() ) ;
					}
					else if( colorPixelsArray == greenPixelsArray ) {
						
						createGreen255PixelsThread( newVal.intValue() , (int)blackSlider.getValue() ) ;
					}
					else if( colorPixelsArray == bluePixelsArray ) {
						
						createBlue255PixelsThread( newVal.intValue() , (int)blackSlider.getValue() ) ;
					}
					else {
						
						createRgb255PixelsThread( newVal.intValue() , (int)blackSlider.getValue() ) ;
					}
				    
				    midtonesSlider.setValue( Math.ceil( ((blackSlider.getValue() + whiteSlider.getValue())/2) ) );
				    tfWhite.setText( Integer.toString( newVal.intValue() ) );
				}
				else {
					
					midtonesSlider.setValue( Math.ceil( ((blackSlider.getValue() + whiteSlider.getValue())/2) ) );
					whiteSlider.setValue( blackSlider.getValue()+2 );
					tfWhite.setText( Integer.toString( (int)(blackSlider.getValue()+2 )) );
				}
			}
		});
    }
    
    public void putDataInArrays() {
    	
    	redSliderBlackValue = 0 ;
    	greenSliderBlackValue = 0 ;
    	blueSliderBlackValue = 0 ;
    	
    	redSliderWhiteValue = 255 ;
    	greenSliderWhiteValue = 255 ;
    	blueSliderWhiteValue = 255 ;
    	
		this.argbPixelsArray          = new int[ width*height ];
		
		this.argb255PixelsArray       = new int[ width*height ];
		this.arg255bPixelsArray       = new int[ width*height ];
		this.ar255gbPixelsArray       = new int[ width*height ];
		this.ar255g255b255PixelsArray = new int[ width*height ];
		
		this.argb0PixelsArray    = new int[ width*height ];
		this.arg0bPixelsArray    = new int[ width*height ];
		this.ar0gbPixelsArray    = new int[ width*height ];
		this.ar0g0b0PixelsArray  = new int[ width*height ];
		
		this.alphaPixelsArray    = new int[ width*height ];
		this.redPixelsArray      = new int[ width*height ];
		this.greenPixelsArray    = new int[ width*height ];
		this.bluePixelsArray     = new int[ width*height ];
		
		this.iterationsArrayA    = new int[ width*height ];
		
		this.redPixelsData       = new int[ 256 ] ;
		this.greenPixelsData     = new int[ 256 ] ;
		this.bluePixelsData      = new int[ 256 ] ;
    	
    	int i = 0 ;
    	final WritableImage wImg = canvas.snapshot( new SnapshotParameters() , null );
    	PixelReader pixelReader = wImg.getPixelReader() ;
    	
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
				int ARGBobtained = pixelReader.getArgb( x , y );
				
				int alpha = (0xff & (ARGBobtained >> 24)) ;
				int red   = (0xff & (ARGBobtained >> 16)) ;
				int green = (0xff & (ARGBobtained >> 8))  ;
				int blue  = (0xff & (ARGBobtained));	
				
				int red255   = (0xff<<16 & (-16777216>>8)) ;
				int green255 = (0xff<<8 & (-16777216>>16)) ;
				int blue255  = (0xff & (-16777216>>24))    ;
				
				int red0   = ~red255   ;
				int green0 = ~green255 ;
				int blue0  = ~blue255  ;
				
				argbPixelsArray[i]          = alpha | red  | green | blue ;
				
				ar255gbPixelsArray[i]       = ARGBobtained | red255   ;
				arg255bPixelsArray[i]       = ARGBobtained | green255 ;
				argb255PixelsArray[i]       = ARGBobtained | blue255  ;
				ar255g255b255PixelsArray[i] = ARGBobtained | red255 | green255 | blue255 ;
				
				ar0gbPixelsArray[i]     = ARGBobtained & red0   ;
				arg0bPixelsArray[i]     = ARGBobtained & green0 ;
				argb0PixelsArray[i]     = ARGBobtained & blue0  ;
				ar0g0b0PixelsArray[i]   = ARGBobtained & red255 & green255 & blue255 ; 
				
				alphaPixelsArray[i]     = alpha ;
				redPixelsArray[i]       = red   ;
				greenPixelsArray[i]     = green ;
				bluePixelsArray[i]      = blue  ;
				
				redPixelsData[red]++     ;
				greenPixelsData[green]++ ;
				bluePixelsData[blue]++   ;
				
				i++;
			}
		}
		
		createImageHistogram();
		addDataToSeries( redSeries , greenSeries , blueSeries );
		addFunctionToChannelsComboBox( channelsComboBox , lineChart , redSeries , greenSeries , blueSeries ) ;
		
		channelsComboBox.setValue( "RGB" );
    }
    
    private void createRed0PixelsThread( int blackSliderValue , int whiteSliderValue ) {
    	
    	IteratingRed0PixelsTask task = new IteratingRed0PixelsTask( blackSliderValue , whiteSliderValue , colorPixelsArray , colorBlackPixelsArray , iterationsArrayA , canvas );
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
	    
	    redSliderBlackValue = blackSliderValue ;
	    //redBlackSliderValueArrayList.set( canvasAndLevelsEffectHashMap.get( myCanvas ) , blackSliderValue ) ;
	    
	    try {
			iterationsArrayA = task.get() ;
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
    }
    
    private void createGreen0PixelsThread( int blackSliderValue , int whiteSliderValue ) {
    	
    	IteratingGreen0PixelsTask task = new IteratingGreen0PixelsTask( blackSliderValue , whiteSliderValue , colorPixelsArray , colorBlackPixelsArray , iterationsArrayA , canvas );
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
	    
	    greenSliderBlackValue = blackSliderValue ;
	    //greenBlackSliderValueArrayList.set( canvasAndLevelsEffectHashMap.get( myCanvas ) , blackSliderValue ) ;
	    
	    try {
			iterationsArrayA = task.get() ;
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
    }
    
    private void createBlue0PixelsThread( int blackSliderValue , int whiteSliderValue ) {
    	
    	IteratingBlue0PixelsTask task = new IteratingBlue0PixelsTask( blackSliderValue , whiteSliderValue , colorPixelsArray , colorBlackPixelsArray , iterationsArrayA , canvas );
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
	    
	    blueSliderBlackValue = blackSliderValue ;
	    //blueBlackSliderValueArrayList.set( canvasAndLevelsEffectHashMap.get( myCanvas ) , blackSliderValue ) ;
	    
	    try {
			iterationsArrayA = task.get() ;
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
    }
    
    private void createRgb0PixelsThread( int blackSliderValue , int whiteSliderValue ) {
    	
    	IteratingRgb0PixelsTask task = new IteratingRgb0PixelsTask( blackSliderValue , whiteSliderValue , colorPixelsArray , colorBlackPixelsArray , iterationsArrayA , canvas );
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
	    
	    try {
			iterationsArrayA = task.get() ;
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	    
	    //rgbBlackSliderValueArrayList.set( canvasAndLevelsEffectHashMap.get( myCanvas ) , blackSliderValue ) ;
    }
    
    private void createRed255PixelsThread( int whiteSliderValue , int blackSliderValue ) {
    	
    	IteratingRed255PixelsTask task = new IteratingRed255PixelsTask( whiteSliderValue , blackSliderValue , colorPixelsArray , colorWhitePixelsArray , iterationsArrayA , canvas );
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
	    
	    redSliderWhiteValue = whiteSliderValue ;
	    //redWhiteSliderValueArrayList.set( canvasAndLevelsEffectHashMap.get( myCanvas ) , whiteSliderValue ) ;
	    
	    try {
			iterationsArrayA = task.get() ;
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
    }
    
    private void createGreen255PixelsThread( int whiteSliderValue , int blackSliderValue ) {
    	
    	IteratingGreen255PixelsTask task = new IteratingGreen255PixelsTask( whiteSliderValue , blackSliderValue , colorPixelsArray , colorWhitePixelsArray , iterationsArrayA , canvas );
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
	    
	    greenSliderWhiteValue = whiteSliderValue ;
	    //greenWhiteSliderValueArrayList.set( canvasAndLevelsEffectHashMap.get( myCanvas ) , whiteSliderValue ) ;
	    
	    try {
			iterationsArrayA = task.get() ;
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
    }
 
    private void createBlue255PixelsThread( int whiteSliderValue , int blackSliderValue ) {
 	
 	    IteratingBlue255PixelsTask task = new IteratingBlue255PixelsTask( whiteSliderValue , blackSliderValue , colorPixelsArray , colorWhitePixelsArray , iterationsArrayA , canvas );
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
	    
	    blueSliderWhiteValue = whiteSliderValue ;
	    //blueWhiteSliderValueArrayList.set( canvasAndLevelsEffectHashMap.get( myCanvas ) , whiteSliderValue ) ;
	    
	    try {
			iterationsArrayA = task.get() ;
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
    }
 
    private void createRgb255PixelsThread( int whiteSliderValue , int blackSliderValue ) {
    	
    	IteratingRgb255PixelsTask task = new IteratingRgb255PixelsTask( whiteSliderValue , blackSliderValue , colorPixelsArray , colorWhitePixelsArray , iterationsArrayA , canvas );
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
	    
	    //rgbWhiteSliderValueArrayList.set( canvasAndLevelsEffectHashMap.get( myCanvas ) , whiteSliderValue ) ;
	    
	    try {
			iterationsArrayA = task.get() ;
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
    }
    
    private void createImageHistogram() {
    	
    	xAxis = new NumberAxis( 0 , 255 , 10 );
		xAxis.setTickLabelsVisible( false );
		xAxis.setTickMarkVisible( false );
		xAxis.setMinorTickVisible( false );
		xAxis.setAnimated( false );
		
		yAxis = new NumberAxis( 0 , 2000 , 10 );
		//yAxis.setAutoRanging( true );
		yAxis.setTickLabelsVisible( false );
		yAxis.setTickMarkVisible( false );
		yAxis.setMinorTickVisible( false );
		yAxis.setAnimated( false );
		
		lineChart = new LineChart( xAxis , yAxis );
		lineChart.setCreateSymbols( false );
		lineChart.setMaxSize( 225, 125 );
		lineChart.setLegendVisible( false );
		lineChart.setHorizontalGridLinesVisible( false );
		lineChart.setVerticalGridLinesVisible( false );
		lineChart.setAnimated( false );
		
		redSeries = new XYChart.Series<>() ;
		greenSeries = new XYChart.Series<>() ;
		blueSeries = new XYChart.Series<>() ;
    }
    
    private void addDataToSeries( XYChart.Series< NumberAxis , NumberAxis > redSeries , XYChart.Series< NumberAxis , NumberAxis > greenSeries 
    		, XYChart.Series< NumberAxis , NumberAxis > blueSeries ) {
    	
    	for( int value = 0 ; value <= 255 ; value++ ) {
			
			redSeries.getData().add( new XYChart.Data( value , redPixelsData[value] ) );
			greenSeries.getData().add( new XYChart.Data( value , greenPixelsData[value] ) );
			blueSeries.getData().add( new XYChart.Data( value , bluePixelsData[value] ) );
		}
    }
    
    private void addFunctionToChannelsComboBox( ComboBox<String> pixelColor , LineChart lineChart , XYChart.Series< NumberAxis , NumberAxis > redSeries
    		, XYChart.Series< NumberAxis , NumberAxis > greenSeries , XYChart.Series< NumberAxis , NumberAxis > blueSeries ) {
    	
		pixelColor.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<String>() {
			@Override
			public void changed( ObservableValue<? extends String> obv , String oldVal , String newVal ) {
				
				if( newVal == "Red" ) {
					
					lineChart.getData().clear();
					lineChart.getData().add( redSeries );
					
					colorPixelsArray = redPixelsArray ;
					colorBlackPixelsArray = ar0gbPixelsArray ;
					colorWhitePixelsArray = ar255gbPixelsArray ;
					
					blacksSlider.setValue( redSliderBlackValue );
					whitesSlider.setValue( redSliderWhiteValue );
				}
				else if( newVal == "Green") {
					
					lineChart.getData().clear();
					lineChart.getData().add( greenSeries );
					
					colorPixelsArray = greenPixelsArray ;
					colorBlackPixelsArray = arg0bPixelsArray ;
					colorWhitePixelsArray = arg255bPixelsArray ;
					
					blacksSlider.setValue( greenSliderBlackValue );
					whitesSlider.setValue( greenSliderWhiteValue );
				}
				else if( newVal == "Blue" ) {
					
					lineChart.getData().clear();
					lineChart.getData().add( blueSeries );
					
					colorPixelsArray = bluePixelsArray ;
					colorBlackPixelsArray = argb0PixelsArray ;
					colorWhitePixelsArray = argb255PixelsArray ;
					
					blacksSlider.setValue( blueSliderBlackValue );
					whitesSlider.setValue( blueSliderWhiteValue );
				}
				else {
					
					lineChart.getData().clear();
					lineChart.getData().addAll( redSeries , greenSeries , blueSeries );
					
					colorPixelsArray = argbPixelsArray ;
					colorBlackPixelsArray = ar0g0b0PixelsArray ;
					colorWhitePixelsArray = ar255g255b255PixelsArray ;
				}
				
			}
		});
    }
}



































/*levelsScrollPane = new ScrollPane();
levelsScrollPane.setMaxSize(250, 200);

levelsVbox = new VBox();
levelsVbox.setSpacing(5);
levelsVbox.setPadding( new Insets( 5 , 0 , 5 , 0 ) );
levelsVbox.setBackground( new Background( new BackgroundFill( Color.rgb( 196 , 196 , 196 ) , CornerRadii.EMPTY , Insets.EMPTY ) ) );
levelsScrollPane.setContent( levelsVbox );

channelsHbox = new HBox();
channelsHbox.setSpacing( 10 );
channelsHbox.setTranslateX( 17 );

channelsLabel = new Label();
channelsLabel.setText( "Channel" );
channelsLabel.setFont( Font.font( "Comic Sans MS" , FontWeight.BOLD , 15 ) );
channelsHbox.getChildren().add( channelsLabel );				

ObservableList<String> pixelType = FXCollections.observableArrayList( "RGB" , "Red" , "Green" , "Blue" ); 

channelsComboBox = new ComboBox<String>();
channelsComboBox.setItems( pixelType );
channelsComboBox.setMinWidth( 125 );
channelsHbox.getChildren().add( channelsComboBox );

blackAndMidtonesAndWhiteSlidersContainer = new VBox();
blackAndMidtonesAndWhiteSlidersContainer.setSpacing( 5 )      ;
blackAndMidtonesAndWhiteSlidersContainer.setTranslateX( 14 )  ;

blacksHbox = new HBox();
blacksHbox.setSpacing( 17 )                 ;
blackAndMidtonesAndWhiteSlidersContainer.getChildren().add( blacksHbox ) ;

blacksSlider = new Slider();
blacksSlider.setMax( 255 )                  ;
blacksSlider.setMin( 0 )                    ;
blacksSlider.setValue( 0 )                  ;
blacksHbox.getChildren().add( blacksSlider ) ;

blacksTextfield = new TextField();
blacksTextfield.setMaxWidth( 40 )              ;
blacksTextfield.setText("0")                   ;
blacksHbox.getChildren().add( blacksTextfield ) ;

midtonesHbox = new HBox();
midtonesHbox.setSpacing( 17 )                 ;
blackAndMidtonesAndWhiteSlidersContainer.getChildren().add( midtonesHbox ) ;

midtonesSlider = new Slider();
midtonesSlider.setMax( 255 );
midtonesSlider.setMin( 0 );
midtonesSlider.setValue( 127 );
midtonesHbox.getChildren().add( midtonesSlider );

midtonesTextfield = new TextField();
midtonesTextfield.setMaxWidth( 40 );
midtonesTextfield.setText("128");
midtonesHbox.getChildren().add( midtonesTextfield );

whitesHbox = new HBox();
whitesHbox.setSpacing( 17 );
blackAndMidtonesAndWhiteSlidersContainer.getChildren().add( whitesHbox );

whitesSlider = new Slider();
whitesSlider.setMax( 255 );
whitesSlider.setMin( 0 );
whitesSlider.setValue( 255 );
whitesHbox.getChildren().add( whitesSlider );

whitesTextfield = new TextField();
whitesTextfield.setMaxWidth( 40 );
whitesTextfield.setText("255");
whitesHbox.getChildren().add( whitesTextfield );

addFunctionToBlackSlider( blacksSlider , midtonesSlider , whitesSlider , blacksTextfield )    ;
addFunctionToMidtonesSlider( blacksSlider , midtonesSlider , whitesSlider , midtonesTextfield ) ;
addFunctionToWhiteSlider( blacksSlider , midtonesSlider , whitesSlider , whitesTextfield )    ;

putDataInArrays() ;

levelsVbox.getChildren().addAll( channelsHbox , lineChart , blackAndMidtonesAndWhiteSlidersContainer );*/