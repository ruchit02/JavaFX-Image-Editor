package com.topNotch.photoEditor.effects.blackAndWhite;

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

public class BlackAndWhiteEffect extends MyEffects {
	
	private Canvas canvas ;
	private int width ;
	private int height ;
	private Blend blend ;
	private ColorAdjust colorAdjust ;
	
	private TabPane tabPane ;
	private ScrollPane scrollPane ;
	private GridPane gridPane ;
	
	private HBox toggleButtonsHbox ;
	private ToggleGroup toggleGroup ;
	private ToggleButton blackAndWhiteToggleButton ;
	private ToggleButton masksToggleButton ;
	
	private VBox rootVbox ;
	private VBox masksVbox ;
	
	private HBox redsHbox ;
	private Label redsLabel ;
	private TextField redsTextfield ;
	private Slider redsSlider ;
	
	private HBox yellowsHbox ;
	private Label yellowsLabel ;
	private TextField yellowsTextfield ;
	private Slider yellowsSlider ;
	
	private HBox greensHbox ;
	private Label greensLabel ;
	private TextField greensTextfield ;
	private Slider greensSlider ;
	
	private HBox cyansHbox ;
	private Label cyansLabel ;
	private TextField cyansTextfield ;
	private Slider cyansSlider ;
	
	private HBox bluesHbox ;
	private Label bluesLabel ;
	private TextField bluesTextfield ;
	private Slider bluesSlider ;
	
	private HBox magentasHbox ;
	private Label magentasLabel ;
	private TextField magentasTextfield ;
	private Slider magentasSlider ;
	
	private int actualRed[];
	private int actualGreen[];
	private int actualBlue[];
	private int rgbAverage[];
	
	
	public BlackAndWhiteEffect( Image img ) {
		
		super( img );
		
		this.canvas = this.getCanvas() ;
		this.width = (int)this.canvas.getWidth();
		this.height = (int)this.canvas.getHeight() ;
		this.blend = this.getBlend() ;
		this.colorAdjust = this.getColorAdjust() ;
		
		this.actualRed = new int[ width*height ];
		this.actualGreen = new int[ width*height ];
		this.actualBlue = new int[ width*height ];
		this.rgbAverage = new int[ width*height ];
		
		this.createEffectObjects();
	}
	
	public BlackAndWhiteEffect( WritableImage wImg ) {
		
		super( wImg );
		
		this.canvas = this.getCanvas() ;
		this.width = (int)this.canvas.getWidth();
		this.height = (int)this.canvas.getHeight() ;
		this.blend = this.getBlend() ;
		this.colorAdjust = this.getColorAdjust() ;
		
		this.createEffectObjects();
	}
	
	public Blend getBlend() {
		
		return super.getBlend();
	}
	
	public Canvas getCanvas() {
		
		return super.getCanvas() ;
	}
	
