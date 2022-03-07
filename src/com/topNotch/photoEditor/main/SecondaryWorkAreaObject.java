package com.topNotch.photoEditor.main;

import java.util.Objects;

import com.topNotch.photoEditor.effects.brigtnsAndCntrst.BrightnessContrastEffect;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public final class SecondaryWorkAreaObject extends BorderPane {
	
	private final static TextField editor = new TextField() ;
	
	private final LinkableEffect effect ;
	
	private DoubleProperty width = new SimpleDoubleProperty(0) ;
	private DoubleProperty height = new SimpleDoubleProperty(0) ;
	private double widthToHeightRATIO = 0 ;
	private double primaryToSecondaryCanvasRATIO = 0 ;
	private final StringProperty objectOf = new SimpleStringProperty("") ;
	
	private final ToggleButton tglBtn ;
	private final Canvas canvas  ;
	private final GraphicsContext gc ;
	private final Label label ;
	
	public SecondaryWorkAreaObject( double width , double height , String ownerName , LinkableEffect effect ) {
		
		this.objectOf.set( Objects.requireNonNull( ownerName ).toUpperCase() );
		this.effect = effect ;
		
		calculateDimensions( width , height ) ;
		
		this.canvas = new Canvas( this.width.get() , this.height.get() ) ;
		this.gc = this.canvas.getGraphicsContext2D() ;
		this.tglBtn = new ToggleButton() ;
		this.label = new Label() ;
		
		objectCreation() ;
	}
	
	private void calculateDimensions( double width , double height ) {
		
		double tempWidth = width ;
		double tempHeight = height ;
		
		this.widthToHeightRATIO = tempWidth/tempHeight ;
		
		tempHeight = 24 ;
		tempHeight = Math.floor( tempHeight );
		tempWidth = this.widthToHeightRATIO*tempHeight ;
		
		this.width.set( tempWidth ) ;
		this.height.set( tempHeight ) ;
		
		primaryToSecondaryCanvasRATIO = width/this.width.get() ;
	}
	
	private void objectCreation() {
		
		this.setHeight(30);
	    this.setWidth(200);
		this.setBackground( new Background( new BackgroundFill( Color.BURLYWOOD , null , null ) ) );
		
		tglBtn.setText("On");
		tglBtn.setSelected(true);
		this.setLeft( tglBtn );
		
		gc.setFill( Color.WHITE );
		gc.fillRect( 0 , 0 , this.width.get() , this.height.get() ) ;
		canvasMouseFilter( canvas ) ;
		this.setCenter( canvas );
		
		label.setText( "Name" );
		this.setRight( label );
		
		textfieldMouseFilter( this ) ;
		
		SecondaryWorkAreaObject.setAlignment( tglBtn , Pos.CENTER_LEFT );
		SecondaryWorkAreaObject.setAlignment( canvas , Pos.CENTER );
		SecondaryWorkAreaObject.setAlignment( label , Pos.CENTER_RIGHT );
	}
	
	private void canvasMouseFilter( Canvas canvas ) {
		
		canvas.addEventFilter( MouseEvent.ANY , new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent e ) {
				
				GraphicsContext gc = canvas.getGraphicsContext2D() ;
				
				if( e.getEventType() == MouseEvent.MOUSE_PRESSED ) {
					
					gc.setStroke( PrimaryWorkObjectManipulatingTools.getInstance().getBrushColor().getValue() );
					gc.setLineWidth( PrimaryWorkObjectManipulatingTools.getInstance().getBrushSize().getSelectionModel().getSelectedItem().doubleValue()*primaryToSecondaryCanvasRATIO );
					gc.beginPath();
					gc.moveTo( e.getX() , e.getY() );
					gc.stroke();
				}
				else if( e.getEventType() == MouseEvent.MOUSE_DRAGGED ) {
					
					gc.lineTo( e.getX() , e.getY() );
					gc.stroke();
				}
				else if( e.getEventType() == MouseEvent.MOUSE_RELEASED ) {
					
					gc.closePath();
				}
				
			}
		});
	}
	
	private void textfieldMouseFilter( SecondaryWorkAreaObject obj ) {
		
		SecondaryWorkAreaObject.editor.setOnKeyPressed( new EventHandler<KeyEvent>() {
			@Override
			public void handle( KeyEvent e ) {
				
				if( e.getCode() == KeyCode.ENTER ) {
					
					label.setText( SecondaryWorkAreaObject.editor.getText() ) ;
					obj.setRight( label );
				}
				
				if( e.getCode() == KeyCode.ESCAPE ) {
					
					SecondaryWorkAreaObject.editor.clear(); 
					obj.setRight( label );
				}
			}
		});
	}
	
	public final String getOwner() {
		
		return this.objectOf.get() ;
	}
	
	public final LinkableEffect getLinkableEffect() {
		
		return this.effect ;
	}
	
	public final TextField getTextField() {
		
		return SecondaryWorkAreaObject.editor ;
	}
	
	public final Canvas getCanvas() {
		
		return this.canvas ;
	}
	
	public final Label getLabel() {
		
		return this.label ;
	}
	
	public final ToggleButton getToggleButton() {
		
		return this.tglBtn ;
	}
	
	public final double getCanvasWidth() {
		
		return this.width.get() ;
	}
	
	public final double getCanvasHeight() {
		
		return this.height.get() ;
	}
}
