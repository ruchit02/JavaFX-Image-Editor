package com.topNotch.photoEditor.main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class PrimaryWorkArea extends TabPane {
	
	private final static PrimaryWorkArea tabPane = new PrimaryWorkArea() ;
	public static int selectedTabIndex = 0 ;
	
	private PrimaryWorkArea() {
		
		createObject() ;
	}
	
	private void createObject() {
		
	    this.setBackground( new Background( new BackgroundFill( Color.rgb( 48, 48, 48) , CornerRadii.EMPTY , Insets.EMPTY ) ) );
	    this.setPrefSize(1114, 614);
	    objectSelectionProperty( this ) ;
	}
	
	private void objectSelectionProperty( TabPane tabPane ) {
		
		tabPane.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<Tab>() {
			@Override
			public void changed( ObservableValue<? extends Tab> obv , Tab oldVal , Tab newVal ) {
				
				
			}
		});
		
		tabPane.getSelectionModel().selectedIndexProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed( ObservableValue<? extends Number> obv , Number oldVal , Number newVal ) {
				
				selectedTabIndex = newVal.intValue() ;
				
				SecondaryWorkArea swa = SecondaryWorkArea.getInstance() ;
				swa.getSelectionModel().select( swa.getLayersTab() );
				swa.getListView().setItems( swa.getListOfLayersList().get( selectedTabIndex ) );
				
				if( !swa.getListView().getItems().isEmpty() ) {
					swa.getListView().getSelectionModel().select( 0 );
				}
				
			}
		});
	}
	
	public static final PrimaryWorkArea getInstance() {
		
		return PrimaryWorkArea.tabPane ;
	}
}
