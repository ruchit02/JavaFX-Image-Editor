package com.topNotch.photoEditor.main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class SecondaryWorkArea extends TabPane {
	
	private static final SecondaryWorkArea tabPane = new SecondaryWorkArea() ;
	
	private final ObservableList< ObservableList<SecondaryWorkAreaObject> > listOf_layersList = FXCollections.observableArrayList() ;
	private final ObservableList< ObservableList<SecondaryWorkAreaObject> > listOf_channelsList = FXCollections.observableArrayList() ;
	
	private final Tab layersTab ;
	private final Tab channelsTab ;
	private final ListView<SecondaryWorkAreaObject> listView ;
	
	private SecondaryWorkArea() {
		
		this.layersTab = new Tab() ;
		this.channelsTab = new Tab() ;
		
		this.listView = new ListView<SecondaryWorkAreaObject>() ;
		
		objectCreation() ;
	}
	
	private void objectCreation() {
		
		listView.setItems( null );
		listView.setEditable(true);
		listView.setMinSize( 200 , 400 );
		//listView.setMaxSize( 200 , 400 );
		listView.setBackground( new Background( new BackgroundFill( Color.rgb( 48, 48, 48) , null , null ) ) );
		listViewSelectedItemProperty( listView ) ;
		listView.setCellFactory( new Callback< ListView<SecondaryWorkAreaObject> , ListCell<SecondaryWorkAreaObject> >(){
			@Override
			public ListCell<SecondaryWorkAreaObject> call( ListView<SecondaryWorkAreaObject> p ){
				
				return new CustomCellFactory() ;
			}
		});
		
		
		layersTab.setText( "Layers" );
		layersTab.setContent( listView );
		
		channelsTab.setText( "Channels" );
		channelsTab.setContent( null );
		
		this.setBackground( new Background( new BackgroundFill( Color.rgb( 48, 48, 48) , null , null ) ) );
		this.getTabs().addAll( layersTab , channelsTab ) ;
		tabPaneMouseFilter( this ) ;
	}
	
	private void listViewSelectedItemProperty( ListView<SecondaryWorkAreaObject> listView ) {
		
		listView.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<SecondaryWorkAreaObject>() {
			@Override
			public void changed( ObservableValue<? extends SecondaryWorkAreaObject> obv , SecondaryWorkAreaObject oldVal , SecondaryWorkAreaObject newVal ) {
				
				PWAOPropertiesTool.getInstance().getPropertyScrollPane().setContent( newVal.getLinkableEffect().getRoot() );
			}
		});
	}
	
	private void tabPaneMouseFilter( TabPane tabPane ) {
		
		tabPane.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<Tab>() {
			@Override
			public void changed( ObservableValue< ? extends Tab > obv , Tab oldVal , Tab newVal ) {
				
				oldVal.setContent( null );
				newVal.setContent( listView );
				
				if( newVal.getText().equals( "Layers" ) ) {
					listView.setItems( listOf_layersList.get( PrimaryWorkArea.getInstance().getSelectionModel().getSelectedIndex() ) );
				}
				
                if( newVal.getText().equals( "Channels" ) ) {
                	listView.setItems( listOf_channelsList.get( PrimaryWorkArea.getInstance().getSelectionModel().getSelectedIndex() ) );
				}
			}
		});
	}
	
	public final class CustomCellFactory extends ListCell<SecondaryWorkAreaObject>{
		
		public CustomCellFactory() {
			
		}
		
		@Override
		public void startEdit() {
			
			super.startEdit(); 
			
			setText(null) ;
			this.getItem().getTextField().setText( this.getItem().getLabel().getText() );
			this.getItem().setRight( this.getItem().getTextField() ) ;
			this.getItem().getRight().requestFocus();
		}
		
		@Override
		public void cancelEdit() {
			
			super.cancelEdit(); 
			
			setText(null) ;
			this.getItem().getTextField().clear();
			this.getItem().setRight( this.getItem().getLabel() ) ;
		}
		
		@Override
		public void updateItem( SecondaryWorkAreaObject dObject , boolean empty ) {
			
			super.updateItem( dObject , empty );
			
			if( empty ) {
				
				setText(null) ;
				setGraphic(null) ;
			}
			else {
				
				setText(null);
				setGraphic( dObject ) ;
			}
		}
	}
	
	public static final SecondaryWorkArea getInstance() {
		
		return SecondaryWorkArea.tabPane ;
	}
	
	public final Tab getLayersTab() {
		
		return this.layersTab ;
	}
	
	public final Tab getChannelsTab() {
		
		return this.channelsTab ;
	}
	
	public final ListView<SecondaryWorkAreaObject> getListView(){
		
		return this.listView ;
	}
	
	public final ObservableList< ObservableList<SecondaryWorkAreaObject> > getListOfLayersList(){
		
		return this.listOf_layersList ;
	}
	
	public final ObservableList< ObservableList<SecondaryWorkAreaObject> > getListOfChannelsList(){
		
		return this.listOf_channelsList ;
	}
}
