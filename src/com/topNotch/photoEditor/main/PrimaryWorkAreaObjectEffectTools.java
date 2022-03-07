package com.topNotch.photoEditor.main;

import com.topNotch.photoEditor.effects.brigtnsAndCntrst.BrightnessContrastEffect;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PrimaryWorkAreaObjectEffectTools extends VBox {

	private static final PrimaryWorkAreaObjectEffectTools effects = new PrimaryWorkAreaObjectEffectTools() ;
	private static String ownerName = "" ;
	
	private final Button hueSatBtn ;
	private final Button briConBtn ;
	private final Button levelsButton ;
	private final Button thresholdButton ;
	private final Button selectiveColorButton ;
	private final Button colorBalanceButton ;
	private final Button curvesButton ;
	private final Button exposureButton ;
	private final Button vibranceButton ;
	private final Button blackAndWhiteButton ;
	private final Button photoFilterButton ;
	private final Button channelMixerButton ;
	private final Button colorLookUpButton ;
	private final Button invertButton ;
	private final Button posterizeButton ;
	private final Button gradientMapButton ;
	
	private final HBox hBox1 ;
	private final HBox hBox2 ;
	private final HBox hBox3 ;
	private final HBox hBox4 ;
	private final HBox hBox5 ;
	
	private PrimaryWorkAreaObjectEffectTools() {
		
		blackAndWhiteButton  = new Button();
		briConBtn            = new Button();
		channelMixerButton   = new Button();
		colorBalanceButton   = new Button();
		colorLookUpButton    = new Button();
		curvesButton         = new Button();
		exposureButton       = new Button();
		gradientMapButton    = new Button();
		hueSatBtn            = new Button();
		invertButton         = new Button();
		levelsButton         = new Button();
		photoFilterButton    = new Button();
		posterizeButton      = new Button();
		selectiveColorButton = new Button();
		thresholdButton      = new Button();
		vibranceButton       = new Button();
		
		hBox1 = new HBox() ;
		hBox2 = new HBox() ;
		hBox3 = new HBox() ;
		hBox4 = new HBox() ;
		hBox5 = new HBox() ;
		
		createObject() ;
	}
	
	private void createObject() {
		
		blackAndWhiteButton.setText("Black & White");
		blackAndWhiteButton.setMinWidth( 80 );
		
		briConBtn.setText("Brightness/Contrast");
		briConBtnMouseFilter( briConBtn ) ;
		
		channelMixerButton.setText("Channel Mixer");
		channelMixerButton.setMinWidth( 80 );
		
		colorBalanceButton.setText("Color Balance");
		colorBalanceButton.setMinWidth( 80 );
		
		colorLookUpButton.setText("Color LookUp");
		colorLookUpButton.setMinWidth( 80 );
		
		curvesButton.setText("Curves");
		
		exposureButton.setText("Exposure");
		
		gradientMapButton.setText("Gradient Map");
		gradientMapButton.setMinWidth( 80 );
		
		hueSatBtn.setText("Hue/Saturation");
		
		invertButton.setText("Invert");
		invertButton.setMinWidth( 52 );
		
		levelsButton.setText("Levels");
		levelsButton.setMinWidth( 54 );
		
		photoFilterButton.setText("Photo Filter");
		photoFilterButton.setMinWidth( 81 );
		
		posterizeButton.setText("Posterize");
		
		selectiveColorButton.setText("Selective Color") ;
		selectiveColorButton.setMinWidth( 80 );
		
		thresholdButton.setText("Threshold");
		thresholdButton.setMinWidth( 75 );
		
		vibranceButton.setText("Vibrance");
		
		hBox1.setSpacing( 1 );
		hBox1.getChildren().addAll( hueSatBtn , posterizeButton , thresholdButton  ) ;
		
		hBox2.setSpacing( 1 );
		hBox2.getChildren().addAll( colorBalanceButton , channelMixerButton , levelsButton );
		
		hBox3.setSpacing( 1 );
		hBox3.getChildren().addAll( exposureButton  , blackAndWhiteButton , photoFilterButton );
		
		hBox4.setSpacing( 1 );
		hBox4.getChildren().addAll( invertButton , gradientMapButton , selectiveColorButton   );
		
		hBox5.setSpacing( 1 );
		hBox5.getChildren().addAll( briConBtn , vibranceButton ,  curvesButton );
		
		this.getChildren().addAll( hBox1 , hBox2 , hBox3 , hBox4 , hBox5 );
	}
	
	private void briConBtnMouseFilter( Button briConBtn ) {
		
		briConBtn.setOnMouseClicked( new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent e ) {
				
				ownerName = "BRIGHTNESS CONTRAST EFFECT" ;
				BrightnessContrastEffect effect = null ;
				
				if( SecondaryWorkArea.getInstance().getListView().getItems().size() != 0 ) {
					
					double height = PrimaryWorkAreaObject.getObjects().get( PrimaryWorkArea.selectedTabIndex ).getCanvasHeight()  ;
					double width = PrimaryWorkAreaObject.getObjects().get( PrimaryWorkArea.selectedTabIndex ).getCanvasWidth()  ;
					
					effect = new BrightnessContrastEffect( 
							SecondaryWorkArea.getInstance().getListView().getItems()
							.get( SecondaryWorkArea.getInstance().getListView().getItems().size()-1 )
							.getLinkableEffect() , width , height ) ;
				}
				
				SecondaryWorkArea.getInstance().getListView().getItems()
			    .add( new SecondaryWorkAreaObject( PrimaryWorkAreaObject.getObjects().get(PrimaryWorkArea.selectedTabIndex).getCanvasWidth() 
				, PrimaryWorkAreaObject.getObjects().get(PrimaryWorkArea.selectedTabIndex).getCanvasHeight() , ownerName , effect ) ) ;
			}
		});
	}
	
	public static final PrimaryWorkAreaObjectEffectTools getInstance() {
		
		return PrimaryWorkAreaObjectEffectTools.effects ;
	}
	
	public final Button getBlackAndWhiteEffBtn() {
		
		return this.blackAndWhiteButton ;
	}
	
    public final Button getBriAndCntrstEffBtn() {
		
		return this.blackAndWhiteButton ;
	}
    
    public final Button getChnlMixrEffBtn() {
		
 		return this.channelMixerButton ;
 	}
    
    public final Button getClrBalanceEffBtn() {
		
 		return this.colorBalanceButton ;
 	}
    
    public final Button getClrLookUpEffBtn() {
		
 		return this.colorLookUpButton ;
 	}
    
    public final Button getCurvesEffBtn() {
		
 		return this.curvesButton ;
 	}
    
    public final Button getExposureEffBtn() {
		
 		return this.exposureButton ;
 	}
    
    public final Button getGradientMapEffBtn() {
		
 		return this.gradientMapButton ;
 	}
    
    public final Button getHueAndSatEffBtn() {
		
 		return this.hueSatBtn ;
 	}
    
    public final Button getInvertEffBtn() {
		
 		return this.invertButton ;
 	}
    
    public final Button getLevelsEffBtn() {
		
 		return this.levelsButton ;
 	}
    
    public final Button getPhotoFiltrEffBtn() {
		
 		return this.photoFilterButton ;
 	}
    
    public final Button getPosterizeEffBtn() {
		
 		return this.posterizeButton ;
 	}
    
    public final Button getSelctveClrEffBtn() {
		
 		return this.selectiveColorButton ;
 	}
    
    public final Button getThresholdEffBtn() {
		
 		return this.thresholdButton ;
 	}
    
    public final Button getVibranceEffBtn() {
    	
    	return this.vibranceButton ;
    }
}
