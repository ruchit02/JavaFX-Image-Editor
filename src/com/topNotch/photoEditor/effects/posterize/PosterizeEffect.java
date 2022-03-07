package com.topNotch.photoEditor.effects.posterize;

import com.topNotch.photoEditor.main.MyEffects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PosterizeEffect extends MyEffects {
	
	private Image img ;
	private WritableImage wImg ;
	private Canvas canvas ;
	private int width ;
	private int height ;
	private ColorAdjust colorAdjust ;
	private Blend blend ;
	
	private TabPane tabPane ;
	private GridPane gridPane ;
	private ScrollPane scrollPane;
	
	private VBox rootVbox ;
	private VBox masksVbox ;
	
	private HBox toggleButtonsHbox ;
	private ToggleGroup toggleGroup ;
	private ToggleButton posterizeToggleButton ;
	private ToggleButton masksToggleButton ;
	
	private HBox posterizeHbox ;
	private Label posterizeLabel ;
	private TextField posterizeTextfield ;
	private Slider posterizeSlider ;
	
	private int actualRed[];
	private int actualGreen[];
	private int actualBlue[];
	
	public PosterizeEffect( Image img ) {
		
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
		
		this.putDataInArrays();
		this.createEffectObjects();
	}
	
    public PosterizeEffect( WritableImage wImg ) {
		
    	super( wImg );
    	
    	this.img = wImg ;
		this.canvas = this.getCanvas() ;
		this.width = (int)this.canvas.getWidth() ;
		this.height = (int)this.canvas.getHeight() ;
		this.blend = this.getBlend() ;
		this.colorAdjust = this.getColorAdjust() ;
    	
		this.actualRed = new int[ width*height ];
		this.actualGreen = new int[ width*height ];
		this.actualBlue = new int[ width*height ];
		
		this.putDataInArrays();
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
    	
    	return super.getColorAdjust();
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
		
		posterizeToggleButton = new ToggleButton();
		posterizeToggleButton.setText("Posterize");
		posterizeToggleButton.setToggleGroup( toggleGroup );
		posterizeToggleButton.setSelected( true );
		toggleButtonsHbox.getChildren().add( posterizeToggleButton );
		
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
		
		posterizeHbox = new HBox();
		posterizeHbox.setSpacing( 135 );
		rootVbox.getChildren().add( posterizeHbox );
		
		posterizeLabel = new Label();
		posterizeLabel.setText("Levels");
		posterizeHbox.getChildren().add( posterizeLabel );
		
		posterizeTextfield = new TextField();
		posterizeTextfield.setMaxWidth( 50 );
		posterizeTextfield.setText("4");
		posterizeHbox.getChildren().add( posterizeTextfield );
		
		posterizeSlider = new Slider();
		posterizeSlider.setMin( 2 );
		posterizeSlider.setMax( 255 );
		rootVbox.getChildren().add( posterizeSlider );
		
		addFunctionalityToPosterizeSlider( posterizeSlider , posterizeTextfield );
		posterizeSlider.setValue( 4 );
    }
    
    private void putDataInArrays() {
    	
    	int i = 0 ;
    	WritableImage wImg = canvas.snapshot( new SnapshotParameters() , null );
    	PixelReader pxlReader = wImg.getPixelReader() ;
    	
    	for( int y = 0 ; y < height ; y++ ) {
    		for( int x = 0 ; x < width ; x++ ) {
    			
    			int argb = pxlReader.getArgb( x , y );
    			
    			int alpha = ( 0xff & (argb>>24) );
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
    
    private void addFunctionalityToPosterizeSlider( Slider posterizeSlider , TextField posterizeTextfield ) {
    	
    	posterizeSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				int value = newVal.intValue() ;
				
				PosterizePixelManipulatorTask task = new PosterizePixelManipulatorTask( canvas , value , actualRed , actualGreen , actualBlue );
				Thread th = new Thread(task);
				th.setDaemon( true );
				th.start();
				
				posterizeTextfield.setText( Integer.toString( value ) );
			}
    	});
    }
}
