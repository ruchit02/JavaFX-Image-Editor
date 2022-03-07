package com.topNotch.photoEditor.effects.invert;

import java.nio.IntBuffer;

import com.topNotch.photoEditor.main.MyEffects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.Blend;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class InvertEffect extends MyEffects {
	
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
	
	private VBox masksVbox ;
	
	private HBox toggleButtonsHbox ;
	private ToggleGroup toggleGroup ;
	private ToggleButton invertToggleButton ;
	private ToggleButton masksToggleButton ;
	
	private Label invertLabel ;
	
	private int iterations[];
	
	public InvertEffect( Image img ) {
		
		super( img );
		
		this.img = img ;
		this.canvas = this.getCanvas() ;
		this.width = (int)this.canvas.getWidth() ;
		this.height = (int)this.canvas.getHeight() ;
		this.iterations = new int[ width*height ] ;
		this.blend = this.getBlend() ;
		this.colorAdjust = this.getColorAdjust() ;
		
		this.createEffectObjects();
	}
	
    public InvertEffect( WritableImage wImg ) {
		
    	super( wImg );
    	
    	this.img = wImg ;
		this.canvas = this.getCanvas() ;
		this.width = (int)this.canvas.getWidth() ;
		this.height = (int)this.canvas.getHeight() ;
		this.iterations = new int[ width*height ] ;
		this.blend = this.getBlend() ;
		this.colorAdjust = this.getColorAdjust() ;
    	
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
		
		invertToggleButton = new ToggleButton();
		invertToggleButton.setText("Invert");
		invertToggleButton.setToggleGroup( toggleGroup );
		invertToggleButton.setSelected( true );
		toggleButtonsHbox.getChildren().add( invertToggleButton );
		
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
					
					scrollPane.setContent( invertLabel );
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
		
		invertLabel = new Label();
		invertLabel.setText("No Invert Properties");
		invertLabel.setFont( Font.font( "Comic Sans MS" , FontWeight.EXTRA_BOLD , 18 ));
		scrollPane.setContent( invertLabel );
		
		invertImagePixels();
    }
    
    private void invertImagePixels() {
    	
    	WritableImage wImg = canvas.snapshot( new SnapshotParameters() , null );
    	PixelReader pxlReader = wImg.getPixelReader() ;
    	PixelWriter pxlWriter = canvas.getGraphicsContext2D().getPixelWriter() ;
    	PixelFormat<IntBuffer> intTypePixelFormat = PixelFormat.getIntArgbInstance() ;
    	
    	int i = 0 ;
    	
    	for( int y = 0 ; y < height ; y++ ) {
    		for( int x = 0 ; x < width ; x++ ) {
    			
    			int ARGBobtained = pxlReader.getArgb( x , y );
    			
    			int alpha = ( ARGBobtained >> 24 ) & 0xff ;
    			int red   = ( ARGBobtained >> 16 ) & 0xff ;
				int green = ( ARGBobtained >> 8 ) & 0xff ;
				int blue  = ( ARGBobtained  ) & 0xff ;
    			
    			int red2 = 255 - red ;
    			int green2 = 255 - green ;
    			int blue2 = 255 - blue ;
    			
    			iterations[i] = alpha<<24 | red2<<16 | green2<<8 | blue2 ;
    			i++ ;
    		}
    	}
    	
    	pxlWriter.setPixels( 0 , 0 , width , height , intTypePixelFormat , iterations , 0 , width*1 );
    }
}
