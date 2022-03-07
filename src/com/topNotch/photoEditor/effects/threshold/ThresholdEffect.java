package com.topNotch.photoEditor.effects.threshold;

import com.topNotch.photoEditor.main.MyEffects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ThresholdEffect extends MyEffects {
	
	private int width ;
	private int height ;
	
	private Image img ;
	private WritableImage wImg ;
	private ScrollPane scrollPane ;
	
	private Blend blend ;
	private Canvas canvas ;
	private ColorAdjust colorAdjust ;
	
	private HBox thresholdHbox ;
	private TextField thresholdTextfield ;
	private Slider thresholdSlider ;
	private VBox thresholdVbox ;
	
	private int argbPixelsArray[]  ;   
	
	private int alphaPixelsArray[]  ; 
	private int redPixelsArray[]    ;  
	private int greenPixelsArray[]  ;  
	private int bluePixelsArray[]   ;   
	
	private NumberAxis xAxis ;
	private NumberAxis yAxis ;
	private LineChart lineChart ;
	private XYChart.Series< NumberAxis , NumberAxis > redSeries ;
	private XYChart.Series< NumberAxis , NumberAxis > greenSeries ;
	private XYChart.Series< NumberAxis , NumberAxis > blueSeries  ;
	private XYChart.Series< NumberAxis , NumberAxis > argbSeries  ;
	
	private int redPixelsData[]    ;
	private int greenPixelsData[]  ;
	private int bluePixelsData[]   ;
	private int argbPixelsData[] ;
	
	public ThresholdEffect( Image img ) {
		
		super( img );
		this.img = img ;
		this.canvas = this.getCanvas() ;
		this.blend = this.getBlend() ;
		this.colorAdjust = this.getColorAdjust() ;
		
		this.width = (int)canvas.getWidth() ;
    	this.height = (int)canvas.getHeight() ;
		
		this.createEffectObjects();
	}
	
	public ThresholdEffect( WritableImage wImg ) {
		
		super( wImg );
		this.wImg = wImg ;
		this.canvas = this.getCanvas() ;
		this.blend = this.getBlend() ;
		this.colorAdjust = this.getColorAdjust() ;
		
		this.width = (int)canvas.getWidth() ;
    	this.height = (int)canvas.getHeight() ;
		
		this.createEffectObjects();
	}
	
	@Override
	public Blend getBlend() {
		
		return super.getBlend();
	}
	
	@Override
	public Canvas getCanvas() {
		
		return super.getCanvas() ;
	}
	
	@Override
	public ColorAdjust getColorAdjust() {
		
		return super.getColorAdjust() ;
	}
	
	@Override
	public ScrollPane getScrollPane() {
		
		return this.scrollPane ;
	}
	
	private void createEffectObjects() {
		
		scrollPane = new ScrollPane();
		
		thresholdVbox = new VBox();
		thresholdVbox.setTranslateX( 12 );
		thresholdVbox.setTranslateY( 15 );
		thresholdVbox.setSpacing(5);
		thresholdVbox.setPadding( new Insets( 5 , 0 , 5 , 0 ) );
		thresholdVbox.setBackground( new Background( new BackgroundFill( Color.rgb( 196 , 196 , 196 ) , CornerRadii.EMPTY , Insets.EMPTY ) ) );
		
		scrollPane.setContent( thresholdVbox );
		
		thresholdHbox = new HBox();
		thresholdHbox.setTranslateX( 15 );
		thresholdHbox.setSpacing( 17 );
		
		thresholdTextfield = new TextField();
		thresholdTextfield.setMaxWidth( 40 );
		thresholdTextfield.setText("0");
		
		thresholdSlider = new Slider();
		thresholdSlider.setMin( 1 );
		thresholdSlider.setMax( 255 );
		thresholdSlider.setValue( 128 );
		
		thresholdHbox.getChildren().addAll( thresholdSlider , thresholdTextfield );
		
		addFunctionalityToThresholdSlider( thresholdSlider , thresholdTextfield );
		
		putDataInArrays();
		createImageHistogram() ;
		addDataToSeries( argbSeries ) ;
		
		lineChart.getData().add( argbSeries);
		
		thresholdVbox.getChildren().addAll(  lineChart , thresholdHbox ) ;
	}
	
	private void addFunctionalityToThresholdSlider( Slider thresholdSlider ,TextField thresholdTextfield ) {
		
		thresholdSlider.valueProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue< ? extends Number > obv , Number oldVal , Number newVal ) {
				
				thresholdTextfield.setText( Integer.toString( newVal.intValue() ) );
			}
		});
	}
	
	private void putDataInArrays() {
		
        argbPixelsArray     = new int[ width*height ];
		
		alphaPixelsArray    = new int[ width*height ];
		redPixelsArray      = new int[ width*height ];
		greenPixelsArray    = new int[ width*height ];
		bluePixelsArray     = new int[ width*height ];
		
		//this.iterationsArrayA    = new int[ width*height ];
		
		redPixelsData       = new int[ 256 ] ;
		greenPixelsData     = new int[ 256 ] ;
		bluePixelsData      = new int[ 256 ] ;
		argbPixelsData      = new int[ 256 ] ;
		
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
				
				argbPixelsArray[i]      = alpha | red  | green | blue ;
				
				alphaPixelsArray[i]     = alpha ;
				redPixelsArray[i]       = red   ;
				greenPixelsArray[i]     = green ;
				bluePixelsArray[i]      = blue  ;
				
				redPixelsData[red]++     ;
				greenPixelsData[green]++ ;
				bluePixelsData[blue]++   ;
				argbPixelsData[ argbPixelsArray[i] ]++;
				
				i++;
			}
		}
	}
	
	private void createImageHistogram() {
		
		xAxis = new NumberAxis( 0 , 255 , 10 );
		xAxis.setTickLabelsVisible( false );
		xAxis.setTickMarkVisible( false );
		xAxis.setMinorTickVisible( false );
		xAxis.setAnimated( false );
		
		yAxis = new NumberAxis( 0 , 10000 , 10 );
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
		argbSeries = new XYChart.Series<>() ;
	}
	
	private void addDataToSeries( XYChart.Series< NumberAxis , NumberAxis > argbSeries ) {
    	
    	for( int value = 0 ; value <= 255 ; value++ ) {
    		
    		argbSeries.getData().add( new XYChart.Data( value , argbPixelsData[value] ) );
		}
    }
}