	public ColorAdjust getColorAdjust() {
		
		return super.getColorAdjust() ;
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
		
		blackAndWhiteToggleButton = new ToggleButton();
		blackAndWhiteToggleButton.setText("Black & White");
		blackAndWhiteToggleButton.setToggleGroup( toggleGroup );
		blackAndWhiteToggleButton.setSelected( true );
		toggleButtonsHbox.getChildren().add( blackAndWhiteToggleButton );
		
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
		
		redsHbox = new HBox();
		redsHbox.setSpacing( 140 );
		rootVbox.getChildren().add( redsHbox );
		
		redsLabel = new Label();
		redsLabel.setText("Reds");
		redsHbox.getChildren().add( redsLabel );
		
		redsTextfield = new TextField();
		redsTextfield.setMaxWidth( 50 );
		redsTextfield.setText("0");
		redsHbox.getChildren().add( redsTextfield );
		
		redsSlider = new Slider();
		redsSlider.setMin( -200 );
		redsSlider.setMax( 300 );
		redsSlider.setValue( 0 );
		rootVbox.getChildren().add( redsSlider );
		
		addFunctionalityToRedsSlider( redsSlider , redsTextfield );
		
		yellowsHbox = new HBox();
		yellowsHbox.setSpacing( 125 );
		rootVbox.getChildren().add( yellowsHbox );
		
		yellowsLabel = new Label();
		yellowsLabel.setText("Yellows");
		yellowsHbox.getChildren().add( yellowsLabel );
		
		yellowsTextfield = new TextField();
		yellowsTextfield.setMaxWidth( 50 );
		yellowsTextfield.setText("0");
		yellowsHbox.getChildren().add( yellowsTextfield );
		
		yellowsSlider = new Slider();
		yellowsSlider.setMin( -200 );
		yellowsSlider.setMax( 300 );
		yellowsSlider.setValue( 0 );
		rootVbox.getChildren().add( yellowsSlider );
		
		addFunctionalityToYellowsSlider( yellowsSlider , yellowsTextfield );
		
		greensHbox = new HBox();
		greensHbox.setSpacing( 127 );
		rootVbox.getChildren().add( greensHbox );
		
		greensLabel = new Label();
		greensLabel.setText("Greens");
		greensHbox.getChildren().add( greensLabel );
		
		greensTextfield = new TextField();
		greensTextfield.setMaxWidth( 50 );
		greensTextfield.setText("0");
		greensHbox.getChildren().add( greensTextfield );
		
		greensSlider = new Slider();
		greensSlider.setMin( -200 );
		greensSlider.setMax( 300 );
		greensSlider.setValue( 0 );
		rootVbox.getChildren().add( greensSlider );
		
		addFunctionalityToGreensSlider( greensSlider , greensTextfield );
		
		cyansHbox = new HBox();
		cyansHbox.setSpacing( 129 );
		rootVbox.getChildren().add( cyansHbox );
		
		cyansLabel = new Label();
		cyansLabel.setText("Cyans");
		cyansHbox.getChildren().add( cyansLabel );
		
		cyansTextfield = new TextField();
		cyansTextfield.setMaxWidth( 50 );
		cyansTextfield.setText("0");
		cyansHbox.getChildren().add( cyansTextfield ) ;	
		
		cyansSlider = new Slider();
		cyansSlider.setMin( -200 );
		cyansSlider.setMax( 300 );
		cyansSlider.setValue( 0 );
		rootVbox.getChildren().add( cyansSlider );
		
		addFunctionalityToCyansSlider( cyansSlider , cyansTextfield );
		
		bluesHbox = new HBox();
		bluesHbox.setSpacing( 135 );
		rootVbox.getChildren().add( bluesHbox );
		
		bluesLabel = new Label();
		bluesLabel.setText("Blues");
		bluesHbox.getChildren().add( bluesLabel );
		
		bluesTextfield = new TextField();
		bluesTextfield.setMaxWidth( 50 );
		bluesTextfield.setText("0");
		bluesHbox.getChildren().add( bluesTextfield );
		
		bluesSlider = new Slider();
		bluesSlider.setMin( -200 );
		bluesSlider.setMax( 300 );
		bluesSlider.setValue( 0 );
		rootVbox.getChildren().add( bluesSlider );
		
		addFunctionalityToBluesSlider( bluesSlider , bluesTextfield );
		
		magentasHbox = new HBox();
		magentasHbox.setSpacing( 120 );
		rootVbox.getChildren().add( magentasHbox );
		
		magentasLabel = new Label();
		magentasLabel.setText("Magentas");
		magentasHbox.getChildren().add( magentasLabel );
		
		magentasTextfield = new TextField();
		magentasTextfield.setMaxWidth( 50 );
		magentasTextfield.setText("0");
		magentasHbox.getChildren().add( magentasTextfield );
		
		magentasSlider = new Slider();
		magentasSlider.setMin( -200 );
		magentasSlider.setMax( 300 );
		magentasSlider.setValue( 0 );
		rootVbox.getChildren().add( magentasSlider );
		
		addFunctionalityToMagentasSlider( magentasSlider , magentasTextfield );
		
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
				
				rgbAverage[i]             = ((actualRed[i] + actualGreen[i] + actualBlue[i])/3) ;
				
				i++ ;
			}
		}
	}
	
	private void addFunctionalityToRedsSlider( Slider redsSlider , TextField redsTextfield ) {
		
		redsSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				int value = newVal.intValue();
				
				blackAndWhiteRedsTask( value );
				
				redsTextfield.setText( Integer.toString( value ) );
			}
		});
	}
	
	private void blackAndWhiteRedsTask( int value ) {
		
		BlackAndWhiteRedsManipulatorTask task = new BlackAndWhiteRedsManipulatorTask( canvas , value 
				, actualRed , actualGreen , actualBlue , rgbAverage );
		Thread th = new Thread(task) ;
		th.setDaemon( true );
		th.start();
	}
	
	private void addFunctionalityToYellowsSlider( Slider yellowsSlider, TextField yellowsTextfield ) {
		
		yellowsSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				int value = newVal.intValue();
				
				blackAndWhiteYellowsTask( value );
				
				yellowsTextfield.setText( Integer.toString( value ) );
			}
		});
	}
	
    private void blackAndWhiteYellowsTask( int value ) {
		
		BlackAndWhiteYellowsManipulatorTask task = new BlackAndWhiteYellowsManipulatorTask( canvas , value 
				, actualRed , actualGreen , actualBlue );
		Thread th = new Thread(task) ;
		th.setDaemon( true );
		th.start();
	}
	
	private void addFunctionalityToGreensSlider( Slider greensSlider, TextField greensTextfield ) {
		
		greensSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				int value = newVal.intValue();
				
				blackAndWhiteGreensTask( value );
				
				greensTextfield.setText( Integer.toString( value ) );
			}
		});
	}
	
    private void blackAndWhiteGreensTask( int value ) {
		
		BlackAndWhiteGreensManipulatorTask task = new BlackAndWhiteGreensManipulatorTask( canvas , value 
				, actualRed , actualGreen , actualBlue , rgbAverage );
		Thread th = new Thread(task) ;
		th.setDaemon( true );
		th.start();
    }
    
	private void addFunctionalityToCyansSlider( Slider cyansSlider, TextField cyansTextfield ) {
		
		cyansSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				int value = newVal.intValue();
				
				blackAndWhiteCyansTask( value ) ;
				
				cyansTextfield.setText( Integer.toString( value ) );
			}
		});
	}
	
    private void blackAndWhiteCyansTask( int value ) {
		
		BlackAndWhiteCyansManipulatorTask task = new BlackAndWhiteCyansManipulatorTask( canvas , value 
				, actualRed , actualGreen , actualBlue );
		Thread th = new Thread(task) ;
		th.setDaemon( true );
		th.start();
    }
	
	private void addFunctionalityToBluesSlider( Slider bluesSlider, TextField bluesTextfield ) {
		
		bluesSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				int value = newVal.intValue();
				
				blackAndWhiteBluesTask( value );
				
				bluesTextfield.setText( Integer.toString( value ) );
			}
		});
	}
	
    private void blackAndWhiteBluesTask( int value ) {
		
		BlackAndWhiteBluesManipulatorTask task = new BlackAndWhiteBluesManipulatorTask( canvas , value 
				, actualRed , actualGreen , actualBlue , rgbAverage );
		Thread th = new Thread(task) ;
		th.setDaemon( true );
		th.start();
    }
	
	private void addFunctionalityToMagentasSlider( Slider magentasSlider, TextField magentasTextfield ) {
		
		magentasSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				int value = newVal.intValue();
				
				blackAndWhiteMagentasTask( value );
				
				magentasTextfield.setText( Integer.toString( value ) );
			}
		});
	}
	
    private void blackAndWhiteMagentasTask( int value ) {
		
		BlackAndWhiteMagentasManipulatorTask task = new BlackAndWhiteMagentasManipulatorTask( canvas , value 
				, actualRed , actualGreen , actualBlue );
		Thread th = new Thread(task) ;
		th.setDaemon( true );
		th.start();
    }
}