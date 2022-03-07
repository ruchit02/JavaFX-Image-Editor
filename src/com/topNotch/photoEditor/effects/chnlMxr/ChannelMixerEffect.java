package com.topNotch.photoEditor.effects.chnlMxr;

import com.topNotch.photoEditor.main.MyEffects;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
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
import javafx.scene.effect.Blend;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ChannelMixerEffect extends MyEffects {
	
	private Canvas canvas ;
	private int width ;
	private int height ;
	private Blend blend ;
	private ColorAdjust colorAdjust ;
	private Image img ;
	private WritableImage wImg ;
	
	private TabPane tabPane ;
	private GridPane gridPane ;
	private ScrollPane scrollPane ;
	
	private HBox toggleButtonsHbox ;
	private ToggleGroup toggleGroup ;
	private ToggleButton channelMixerToggleButton ;
	private ToggleButton masksToggleButton ;
	
	private VBox rootVbox ;
	private VBox masksVbox ;
	
	private HBox redHbox ;
	private HBox greenHbox ;
	private HBox blueHbox ;
	
	private Slider redSlider ;
	private Slider greenSlider ;
	private Slider blueSlider ;
	
	private TextField redTextfield ;
	private TextField greenTextfield ;
	private TextField blueTextfield ;
	
	private Label redLabel ;
	private Label greenLabel ;
	private Label blueLabel ;
	
	private HBox outputChannelHbox ;
	private Label outputChannelLabel ;
	private ComboBox<String> outputChannelComboBox ;
	
	private HBox totalHbox ;
	private Label totalLabel ;
	private TextField totalTextfield ;
	
	private int actualRed[] ;
	private int actualGreen[] ;
	private int actualBlue[] ;
	
	public ChannelMixerEffect( Image img ) {
		
		super( img );
		
		this.img = img ;
		this.canvas = this.getCanvas() ;
		this.width = (int)this.canvas.getWidth() ;
		this.height = (int)this.canvas.getHeight() ;
		this.blend = this.getBlend() ;
		this.colorAdjust = this.getColorAdjust() ;
		
		this.actualRed = new int[ width*height ];
		this.actualGreen = new int[ width*height ];
		this.actualBlue = new int[ width*height ];
		
		this.createEffectObjects();
	}
	
	public ChannelMixerEffect( WritableImage wImg ) {
		
		super( wImg );
		
		this.wImg = wImg ;
		this.canvas = this.getCanvas() ;
		this.width = (int)this.canvas.getWidth() ;
		this.height = (int)this.canvas.getHeight() ;
		this.blend = this.getBlend() ;
		this.colorAdjust = this.getColorAdjust() ;
		
		this.actualRed = new int[ width*height ];
		this.actualGreen = new int[ width*height ];
		this.actualBlue = new int[ width*height ];
		
		this.createEffectObjects();
	}
	
	@Override
	public Blend getBlend() {
		 
		return super.getBlend();
	}
	
	@Override
	public Canvas getCanvas() {
		
		return super.getCanvas();
	}
	
	@Override
	public ColorAdjust getColorAdjust() {
		
		return super.getColorAdjust() ;
	}
	
	@Override
	public ScrollPane getScrollPane() {
		
		return scrollPane ;
	}
	
	public TabPane getTabPane() {
		
		return this.tabPane ;
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
		
		channelMixerToggleButton = new ToggleButton();
		channelMixerToggleButton.setText("Channel Mixer");
		channelMixerToggleButton.setToggleGroup( toggleGroup );
		channelMixerToggleButton.setSelected( true );
		toggleButtonsHbox.getChildren().add( channelMixerToggleButton );
		
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
		gridPane.getChildren().add( scrollPane );
		
		rootVbox = new VBox();
		//rootVbox.setMinWidth( 230 );
		rootVbox.setSpacing( 5 );
		rootVbox.setPadding( new Insets( 5 , 0 , 5 , 0 ) );
		rootVbox.setMaxWidth( 235 );
		scrollPane.setContent( rootVbox );
		
		outputChannelHbox = new HBox();
		outputChannelHbox.setTranslateX( 5 );
		outputChannelHbox.setSpacing( 15 );
		rootVbox.getChildren().add( outputChannelHbox );
		
		outputChannelLabel = new Label();
		outputChannelLabel.setText("Output Channel:");
		outputChannelHbox.getChildren().add( outputChannelLabel );
		
		ObservableList<String> channels = FXCollections.observableArrayList( "Red" , "Green" , "Blue" );
		
		outputChannelComboBox = new ComboBox<String>();
		outputChannelComboBox.setItems( channels );
		outputChannelComboBox.setValue( "Red" );
		outputChannelHbox.getChildren().add( outputChannelComboBox );
		
		outputChannelComboBox.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<String>() {
			@Override
			public void changed( ObservableValue<? extends String> obv , String oldVal , String newVal ) {
				
				if( newVal == "Red" ) {
					
				}
				else if( newVal == "Green" ) {
					
				}
				else {
					
				}
			}
		});
		
		redHbox = new HBox();
		redHbox.setSpacing( 150 );
		redHbox.setTranslateX( 5 );
		rootVbox.getChildren().add( redHbox );
		
		redLabel = new Label();
		redLabel.setText("Red");
		redHbox.getChildren().add( redLabel );
		
		redTextfield = new TextField();
		redTextfield.setMaxWidth( 50 );
		redTextfield.setText("100");
		redHbox.getChildren().add( redTextfield );
		
		redSlider = new Slider();
		redSlider.setMin( -200 );
		redSlider.setMax( 200 );
		redSlider.setValue( 100 );
		//redSlider.setMaxWidth( 236 );
		rootVbox.getChildren().add( redSlider );
		
		addFunctionalityToRedSlider( redSlider , redTextfield );
		
		greenHbox = new HBox();
		greenHbox.setSpacing( 138 );
		greenHbox.setTranslateX( 5 );
		rootVbox.getChildren().add( greenHbox );
		
		greenLabel = new Label();
		greenLabel.setText("Green");
		greenHbox.getChildren().add( greenLabel );
		
		greenTextfield = new TextField();
		greenTextfield.setMaxWidth( 50 );
		greenTextfield.setText("0");
		greenHbox.getChildren().add( greenTextfield );
		
		greenSlider = new Slider();
		greenSlider.setMin( -200 );
		greenSlider.setMax( 200 );
		greenSlider.setValue( 0 );
		//greenSlider.setMaxWidth( 236 );
		rootVbox.getChildren().add( greenSlider );
		
		addFunctionalityToGreenSlider( greenSlider , greenTextfield );
		
		blueHbox = new HBox();
		blueHbox.setSpacing( 147 );
		blueHbox.setTranslateX( 5 );
		rootVbox.getChildren().add( blueHbox );
		
		blueLabel = new Label();
		blueLabel.setText("Blue");
		blueHbox.getChildren().add( blueLabel );
		
		blueTextfield = new TextField();
		blueTextfield.setMaxWidth( 50 );
		blueTextfield.setText("0");
		blueHbox.getChildren().add( blueTextfield );
		
		blueSlider = new Slider();
		blueSlider.setMin( -200 );
		blueSlider.setMax( 200 );
		blueSlider.setValue( 0 );
		//blueSlider.setMaxWidth( 236 );
		rootVbox.getChildren().add( blueSlider );
		
		addFunctionalityToBlueSlider( blueSlider , blueTextfield );
		
		totalHbox = new HBox();
		totalHbox.setTranslateX( 5 );
		totalHbox.setSpacing( 145 );
		rootVbox.getChildren().add( totalHbox );
		
		totalLabel = new Label();
		totalLabel.setText("Total:");
		totalHbox.getChildren().add( totalLabel );
		
		totalTextfield = new TextField();
		totalTextfield.setBackground(new Background( new BackgroundFill( Color.TRANSPARENT , new CornerRadii(0.0) , new Insets( 0.0 , 0.0 , 0.0 , 0.0) ) ));
		totalTextfield.setMaxWidth(50);
		totalTextfield.setText("100");
		totalHbox.getChildren().add( totalTextfield );
		
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
    			
    			i++ ;
    		}
    	}
	}
	
	private void addFunctionalityToRedSlider( Slider redSlider , TextField redTextfield ) {
		
		redSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				int value = newVal.intValue() ;
				
                if( outputChannelComboBox.getSelectionModel().getSelectedItem() == "Red" ) {
					
                	redChannelRedPixelTask( value );
				}
				else if( outputChannelComboBox.getSelectionModel().getSelectedItem() == "Green" ) {
					
					greenChannelRedPixelTask( value );
				}
				else {
					
					blueChannelRedPixelTask( value );
				}
				
				redTextfield.setText( Integer.toString( value ) );
				totalTextfield.setText( Double.toString( (redSlider.getValue() + greenSlider.getValue() + blueSlider.getValue()) ) );
			}
		});
	}
	
    private void redChannelRedPixelTask( int value ) {
    	
    	ChannelMixerRedPixelManipulatorForRedChannelTask task = new ChannelMixerRedPixelManipulatorForRedChannelTask( canvas , value , actualRed , actualGreen , actualBlue ) ;
    	Thread th = new Thread(task) ;
    	th.setDaemon( true );
    	th.start();
	}
	
	private void greenChannelRedPixelTask( int value ) {
		
		ChannelMixerRedPixelManipulatorForGreenChannelTask task = new ChannelMixerRedPixelManipulatorForGreenChannelTask( canvas , value , actualRed , actualGreen , actualBlue ) ;
    	Thread th = new Thread(task) ;
    	th.setDaemon( true );
    	th.start();
	}
	
	private void blueChannelRedPixelTask( int value ) {
		
		ChannelMixerRedPixelManipulatorForBlueChannelTask task = new ChannelMixerRedPixelManipulatorForBlueChannelTask( canvas , value , actualRed , actualGreen , actualBlue ) ;
    	Thread th = new Thread(task) ;
    	th.setDaemon( true );
    	th.start();
	}
	
	private void addFunctionalityToGreenSlider( Slider greenSlider , TextField greenTextfield ) {
		
		greenSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				int value = newVal.intValue() ;
				
                if( outputChannelComboBox.getSelectionModel().getSelectedItem() == "Red" ) {
					
                	redChannelGreenPixelTask( value );
				}
				else if( outputChannelComboBox.getSelectionModel().getSelectedItem() == "Green" ) {
					
					greenChannelGreenPixelTask( value );
				}
				else {
					
					blueChannelGreenPixelTask( value );
				}
				
				greenTextfield.setText( Integer.toString( value ) );
				totalTextfield.setText( Double.toString( (redSlider.getValue() + greenSlider.getValue() + blueSlider.getValue()) ) );
			}
		});
	}
	
    private void redChannelGreenPixelTask( int value ) {
		
    	ChannelMixerRedPixelManipulatorForGreenChannelTask task = new ChannelMixerRedPixelManipulatorForGreenChannelTask( canvas , value , actualRed , actualGreen , actualBlue ) ;
    	Thread th = new Thread(task) ;
    	th.setDaemon( true );
    	th.start();
	}
	
    private void greenChannelGreenPixelTask( int value ) {
		
    	ChannelMixerGreenPixelManipulatorForGreenChannelTask task = new ChannelMixerGreenPixelManipulatorForGreenChannelTask( canvas , value , actualRed , actualGreen , actualBlue ) ;
    	Thread th = new Thread(task) ;
    	th.setDaemon( true );
    	th.start();
	}
    
    private void blueChannelGreenPixelTask( int value ) {
		
    	ChannelMixerGreenPixelManipulatorForBlueChannelTask task = new ChannelMixerGreenPixelManipulatorForBlueChannelTask( canvas , value , actualRed , actualGreen , actualBlue ) ;
    	Thread th = new Thread(task) ;
    	th.setDaemon( true );
    	th.start();
	}
	
	private void addFunctionalityToBlueSlider( Slider blueSlider , TextField blueTextfield ) {
		
		blueSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				int value = newVal.intValue() ;
				
				if( outputChannelComboBox.getSelectionModel().getSelectedItem() == "Red" ) {
					
					redChannelBluePixelTask( value );
				}
				else if( outputChannelComboBox.getSelectionModel().getSelectedItem() == "Green" ) {
					
					greenChannelBluePixelTask( value );
				}
				else {
					
					blueChannelBluePixelTask( value );
				}
				
				blueTextfield.setText( Integer.toString( value ) );
				totalTextfield.setText( Double.toString( (redSlider.getValue() + greenSlider.getValue() + blueSlider.getValue()) ) );
			}
		});
	}
    
    private void redChannelBluePixelTask( int value ) {
		
    	ChannelMixerBluePixelManipulatorForRedChannelTask task = new ChannelMixerBluePixelManipulatorForRedChannelTask( canvas , value , actualRed , actualGreen , actualBlue ) ;
    	Thread th = new Thread(task) ;
    	th.setDaemon( true );
    	th.start();
	}
	
    private void greenChannelBluePixelTask( int value ) {
		
    	ChannelMixerBluePixelManipulatorForGreenChannelTask task = new ChannelMixerBluePixelManipulatorForGreenChannelTask( canvas , value , actualRed , actualGreen , actualBlue ) ;
    	Thread th = new Thread(task) ;
    	th.setDaemon( true );
    	th.start();
	}
    
    private void blueChannelBluePixelTask( int value ) {
    	
    	ChannelMixerBluePixelManipulatorForBlueChannelTask task = new ChannelMixerBluePixelManipulatorForBlueChannelTask( canvas , value , actualRed , actualGreen , actualBlue ) ;
    	Thread th = new Thread(task) ;
    	th.setDaemon( true );
    	th.start();
	}
}
