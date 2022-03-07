package com.topNotch.photoEditor.effects.clrBalance;

import com.topNotch.photoEditor.main.MyEffects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ColorBalanceEffect extends MyEffects{
	
	private int width ;
	private int height ;
	private Image img ;
	private WritableImage wImg ;
	private Blend blend ;
	private ColorAdjust colorAdjust ;
	private Canvas canvas ;
	
	private TabPane tabPane ;
	private GridPane gridPane ;
	private ScrollPane scrollPane;
	
	private VBox rootVbox ;
	private VBox masksVbox ;
	
	private HBox toggleButtonsHbox ;
	private ToggleGroup toggleGroup ;
	private ToggleButton colorBalanceToggleButton ;
	private ToggleButton masksToggleButton ;
	
	private HBox colorToneHbox ;
	private Label colorToneLabel ;
	private ComboBox<String> colorToneComboBox ;
	
	private HBox cyanRedHbox ;
	private HBox cyanRedSliderAndTextfieldHbox ;
	private Label cyanLabel ;
	private Label redLabel ;
	private Slider cyanRedSlider ;
	private TextField cyanRedTextfield ;
	
	private HBox magentaGreenHbox ;
	private HBox magentaGreenSliderAndTextfieldHbox ;
	private Label magentaLabel ;
	private Label greenLabel ;
	private Slider magentaGreenSlider ;
	private TextField magentaGreenTextfield ;
	
	private HBox yellowBlueHbox ;
	private HBox yellowBlueSliderAndTextfieldHbox ;
	private Label yellowLabel ;
	private Label blueLabel ;
	private Slider yellowBlueSlider ;
	private TextField yellowBlueTextfield ;
	
	private int actualRed[]  ; 
	private int actualGreen[] ;
	private int actualBlue[]  ;
	
	private int counterRed[];
	private int counterGreen[];
	private int counterBlue[];
	
	private double counterRedInFraction[];
	private double counterGreenInFraction[];
	private double counterBlueInFraction[];
	
	public ColorBalanceEffect( Image img ) {
		
		super( img );
		this.img = img ;
		this.canvas = this.getCanvas() ;
		this.width = (int)this.canvas.getWidth() ;
		this.height = (int)this.canvas.getHeight() ;
		this.blend = this.getBlend() ;
		this.colorAdjust = this.getColorAdjust() ;
		
		this.createEffectObjects();
	}
	
	public ColorBalanceEffect( WritableImage wImg ) {
		
		super( wImg );
		this.wImg = wImg ;
		this.canvas = this.getCanvas() ;
		this.width = (int)this.canvas.getWidth() ;
		this.height = (int)this.canvas.getHeight() ;
		this.blend = this.getBlend() ;
		this.colorAdjust = this.getColorAdjust() ;
		
		this.createEffectObjects();
	}
	
	public Blend getBlend() {
		
		return super.getBlend() ;
	}
	
	public Canvas getCanvas() {
		
		return super.getCanvas() ;
	}
	
	public ColorAdjust getColorAdjust() {
		
		return super.getColorAdjust() ;
	}
	
	@Override
	public ScrollPane getScrollPane() {
		
		return scrollPane ;
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
		
		colorBalanceToggleButton = new ToggleButton();
		colorBalanceToggleButton.setText("Color Balance");
		colorBalanceToggleButton.setToggleGroup( toggleGroup );
		colorBalanceToggleButton.setSelected( true );
		toggleButtonsHbox.getChildren().add( colorBalanceToggleButton );
		
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
		rootVbox.setMaxWidth(235);
		scrollPane.setContent( rootVbox );
		
		colorToneHbox = new HBox();
		colorToneHbox.setSpacing( 30 );
		colorToneHbox.setPrefWidth( Double.MAX_VALUE );
		rootVbox.getChildren().add( colorToneHbox );
		
		colorToneLabel = new Label();
		colorToneLabel.setText("Tone:");
		colorToneLabel.setFont( Font.font( "Comic Sans MS" , FontWeight.BOLD , 15 ) );
		colorToneHbox.getChildren().add( colorToneLabel );
		
		ObservableList<String> pixelTypes = FXCollections.observableArrayList( "Shadows" , "Midtones" , "Highlights" );
		
		colorToneComboBox = new ComboBox<String>() ;
		colorToneComboBox.setItems( pixelTypes );
		colorToneComboBox.setMinWidth( 125 );
		colorToneComboBox.setValue("Midtones");
		colorToneHbox.getChildren().add( colorToneComboBox );
		
		cyanRedHbox = new HBox();
		cyanRedHbox.setSpacing( 80 );
		cyanRedHbox.setPadding( new Insets( 0 , 0 , 0 , 5) );
		rootVbox.getChildren().add( cyanRedHbox );
		
		cyanLabel = new Label();
		cyanLabel.setText("Cyan");
		cyanRedHbox.getChildren().add( cyanLabel );
		
		redLabel = new Label();
		redLabel.setText("Red");
		cyanRedHbox.getChildren().add( redLabel );
		
		cyanRedSliderAndTextfieldHbox = new HBox();
		cyanRedSliderAndTextfieldHbox.setSpacing( 17 );
		cyanRedSliderAndTextfieldHbox.setAlignment( Pos.BOTTOM_CENTER );
		cyanRedSliderAndTextfieldHbox.setPrefWidth( Double.MAX_VALUE );
		rootVbox.getChildren().add( cyanRedSliderAndTextfieldHbox );
		
		cyanRedSlider = new Slider();
		cyanRedSlider.setMin( -100 );
		cyanRedSlider.setMax( 100 );
		cyanRedSlider.setValue( 0 );
		cyanRedSliderAndTextfieldHbox.getChildren().add( cyanRedSlider );
		
		cyanRedTextfield = new TextField();
		cyanRedTextfield.setText("0");
		cyanRedTextfield.setMaxWidth( 40 );
		cyanRedSliderAndTextfieldHbox.getChildren().add( cyanRedTextfield );
		
		addFunctionalityToCyanRedSlider( cyanRedSlider , cyanRedTextfield );
		
		magentaGreenHbox = new HBox();
		magentaGreenHbox.setSpacing( 50 );
		magentaGreenHbox.setPadding( new Insets( 0 , 0 , 0 , 5) );
		rootVbox.getChildren().add( magentaGreenHbox );
		
		magentaLabel = new Label();
		magentaLabel.setText("Magenta");
		magentaGreenHbox.getChildren().add( magentaLabel );
		
		greenLabel = new Label();
		greenLabel.setText("Green");
		magentaGreenHbox.getChildren().add( greenLabel );
		
        magentaGreenSliderAndTextfieldHbox = new HBox();
        magentaGreenSliderAndTextfieldHbox.setSpacing( 17 );
        magentaGreenSliderAndTextfieldHbox.setAlignment( Pos.BOTTOM_CENTER );
        magentaGreenSliderAndTextfieldHbox.setPrefWidth( Double.MAX_VALUE );
        rootVbox.getChildren().add( magentaGreenSliderAndTextfieldHbox );
        
        magentaGreenSlider = new Slider();
        magentaGreenSlider.setMin( -100 );
        magentaGreenSlider.setMax( 100 );
        magentaGreenSlider.setValue( 0 );
        magentaGreenSliderAndTextfieldHbox.getChildren().add( magentaGreenSlider );
        
        magentaGreenTextfield = new TextField();
        magentaGreenTextfield.setText("0");
        magentaGreenTextfield.setMaxWidth( 40 );
        magentaGreenSliderAndTextfieldHbox.getChildren().add( magentaGreenTextfield ) ;
		
        addFunctionalityToMagentaGreenSlider( magentaGreenSlider , magentaGreenTextfield ) ;
        
        yellowBlueHbox = new HBox();
        yellowBlueHbox.setSpacing( 70 );
        yellowBlueHbox.setPadding( new Insets( 0 , 0 , 0 , 5) );
        rootVbox.getChildren().add( yellowBlueHbox );
        
        yellowLabel = new Label();
        yellowLabel.setText("Yellow");
        yellowBlueHbox.getChildren().add( yellowLabel );
        
        blueLabel = new Label();
        blueLabel.setText("Blue");
        yellowBlueHbox.getChildren().add( blueLabel );
        
        yellowBlueSliderAndTextfieldHbox = new HBox() ;
        yellowBlueSliderAndTextfieldHbox.setSpacing( 17 ) ;
        yellowBlueSliderAndTextfieldHbox.setAlignment( Pos.BOTTOM_CENTER );
        yellowBlueSliderAndTextfieldHbox.setPrefWidth( Double.MAX_VALUE );
        rootVbox.getChildren().add( yellowBlueSliderAndTextfieldHbox ) ;
        
        yellowBlueSlider = new Slider();
        yellowBlueSlider.setMin( -100 );
        yellowBlueSlider.setMax( 100 );
        yellowBlueSlider.setValue( 0 );
        yellowBlueSliderAndTextfieldHbox.getChildren().add( yellowBlueSlider ) ;
        
        yellowBlueTextfield = new TextField() ;
        yellowBlueTextfield.setText("0") ;
        yellowBlueTextfield.setMaxWidth( 40 ) ;
        yellowBlueSliderAndTextfieldHbox.getChildren().add( yellowBlueTextfield ) ;
        
        addFunctionalityToYellowBlueSlider( yellowBlueSlider , yellowBlueTextfield ) ;
        
        putDataInArrays();
	}
	
	private void putDataInArrays() {
		
		actualRed = new int[ (int)(width*height) ] ; 
		actualGreen = new int[ (int)(width*height) ] ; 
		actualBlue  = new int[ (int)(width*height) ] ; 
		
		counterRed = new int[ (int)(width*height) ] ; 
		counterGreen = new int[ (int)(width*height) ] ; 
		counterBlue = new int[ (int)(width*height) ] ; 
		
		counterRedInFraction = new double[ (int)(width*height) ] ; 
		counterGreenInFraction = new double[ (int)(width*height) ] ; 
		counterBlueInFraction = new double[ (int)(width*height) ] ; 
		
		int i = 0 ;
    	final WritableImage wImg = canvas.snapshot( new SnapshotParameters() , null );
    	PixelReader pixelReader = wImg.getPixelReader() ;
		
		for( int y = 0 ; y < height ; y++ ) {
			for( int x = 0 ; x < width ; x++ ) {
				
                int ARGBobtained = pixelReader.getArgb( x , y );
				
				int red   = (0xff & (ARGBobtained >> 16)) ;
				int green = (0xff & (ARGBobtained >> 8))  ;
				int blue  = (0xff & (ARGBobtained));	
				
				actualRed[i]       = red   ;
				actualGreen[i]     = green ;
				actualBlue[i]      = blue  ;
				
				counterRed[i] = 255 - actualRed[i] ; 
				counterGreen[i] = 255 - actualGreen[i] ;
				counterBlue[i] = 255 - actualBlue[i] ;
				
				counterRedInFraction[i] = counterRed[i]/(double)256 ;
				counterGreenInFraction[i] = counterRed[i]/(double)256 ;
				counterBlueInFraction[i] = counterRed[i]/(double)256 ;
				
				i++ ;
			}
		}
	}
	
	private void addFunctionalityToCyanRedSlider( Slider cyanRedSlider , TextField cyanRedTextfield ) {
		
		cyanRedSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				int value = newVal.intValue() ;
				
				if( value >= 0 ) {
					
					if( colorToneComboBox.getSelectionModel().getSelectedItem() == "Shadows" ) {
						
						callCyanRedShadowsTask( value );
					}
					else if( colorToneComboBox.getSelectionModel().getSelectedItem() == "Midtones" ) {
						
						callCyanRedMidtonesTask( value );
					}
					else {
						
						callCyanRedHighlightsTask( value );
					}
					
				}
				else {
					
                    if( colorToneComboBox.getSelectionModel().getSelectedItem() == "Shadows" ) {
						
                    	callCyanRedShadowsTask2( newVal.intValue() ) ;
					}
					else if( colorToneComboBox.getSelectionModel().getSelectedItem() == "Midtones" ) {
						
						callCyanRedMidtonesTask2( newVal.intValue() );
					}
					else {
						
						callCyanRedHighlightsTask2( newVal.intValue() );
					}
					
				}
			    
				cyanRedTextfield.setText( Integer.toString( newVal.intValue() ) );
			}
		});
	}
	
	private void callCyanRedShadowsTask( int value ) {
		
		IterateCyanRedShadowsTask task = new IterateCyanRedShadowsTask( canvas , value , actualRed , actualGreen , actualBlue 
                , counterRedInFraction , counterGreenInFraction , counterBlueInFraction ) ;
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
	}
	
	private void callCyanRedShadowsTask2( int value ) {
		
		IterateCyanRedShadowsTask2 task = new IterateCyanRedShadowsTask2( canvas , value , actualRed , actualGreen , actualBlue 
                , counterRedInFraction , counterGreenInFraction , counterBlueInFraction ) ;
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
	}
	
	private void callCyanRedMidtonesTask( int value ) {
		
		IterateCyanRedMidtonesTask task = new IterateCyanRedMidtonesTask( canvas , value , actualRed , actualGreen , actualBlue 
                , counterRedInFraction , counterGreenInFraction , counterBlueInFraction ) ;
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
	}
	
	private void callCyanRedMidtonesTask2( int value ) {
		
		IterateCyanRedMidtonesTask2 task = new IterateCyanRedMidtonesTask2( canvas , value , actualRed , actualGreen , actualBlue 
                , counterRedInFraction , counterGreenInFraction , counterBlueInFraction ) ;
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
	}
	
	private void callCyanRedHighlightsTask( int value ) {
		
		IterateCyanRedHighlightsTask task = new IterateCyanRedHighlightsTask( canvas , value , actualRed , actualGreen , actualBlue 
                , counterRedInFraction , counterGreenInFraction , counterBlueInFraction ) ;
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
	}
	
	private void callCyanRedHighlightsTask2( int value ) {
		
		IterateCyanRedHighlightsTask2 task = new IterateCyanRedHighlightsTask2( canvas , value , actualRed , actualGreen , actualBlue 
                , counterRedInFraction , counterGreenInFraction , counterBlueInFraction ) ;
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
	}
	
    private void addFunctionalityToMagentaGreenSlider( Slider magentaGreenSlider , TextField magentaGreenTextfield ) {
		
    	magentaGreenSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				int value = newVal.intValue() ;
				
				if( value >= 0 ) {
					
					if( colorToneComboBox.getSelectionModel().getSelectedItem() == "Shadows" ) {
						
						callMagentaGreenShadowsTask( value );
					}
					else if( colorToneComboBox.getSelectionModel().getSelectedItem() == "Midtones" ) {
						
						callMagentaGreenMidtonesTask( value );
					}
					else {
						
						callMagentaGreenHighlightsTask( value );
					}
					
				}
				else {
					
                    if( colorToneComboBox.getSelectionModel().getSelectedItem() == "Shadows" ) {
						
                    	callMagentaGreenShadowsTask2( newVal.intValue() ) ;
					}
					else if( colorToneComboBox.getSelectionModel().getSelectedItem() == "Midtones" ) {
						
						callMagentaGreenMidtonesTask2( newVal.intValue() );
					}
					else {
						
						callMagentaGreenHighlightsTask2( newVal.intValue() );
					}
					
				}
				
				magentaGreenTextfield.setText( Integer.toString( newVal.intValue() ) );
			}
		});
	}
    
    private void callMagentaGreenShadowsTask( int value ) {
    	
    	IterateMagentaGreenShadowsTask task = new IterateMagentaGreenShadowsTask( canvas , value , actualRed , actualGreen , actualBlue 
                , counterRedInFraction , counterGreenInFraction , counterBlueInFraction ) ;
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
    }
    
    private void callMagentaGreenShadowsTask2( int value ) {
    	
    	IterateMagentaGreenShadowsTask2 task = new IterateMagentaGreenShadowsTask2( canvas , value , actualRed , actualGreen , actualBlue 
                , counterRedInFraction , counterGreenInFraction , counterBlueInFraction ) ;
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
    }
    
    private void callMagentaGreenMidtonesTask( int value ) {
    	
    	IterateMagentaGreenMidtonesTask task = new IterateMagentaGreenMidtonesTask( canvas , value , actualRed , actualGreen , actualBlue 
                , counterRedInFraction , counterGreenInFraction , counterBlueInFraction ) ;
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
    }
    
    private void callMagentaGreenMidtonesTask2( int value ) {
    	
    	IterateMagentaGreenMidtonesTask2 task = new IterateMagentaGreenMidtonesTask2( canvas , value , actualRed , actualGreen , actualBlue 
                , counterRedInFraction , counterGreenInFraction , counterBlueInFraction ) ;
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
    }
    
    private void callMagentaGreenHighlightsTask( int value ) {
    	
    	IterateMagentaGreenHighlightsTask task = new IterateMagentaGreenHighlightsTask( canvas , value , actualRed , actualGreen , actualBlue 
                , counterRedInFraction , counterGreenInFraction , counterBlueInFraction ) ;
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
    }
    
    private void callMagentaGreenHighlightsTask2( int value ) {
    	
    	IterateMagentaGreenHighlightsTask2 task = new IterateMagentaGreenHighlightsTask2( canvas , value , actualRed , actualGreen , actualBlue 
                , counterRedInFraction , counterGreenInFraction , counterBlueInFraction ) ;
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
    }
    
    private void addFunctionalityToYellowBlueSlider( Slider yellowBlueSlider , TextField yellowBlueTextfield ) {
    	
    	yellowBlueSlider.valueProperty().addListener( new ChangeListener<Number>() {
    		@Override
    		public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
    			
    			int value = newVal.intValue() ;
    			
				if( value >= 0 ) {
					
					if( colorToneComboBox.getSelectionModel().getSelectedItem() == "Shadows" ) {
						
						callYellowBlueShadowsTask( value );
					}
					else if( colorToneComboBox.getSelectionModel().getSelectedItem() == "Midtones" ) {
						
						callYellowBlueMidtonesTask( value );
					}
					else {
						
						callYellowBlueHighlightsTask( value );
					}
					
				}
				else {
					
                    if( colorToneComboBox.getSelectionModel().getSelectedItem() == "Shadows" ) {
						
                    	callYellowBlueShadowsTask2( newVal.intValue() ) ;
					}
					else if( colorToneComboBox.getSelectionModel().getSelectedItem() == "Midtones" ) {
						
						callYellowBlueMidtonesTask2( newVal.intValue() );
					}
					else {
						
						callYellowBlueHighlightsTask2( newVal.intValue() );
					}
				}
                
    			yellowBlueTextfield.setText( Integer.toString( newVal.intValue() ) );
    		}
    	});
    }
    
    private void callYellowBlueShadowsTask( int value ) {
    	
    	IterateYellowBlueShadowsTask task = new IterateYellowBlueShadowsTask( canvas , value , actualRed , actualGreen , actualBlue 
                , counterRedInFraction , counterGreenInFraction , counterBlueInFraction ) ;
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
    }
    
    private void callYellowBlueShadowsTask2( int value ) {
    	
    	IterateYellowBlueShadowsTask2 task = new IterateYellowBlueShadowsTask2( canvas , value , actualRed , actualGreen , actualBlue 
                , counterRedInFraction , counterGreenInFraction , counterBlueInFraction ) ;
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
    }
    
    private void callYellowBlueMidtonesTask( int value ) {
    	
    	IterateYellowBlueMidtonesTask task = new IterateYellowBlueMidtonesTask( canvas , value , actualRed , actualGreen , actualBlue 
                , counterRedInFraction , counterGreenInFraction , counterBlueInFraction ) ;
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
    }
    
    private void callYellowBlueMidtonesTask2( int value ) {
    	
    	IterateYellowBlueMidtonesTask2 task = new IterateYellowBlueMidtonesTask2( canvas , value , actualRed , actualGreen , actualBlue 
                , counterRedInFraction , counterGreenInFraction , counterBlueInFraction ) ;
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
    }
    
    private void callYellowBlueHighlightsTask( int value ) {
    	
    	IterateYellowBlueHighlightsTask task = new IterateYellowBlueHighlightsTask( canvas , value , actualRed , actualGreen , actualBlue 
                , counterRedInFraction , counterGreenInFraction , counterBlueInFraction ) ;
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
    }
    
    private void callYellowBlueHighlightsTask2( int value ) {
    	
    	IterateYellowBlueHighlightsTask2 task = new IterateYellowBlueHighlightsTask2( canvas , value , actualRed , actualGreen , actualBlue 
                , counterRedInFraction , counterGreenInFraction , counterBlueInFraction ) ;
		Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
    }
}
