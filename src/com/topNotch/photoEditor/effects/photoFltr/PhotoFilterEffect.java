package com.topNotch.photoEditor.effects.photoFltr;

import com.topNotch.photoEditor.main.MyEffects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Slider;
import javafx.scene.effect.Blend;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PhotoFilterEffect extends MyEffects {
	
	private int width ;
	private int height ;
	
	private Canvas canvas ;
	private Blend blend ;
	private ColorAdjust colorAdjust ;
	
	private Image img ;
	private WritableImage wImg ;
	
	private TabPane tabPane ;
	private GridPane gridPane ;
	private ScrollPane scrollPane ;
	private VBox rootVbox ;
	private VBox masksVbox ;
	
	private HBox toggleButtonsHbox ;
	private ToggleGroup toggleGroup ;
	private ToggleButton photoFilterToggleButton ;
	private ToggleButton masksToggleButton ;
	
	private ComboBox<String> filterBox ;
	private Slider filterSlider ;
	private TextField filterTextfield ;
	
	private int actualRed[] ;
	private int actualGreen[] ;
	private int actualBlue[] ;
	private int rgbAverage[] ;
	
	public PhotoFilterEffect(Image img) {
		
		super(img) ;
		
		this.canvas = this.getCanvas() ;
		this.width = (int)this.canvas.getWidth() ;
		this.height = (int)this.canvas.getHeight();
		
		this.blend = this.getBlend() ;
		this.colorAdjust = this.getColorAdjust() ;
		
		this.actualRed = new int[ width*height ] ;
		this.actualGreen = new int[ width*height ] ;
		this.actualBlue = new int[ width*height ] ;
		this.rgbAverage = new int[ width*height ] ;
		
		this.createEffectObject();
	}
	
	public PhotoFilterEffect(WritableImage wImg) {
		
		super( wImg );
		
		this.canvas = this.getCanvas() ;
		this.width = (int)this.canvas.getWidth() ;
		this.height = (int)this.canvas.getHeight();
		
		this.blend = this.getBlend() ;
		this.colorAdjust = this.getColorAdjust() ;
		
		this.actualRed = new int[ width*height ] ;
		this.actualGreen = new int[ width*height ] ;
		this.actualBlue = new int[ width*height ] ;
		this.rgbAverage = new int[ width*height ] ;
		
		this.createEffectObject();
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
	
	private void createEffectObject() {
		
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
		
		photoFilterToggleButton = new ToggleButton();
		photoFilterToggleButton.setText("Vibrance");
		photoFilterToggleButton.setToggleGroup( toggleGroup );
		photoFilterToggleButton.setSelected( true );
		toggleButtonsHbox.getChildren().add( photoFilterToggleButton );
		
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
		
		ObservableList<String> filterList = FXCollections.observableArrayList( "Warming Filter 1" , "Warming Filter 2" 
				, "Warming Filter 3" , "Cooling Filter 1" , "Cooling Filter 2" , "Cooling Filter 3" , "Red" , "Orange"
				, "Yellow" , "Green" , "Cyan" , "Blue" , "Violet" , "Magenta" , "Sepia" , "Deep Red" , "Deep Blue"
				, "Deep Emerald" , "Deep Yellow" , "Underwater" , "25% Grey" , "50% Grey" , "75% Grey" ) ;
		
		filterBox = new ComboBox<String>() ;
		filterBox.setItems( filterList );
		filterBox.setValue("Warming Filter 1");
		rootVbox.getChildren().add( filterBox );
		
		filterBox.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<String>() {
			@Override
			public void changed( ObservableValue<? extends String> obv , String oldVal , String newVal ) {
				
                if( filterBox.getSelectionModel().getSelectedItem() == "Warming Filter 1" ) {
					
					callFilterTask( (int)filterSlider.getValue() , 236 , 138 , 0 ) ;
				}
				else if( filterBox.getSelectionModel().getSelectedItem() == "Warming Filter 2" ) {
					
					callFilterTask( (int)filterSlider.getValue() , 250 , 150 , 0 ) ;
				}
                else if( filterBox.getSelectionModel().getSelectedItem() == "Warming Filter 3" ) {
					
					callFilterTask( (int)filterSlider.getValue() , 235 , 177 , 19 ) ;
				}
                else if( filterBox.getSelectionModel().getSelectedItem() == "Cooling Filter 1" ) {
					
					callFilterTask( (int)filterSlider.getValue() , 0 , 109 , 255 ) ;
				}
                else if( filterBox.getSelectionModel().getSelectedItem() == "Cooling Filter 2" ) {
					
					callFilterTask( (int)filterSlider.getValue() , 0 , 93 , 255 ) ;
				}
                else if( filterBox.getSelectionModel().getSelectedItem() == "Cooling Filter 3" ) {
					
					callFilterTask( (int)filterSlider.getValue() , 0 , 181 , 255 ) ;
				}
                else if( filterBox.getSelectionModel().getSelectedItem() == "Red" ) {
					
					callFilterTask( (int)filterSlider.getValue() , 234 , 26 , 26 ) ;
				}
                else if( filterBox.getSelectionModel().getSelectedItem() == "Orange" ) {
	
	                callFilterTask( (int)filterSlider.getValue() , 243 , 132 , 23 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Yellow" ) {
	
	                callFilterTask( (int)filterSlider.getValue() , 249 , 227 , 28 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Green" ) {
	
	                callFilterTask( (int)filterSlider.getValue() , 25 , 201 , 25 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Cyan" ) {
	
	                callFilterTask( (int)filterSlider.getValue() , 29 , 203 , 234 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Blue" ) {
                	
	                callFilterTask( (int)filterSlider.getValue() , 29 , 53 , 234 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Violet" ) {
                	
	                callFilterTask( (int)filterSlider.getValue() , 155 , 29 , 234 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Magenta" ) {
                	
	                callFilterTask( (int)filterSlider.getValue() , 227 , 24 , 227 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Sepia" ) {
                	
	                callFilterTask( (int)filterSlider.getValue() , 172 , 122 , 51 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Deep Red" ) {
                	
	                callFilterTask( (int)filterSlider.getValue() , 255 , 0 , 0 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Deep Blue" ) {
                	
	                callFilterTask( (int)filterSlider.getValue() , 0 , 34 , 205 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Deep Emerald" ) {
                	
	                callFilterTask( (int)filterSlider.getValue() , 0 , 141 , 0 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Deep Yellow" ) {
                	
	                callFilterTask( (int)filterSlider.getValue() , 255 , 213 , 0 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Underwater" ) {
                	
	                callFilterTask( (int)filterSlider.getValue() , 0 , 194 , 177 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "25% Grey" ) {
                	
	                callFilterTask( (int)filterSlider.getValue() , 192 , 192 , 192 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "50% Grey" ) {
                	
	                callFilterTask( (int)filterSlider.getValue() , 128 , 128 , 128 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "75% Grey" ) {
                	
	                callFilterTask( (int)filterSlider.getValue() , 64 , 64 , 64 ) ;
                }
			}
		});
		
		filterSlider = new Slider();
		filterSlider.setMin( 0 );
		filterSlider.setMax( 100 );
		rootVbox.getChildren().add( filterSlider );
		
		filterTextfield = new TextField();
		filterTextfield.setText("0");
		rootVbox.getChildren().add( filterTextfield );
		
		addFucntionalityToFilterSlider( filterSlider , filterTextfield ) ;
		
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
    			
    			rgbAverage[i] = (int)((actualRed[i] + actualGreen[i] + actualBlue[i])/(double)3);
    			
    			i++ ;
    		}
    	}
	}
	
	private void addFucntionalityToFilterSlider( Slider filterSlider , TextField filterTextfield ) {
		
		filterSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				int value = newVal.intValue() ;
				
				if( filterBox.getSelectionModel().getSelectedItem() == "Warming Filter 1" ) {
					
					callFilterTask( value , 236 , 138 , 0 ) ;
				}
				else if( filterBox.getSelectionModel().getSelectedItem() == "Warming Filter 2" ) {
					
					callFilterTask( value , 250 , 150 , 0 ) ;
				}
                else if( filterBox.getSelectionModel().getSelectedItem() == "Warming Filter 3" ) {
					
					callFilterTask( value , 235 , 177 , 19 ) ;
				}
                else if( filterBox.getSelectionModel().getSelectedItem() == "Cooling Filter 1" ) {
					
					callFilterTask( value , 0 , 109 , 255 ) ;
				}
                else if( filterBox.getSelectionModel().getSelectedItem() == "Cooling Filter 2" ) {
					
					callFilterTask( value , 0 , 93 , 255 ) ;
				}
                else if( filterBox.getSelectionModel().getSelectedItem() == "Cooling Filter 3" ) {
					
					callFilterTask( value , 0 , 181 , 255 ) ;
				}
                else if( filterBox.getSelectionModel().getSelectedItem() == "Red" ) {
					
					callFilterTask( value , 234 , 26 , 26 ) ;
				}
                else if( filterBox.getSelectionModel().getSelectedItem() == "Orange" ) {
	
	                callFilterTask( value , 243 , 132 , 23 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Yellow" ) {
	
	                callFilterTask( value , 249 , 227 , 28 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Green" ) {
	
	                callFilterTask( value , 25 , 201 , 25 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Cyan" ) {
	
	                callFilterTask( value , 29 , 203 , 234 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Blue" ) {
                	
	                callFilterTask( value , 29 , 53 , 234 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Violet" ) {
                	
	                callFilterTask( value , 155 , 29 , 234 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Magenta" ) {
                	
	                callFilterTask( value , 227 , 24 , 227 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Sepia" ) {
                	
	                callFilterTask( value , 172 , 122 , 51 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Deep Red" ) {
                	
	                callFilterTask( value , 255 , 0 , 0 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Deep Blue" ) {
                	
	                callFilterTask( value , 0 , 34 , 205 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Deep Emerald" ) {
                	
	                callFilterTask( value , 0 , 141 , 0 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Deep Yellow" ) {
                	
	                callFilterTask( value , 255 , 213 , 0 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "Underwater" ) {
                	
	                callFilterTask( value , 0 , 194 , 177 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "25% Grey" ) {
                	
	                callFilterTask( (int)filterSlider.getValue() , 192 , 192 , 192 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "50% Grey" ) {
                	
	                callFilterTask( (int)filterSlider.getValue() , 128 , 128 , 128 ) ;
                }
                else if( filterBox.getSelectionModel().getSelectedItem() == "75% Grey" ) {
                	
	                callFilterTask( (int)filterSlider.getValue() , 64 , 64 , 64 ) ;
                }
				
				
				filterTextfield.setText( Integer.toString( value ));
			}
		});
	}
	
	private void callFilterTask( int value , int filterRed , int filterGreen , int filterBlue ) {
		
		PhotoFilterWarmingFilterTask task = new PhotoFilterWarmingFilterTask( canvas , value , actualRed , actualGreen , actualBlue 
                , rgbAverage ,  filterRed , filterGreen , filterBlue );
        Thread th = new Thread(task);
        th.setDaemon( true );
        th.start();
	}
}