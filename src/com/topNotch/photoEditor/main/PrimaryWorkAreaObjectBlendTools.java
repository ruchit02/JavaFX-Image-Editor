package com.topNotch.photoEditor.main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public final class PrimaryWorkAreaObjectBlendTools extends ComboBox<String> {

	private static final PrimaryWorkAreaObjectBlendTools blends = new PrimaryWorkAreaObjectBlendTools() ;
	
	private final ObservableList<String> blendNames = FXCollections.observableArrayList( "ADD" , "BLUE" , "COLOR_BURN" , "COLOR_DODGE" 
			, "DARKEN" , "DIFFERENCE" , "EXCLUSION" , "GREEN" , "HARD_LIGHT" , "LIGHTEN" , "MULTIPLY" , "NORMAL"
			, "OVERLAY" , "RED" , "SCREEN" , "SOFT_LIGHT" ) ;
	
	private PrimaryWorkAreaObjectBlendTools() {
		
		createObject() ;
	}
	
	private void createObject() {
		
		this.setItems( blendNames );
		this.setDisable(true);
		blendBoxMouseFilter( this ) ;
	}
	
	private void blendBoxMouseFilter( ComboBox<String> blendBox ) {
		
		 /*blendBox.valueProperty().addListener( new ChangeListener<String>() {
	 			@Override
	 			public void changed( ObservableValue< ? extends String > obv , String oldVal , String newVal ) {
	 				
	 				if( newVal == "ADD" ) {
	 					
	 					ImageInput myImg = new ImageInput();
	 	            	myImg.setSource( imagesArrList.get( tabPane.getSelectionModel().getSelectedIndex() )); 					
	 	            	
	 	            	if( (sPane2_listView.getSelectionModel().getSelectedIndex() - 1 != 0) && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.ADD ) ) {

	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.ADD );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            	
	 	            	if( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 == 0 && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.ADD ) ) {
	 	            		 
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.ADD );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 				}
	 				else if( newVal == "BLUE" ) {
	 					
	 					ImageInput myImg = new ImageInput();
	 	            	myImg.setSource( imagesArrList.get( tabPane.getSelectionModel().getSelectedIndex() )); 					
	 	            	
	 	            	if( (sPane2_listView.getSelectionModel().getSelectedIndex() - 1 != 0) && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.BLUE ) ) {

	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.BLUE );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            	
	 	            	if( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 == 0 && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.BLUE ) ) {
	 	            		 
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.BLUE );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 				}
	 				else if( newVal == "COLOR_BURN" ) {
	 					
	 					ImageInput myImg = new ImageInput();
	 	            	myImg.setSource( imagesArrList.get( tabPane.getSelectionModel().getSelectedIndex() ));
	 					
	 	            	if( (sPane2_listView.getSelectionModel().getSelectedIndex() - 1 != 0) && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.COLOR_BURN ) ) {

	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.COLOR_BURN );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            	
	 	            	if(sPane2_listView.getSelectionModel().getSelectedIndex() - 1 == 0 && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.COLOR_BURN ) ) {
	 	            		
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.COLOR_BURN );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 				}
	 	            else if( newVal == "COLOR_DODGE" ) {
	 	            	
	 	            	ImageInput myImg = new ImageInput();
	 	            	myImg.setSource( imagesArrList.get( tabPane.getSelectionModel().getSelectedIndex() ));
	 	            	
	 	            	if( (sPane2_listView.getSelectionModel().getSelectedIndex() - 1 != 0) && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.COLOR_DODGE ) ) {
	 	            		
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.COLOR_DODGE );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		callTheLegend( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            	
	 	            	if(sPane2_listView.getSelectionModel().getSelectedIndex() - 1 == 0 && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.COLOR_DODGE ) ) {
	 	            		 
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.COLOR_DODGE );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		callTheLegend( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 				}
	 	            else if( newVal == "DARKEN" ) {
	 	            	
	 	            	ImageInput myImg = new ImageInput();
	 	            	myImg.setSource( imagesArrList.get( tabPane.getSelectionModel().getSelectedIndex() ));
	 	            	
	 	            	if( (sPane2_listView.getSelectionModel().getSelectedIndex() - 1 != 0) && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.DARKEN ) ) {
	 	            		
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.DARKEN );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            	
	 	            	if(sPane2_listView.getSelectionModel().getSelectedIndex() - 1 == 0 && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.DARKEN ) ) {
	 	            		 
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.DARKEN );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            }
	 	            else if( newVal == "DIFFERENCE" ) {
	 	            	
	 	            	ImageInput myImg = new ImageInput();
	 	            	myImg.setSource( imagesArrList.get( tabPane.getSelectionModel().getSelectedIndex() ));
	 	            	
	 	            	if( (sPane2_listView.getSelectionModel().getSelectedIndex() - 1 != 0) && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.DIFFERENCE ) ) {
	 	            		
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.DIFFERENCE );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            	
	 	            	if(sPane2_listView.getSelectionModel().getSelectedIndex() - 1 == 0 && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.DIFFERENCE ) ) {
	 	            		
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.DIFFERENCE );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            }
	 	            else if( newVal == "EXCLUSION" ) {
	 	            	
	 	            	ImageInput myImg = new ImageInput();
	 	            	myImg.setSource( imagesArrList.get( tabPane.getSelectionModel().getSelectedIndex() ));
	 	            	
	 	            	if( (sPane2_listView.getSelectionModel().getSelectedIndex() - 1 != 0) && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.EXCLUSION ) ) {
	 	            		
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.EXCLUSION );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            	
	 	            	if(sPane2_listView.getSelectionModel().getSelectedIndex() - 1 == 0 && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.EXCLUSION ) ) {
	 	            		 
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.EXCLUSION );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            }
	 	            else if( newVal == "GREEN" ) {
	 	            	
	 	            	ImageInput myImg = new ImageInput();
	 	            	myImg.setSource( imagesArrList.get( tabPane.getSelectionModel().getSelectedIndex() )); 					
	 	            	
	 	            	if( (sPane2_listView.getSelectionModel().getSelectedIndex() - 1 != 0) && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.GREEN ) ) {

	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.GREEN );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            	
	 	            	if( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 == 0 && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.GREEN ) ) {
	 	            		 
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.GREEN );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            }
	 	            else if( newVal == "HARD_LIGHT" ) {
	 	            	
	 	            	ImageInput myImg = new ImageInput();
	 	            	myImg.setSource( imagesArrList.get( tabPane.getSelectionModel().getSelectedIndex() ));
	 	            	
	 	            	if( (sPane2_listView.getSelectionModel().getSelectedIndex() - 1 != 0) && ( BlendArrList.get(BlendArrList.size() - 1).getMode() != BlendMode.HARD_LIGHT ) ) {
	 	            		
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.HARD_LIGHT );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            	
	 	            	if(sPane2_listView.getSelectionModel().getSelectedIndex() - 1 == 0 && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.HARD_LIGHT ) ) {
	 	            		 
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.HARD_LIGHT );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 );
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            }
	 	            else if( newVal == "LIGHTEN" ) {
	 	            	
	 	            	ImageInput myImg = new ImageInput();
	 	            	myImg.setSource( imagesArrList.get( tabPane.getSelectionModel().getSelectedIndex() ));
	 	            	
	 	            	if( (sPane2_listView.getSelectionModel().getSelectedIndex() - 1 != 0) && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.LIGHTEN ) ) {
	 	            		
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.LIGHTEN );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	              	}
	 	            	
	 	            	if(sPane2_listView.getSelectionModel().getSelectedIndex() - 1 == 0 && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.LIGHTEN ) ) {
	 	            		 
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.LIGHTEN );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 );
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            }
	 	            else if( newVal == "MULTIPLY" ) {
	 	            	
	 	            	ImageInput myImg = new ImageInput();
	 	            	myImg.setSource( imagesArrList.get( tabPane.getSelectionModel().getSelectedIndex() ));
	 	            	
	 	            	if( (sPane2_listView.getSelectionModel().getSelectedIndex() - 1 != 0) && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.MULTIPLY ) ) {
	 	            		
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.MULTIPLY );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 ) ;
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            	
	 	            	if(sPane2_listView.getSelectionModel().getSelectedIndex() - 1 == 0 && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.MULTIPLY ) ) {
	 	            		
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.MULTIPLY );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 );
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            }
	 	            else if( newVal == "NORMAL" ) {
	 	            	
	 	            	ImageInput myImg = new ImageInput();
	 	            	myImg.setSource( imagesArrList.get( tabPane.getSelectionModel().getSelectedIndex() ));
	 	            	
	 	            	if( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 != 0 ) {
	 	            		
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( null );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 );
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            	
	 	            	if(sPane2_listView.getSelectionModel().getSelectedIndex() - 1 == 0){
	 	            		
	 	            		BlendArrList.get(  sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( null );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 );
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            }
	 	            else if( newVal == "OVERLAY" ) {
	 	            	
	 	            	ImageInput myImg = new ImageInput();
	 	            	myImg.setSource( imagesArrList.get( tabPane.getSelectionModel().getSelectedIndex() ));
	 	            	
	 	            	if( (sPane2_listView.getSelectionModel().getSelectedIndex() - 1 != 0) && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.OVERLAY ) ) {
	 	            		
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.OVERLAY );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 );
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            	
	 	            	if(sPane2_listView.getSelectionModel().getSelectedIndex() - 1 == 0 && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.OVERLAY ) ) {
	 	            		 
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.OVERLAY );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 );
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            }
	 	            else if( newVal == "RED" ) {
	 	            	
	 	            	ImageInput myImg = new ImageInput();
	 	            	myImg.setSource( imagesArrList.get( tabPane.getSelectionModel().getSelectedIndex() ));
	 	            	
                  if( (sPane2_listView.getSelectionModel().getSelectedIndex() - 1 != 0) && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.RED ) ) {
	 	            		
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.RED );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 );
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
                  
                  if(sPane2_listView.getSelectionModel().getSelectedIndex() - 1 == 0 && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.RED ) ) {
	 	            		 
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.RED );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 );
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
                  
	 	            }
	 	            else if( newVal == "SCREEN" ) {
	 	            	
	 	            	ImageInput myImg = new ImageInput();
	 	            	myImg.setSource( imagesArrList.get( tabPane.getSelectionModel().getSelectedIndex() ));
	 	            	
	 	            	if( (sPane2_listView.getSelectionModel().getSelectedIndex() - 1 != 0) && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.SCREEN ) ) {
	 	            		
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.SCREEN );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 );
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            	
	 	            	if(sPane2_listView.getSelectionModel().getSelectedIndex() - 1 == 0 && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.SCREEN ) ) {
	 	            		 
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.SCREEN );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 );
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            	
	 				}
	 	            else if( newVal == "SOFT_LIGHT" ) {
	 	            	
	 	            	ImageInput myImg = new ImageInput();
	 	            	myImg.setSource( imagesArrList.get( tabPane.getSelectionModel().getSelectedIndex() ));
	 	            	
	 	            	if( (sPane2_listView.getSelectionModel().getSelectedIndex() - 1 != 0) && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.SOFT_LIGHT ) ) {
	 	            		
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.SOFT_LIGHT );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 );
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            	
	 	            	if(sPane2_listView.getSelectionModel().getSelectedIndex() - 1 == 0 && ( BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1).getMode() != BlendMode.SOFT_LIGHT ) ) {
	 	            		 
	 	            		BlendArrList.get( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 ).setMode( BlendMode.SOFT_LIGHT );
	 	            		callCanvasSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex()-1 );
	 	            		//callLayersSnapshotProcess( sPane2_listView.getSelectionModel().getSelectedIndex() - 1 );
	 	            	}
	 	            }
	 			}
	 		});*/
	}
	
	public static final PrimaryWorkAreaObjectBlendTools getInstance() {
		
		return PrimaryWorkAreaObjectBlendTools.blends ;
	}
}
