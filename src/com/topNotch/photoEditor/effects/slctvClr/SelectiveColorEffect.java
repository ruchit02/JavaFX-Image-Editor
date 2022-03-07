package com.topNotch.photoEditor.effects.slctvClr;

import java.util.concurrent.ExecutionException;

import com.topNotch.photoEditor.main.MyEffects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SelectiveColorEffect extends MyEffects{
	
	private int redsCyanSliderValue = 0 ;
	private int redsMagentaSliderValue = 0 ;
	private int redsYellowSliderValue = 0 ;
	private int redsBlackSliderValue = 0 ;
	
	private int greensCyanSliderValue = 0 ;
	private int greensMagentaSliderValue = 0 ;
	private int greensYellowSliderValue = 0 ;
	private int greensBlackSliderValue = 0 ;
	
	private int bluesCyanSliderValue = 0 ;
	private int bluesMagentaSliderValue = 0 ;
	private int bluesYellowSliderValue = 0 ;
	private int bluesBlackSliderValue = 0 ;
	
	private int yellowsCyanSliderValue = 0 ;
	private int yellowsMagentaSliderValue = 0 ;
	private int yellowsYellowSliderValue = 0 ;
	private int yellowsBlackSliderValue = 0 ;
	
	private int cyansCyanSliderValue = 0 ;
	private int cyansMagentaSliderValue = 0 ;
	private int cyansYellowSliderValue = 0 ;
	private int cyansBlackSliderValue = 0 ;
	
	private int magentasCyanSliderValue = 0 ;
	private int magentasMagentaSliderValue = 0 ;
	private int magentasYellowSliderValue = 0 ;
	private int magentasBlackSliderValue = 0 ;
	
	private int width ;
	private int height ;
	
	private Image img ;
	private WritableImage wImg ;
	private Blend blend ;
	private Canvas canvas ;
	private ColorAdjust colorAdjust ;
	
	private TabPane tabPane ;
	private GridPane gridPane ;
	private ScrollPane scrollPane ;
	private VBox rootVbox ;
	private VBox masksVbox ;
	
	private HBox toggleButtonsHbox ;
	private ToggleGroup toggleGroup ;
	private ToggleButton selectiveColorToggleButton ;
	private ToggleButton masksToggleButton ;
	
	private HBox colorsHbox ;
	private Label colorsLabel ;
	private ComboBox<String> colorsComboBox ;
	
	private VBox cyansVbox ;
	private Label cyansLabel ;
	private HBox cyansSliderAndTextfieldHbox ;
	private Slider cyansSlider ;
	private TextField cyansTextfield ;
	
	private VBox magentasVbox ;
	private Label magentasLabel ;
	private HBox magentasSliderAndTextfieldHbox ;
	private Slider magentasSlider ;
	private TextField magentasTextfield ;
	
	private VBox yellowsVbox ;
	private Label yellowsLabel ;
	private HBox yellowsSliderAndTextfieldHbox ;
	private Slider yellowsSlider ;
	private TextField yellowsTextfield ;
	
	private VBox blacksVbox ;
	private Label blacksLabel ;
	private HBox blacksSliderAndTextfieldHbox ;
	private Slider blacksSlider ;
	private TextField blacksTextfield ;
	
	private int previousIteration[] ;
	
	private int actualRed[]  ; 
	private int actualGreen[] ;
	private int actualBlue[]  ;
	
	private int counterRed[];
	private int counterGreen[];
	private int counterBlue[];
	
	private double counterRedInFraction[];
	private double counterGreenInFraction[];
	private double counterBlueInFraction[];
	
	private double actualRedInFraction[];
	private double actualGreenInFraction[];
	private double actualBlueInFraction[];
	
	private double rgbAverageInFraction[] ;
	
	private int extraRedAboveAverage[] ;
	private int extraGreenAboveAverage[] ;
	private int extraBlueAboveAverage[] ;
	
	private int blueBelowAverage[] ;
	
	private int differenceBetweenActualRedAndActualGreen[] ;
	private int differenceBetweenActualRedAndActualBlue[] ;
	
	private int differenceBetweenActualGreenAndActualBlue[] ;
	private int differenceBetweenActualGreenAndActualRed[] ;
	
	private int differenceBetweenActualBlueAndActualGreen[] ;
	private int differenceBetweenActualBlueAndActualRed[] ;
	
	private int rgbAverage[] ;
	
	public SelectiveColorEffect( Image img ) {
		
		super( img );
		this.img = img ;
		this.canvas = this.getCanvas() ;
		this.width = (int)this.canvas.getWidth() ;
		this.height = (int)this.canvas.getHeight() ;
		this.blend = this.getBlend() ;
		this.colorAdjust = this.getColorAdjust() ;
		
		previousIteration = new int[ (int)(width*height) ] ;
		
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
		
		extraRedAboveAverage   = new int[ (int)(width*height) ];
		extraGreenAboveAverage = new int[ (int)(width*height) ];
		extraBlueAboveAverage  = new int[ (int)(width*height) ];
		
		blueBelowAverage       = new int[ (int)(width*height) ];    
		
		differenceBetweenActualRedAndActualGreen = new int[ (int)(width*height) ] ;
		differenceBetweenActualRedAndActualBlue = new int[ (int)(width*height) ] ;
		
		differenceBetweenActualGreenAndActualRed = new int[ (int)(width*height) ] ;
		differenceBetweenActualGreenAndActualBlue = new int[ (int)(width*height) ] ;
		
		differenceBetweenActualBlueAndActualGreen = new int[ (int)(width*height) ] ;
		differenceBetweenActualBlueAndActualRed = new int[ (int)(width*height) ] ;
		
		rgbAverageInFraction = new double[ (int)(width*height) ];
		
		this.createEffectObjects();
	}
	
    public SelectiveColorEffect( WritableImage wImg ) {
		
		super( wImg );
		this.wImg = wImg ;
		this.canvas = this.getCanvas() ;
		this.width = (int)this.canvas.getWidth() ;
		this.height = (int)this.canvas.getHeight() ;
		this.blend = this.getBlend() ;
		this.colorAdjust = this.getColorAdjust() ;
		
		previousIteration = new int[ (int)(width*height) ] ;
		
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
		
		extraRedAboveAverage   = new int[ (int)(width*height) ];
		extraGreenAboveAverage = new int[ (int)(width*height) ];
		extraBlueAboveAverage  = new int[ (int)(width*height) ];
		
		blueBelowAverage       = new int[ (int)(width*height) ];    
		
		differenceBetweenActualRedAndActualGreen = new int[ (int)(width*height) ] ;
		differenceBetweenActualRedAndActualBlue = new int[ (int)(width*height) ] ;
		
		differenceBetweenActualGreenAndActualRed = new int[ (int)(width*height) ] ;
		differenceBetweenActualGreenAndActualBlue = new int[ (int)(width*height) ] ;
		
		differenceBetweenActualBlueAndActualGreen = new int[ (int)(width*height) ] ;
		differenceBetweenActualBlueAndActualRed = new int[ (int)(width*height) ] ;
		
		rgbAverageInFraction = new double[ (int)(width*height) ];
		
		this.createEffectObjects();
	}
    
    @Override
    public Blend getBlend() {
    	
    	return super.getBlend() ;
    }
    
    @Override
    public Canvas getCanvas() {
    	
    	return super.getCanvas() ;
    }
    
    @Override
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
		
		selectiveColorToggleButton = new ToggleButton();
		selectiveColorToggleButton.setText("Selective Color");
		selectiveColorToggleButton.setToggleGroup( toggleGroup );
		selectiveColorToggleButton.setSelected( true );
		toggleButtonsHbox.getChildren().add( selectiveColorToggleButton );
		
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
		
		colorsHbox = new HBox();
		rootVbox.getChildren().add( colorsHbox );
		
		colorsLabel = new Label() ;
		colorsLabel.setText("Colors :");
		colorsHbox.getChildren().add( colorsLabel );
		
		ObservableList<String> colorTypes = FXCollections.observableArrayList( "Reds" , "Yellows" , "Greens" , "Cyans" , "Blues" , "Magentas" , "Whites" , "Neutrals" , "Blacks" );
		
		colorsComboBox = new ComboBox<String>() ;
		colorsComboBox.setItems( colorTypes );
		colorsComboBox.setMinWidth( 125 );
		colorsComboBox.setValue("Reds");
		colorsHbox.getChildren().add( colorsComboBox );
		
		colorsComboBox.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<String>() {
			@Override
			public void changed( ObservableValue<? extends String> obv , String oldVal , String newVal ) {
				
				if( newVal == "Reds" ) {
					
					WritableImage wImg = canvas.snapshot( new SnapshotParameters() , null );
					
					cyansSlider.setValue( redsCyanSliderValue ) ;
					magentasSlider.setValue( redsMagentaSliderValue ) ;
					yellowsSlider.setValue( redsYellowSliderValue ) ;
				    blacksSlider.setValue( redsBlackSliderValue ) ;
				    
				    canvas.getGraphicsContext2D().drawImage( wImg , 0 , 0 , canvas.getWidth() , canvas.getHeight() );
				}
				else if( newVal == "Yellows" ) {
					
					WritableImage wImg = canvas.snapshot( new SnapshotParameters() , null );
					
					cyansSlider.setValue( yellowsCyanSliderValue ) ;
					magentasSlider.setValue( yellowsMagentaSliderValue ) ;
					yellowsSlider.setValue( yellowsYellowSliderValue ) ;
					blacksSlider.setValue( yellowsBlackSliderValue ) ;
					
					canvas.getGraphicsContext2D().drawImage( wImg , 0 , 0 , canvas.getWidth() , canvas.getHeight() );
				}
				else if( newVal == "Greens" ) {
					
					WritableImage wImg = canvas.snapshot( new SnapshotParameters() , null );
					
					cyansSlider.setValue( greensCyanSliderValue ) ;
					magentasSlider.setValue( greensMagentaSliderValue ) ;
					yellowsSlider.setValue( greensYellowSliderValue ) ;
					blacksSlider.setValue( greensBlackSliderValue ) ;
					
					canvas.getGraphicsContext2D().drawImage( wImg , 0 , 0 , canvas.getWidth() , canvas.getHeight() );
				}
                else if( newVal == "Cyans" ) {
					
                	WritableImage wImg = canvas.snapshot( new SnapshotParameters() , null );
                	
                	cyansSlider.setValue( cyansCyanSliderValue ) ;
					magentasSlider.setValue( cyansMagentaSliderValue ) ;
					yellowsSlider.setValue( cyansYellowSliderValue ) ;
					blacksSlider.setValue( cyansBlackSliderValue ) ;
					
					canvas.getGraphicsContext2D().drawImage( wImg , 0 , 0 , canvas.getWidth() , canvas.getHeight() );
				}
                else if( newVal == "Blues" ) {
                	
                	WritableImage wImg = canvas.snapshot( new SnapshotParameters() , null );
                	
                	cyansSlider.setValue( bluesCyanSliderValue ) ;
					magentasSlider.setValue( bluesMagentaSliderValue ) ;
					yellowsSlider.setValue( bluesYellowSliderValue ) ;
					blacksSlider.setValue( bluesBlackSliderValue ) ;
					
					canvas.getGraphicsContext2D().drawImage( wImg , 0 , 0 , canvas.getWidth() , canvas.getHeight() );
                }
                else if( newVal == "Magentas" ) {
                	
                	WritableImage wImg = canvas.snapshot( new SnapshotParameters() , null );
                	
                	cyansSlider.setValue( magentasCyanSliderValue ) ;
					magentasSlider.setValue( magentasMagentaSliderValue ) ;
					yellowsSlider.setValue( magentasYellowSliderValue ) ;
					blacksSlider.setValue( magentasBlackSliderValue ) ;
					
					canvas.getGraphicsContext2D().drawImage( wImg , 0 , 0 , canvas.getWidth() , canvas.getHeight() );
                }
                else if( newVal == "Whites" ) {
					
				}
                else if( newVal == "Neutrals" ) {
	
                }
                else {
                	
                }
			}
		});
		
		cyansVbox = new VBox();
		rootVbox.getChildren().add( cyansVbox );
		
		cyansLabel = new Label();
		cyansLabel.setText("Cyans");
		cyansVbox.getChildren().add( cyansLabel );
		
		cyansSliderAndTextfieldHbox = new HBox();
		cyansSliderAndTextfieldHbox.setSpacing( 17 );
		cyansVbox.getChildren().add( cyansSliderAndTextfieldHbox );
		
		cyansSlider = new Slider();
		cyansSlider.setMin( -100 );
		cyansSlider.setMax( 100 );
		cyansSlider.setValue( 0 );
		cyansSliderAndTextfieldHbox.getChildren().add( cyansSlider );
		
		/*cyansSlider.setOnMouseReleased( new EventHandler<MouseEvent>() {
        	@Override
        	public void handle( MouseEvent e ) {
        		
        		putDataInArrays();
        	}
        });*/
		
		cyansTextfield = new TextField();
		cyansTextfield.setText("0");
		cyansTextfield.setMaxWidth( 40 );
		cyansSliderAndTextfieldHbox.getChildren().add( cyansTextfield );
		
		addFunctionalityToCyansSlider( cyansSlider , cyansTextfield );
		
		magentasVbox = new VBox();
		rootVbox.getChildren().add( magentasVbox );
		
		magentasLabel = new Label();
		magentasLabel.setText("Magentas");
		magentasVbox.getChildren().add( magentasLabel );
		
        magentasSliderAndTextfieldHbox = new HBox();
        magentasSliderAndTextfieldHbox.setSpacing( 17 );
        magentasVbox.getChildren().add( magentasSliderAndTextfieldHbox );
        
        magentasSlider = new Slider();
        magentasSlider.setMin( -100 );
        magentasSlider.setMax( 100 );
        magentasSlider.setValue( 0 );
        magentasSliderAndTextfieldHbox.getChildren().add( magentasSlider );
        
        /*magentasSlider.setOnMouseReleased( new EventHandler<MouseEvent>() {
        	@Override
        	public void handle( MouseEvent e ) {
        		
        		putDataInArrays();
        	}
        });*/
        
        magentasTextfield = new TextField();
        magentasTextfield.setText("0");
        magentasTextfield.setMaxWidth( 40 );
        magentasSliderAndTextfieldHbox.getChildren().add( magentasTextfield ) ;
		
        addFunctionalityToMagentasSlider( magentasSlider , magentasTextfield ) ;
        
        yellowsVbox = new VBox();
        rootVbox.getChildren().add( yellowsVbox );
        
        yellowsLabel = new Label();
        yellowsLabel.setText("Yellows");
        yellowsVbox.getChildren().add( yellowsLabel );
        
        yellowsSliderAndTextfieldHbox = new HBox() ;
        yellowsSliderAndTextfieldHbox.setSpacing( 17 ) ;
        yellowsVbox.getChildren().add( yellowsSliderAndTextfieldHbox ) ;
        
        yellowsSlider = new Slider();
        yellowsSlider.setMin( -100 );
        yellowsSlider.setMax( 100 );
        yellowsSlider.setValue( 0 );
        yellowsSliderAndTextfieldHbox.getChildren().add( yellowsSlider ) ;
        
        /*yellowsSlider.setOnMouseReleased( new EventHandler<MouseEvent>() {
        	@Override
        	public void handle( MouseEvent e ) {
        		
        		putDataInArrays();
        	}
        });*/
        
        yellowsTextfield = new TextField() ;
        yellowsTextfield.setText("0") ;
        yellowsTextfield.setMaxWidth( 40 ) ;
        yellowsSliderAndTextfieldHbox.getChildren().add( yellowsTextfield ) ;
        
        addFunctionalityToYellowsSlider( yellowsSlider , yellowsTextfield ) ;
        
        blacksVbox = new VBox();
        rootVbox.getChildren().add( blacksVbox );
        
        blacksLabel = new Label();
        blacksLabel.setText("Blacks");
        blacksVbox.getChildren().add( blacksLabel );
        
        blacksSliderAndTextfieldHbox = new HBox() ;
        blacksSliderAndTextfieldHbox.setSpacing( 17 ) ;
        blacksVbox.getChildren().add( blacksSliderAndTextfieldHbox ) ;
        
        blacksSlider = new Slider();
        blacksSlider.setMin( -100 );
        blacksSlider.setMax( 100 );
        blacksSlider.setValue( 0 );
        blacksSliderAndTextfieldHbox.getChildren().add( blacksSlider ) ;
        
        /*blacksSlider.setOnMouseReleased( new EventHandler<MouseEvent>() {
        	@Override
        	public void handle( MouseEvent e ) {
        		
        		putDataInArrays();
        	}
        });*/
        
        blacksTextfield = new TextField() ;
        blacksTextfield.setText("0") ;
        blacksTextfield.setMaxWidth( 40 ) ;
        blacksSliderAndTextfieldHbox.getChildren().add( blacksTextfield ) ;
        
        addFunctionalityToBlacksSlider( blacksSlider , blacksTextfield ) ;
        
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
				
				extraRedAboveAverage[i]   = actualRed[i] - rgbAverage[i] ;
				extraGreenAboveAverage[i] = actualGreen[i] - rgbAverage[i] ;
				extraBlueAboveAverage[i]  = actualBlue[i] - rgbAverage[i] ;
				
				blueBelowAverage[i]       = rgbAverage[i] - actualBlue[i] ;
				
				differenceBetweenActualRedAndActualGreen[i]  = actualRed[i] - actualGreen[i] ;
				differenceBetweenActualRedAndActualBlue[i]   = actualRed[i] - actualBlue[i] ;
				
				differenceBetweenActualGreenAndActualRed[i]  = actualGreen[i] - actualRed[i] ;
				differenceBetweenActualGreenAndActualBlue[i] = actualGreen[i] - actualBlue[i] ;
				
				differenceBetweenActualBlueAndActualGreen[i] = actualBlue[i] - actualGreen[i] ;
				differenceBetweenActualBlueAndActualRed[i]   = actualBlue[i] - actualRed[i] ;
				
				rgbAverageInFraction[i] = rgbAverage[i]/(double)256 ;
				
				i++ ;
			}
		}
    }
    
    private void addFunctionalityToCyansSlider( Slider cyansSlider , TextField cyansTextfield ) {
    	
    	cyansSlider.valueProperty().addListener( new ChangeListener<Number>() {
    		@Override
    		public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
    			
    			int value = newVal.intValue() ;
                
    			if( colorsComboBox.getSelectionModel().getSelectedItem() == "Reds" ) {
					
					callCyansManipulatorForRedsTask( value );
					redsCyanSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Yellows" ) {
					
					callCyansManipulatorForYellowsTask( value );
					yellowsCyanSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Greens" ) {
					
					callCyansManipulatorForGreensTask( value );
					greensCyanSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Cyans" ) {
					
					callCyansManipulatorForCyansTask( value );
					cyansCyanSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Blues" ) {
					
					callCyansManipulatorForBluesTask( value ) ;
					bluesCyanSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Magentas" ) {
					
					callCyansManipulatorForMagentas( value ) ;
					magentasCyanSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Whites" ) {
					
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Neutrals" ) {
					
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Blacks" ) {
					
				}
				else {
					
				}
    			
    			cyansTextfield.setText( Integer.toString( newVal.intValue() ) );
    		}
    	});
    }
    
    private void callCyansManipulatorForRedsTask( int value ) {
    	
    	SelectiveColorCyansManipulatorForRedsTask task = new SelectiveColorCyansManipulatorForRedsTask( canvas , value ,  actualRed , actualGreen , actualBlue 
            , actualRedInFraction , counterRedInFraction , differenceBetweenActualRedAndActualGreen ) ;
    	Thread th = new Thread(task) ;
    	th.setDaemon( true );
    	th.start(); 
    }
    
    private void callCyansManipulatorForYellowsTask( int value ) {
    	
    	SelectiveColorCyansManipulatorForYellowsTask task = new SelectiveColorCyansManipulatorForYellowsTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualRedInFraction , counterRedInFraction , differenceBetweenActualRedAndActualBlue ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start();
    }
    
    private void callCyansManipulatorForGreensTask( int value ) {
    	
    	SelectiveColorCyansManipulatorForGreensTask task = new SelectiveColorCyansManipulatorForGreensTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualRedInFraction , counterRedInFraction , differenceBetweenActualGreenAndActualRed ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start();
    }
    
    private void callCyansManipulatorForCyansTask( int value ) {
    	
    	SelectiveColorCyansManipulatorForCyansTask task = new SelectiveColorCyansManipulatorForCyansTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualRedInFraction , counterRedInFraction , differenceBetweenActualGreenAndActualRed , previousIteration ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start();
        
        try {
			previousIteration = task.get() ;
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void callCyansManipulatorForBluesTask( int value ) {
        
        SelectiveColorCyansManipulatorForBluesTask task = new SelectiveColorCyansManipulatorForBluesTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualRedInFraction , counterRedInFraction , differenceBetweenActualBlueAndActualGreen ) ;
        Thread th = new Thread(task) ;
        th.setDaemon(true);
        th.start();
    }
    
    private void callCyansManipulatorForMagentas( int value ) {
    	
    	SelectiveColorCyansManipulatorForMagentasTask task = new SelectiveColorCyansManipulatorForMagentasTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualRedInFraction , counterRedInFraction , differenceBetweenActualBlueAndActualGreen , differenceBetweenActualRedAndActualGreen ) ;
        Thread th = new Thread(task) ;
        th.setDaemon(true);
        th.start();
    }
    
    private void addFunctionalityToMagentasSlider( Slider magentasSlider , TextField magentasTextfield ) {
    	
    	magentasSlider.valueProperty().addListener( new ChangeListener<Number>() {
    		@Override
    		public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
    			
    			int value = newVal.intValue() ;
    			
    			if( colorsComboBox.getSelectionModel().getSelectedItem() == "Reds" ) {
					
					callMagentasManipulatorForRedsTask( value ) ;
					redsMagentaSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Yellows" ) {
					
					callMagentasManipulatorForYellowsTask( value );
					yellowsMagentaSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Greens" ) {
					
					callMagentasManipulatorForGreensTask( value );
					greensMagentaSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Cyans" ) {
					
					callMagentasManipulatorForCyansTask( value );
					cyansMagentaSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Blues" ) {
					
					callMagentasManipulatorForBluesTask( value ) ;
					bluesMagentaSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Magentas" ) {
					
					callMagentasManipulatorForMagentasTask( value );
					magentasMagentaSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Whites" ) {
					
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Neutrals" ) {
					
				}
				else {
					
				}
				
    			
    			magentasTextfield.setText( Integer.toString( newVal.intValue() ) );
    		}
    	});
    }
    
    private void callMagentasManipulatorForRedsTask( int value ) {
    	
    	SelectiveColorMagentasManipulatorForRedsTask task = new SelectiveColorMagentasManipulatorForRedsTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualGreenInFraction , counterGreenInFraction , differenceBetweenActualRedAndActualGreen ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start(); 
    }
    
    private void callMagentasManipulatorForYellowsTask( int value ) {
    	
    	SelectiveColorMagentasManipulatorForYellowsTask task = new SelectiveColorMagentasManipulatorForYellowsTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualGreenInFraction , counterGreenInFraction , differenceBetweenActualRedAndActualBlue ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start(); 
    }
    
    private void callMagentasManipulatorForGreensTask( int value ) {
    	
    	SelectiveColorMagentasManipulatorForGreensTask task = new SelectiveColorMagentasManipulatorForGreensTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualGreenInFraction , counterGreenInFraction , differenceBetweenActualGreenAndActualRed ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start();
    }
    
    private void callMagentasManipulatorForCyansTask( int value ) {
    	
    	SelectiveColorMagentasManipulatorForCyansTask task = new SelectiveColorMagentasManipulatorForCyansTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualGreenInFraction , counterGreenInFraction , differenceBetweenActualGreenAndActualRed , differenceBetweenActualBlueAndActualRed , previousIteration ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start();
        
        try {
			previousIteration = task.get() ;
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void callMagentasManipulatorForBluesTask( int value ) {
    	
    	SelectiveColorMagentasManipulatorForBluesTask task = new SelectiveColorMagentasManipulatorForBluesTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualGreenInFraction , counterGreenInFraction , differenceBetweenActualBlueAndActualGreen ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start();
    }
    
    private void callMagentasManipulatorForMagentasTask( int value ) {
    	
    	SelectiveColorMagentasManipulatorForMagentasTask task = new SelectiveColorMagentasManipulatorForMagentasTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualGreenInFraction , counterGreenInFraction , differenceBetweenActualBlueAndActualGreen , differenceBetweenActualRedAndActualGreen ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start();
    }
    
    private void addFunctionalityToYellowsSlider( Slider yellowsSlider , TextField yellowsTextfield ) {
    	
    	yellowsSlider.valueProperty().addListener( new ChangeListener<Number>() {
    		@Override
    		public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
    			
    			int value = newVal.intValue() ;
    			
    			if( colorsComboBox.getSelectionModel().getSelectedItem() == "Reds" ) {
					
					callYellowsManipulatorForRedsTask( value ) ;
					redsYellowSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Yellows" ) {
					
					callYellowsManipulatorForYellowsTask( value );
					yellowsYellowSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Greens" ) {
					
					callYellowsManipulatorForGreensTask( value );
					greensYellowSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Cyans" ) {
					
					callYellowsManipulatorForCyansTask( value ) ;
					cyansYellowSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Blues" ) {
					
					callYellowsManipulatorForBluesTask( value );
					bluesYellowSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Magentas" ) {
					
					callYellowsManipulatorForMagentasTask( value );
					magentasYellowSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Whites" ) {
					
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Neutrals" ) {
					
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Blacks" ) {
					
				}
				else {
					
				}
    			
    			yellowsTextfield.setText( Integer.toString( newVal.intValue() ) );
    		}
    	});
    }
    
    private void callYellowsManipulatorForRedsTask( int value ) {
    	
    	SelectiveColorYellowsManipulatorForRedsTask task = new SelectiveColorYellowsManipulatorForRedsTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualBlueInFraction , counterBlueInFraction , differenceBetweenActualRedAndActualBlue ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start(); 
    }
    
    private void callYellowsManipulatorForYellowsTask( int value ) {
    	
    	SelectiveColorYellowsManipulatorForYellowsTask task = new SelectiveColorYellowsManipulatorForYellowsTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualBlueInFraction , counterBlueInFraction , differenceBetweenActualRedAndActualBlue ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start(); 
    }
    
    private void callYellowsManipulatorForGreensTask( int value ) {
    	
    	SelectiveColorYellowsManipulatorForGreensTask task = new SelectiveColorYellowsManipulatorForGreensTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualBlueInFraction , counterBlueInFraction , differenceBetweenActualGreenAndActualBlue ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start(); 
    }
    
    private void callYellowsManipulatorForCyansTask( int value ) {
    	
    	SelectiveColorYellowsManipulatorForCyansTask task = new SelectiveColorYellowsManipulatorForCyansTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualBlueInFraction , counterBlueInFraction , differenceBetweenActualBlueAndActualRed , differenceBetweenActualBlueAndActualRed , previousIteration ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start(); 
        
        try {
			previousIteration = task.get() ;
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void callYellowsManipulatorForBluesTask( int value ) {
    	
    	SelectiveColorYellowsManipulatorForBluesTask task = new SelectiveColorYellowsManipulatorForBluesTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , counterBlueInFraction , differenceBetweenActualBlueAndActualGreen ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start();
    }
    
    private void callYellowsManipulatorForMagentasTask( int value ) {
    	
    	SelectiveColorYellowsManipulatorForMagentasTask task = new SelectiveColorYellowsManipulatorForMagentasTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualBlueInFraction , counterBlueInFraction , differenceBetweenActualBlueAndActualGreen  , differenceBetweenActualRedAndActualGreen ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start();
    }
    
    private void addFunctionalityToBlacksSlider( Slider blacksSlider , TextField blacksTextfield ) {
    	
    	blacksSlider.valueProperty().addListener( new ChangeListener<Number>() {
    		@Override
    		public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
    			
    			int value = newVal.intValue() ;
    			
    			if( colorsComboBox.getSelectionModel().getSelectedItem() == "Reds" ) {
					
    				callBlacksManipulatorForRedsTask( value );
    				redsBlackSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Yellows" ) {
					
					callBlacksManipulatorForYellowsTask( value );
					yellowsBlackSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Greens" ) {
					
					callBlacksManipulatorForGreensTask( value ) ;
					greensBlackSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Cyans" ) {
					
					callBlacksManipulatorForCyansTask( value );
					cyansBlackSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Blues" ) {
					
					callBlacksManipulatorForBluesTask( value );
					bluesBlackSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Magentas" ) {
					
					callBlacksManipulatorForMagentasTask( value );
					magentasBlackSliderValue = value ;
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Whites" ) {
					
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Neutrals" ) {
					
				}
				else if( colorsComboBox.getSelectionModel().getSelectedItem() == "Blacks" ) {
					
				}
				else {
					
				}
    			
    			blacksTextfield.setText( Integer.toString( newVal.intValue() ) );
    		}
    	});
    }
    
    private void callBlacksManipulatorForRedsTask( int value ) {
    	
    	SelectiveColorBlacksManipulatorForRedsTask task = new SelectiveColorBlacksManipulatorForRedsTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualRedInFraction , actualGreenInFraction , actualBlueInFraction , counterRedInFraction 
                , counterGreenInFraction , counterBlueInFraction , differenceBetweenActualRedAndActualGreen ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start();
    }
    
    private void callBlacksManipulatorForYellowsTask( int value ) {
    	
    	SelectiveColorBlacksManipulatorForYellowsTask task = new SelectiveColorBlacksManipulatorForYellowsTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualRedInFraction , actualGreenInFraction , actualBlueInFraction , counterRedInFraction 
                , counterGreenInFraction , counterBlueInFraction , differenceBetweenActualRedAndActualBlue
                , differenceBetweenActualGreenAndActualBlue ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start();
    }
    
    private void callBlacksManipulatorForGreensTask( int value ) {
    	
    	SelectiveColorBlacksManipulatorForGreensTask task = new SelectiveColorBlacksManipulatorForGreensTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualRedInFraction , actualGreenInFraction , actualBlueInFraction , counterRedInFraction 
                , counterGreenInFraction , counterBlueInFraction , differenceBetweenActualGreenAndActualRed ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start();
    }
    
    private void callBlacksManipulatorForCyansTask( int value ) {
    	
    	SelectiveColorBlacksManipulatorForCyansTask task = new SelectiveColorBlacksManipulatorForCyansTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualRedInFraction , actualGreenInFraction , actualBlueInFraction , counterRedInFraction 
                , counterGreenInFraction , counterBlueInFraction , differenceBetweenActualGreenAndActualRed
                , differenceBetweenActualBlueAndActualRed , previousIteration ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start();
        
        try {
			previousIteration = task.get() ;
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void callBlacksManipulatorForBluesTask( int value ) {
    	
    	SelectiveColorBlacksManipulatorForBluesTask task = new SelectiveColorBlacksManipulatorForBluesTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualRedInFraction , actualGreenInFraction , actualBlueInFraction , counterRedInFraction 
                , counterGreenInFraction , counterBlueInFraction , differenceBetweenActualBlueAndActualGreen ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start();
    }
    
    private void callBlacksManipulatorForMagentasTask( int value ) {
    	
    	SelectiveColorBlacksManipulatorForMagentasTask task = new SelectiveColorBlacksManipulatorForMagentasTask( canvas , value ,  actualRed , actualGreen , actualBlue 
                , actualRedInFraction , actualGreenInFraction , actualBlueInFraction , counterRedInFraction 
                , counterGreenInFraction , counterBlueInFraction , differenceBetweenActualBlueAndActualGreen
                , differenceBetweenActualRedAndActualGreen ) ;
        Thread th = new Thread(task) ;
        th.setDaemon( true );
        th.start();
    }
    
}
