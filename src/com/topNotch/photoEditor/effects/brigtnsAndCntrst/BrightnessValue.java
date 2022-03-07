package com.topNotch.photoEditor.effects.brigtnsAndCntrst;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class BrightnessValue {

	private BooleanProperty FOUND = new SimpleBooleanProperty(false) ;
	private DoubleProperty value = new SimpleDoubleProperty(0) ;
	
	public BrightnessValue( boolean status , double value ) {
		
		this.FOUND.set( status );
		this.value.set( value );
	}
	
	public boolean getStatus() {
		
		return this.FOUND.get() ;
	}
	
	public double getValue() {
		
		return this.value.get() ;
	}
	
    public void setStatus( boolean status ) {
		
		this.FOUND.set( status ) ;
	}
	
	public void setValue( double value ) {
		
		this.value.set( value ) ;
	}
}
