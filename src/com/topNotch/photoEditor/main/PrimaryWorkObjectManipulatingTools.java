package com.topNotch.photoEditor.main;

import java.io.File;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;

public final class PrimaryWorkObjectManipulatingTools extends ToolBar {

	private static final PrimaryWorkObjectManipulatingTools toolBar = new PrimaryWorkObjectManipulatingTools() ;
	
	private final String ownerName = "ORIGINAL IMAGE" ;
	
	private final Button opnBtn ;
	private final Button crpBtn ;
	private final Button saveBtn ;
	private final Button paintBtn ;
	private final Button newLayer ;
	private final ColorPicker solidColorPicker ;
	private final ColorPicker brushColor ;
	private final ComboBox<Number> brushSizeBox ;
	
	private PrimaryWorkObjectManipulatingTools() {
		
		 opnBtn = new Button();
		 crpBtn = new Button();
		 saveBtn = new Button();
		 paintBtn = new Button();
		 solidColorPicker = new ColorPicker();
		 brushColor = new ColorPicker();
		 brushSizeBox = new ComboBox<Number>();
		 newLayer = new Button();
		 
		 createObject() ;
	}
	
	private void createObject() {
	   
		opnBtn.setText("Open");
		ImageView opnBtnImgView = new ImageView();
		opnBtnImgView.setImage( new Image( getClass().getResourceAsStream("/ImgResources/icons8-pictures-folder-64.png") ) );
		opnBtnImgView.setFitHeight(20);
		opnBtnImgView.setPreserveRatio( true );
		opnBtn.setGraphic( opnBtnImgView );
		openButtonMouseFilter( opnBtn );
		
		crpBtn.setText("Crop");
		ImageView crpBtnImgView = new ImageView();
		crpBtnImgView.setImage( new Image( getClass().getResourceAsStream("/ImgResources/icons8-crop-64.png")) );
		crpBtnImgView.setFitHeight(20);
		crpBtnImgView.setPreserveRatio( true );
		crpBtn.setGraphic( crpBtnImgView );
		
		saveBtn.setText("Save");
		
		solidColorPicker.setValue( Color.WHITE );
		
		paintBtn.setText("Paint");
		ImageView paintBtnImgView = new ImageView();
		paintBtnImgView.setImage( new Image( getClass().getResourceAsStream("/ImgResources/icons8-edit-64.png") ) );
		paintBtnImgView.setFitHeight(20);
		paintBtnImgView.setPreserveRatio( true );
		paintBtn.setGraphic( paintBtnImgView );
		
		ObservableList<String> gradientTypes = FXCollections.observableArrayList( "Linear Gradient" , "Reverse L-Gradient" , "Radial Gradient" , "None" ) ;
		
		ComboBox<String> gradientBox = new ComboBox<String>();
		gradientBox.setItems( gradientTypes ) ;
		gradientBoxCellFactory( gradientBox ) ;
		
        ObservableList<Number> sizeList = FXCollections.observableArrayList( 1 , 2 , 3 , 4 , 5 , 10 , 15 , 20 , 40 , 80 ) ;
		
        brushSizeBox.setItems( sizeList );
        brushSizeBox.setValue( 1 );
		
		brushColor.setValue( Color.WHITE );
		
		newLayer.setText("New Layer");
		
		this.setMaxWidth( 1114 );
		this.setPrefHeight(20);
		this.setBackground( new Background( new BackgroundFill( Color.rgb(48, 48, 48) , CornerRadii.EMPTY , Insets.EMPTY ) ) );
		this.getItems().addAll( opnBtn , crpBtn , saveBtn , paintBtn , gradientBox 
				                 , solidColorPicker , newLayer , brushSizeBox , brushColor  ) ;
	}
	
	private void openButtonMouseFilter( Button opnBtn ) {
		
		opnBtn.setOnMouseClicked( new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent e ) {
				
				FileChooser fileChooser = new FileChooser() ;
				fileChooser.getExtensionFilters().add( new ExtensionFilter("Image Files" , "*.jpg" ) );
				fileChooser.setTitle("Image Chooser");
				
				List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
				
				if( selectedFiles != null ) {
					
					for( int i = 0 ; i < selectedFiles.size() ; i++ ) {
						
						 SecondaryWorkArea.getInstance().getListOfLayersList().add( FXCollections.observableArrayList() ) ;
						 SecondaryWorkArea.getInstance().getListOfChannelsList().add( FXCollections.observableArrayList() ) ;
						 
						 PrimaryWorkAreaObject dispObjCrtr = new PrimaryWorkAreaObject( selectedFiles.get( i ).toURI().toString() 
								                                                      , selectedFiles.get( i ).getName() ) ;
						 
						 SecondaryWorkArea.getInstance().getListOfLayersList().get(i)
						 .add( new SecondaryWorkAreaObject( PrimaryWorkAreaObject.getObjects().get(i).getCanvasWidth() 
						 , PrimaryWorkAreaObject.getObjects().get(i).getCanvasHeight() , ownerName , dispObjCrtr ) ) ;
						 
						 PrimaryWorkArea.getInstance().getTabs().add( dispObjCrtr.getTab() );
					}
					
					PrimaryWorkArea.getInstance().getSelectionModel().select( PrimaryWorkArea.getInstance().getTabs().size() - 1 );
				}
			}
		});
	}
	
	private void gradientBoxCellFactory( ComboBox<String> gradientBox ) {
		
		gradientBox.setCellFactory( new Callback< ListView<String> , ListCell<String> >(){
			@Override
			public ListCell<String> call( ListView<String> listView ){
				
				return new ListCell<String>() {
					
					@Override
					protected void updateItem( String item , boolean empty ) {
						
						super.updateItem( item , empty);
						
						if( empty || item == null ) {
							
							setGraphic( null );
							//setText( null );
						}
						else {
							
							setText( item );
							setFont( Font.font( "Comic Sans MS" , FontWeight.BOLD , 12 ) );
							setTextFill( Color.PURPLE );
							if( item == "Linear Gradient") {
								
								ImageView lg_ImgView = new ImageView( new Image(getClass().getResourceAsStream("/ImgResources/icons8-line-40.png")) );
								lg_ImgView.setFitHeight(20);
								lg_ImgView.setPreserveRatio(true);
								setGraphic( lg_ImgView );
							}
							else if( item == "Radial Gradient" ) {
								
								ImageView rg_ImgView = new ImageView( new Image( getClass().getResourceAsStream("/ImgResources/icons8-oval-filled-40.png") ) );
								rg_ImgView.setFitHeight(20);
								rg_ImgView.setPreserveRatio(true);
								setGraphic( rg_ImgView );
							}
							else if( item == "None" ) {
								
								;
							}
							
						}
					}
				};
			}
		});
	}
	
	public static final PrimaryWorkObjectManipulatingTools getInstance() {
		
		return PrimaryWorkObjectManipulatingTools.toolBar ;
	}
	
	public final Button getOpenButton() {
		
		return this.opnBtn ;
	}
	
	public final Button getCropButton() {
		
		return this.crpBtn ;
	}
	
	public final Button getSaveButton() {
		
		return this.saveBtn ;
	}
	
	public final Button getNewLayer() {
		
		return this.newLayer ;
	}
	
	public final Button getPaintButton() {
		
		return this.paintBtn ;
	}
	
	public final ColorPicker getSolidColorPicker() {
		
		return this.solidColorPicker ;
	}
	
	public final ColorPicker getBrushColor() {
		
		return this.brushColor ;
	}
	
	public final ComboBox<Number> getBrushSize() {
		
		return this.brushSizeBox ;
	}
}
