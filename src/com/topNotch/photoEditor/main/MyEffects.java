package com.topNotch.photoEditor.main;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Blend;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public abstract class MyEffects {
	
	private Canvas canvas ;
	private Blend blend ;
	private ColorAdjust colorAdjust ;
	private Image img ;
	private WritableImage wImg ;
	
	public MyEffects() {
		
	}
	
	public MyEffects( Image img ) {
		
		this.canvas = new Canvas() ;
		this.blend = new Blend() ;
		this.colorAdjust = new ColorAdjust() ;
		
		this.img = img ;
		this.makeCanvas( this.blend , this.canvas , this.img );
		this.setTopAndBottomInputsOfBlendEffect( this.blend , this.img , this.colorAdjust );
	}
	
	public MyEffects(  WritableImage wImg ) {
		
	    this.canvas = new Canvas();
	    this.blend = new Blend() ;
		this.colorAdjust = new ColorAdjust() ;
		
		this.wImg = wImg ;
		this.makeCanvas( this.blend , this.canvas , this.wImg );
		this.setTopAndBottomInputsOfBlendEffect( this.blend , wImg , this.colorAdjust );
	}
	
	private void makeCanvas( Blend blend , Canvas canvas , Image img ) {
		
		canvas.setWidth( img.getWidth() );
		canvas.setHeight( img.getHeight() );
		
		GraphicsContext gc = canvas.getGraphicsContext2D() ;
		gc.drawImage( img , 0 , 0 , canvas.getWidth() , canvas.getHeight() );
		
		canvas.setEffect( blend );
	}
	
    private void setTopAndBottomInputsOfBlendEffect( Blend blend , Image img , ColorAdjust colorAdjust ) {
		
		ImageInput snapshotOfTheCanvasJustBelow = new ImageInput();
		snapshotOfTheCanvasJustBelow.setSource( img );
		
		blend.setBottomInput( snapshotOfTheCanvasJustBelow );
		blend.setTopInput( colorAdjust );
	}
	
	private void makeCanvas( Blend blend , Canvas canvas , WritableImage wImg ) {
		
		canvas.setWidth( wImg.getWidth() );
		canvas.setHeight( wImg.getHeight() );
		
		GraphicsContext gc = canvas.getGraphicsContext2D() ;
		gc.drawImage( wImg , 0 , 0 , canvas.getWidth() , canvas.getHeight() );
		
		canvas.setEffect( blend );
	}
	
    private void setTopAndBottomInputsOfBlendEffect( Blend blend , WritableImage wImg , ColorAdjust colorAdjust ) {
		
		ImageInput snapshotOfTheCanvasJustBelow = new ImageInput();
		snapshotOfTheCanvasJustBelow.setSource( wImg );
		
		blend.setBottomInput( snapshotOfTheCanvasJustBelow );
		blend.setTopInput( colorAdjust );
	}
	
    public Canvas getCanvas() {
    	
    	return this.canvas ;
    }
    
    public Blend getBlend() {
    	
    	return this.blend ;
    }
    
    public ColorAdjust getColorAdjust() {
    	
    	return this.colorAdjust ;
    }
    
    public abstract ScrollPane getScrollPane();
}
