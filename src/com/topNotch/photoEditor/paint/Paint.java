package com.topNotch.photoEditor.paint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Paint {
	
	
	public void callPaintScreen( final Canvas canvas , final GraphicsContext gc ) {
		
		
		Group paint_root = new Group();
		
		ScrollPane paint_sPane = new ScrollPane();
		paint_sPane.setPrefSize(1114, 583);
		paint_sPane.setBorder( new Border( new BorderStroke( Color.BLACK , BorderStrokeStyle.SOLID 
				, CornerRadii.EMPTY , BorderWidths.DEFAULT)) );
		paint_root.getChildren().add( paint_sPane );
		
		BorderPane paint_bPane = new BorderPane();
		paint_bPane.setPrefSize( 1100 , 570 );
		paint_bPane.setBackground( new Background( new BackgroundFill( Color.GREY , CornerRadii.EMPTY , Insets.EMPTY ) ) );
		//paint_bPane.setBorder( new Border( new BorderStroke( Color.BLACK , BorderStrokeStyle.SOLID 
				//, CornerRadii.EMPTY , BorderWidths.DEFAULT ) ) );
		paint_root.getChildren().add( paint_bPane );
		
		HBox paint_hBox = new HBox();
		paint_hBox.setSpacing( 5 );
		paint_hBox.setTranslateY( 600 );
		//paint_hBox.setBorder( new Border( new BorderStroke( Color.BLACK , BorderStrokeStyle.SOLID 
				//, CornerRadii.EMPTY , BorderWidths.DEFAULT ) ) );
		paint_root.getChildren().add( paint_hBox );
		
		ColorPicker clrPicker = new ColorPicker();
		clrPicker.setValue( Color.CORAL );
		paint_hBox.getChildren().add( clrPicker );
		
		canvas.setWidth(1000);
		canvas.setHeight(500);
	    gc.setFill(Color.ALICEBLUE);
	    gc.fillRect( 0 , 0 , 1000 , 500 );
	    paint_bPane.setCenter( canvas );
		//gc.drawImage( img , 0 , 0 , 1000 , 500 );
		
		Button resetBtn = new Button();
		resetBtn.setText("Reset");
		ImageView resetBtnImgView = new ImageView();
		resetBtnImgView.setImage( new Image( getClass().getResourceAsStream("/ImgResources/icons8-full-trash-64.png") ) );
		resetBtnImgView.setFitHeight(20);
		resetBtnImgView.setPreserveRatio( true );
		resetBtn.setGraphic( resetBtnImgView );
		paint_hBox.getChildren().add( resetBtn );
		
		resetBtn.setOnMouseClicked( new EventHandler<MouseEvent>() {

			@Override
			public void handle( MouseEvent mcEvent ) {
				
				/*if( Creator.getImage() != null ) {
					
				    gc.clearRect( 0 , 0 , Creator.getCanvasWidth(), Creator.getCanvasHeight() );
					gc.drawImage( Creator.getImage() , 0 , 0 , Creator.getCanvasWidth() , Creator.getCanvasHeight() );
				}
				else {
					
					gc.clearRect( 0 , 0 , 1000 , 500 );
					gc.fillRect( 0 , 0 , 1000 , 500 );
				}*/
				
			}
		});
		
		ObservableList<String> shapesList = FXCollections.observableArrayList( "Doodle" , "Line" , "Triangle" 
				, "Oval" , "Rectangle" , "Round Rectangle" , "Quadratic Curve" , "Cubic Curve" );
		
		ComboBox<String> shapesBox = new ComboBox<String>() ;
		shapesBox.setItems( shapesList );
		paint_hBox.getChildren().add( shapesBox );

		shapesBox.setCellFactory( new Callback< ListView<String> , ListCell<String> >() {
			@Override
			public ListCell<String> call( ListView<String> listView ){
				
				return new ListCell<String>() {
					
					@Override
					protected void updateItem( String item , boolean empty ) {
						
						super.updateItem( item , empty);
						
						if( empty || item == null ) {
							
							setGraphic(null);
							setText(null);
						}
						else {
							
							setText(item);
							setFont( Font.font( "Comic Sans MS" , FontWeight.BOLD , 12 ) );
							setTextFill( Color.PURPLE );
							
							if( item == "Doodle" ) {
								
								ImageView doodle_ImgView = new ImageView( new Image( getClass().getResourceAsStream("/ImgResources/icons8-crayon-64.png") ) );
								doodle_ImgView.setFitHeight(20);
								doodle_ImgView.setPreserveRatio(true);
								setGraphic( doodle_ImgView );
							}
							else if( item == "Line" ) {
								
								ImageView line_ImgView = new ImageView( new Image( getClass().getResourceAsStream("/ImgResources/icons8-line-40.png") ) );
								line_ImgView.setFitHeight(20);
								line_ImgView.setPreserveRatio(true);
								setGraphic( line_ImgView );
							}
							else if( item == "Triangle" ) {
								
								ImageView triangle_ImgView = new ImageView( new Image( getClass().getResourceAsStream("/ImgResources/icons8-triangle-64.png") ) );
								triangle_ImgView.setFitHeight(20);
								triangle_ImgView.setPreserveRatio(true);
								setGraphic( triangle_ImgView );
							}
							else if( item == "Oval" ) {
								
								ImageView oval_ImgView = new ImageView( new Image( getClass().getResourceAsStream("/ImgResources/icons8-oval-filled-40.png") ) );
								oval_ImgView.setFitHeight(20);
								oval_ImgView.setPreserveRatio(true);
								setGraphic( oval_ImgView );
							}
							else if( item == "Rectangle" ) {
							
								ImageView rectangle_ImgView = new ImageView( new Image( getClass().getResourceAsStream("/ImgResources/icons8-rectangular-64.png") ) );
								rectangle_ImgView.setFitHeight(20);
								rectangle_ImgView.setPreserveRatio(true);
								setGraphic( rectangle_ImgView );
							}
							else if( item == "Round Rectangle" ) {
								
								ImageView roundRect_ImgView = new ImageView( new Image( getClass().getResourceAsStream("/ImgResources/icons8-square-64.png") ) );
								roundRect_ImgView.setFitHeight(20);
								roundRect_ImgView.setPreserveRatio(true);
								setGraphic( roundRect_ImgView );
							}
							else if( item == "Quadratic Curve" ) {
								
								ImageView quadCurve_ImgView = new ImageView( new Image( getClass().getResourceAsStream("/ImgResources/icons8-polyline-64.png") ) );
								quadCurve_ImgView.setFitHeight(20);
								quadCurve_ImgView.setPreserveRatio(true);
								setGraphic( quadCurve_ImgView );
							}
							else if( item == "Cubic Curve" ) {
								
								ImageView cubicCurve_ImgView = new ImageView( new Image( getClass().getResourceAsStream("/ImgResources/icons8-polyline-64.png") ) );
								cubicCurve_ImgView.setFitHeight(20);
								cubicCurve_ImgView.setPreserveRatio(true);
								setGraphic( cubicCurve_ImgView );
							}
							
						}	
					}
				};
			}
		});
		
		ObservableList<Number> sizeList = FXCollections.observableArrayList( 1 , 3 , 5 , 10 ) ;
		
		ComboBox<Number> sizeBox = new ComboBox<Number>();
		sizeBox.setItems( sizeList );
		sizeBox.setValue( 1 );
		paint_hBox.getChildren().add( sizeBox );
		
		Button loadImgBtn = new Button();
		loadImgBtn.setText("Load Image");
		ImageView loadBtnImgView = new ImageView( new Image( getClass().getResourceAsStream("/ImgResources/icons8-full-image-64.png")) );
		loadBtnImgView.setFitHeight(20);
		loadBtnImgView.setPreserveRatio(true);
		loadImgBtn.setGraphic( loadBtnImgView );
		paint_hBox.getChildren().add( loadImgBtn );
		
		ObservableList<String> fontNames = FXCollections.observableArrayList( Font.getFontNames() ) ;
		    
		ComboBox<String> fontBox = new ComboBox<String>();
		fontBox.setItems( fontNames );
		fontBox.setValue("Agency FB");
	    paint_hBox.getChildren().add( fontBox );
		
	    ToggleButton textTglBtn = new ToggleButton();
	    textTglBtn.setSelected( false );
	    textTglBtn.setText("Text");
	    ImageView textTglBtnImgView = new ImageView();
	    textTglBtnImgView.setImage( new Image( getClass().getResourceAsStream("/ImgResources/icons8-type-48.png") ) );
	    textTglBtnImgView.setFitHeight(20);
	    textTglBtnImgView.setPreserveRatio( true );
	    textTglBtn.setGraphic( textTglBtnImgView );
	    paint_hBox.getChildren().add( textTglBtn );
	    
	    TextArea paintTextArea = new TextArea();
	    paintTextArea.setBackground( new Background( new BackgroundFill( Color.AQUA , CornerRadii.EMPTY , Insets.EMPTY) ));
	    paintTextArea.setPrefRowCount( 1 );
	    paintTextArea.setPrefWidth(100);
	    //paint_root.getChildren().add( paintTextArea );
	    
	    EventHandler<MouseEvent> textAreaHandler = new EventHandler<MouseEvent>() {
	    	@Override
	    	public void handle( MouseEvent mcEvent ) {
	    		
	    		paintTextArea.setTranslateX( mcEvent.getSceneX() );
	    		paintTextArea.setTranslateY( mcEvent.getSceneY() - paint_sPane.getTranslateY() );
	    	}
	    };
	    
	    EventHandler<KeyEvent> textHandler = new EventHandler<KeyEvent>() {
	    	@Override
	    	public void handle( KeyEvent ktEvent ) {
	    		
	    		gc.fillText( ktEvent.getCharacter(), paintTextArea.getTranslateX() , paintTextArea.getTranslateY());
	    	}
	    };
	    
	    textTglBtn.selectedProperty().addListener( new ChangeListener<Boolean>() {
	    	@Override
	    	public void changed( ObservableValue< ? extends Boolean > obv , Boolean oldVal , Boolean newVal ) {
	    		
	    		if( newVal == true ) {
	    			
	    			canvas.setCursor( Cursor.TEXT );
	    			canvas.addEventHandler( MouseEvent.MOUSE_CLICKED , textAreaHandler );
	    			canvas.addEventHandler( KeyEvent.ANY , textHandler );
	    		}
	    		else {
	    			
	    			canvas.removeEventHandler( MouseEvent.MOUSE_CLICKED , textAreaHandler );
	    			canvas.removeEventHandler( KeyEvent.ANY , textHandler );
	    		}
	    	}
	    });
	    
		Line paint_line = new Line() ;
		
		Rectangle paint_rectangle = new Rectangle();
		paint_rectangle.setFill( Color.TRANSPARENT );
		
		Ellipse paint_ellipse = new Ellipse();
		paint_ellipse.setFill( Color.TRANSPARENT );
		
		Polygon paint_triangle = new Polygon();
		paint_triangle.setFill( Color.TRANSPARENT );
		
		QuadCurve paint_quadCurve = new QuadCurve();
		paint_quadCurve.setFill( Color.TRANSPARENT );
		
		CubicCurve paint_cubicCurve = new CubicCurve();
		paint_cubicCurve.setFill( Color.TRANSPARENT );
		
		canvas.addEventFilter( MouseEvent.ANY , new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent e ) {
				
                if( shapesBox.getSelectionModel().getSelectedItem() == "Doodle" ) {
					
					if( e.getEventType() == MouseEvent.MOUSE_PRESSED ) {
						
						gc.setStroke( clrPicker.getValue() );
						gc.setLineWidth( sizeBox.getSelectionModel().getSelectedItem().doubleValue() );
						gc.beginPath();
						gc.moveTo( e.getX() , e.getY() );
						gc.stroke();
					}
					else if ( e.getEventType() == MouseEvent.MOUSE_DRAGGED ){
						
						gc.lineTo( e.getX() , e.getY() );
						gc.stroke();
					}
					else if( e.getEventType() == MouseEvent.MOUSE_RELEASED ) {
						
						gc.closePath();
					}
                }
				if( shapesBox.getSelectionModel().getSelectedItem() == "Line" ) {
					
					paint_line.setStroke( clrPicker.getValue() );
					paint_line.setStrokeWidth( sizeBox.getSelectionModel().getSelectedItem().doubleValue() );
					if( e.getEventType() == MouseEvent.MOUSE_PRESSED ) {
						
						paint_bPane.getChildren().add( paint_line );
						paint_line.setStartX( e.getX() + (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2) );
						paint_line.setStartY( e.getY() + (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2) );
					}
					else if ( e.getEventType() == MouseEvent.MOUSE_DRAGGED ){
						
						paint_line.setEndX( e.getX() + (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2) );
						paint_line.setEndY( e.getY() + (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2) );
					}
					else if( e.getEventType() == MouseEvent.MOUSE_RELEASED ) {
						
						gc.setStroke( clrPicker.getValue() );
						gc.setLineWidth( paint_line.getStrokeWidth() );
						gc.strokeLine( paint_line.getStartX() - (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2) 
								, paint_line.getStartY() - (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2) 
								, paint_line.getEndX() - (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2) 
								, paint_line.getEndY() - (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2) );
						paint_bPane.getChildren().remove( paint_line );
					}
				}
				else if( shapesBox.getSelectionModel().getSelectedItem() == "Rectangle" ) {
					
					paint_rectangle.setStroke( clrPicker.getValue() );
					paint_rectangle.setStrokeWidth( sizeBox.getSelectionModel().getSelectedItem().doubleValue() );
					if( e.getEventType() == MouseEvent.MOUSE_PRESSED ) {
						
						paint_bPane.getChildren().add( paint_rectangle );
						paint_rectangle.setArcWidth(0);
						paint_rectangle.setArcHeight(0);
						paint_rectangle.setTranslateX( e.getSceneX() );
						paint_rectangle.setTranslateY( e.getSceneY() - paint_sPane.getTranslateY());
					}
					else if( e.getEventType() == MouseEvent.MOUSE_DRAGGED ) {
						
						paint_rectangle.setWidth( e.getSceneX() - paint_rectangle.getTranslateX() );
						paint_rectangle.setHeight( e.getSceneY() - paint_rectangle.getTranslateY() - paint_sPane.getTranslateY() );
					}
					else if( e.getEventType() == MouseEvent.MOUSE_RELEASED ) {
						
						gc.setStroke( clrPicker.getValue() );
						gc.setLineWidth( paint_rectangle.getStrokeWidth() );
						gc.strokeRect( paint_rectangle.getTranslateX()- (( paint_bPane.getPrefWidth()-canvas.getWidth() )/2) , paint_rectangle.getTranslateY() - (( paint_bPane.getPrefHeight()-canvas.getHeight() )/2)
								, paint_rectangle.getWidth() , paint_rectangle.getHeight() );
						paint_rectangle.setWidth(0);
						paint_rectangle.setHeight(0);
						paint_bPane.getChildren().remove( paint_rectangle );
					}
				}
				else if( shapesBox.getSelectionModel().getSelectedItem() == "Oval") {
					
					paint_ellipse.setStroke( clrPicker.getValue() );
					paint_ellipse.setStrokeWidth( sizeBox.getSelectionModel().getSelectedItem().doubleValue() );
					if( e.getEventType() == MouseEvent.MOUSE_PRESSED ) {
					
						paint_bPane.getChildren().add( paint_ellipse );
						paint_ellipse.setTranslateX( e.getSceneX()  );
						paint_ellipse.setTranslateY( e.getSceneY() - paint_sPane.getTranslateY() );
					}
					else if( e.getEventType() == MouseEvent.MOUSE_DRAGGED ) {
						
						paint_ellipse.setCenterX( e.getSceneX() - paint_ellipse.getTranslateX() );
						paint_ellipse.setCenterY( e.getSceneY() - paint_ellipse.getTranslateY() - paint_sPane.getTranslateY() );
						
						paint_ellipse.setRadiusX( e.getSceneX() - paint_ellipse.getTranslateX() );
						paint_ellipse.setRadiusY( e.getSceneY() - paint_ellipse.getTranslateY() - paint_sPane.getTranslateY() );
					}
					else if( e.getEventType() == MouseEvent.MOUSE_RELEASED ) {
						
						gc.setStroke( clrPicker.getValue() );
						gc.setLineWidth( paint_ellipse.getStrokeWidth() );
						gc.strokeOval( paint_ellipse.getTranslateX() - (( paint_bPane.getPrefWidth()-canvas.getWidth() )/2) , paint_ellipse.getTranslateY() - (( paint_bPane.getPrefHeight()-canvas.getHeight() )/2)
								, 2*paint_ellipse.getRadiusX() , 2*paint_ellipse.getRadiusY() );
						paint_ellipse.setRadiusX(0);
						paint_ellipse.setRadiusY(0);
						paint_bPane.getChildren().remove( paint_ellipse );
					}
				}
                else if( shapesBox.getSelectionModel().getSelectedItem() == "Triangle") {
					
                	paint_rectangle.setStroke( null );
                	paint_triangle.setStroke( clrPicker.getValue() );
                	paint_triangle.setStrokeWidth( sizeBox.getSelectionModel().getSelectedItem().doubleValue() );
					if( e.getEventType() == MouseEvent.MOUSE_PRESSED ) {
						
						paint_bPane.getChildren().add( paint_triangle );
						
						paint_rectangle.setTranslateX( e.getSceneX() );
						paint_rectangle.setTranslateY( e.getSceneY() - paint_sPane.getTranslateY() );
						
						paint_triangle.setTranslateX( paint_rectangle.getTranslateX() );
						paint_triangle.setTranslateY( paint_rectangle.getTranslateY() );
					}
					else if( e.getEventType() == MouseEvent.MOUSE_DRAGGED ) {
						
						if( paint_triangle.getPoints() != null ) {
							
							paint_triangle.getPoints().clear();
						}
						
						paint_rectangle.setWidth( e.getSceneX() - paint_rectangle.getTranslateX() );
						paint_rectangle.setHeight( e.getSceneY() - paint_rectangle.getTranslateY() - paint_sPane.getTranslateY() );
						
						Double[] values = new Double[] {
								e.getSceneX()-paint_rectangle.getTranslateX()-(paint_rectangle.getWidth()*0.5) , e.getSceneY()-paint_rectangle.getTranslateY()-paint_sPane.getTranslateY()-paint_rectangle.getHeight() ,
								e.getSceneX()-paint_rectangle.getTranslateX()-paint_rectangle.getWidth() , e.getSceneY()-paint_rectangle.getTranslateY()-paint_sPane.getTranslateY() ,
								e.getSceneX()-paint_rectangle.getTranslateX() , e.getSceneY()-paint_rectangle.getTranslateY()-paint_sPane.getTranslateY() , 
						} ;
						
						paint_triangle.getPoints().addAll( values );
					}
					else if( e.getEventType() == MouseEvent.MOUSE_RELEASED ) {
						
						gc.setStroke( clrPicker.getValue() );
						gc.setLineWidth( paint_triangle.getStrokeWidth() );
						gc.strokePolygon( new double[] { e.getSceneX()-(paint_rectangle.getWidth()*0.5) - (( paint_bPane.getPrefWidth()-canvas.getWidth() )/2) ,
								e.getSceneX()-paint_rectangle.getWidth() - (( paint_bPane.getPrefWidth()-canvas.getWidth() )/2) ,
								e.getSceneX() - (( paint_bPane.getPrefWidth()-canvas.getWidth() )/2) } , 
								new double[] { e.getSceneY()-paint_sPane.getTranslateY()-paint_rectangle.getHeight() - (( paint_bPane.getPrefHeight()-canvas.getHeight() )/2) ,
										e.getSceneY()-paint_sPane.getTranslateY() - (( paint_bPane.getPrefHeight()-canvas.getHeight() )/2) , 
										 e.getSceneY()-paint_sPane.getTranslateY() - (( paint_bPane.getPrefHeight()-canvas.getHeight() )/2) }  , 3 );
						paint_triangle.getPoints().clear();
						paint_bPane.getChildren().remove( paint_triangle );
					}
				}
                else if( shapesBox.getSelectionModel().getSelectedItem() == "Round Rectangle" ) {
					
                	paint_rectangle.setStroke( clrPicker.getValue() );
                	paint_rectangle.setStrokeWidth( sizeBox.getSelectionModel().getSelectedItem().doubleValue() );
					if( e.getEventType() == MouseEvent.MOUSE_PRESSED ) {
						
						paint_bPane.getChildren().add( paint_rectangle );
						paint_rectangle.setTranslateX( e.getSceneX() );
						paint_rectangle.setTranslateY( e.getSceneY() - paint_sPane.getTranslateY());
					}
					else if( e.getEventType() == MouseEvent.MOUSE_DRAGGED ) {
						
						paint_rectangle.setWidth( e.getSceneX() - paint_rectangle.getTranslateX() );
						paint_rectangle.setHeight( e.getSceneY() - paint_rectangle.getTranslateY() - paint_sPane.getTranslateY() );
						if(  paint_rectangle.getWidth() > 60 && paint_rectangle.getHeight() > 60 ) {
							
							paint_rectangle.setArcWidth( 30 );
							paint_rectangle.setArcHeight( 30 );
						}
						else {
							
							paint_rectangle.setArcWidth( paint_rectangle.getWidth()/4 );
							paint_rectangle.setArcHeight( paint_rectangle.getHeight()/4 ); 
						}
					}
					else if( e.getEventType() == MouseEvent.MOUSE_RELEASED ) {
						
						gc.setStroke( clrPicker.getValue() );
						gc.setLineWidth( paint_rectangle.getStrokeWidth() );
						gc.strokeRoundRect( paint_rectangle.getTranslateX() - (( paint_bPane.getPrefWidth()-canvas.getWidth() )/2) , paint_rectangle.getTranslateY() - (( paint_bPane.getPrefHeight()-canvas.getHeight() )/2)
								, paint_rectangle.getWidth() , paint_rectangle.getHeight() 
								, paint_rectangle.getArcWidth() , paint_rectangle.getArcHeight() );
						paint_rectangle.setWidth(0);
						paint_rectangle.setHeight(0);
						paint_bPane.getChildren().remove( paint_rectangle );
					}
				}
                else if( shapesBox.getSelectionModel().getSelectedItem() == "Quadratic Curve" ) {
					
                	paint_line.setStroke( clrPicker.getValue() );
					paint_line.setStrokeWidth( sizeBox.getSelectionModel().getSelectedItem().doubleValue() );
                	
					paint_quadCurve.setStroke( clrPicker.getValue() );
					paint_quadCurve.setStrokeWidth( sizeBox.getSelectionModel().getSelectedItem().doubleValue() );
					if( e.getEventType() == MouseEvent.MOUSE_PRESSED ) {
						
						if( !paint_bPane.getChildren().contains( paint_line ) ) {
							
							paint_bPane.getChildren().add( paint_line );
							paint_line.setStartX( e.getX() + (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2) );
							paint_line.setStartY( e.getY() + (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2) );
						}
						else {
							
							paint_bPane.getChildren().remove( paint_line );
							paint_bPane.getChildren().add( paint_quadCurve );
							
							paint_quadCurve.setControlX( e.getX() + (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2) );
							paint_quadCurve.setControlY( e.getY() + (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2) );
						}
					}
					else if( e.getEventType() == MouseEvent.MOUSE_DRAGGED ) {
						
						if( paint_bPane.getChildren().contains( paint_line ) ) {
							
							paint_line.setEndX( e.getX() + (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2) );
							paint_line.setEndY( e.getY() + (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2) );
						}	
						else {
							
							paint_quadCurve.setControlX( e.getX() + (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2) );
							paint_quadCurve.setControlY( e.getY() + (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2) );
						}
					}
					else if( e.getEventType() == MouseEvent.MOUSE_RELEASED ) {
						
						if( !paint_bPane.getChildren().contains( paint_line ) ) {
							
							gc.setStroke( clrPicker.getValue() );
							gc.setLineWidth( paint_quadCurve.getStrokeWidth() );
							gc.beginPath();
							gc.moveTo( paint_quadCurve.getStartX() - (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2) 
									, paint_quadCurve.getStartY() - (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2) );
							gc.quadraticCurveTo( paint_quadCurve.getControlX() - (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2)
									, paint_quadCurve.getControlY() - (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2)
									, paint_quadCurve.getEndX() - (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2)
									, paint_quadCurve.getEndY() - (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2) );
							gc.stroke();
							gc.closePath();
							paint_bPane.getChildren().remove( paint_quadCurve );
						}
						else {
							
							paint_quadCurve.setStartX( paint_line.getStartX() );
							paint_quadCurve.setStartY( paint_line.getStartY() );
							
							paint_quadCurve.setEndX( paint_line.getEndX() );
							paint_quadCurve.setEndY( paint_line.getEndY() );
						}
					}
				}
                else if( shapesBox.getSelectionModel().getSelectedItem() == "Cubic Curve" ) {
					
					paint_line.setStroke( clrPicker.getValue() );
					paint_line.setStrokeWidth( sizeBox.getSelectionModel().getSelectedItem().doubleValue() );
					
					paint_quadCurve.setStroke( clrPicker.getValue() );
					paint_quadCurve.setStrokeWidth( sizeBox.getSelectionModel().getSelectedItem().doubleValue() );
					
					paint_cubicCurve.setStroke( clrPicker.getValue() );
					paint_cubicCurve.setStrokeWidth( sizeBox.getSelectionModel().getSelectedItem().doubleValue() );
					if( e.getEventType() == MouseEvent.MOUSE_PRESSED ) {
						
						if( !paint_bPane.getChildren().contains( paint_line ) && !paint_bPane.getChildren().contains( paint_quadCurve ) ) {
							
							paint_bPane.getChildren().add( paint_line );
							paint_line.setStartX( e.getX() + (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2) );
							paint_line.setStartY( e.getY() + (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2) );
						}
						else {
							
							if( paint_bPane.getChildren().contains( paint_line ) ) {
								
								paint_bPane.getChildren().remove( paint_line );
							}
							if( paint_bPane.getChildren().contains( paint_quadCurve ) ) {
								
								paint_bPane.getChildren().remove( paint_quadCurve );
								paint_bPane.getChildren().add( paint_cubicCurve );
								
								paint_cubicCurve.setControlX2( e.getX() + (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2) );
								paint_cubicCurve.setControlY2( e.getY() + (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2) );
							}
							if( !paint_bPane.getChildren().contains( paint_quadCurve ) && !paint_bPane.getChildren().contains( paint_cubicCurve ) ) {
								
								paint_bPane.getChildren().add( paint_quadCurve );

								paint_quadCurve.setControlX( e.getX() + (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2) );
								paint_quadCurve.setControlY( e.getY() + (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2) );
							}
						}
					}
					else if( e.getEventType() == MouseEvent.MOUSE_DRAGGED ) {
						
						if( paint_bPane.getChildren().contains( paint_line ) ) {
							
							paint_line.setEndX( e.getX() + (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2) );
							paint_line.setEndY( e.getY() + (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2) );
						}	
						if( paint_bPane.getChildren().contains( paint_quadCurve ) && !paint_bPane.getChildren().contains( paint_line )){
							
							paint_quadCurve.setControlX( e.getX() + (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2) );
							paint_quadCurve.setControlY( e.getY() + (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2) );
						}
						if( paint_bPane.getChildren().contains( paint_cubicCurve ) ) {
							
							paint_cubicCurve.setControlX2( e.getX() + (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2) );
							paint_cubicCurve.setControlY2( e.getY() + (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2) );
						}
					}
					else if( e.getEventType() == MouseEvent.MOUSE_RELEASED ) {
						
						if( !paint_bPane.getChildren().contains( paint_line ) && !paint_bPane.getChildren().contains( paint_quadCurve ) ) {
							
							gc.setStroke( clrPicker.getValue() );
							gc.setLineWidth( paint_cubicCurve.getStrokeWidth() );
							gc.beginPath();
							gc.moveTo( paint_cubicCurve.getStartX() - (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2) 
									, paint_cubicCurve.getStartY() - (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2) );
							gc.bezierCurveTo( paint_cubicCurve.getControlX1() - (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2)
									, paint_cubicCurve.getControlY1() - (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2)
									, paint_cubicCurve.getControlX2() - (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2)
									, paint_cubicCurve.getControlY2() - (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2)
									, paint_cubicCurve.getEndX() - (( paint_bPane.getPrefWidth() - canvas.getWidth() )/2) 
									, paint_cubicCurve.getEndY() - (( paint_bPane.getPrefHeight() - canvas.getHeight() )/2) );
							gc.stroke();
							gc.closePath();
							paint_bPane.getChildren().remove( paint_cubicCurve );
						}
						if( !paint_bPane.getChildren().contains( paint_quadCurve ) ) {
							
							paint_quadCurve.setStartX( paint_line.getStartX() );
							paint_quadCurve.setStartY( paint_line.getStartY() );
							
							paint_quadCurve.setEndX( paint_line.getEndX() );
							paint_quadCurve.setEndY( paint_line.getEndY() );
						}
						if( !paint_bPane.getChildren().contains( paint_cubicCurve ) && paint_bPane.getChildren().contains( paint_quadCurve ) ) {
							
							paint_cubicCurve.setStartX( paint_quadCurve.getStartX() );
							paint_cubicCurve.setStartY( paint_quadCurve.getStartY() );
							
							paint_cubicCurve.setEndX( paint_quadCurve.getEndX() );
							paint_cubicCurve.setEndY( paint_quadCurve.getEndY() );
							
							paint_cubicCurve.setControlX1( paint_quadCurve.getControlX() );
							paint_cubicCurve.setControlY1( paint_quadCurve.getControlY() );
						}
					}
				}
				
				e.consume();
			}
		});
		
		loadImgBtn.setOnMouseClicked( new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mcEvent ) {
				
				/*if( Creator.getImage() == null ) {
					;
				}
				else {
					
					canvas.setWidth( Creator.getCanvasWidth() );
					canvas.setHeight( Creator.getCanvasHeight() );
					gc.drawImage( Creator.getImage() , 0 , 0 , Creator.getCanvasWidth() , Creator.getCanvasHeight() );
				}*/
			}
		});
		
		Scene paintScene = new Scene( paint_root , Screen.getPrimary().getVisualBounds().getWidth() -16
				, Screen.getPrimary().getVisualBounds().getHeight() -39 , Color.GAINSBORO );
		
		Stage newWindow = new Stage();
		newWindow.setTitle("Doodle");
		newWindow.setScene( paintScene );
		newWindow.show() ;
	}
	
}
