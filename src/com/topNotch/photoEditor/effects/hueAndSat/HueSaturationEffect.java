package com.topNotch.photoEditor.effects.hueAndSat;

import java.util.ArrayList;

import com.topNotch.photoEditor.main.MyEffects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class HueSaturationEffect extends MyEffects {
	
	private Image img ;
	private WritableImage wImg ;
	private Canvas canvas ;
	private Blend blend ;
	private ColorAdjust colorAdjust ;
	
	private TabPane tabPane ;
	private GridPane gridPane ;
	private ScrollPane scrollPane;
	
	private VBox rootVbox ;
	private VBox masksVbox ;
	
	private HBox toggleButtonsHbox ;
	private ToggleGroup toggleGroup ;
	private ToggleButton hueSaturationToggleButton ;
	private ToggleButton masksToggleButton ;
	
	private HBox hueHbox ;
	private Label hueLabel ;
	private TextField hueTextfield ;
	private Slider hueSlider ;
	
	private HBox saturationHbox ;
	private Label saturationLabel ;
	private TextField saturationTextfield ;
	private Slider saturationSlider ;
	
	public HueSaturationEffect( Image img ) {
		
		super( img );
		this.img = img ;
		this.canvas = this.getCanvas() ;
		this.blend = this.getBlend() ;
		this.colorAdjust = this.getColorAdjust() ;
		
		this.createEffectObjects();
	}
	
    public HueSaturationEffect( WritableImage wImg ) {
    	
    	super( wImg ) ;
    	this.wImg = wImg ;
		this.canvas = this.getCanvas() ;
		this.blend = this.getBlend() ;
		this.colorAdjust = this.getColorAdjust() ;
		
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
		
		return this.scrollPane ;
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
		
		hueSaturationToggleButton = new ToggleButton();
		hueSaturationToggleButton.setText("Hue/Saturation");
		hueSaturationToggleButton.setToggleGroup( toggleGroup );
		hueSaturationToggleButton.setSelected( true );
		toggleButtonsHbox.getChildren().add( hueSaturationToggleButton );
		
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
		rootVbox.setMaxWidth(235);
		rootVbox.setSpacing( 5 );
		scrollPane.setContent( rootVbox );
		
		hueHbox = new HBox();
		rootVbox.getChildren().add( hueHbox );
		
		hueLabel = new Label();
		hueLabel.setText("Hue");
		hueHbox.getChildren().add( hueLabel );
		
		hueTextfield = new TextField();
		hueTextfield.setText( "0" );
		hueTextfield.setMaxWidth(60);
		hueTextfield.setEditable(false);
		hueHbox.getChildren().add( hueTextfield );
		
		hueSlider = new Slider();
		hueSlider.setPrefWidth(Double.MAX_VALUE);
		hueSlider.setMin(-1);
		hueSlider.setMax(1);
		hueSlider.setValue( 0 );
		rootVbox.getChildren().add( hueSlider );
		
		addFunctionalityToHueSlider( hueSlider , hueTextfield );
		
		saturationHbox = new HBox() ;
		rootVbox.getChildren().add( saturationHbox );
		
		saturationLabel = new Label();
		saturationLabel.setText("Saturation");
		saturationHbox.getChildren().add( saturationLabel );
		
		saturationTextfield = new TextField();
		saturationTextfield.setText( "0" );
		saturationTextfield.setMaxWidth(60);
		saturationTextfield.setEditable(false);
		saturationHbox.getChildren().add( saturationTextfield );
		
		saturationSlider = new Slider();
		saturationSlider.setPrefWidth(Double.MAX_VALUE);
		saturationSlider.setMin(-1);
		saturationSlider.setMax(1);
		saturationSlider.setValue( 0 );
		rootVbox.getChildren().add( saturationSlider );
		
		addFunctionalityToSaturationSlider( saturationSlider , saturationTextfield );
	}
	
	private void addFunctionalityToHueSlider( Slider hueSlider , TextField hueTextfield ) {
		
		hueSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				colorAdjust.setHue( newVal.doubleValue() );
				hueTextfield.setText( Double.toString( newVal.doubleValue() )); ////Hue textField
			}
		});
	}
	
	private void addFunctionalityToSaturationSlider( Slider saturationSlider , TextField saturationTextfield ) {
		
		saturationSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				colorAdjust.setSaturation( newVal.doubleValue()*0.5 );
				saturationTextfield.setText( Double.toString( newVal.doubleValue() )); ////saturation textField
			}
		});
	}
}
