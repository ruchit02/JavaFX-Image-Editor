package com.topNotch.photoEditor.main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class PWAOPropertiesTool extends BorderPane {

	private static final PWAOPropertiesTool propsTool = new PWAOPropertiesTool() ;
	
	private final TabPane tabPane ;
	private final Tab propertyTab ;
	private final Tab maskTab ;
	private final ScrollPane propertyScrollPane ;
	private final ScrollPane maskScrollPane ;
	
	private final ToggleButton propsTglBtn ;
	
	private  PWAOPropertiesTool() {
		
		tabPane = new TabPane() ;
		propertyTab = new Tab();
		maskTab = new Tab() ;
		propertyScrollPane = new ScrollPane() ;
		maskScrollPane = new ScrollPane() ;
		
		propsTglBtn = new ToggleButton() ;
		
		createObject() ;
	}
	
	private void createObject() {
		
		propsTglBtn.setText( "Properties" );
		propsTglBtn.setSelected(false);
		propsTglBtnSelectionProperty( propsTglBtn ) ;
		
		propertyScrollPane.setMinSize( 250 , 225 );
		propertyScrollPane.setMaxSize( 250 , 225 );
		
		maskScrollPane.setMinSize( 250 , 225 );
		maskScrollPane.setMaxSize( 250 , 225 );
		
		propertyTab.setContent( propertyScrollPane );
		propertyTab.setText( "Property" );
		
		maskTab.setContent( maskScrollPane ) ;
		maskTab.setText( "Mask" );
		
		tabPane.getTabs().addAll( propertyTab , maskTab ) ;
		
		this.setCenter( tabPane );
		this.setMinSize(250 , 275);
		this.setMaxSize(250, 275);
		this.setTranslateX(850);
		this.setTranslateY(125);
		this.setBackground( new Background( new BackgroundFill( Color.GAINSBORO , null , null ) ) );
	}
	
	private void propsTglBtnSelectionProperty( ToggleButton propsTglBtn ) {
		
		propsTglBtn.selectedProperty().addListener( new ChangeListener<Boolean>() {
			@Override
			public void changed( ObservableValue<? extends Boolean> obv , Boolean oldVal , Boolean newVal ) {
				
				//this class is added to a hbox , which is added to a vbox , which is added to a borderpane
				BorderPane pane = (BorderPane)propsTglBtn.getParent().getParent().getParent() ;
				
				if( newVal ) {
					pane.getChildren().add( getInstance() ) ;
				}
				else {
					pane.getChildren().remove( getInstance() ) ;
				}
				
			}
		});
	}
	
	public static final PWAOPropertiesTool getInstance() {
		
		return PWAOPropertiesTool.propsTool ;
	}
	
	public final ScrollPane getPropertyScrollPane() {
		
		return this.propertyScrollPane ;
	}
	
	public final ToggleButton getPropertiesToggleBtn() {
		
		return this.propsTglBtn ;
	}
	
	public final ScrollPane getMaskScrollPane() {
		
		return this.maskScrollPane ;
	}
	
	public final Tab getPropertyTab() {
		
		return this.propertyTab ;
	}
	
	public final Tab getMaskTab() {
		
		return this.maskTab ;
	}
}
