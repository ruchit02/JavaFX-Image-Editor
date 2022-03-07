package com.topNotch.photoEditor.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PrimaryWorkAreaObject implements LinkableEffect {
	
	private static final List<PrimaryWorkAreaObject> objectsCreated = new ArrayList<PrimaryWorkAreaObject>() ;
	
	private final VBox root ;
	private final String filePath ;
	private final String fileName ;
	private final Image image ;
	private final Tab imageTab ;
	private final ScrollPane scrollPane ;
	private final StackPane stackPane ;
	private final Canvas canvas ;
	private final GraphicsContext gc ;
	private DoubleProperty width = new SimpleDoubleProperty(0) ;
	private DoubleProperty height = new SimpleDoubleProperty(0) ;
	
	private final int[] imageARGB ;
	private final int[] imageRED ;
	private final int[] imageGREEN ;
	private final int[] imageBLUE ;
	
	private final int postModificationRED[]   ; 
	private final int postModificationGREEN[] ;
	private final int postModificationBLUE[]  ;
	private final int postModificationARGB[]  ;
	
	private final int staticRED[]   ; 
	private final int staticGREEN[] ;
	private final int staticBLUE[]  ;
	private final int staticARGB[]  ;
	
	public PrimaryWorkAreaObject( String filePath , String fileName ) {
		
		this.filePath   = Objects.requireNonNull( filePath ) ;
		this.fileName   = Objects.requireNonNull( fileName ) ;
		this.image      = new Image( filePath ) ;
		this.imageTab   = new Tab() ;
		this.scrollPane = new ScrollPane() ;
		this.stackPane  = new StackPane() ;
		this.canvas     = new Canvas() ;
		this.gc         = this.canvas.getGraphicsContext2D() ;
		this.root       = new VBox() ;
		
		resizeImage() ;
		createObject() ;
		
		this.imageARGB      = new int[ (int)this.width.get()*(int)this.height.get() ] ;
		this.imageRED       = new int[ (int)this.width.get()*(int)this.height.get() ] ;
		this.imageGREEN     = new int[ (int)this.width.get()*(int)this.height.get() ] ;
		this.imageBLUE      = new int[ (int)this.width.get()*(int)this.height.get() ] ;
		
		this.postModificationRED    = new int[ (int)this.width.get()*(int)this.height.get() ] ;
		this.postModificationGREEN  = new int[ (int)this.width.get()*(int)this.height.get() ] ;
		this.postModificationBLUE   = new int[ (int)this.width.get()*(int)this.height.get() ] ;
		this.postModificationARGB   = new int[ (int)this.width.get()*(int)this.height.get() ] ;
		
		this.staticRED    = new int[ (int)this.width.get()*(int)this.height.get() ] ;
		this.staticGREEN  = new int[ (int)this.width.get()*(int)this.height.get() ] ;
		this.staticBLUE   = new int[ (int)this.width.get()*(int)this.height.get() ] ;
		this.staticARGB   = new int[ (int)this.width.get()*(int)this.height.get() ] ;
		
		storeImageData() ;
	}
	
	@Override
	public final int[] getPostModifiedRED() {
		
		return this.postModificationRED ;
	}
	
	@Override
	public final int[] getPostModifiedGREEN() {
		
		return this.postModificationGREEN ;
	}
	
	@Override
	public final int[] getPostModifiedBLUE() {
		
		return this.postModificationBLUE ;
	}
	
	@Override
	public final int[] getPostModifiedARGB() {
		
		return this.postModificationARGB ;
	}
	
	@Override
	public VBox getRoot() {
		
		return this.root ;
	}
	
	private void resizeImage() {
		
		double w = image.getWidth() ;
		double h = image.getHeight() ;
		
		double res = 0 , specialResult = 0  ;
		
        if( w > h ) {
			
			res = h/w ;
			specialResult = w/h ;
			//stkPane.getPrefWidth() = 1100
			while( w > 1100 ) {
				
				w = w - 200 ;
			}
			
			this.width.set( w ) ;
			this.height.set( res*this.width.get() ) ;
			
			//SPECIAL CASE 
			//stkPane.getPrefHeight() = 570
			if ( this.height.get() > 570 ) {
				
				while ( this.height.get() > 570 ) {
					
					this.height.set( this.height.get() - 75 ) ;
				}
				this.width.set( specialResult*this.height.get() ) ;
			}
			this.width.set( Math.floor( this.width.get() ) );
			this.height.set( Math.floor( this.height.get() ) );
		}
		else {
			
			res = w/h ;
			specialResult = h/w ;
			//stkPane.getPrefHeight = 570
			while(  h > 570 ) {
				
				h = h - 200 ;
			}
			this.height.set( h ) ;
			this.width.set( res*this.height.get() ) ;
			
			//SPECIAL CASE 
			//stkPane.getPrefWidth() = 1100
			if ( this.width.get() > 1100 ) {
				
				while ( this.width.get() > 1100 ) {
					
					this.width.set( this.width.get() - 75 );
				}
				this.height.set( specialResult*this.width.get() ) ;
			}
			this.width.set( Math.floor( this.width.get() ) );
			this.height.set( Math.floor( this.height.get() ) );
		}
	}
	
	private void createObject() {
		
		this.imageTab.setText( fileName );
		
		this.scrollPane.setPrefSize(1114, 583);
		
		this.stackPane.setBackground(new Background( new BackgroundFill( Color.rgb( 48, 48, 48) , new CornerRadii(0.0) , new Insets( 0.0 , 0.0 , 0.0 , 0.0) ) ));
		this.stackPane.setPrefSize(1100, 570 );
		
		this.canvas.setWidth( this.width.get() );
		this.canvas.setHeight( this.height.get() );
		this.gc.drawImage( image , 0 , 0 , this.width.get() , this.height.get() );
		displayPixelCo_ordinates( canvas , PrimaryWorkAreaObjectStatsDisplayingTools.getInstance().getTextFieldX() 
				                , PrimaryWorkAreaObjectStatsDisplayingTools.getInstance().getTextFieldY() ) ;
		installRGBValueFinder( canvas , PrimaryWorkAreaObjectStatsDisplayingTools.getInstance().getTextFieldR()
				, PrimaryWorkAreaObjectStatsDisplayingTools.getInstance().getTextFieldG() 
				, PrimaryWorkAreaObjectStatsDisplayingTools.getInstance().getTextFieldB() ) ;
		
		installCanvasScrollingProperty( canvas , stackPane , scrollPane ) ;
		
		this.imageTab.setContent( this.scrollPane );
		this.scrollPane.setContent( this.stackPane );
		this.stackPane.getChildren().add( this.canvas );
		
		objectsCreated.add( this );
	}
	
	private void displayPixelCo_ordinates( Canvas canvas , TextField tfX , TextField tfY ) {
		
		 EventHandler<MouseEvent> pixelCo_ordinates = new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseMovedEvent ) {
				
			    tfX.setText( Double.toString( mouseMovedEvent.getX() ) );
				tfY.setText( Double.toString( mouseMovedEvent.getY() ) ) ;  
				
				mouseMovedEvent.consume();
			}
		};
		
		canvas.addEventHandler( MouseEvent.MOUSE_MOVED , pixelCo_ordinates );
	}
	
    private void installRGBValueFinder( Canvas canvas , TextField textFieldRed , TextField textFieldGreen , TextField textFieldBlue ) {
		
		EventHandler<MouseEvent> canvasColorComponent = new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mcEvent ) {
				
				WritableImage wImage = canvas.snapshot( new SnapshotParameters() , null );
				int ARGBobtained = wImage.getPixelReader().getArgb(  (int)mcEvent.getX() , (int)mcEvent.getY() ) ;
				
				int red   = ( ARGBobtained >> 16 ) & 0xff ;
				int green = ( ARGBobtained >> 8 ) & 0xff ;
				int blue  = ( ARGBobtained  ) & 0xff ;
				
				textFieldRed.setText( Integer.toString(red) );
				textFieldGreen.setText( Integer.toString(green));
				textFieldBlue.setText( Integer.toString(blue));
			}
		};
		
		canvas.addEventHandler( MouseEvent.MOUSE_MOVED , canvasColorComponent );
	}
	
	private void installCanvasScrollingProperty( Canvas canvas , StackPane stackPane , ScrollPane scrollPane ) {
		
		EventHandler<ScrollEvent> canvasScrolling = new EventHandler<ScrollEvent>() {
			@Override
			public void handle( ScrollEvent scrollEvent ) {
			
				double scale = scrollEvent.getDeltaY() ;
				
				if( scrollEvent.getDeltaY() > 0  ) {				
	
					stackPane.setScaleX( stackPane.getScaleX()*scale/35 );
					stackPane.setScaleY( stackPane.getScaleY()*scale/35 );
			     	
                    if( stackPane.getScaleX() > 1.05 ) {
						
                    	scrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
					}
					
					if( stackPane.getScaleY() > 1.05 ) {
						
						scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
					}
			     	
				}
				else if( scrollEvent.getDeltaY() < 0 ) {
					
					stackPane.setScaleX(- stackPane.getScaleY()/(scale/35) );
					stackPane.setScaleY(- stackPane.getScaleY()/(scale/35) );
					
					 if( stackPane.getScaleX() <= 1.05 ) {
							
						 scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
						}
						
						if( stackPane.getScaleY() <= 1.05 ) {
							
							scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
						}
				}	
				stackPane.setPrefWidth(stackPane.getScaleX()*1100);
				stackPane.setPrefHeight(stackPane.getScaleY()*570);
	 
	            	scrollEvent.consume();
			}
		};
		
		canvas.setOnScroll( canvasScrolling );
	}
	
	private void storeImageData() {
		
		int i = 0 ;
		final WritableImage wImg = canvas.snapshot( new SnapshotParameters() , null );
    	final PixelReader pixelReader = wImg.getPixelReader() ;
		
		for( int y = 0 ; y < (int)this.height.get() ; y++ ) {
			
			for( int x = 0 ; x < (int)this.width.get() ; x++ ) {
				
				int ARGBobtained         = pixelReader.getArgb( x , y );
				
				int red                  = (0xff & (ARGBobtained >> 16)) ;
				int green                = (0xff & (ARGBobtained >> 8))  ;
				int blue                 = (0xff & (ARGBobtained));	
				
				imageRED[i]              = red   ;
				imageGREEN[i]            = green ;
				imageBLUE[i]             = blue  ;
				imageARGB[i]             = ARGBobtained ;
				
				staticRED[i]			 = red ;
				staticGREEN[i]			 = green ;
				staticBLUE[i]			 = blue ;
				staticARGB[i]			 = ARGBobtained ;
				
				i++ ;
			}
		}
		
		putDataInFinalArray() ;
	}
	
    private void putDataInFinalArray() {
		
		int i = 0 ;
		final WritableImage wImg = canvas.snapshot( new SnapshotParameters() , null );
    	final PixelReader pixelReader = wImg.getPixelReader() ;
		
		for( int y = 0 ; y < (int)height.get() ; y++ ) {
			for( int x = 0 ; x < (int)width.get() ; x++ ) {
				
                int ARGBobtained          			= pixelReader.getArgb( x , y );
				
				int red                   			= (0xff & (ARGBobtained >> 16)) ;
				int green                 			= (0xff & (ARGBobtained >> 8))  ;
				int blue                  			= (0xff & (ARGBobtained));	
				
				postModificationRED[i]            	= red ;
				postModificationGREEN[i]          	= green ;
				postModificationBLUE[i]           	= blue ;
				postModificationARGB[i]				= ARGBobtained ;
				
				i++ ;
			}
		}
	}
	
	//getters only
	public static final List<PrimaryWorkAreaObject> getObjects(){
		
		return PrimaryWorkAreaObject.objectsCreated ;
	}
	
	public final String getFilePath() {
		
		return this.filePath ;
	}
	
	public final String getFileName() {
		
		return this.fileName ;
	}
	
	public final Image getImage() {
		
		return this.image ;
	}
	
	public final ScrollPane getScrollPane() {
		
		return this.scrollPane ;
	}
	
	public final StackPane getStackPane() {
		
		return this.stackPane ;
	}
	
	public final Canvas getCanvas() {
		
		return this.canvas ;
	}
	
	public final Tab getTab() {
		
		return this.imageTab ;
	}
	
	public final double getCanvasWidth() {
		
		return this.width.get() ;
	}
	
	public final double getCanvasHeight() {
		
		return this.height.get() ;
	}
	
	public final int[] getImageData() {
		
		return this.imageARGB ;
	}
	
	public final int[] getImageRed() {
		
		return this.imageRED ;
	}
	
	public final int[] getImageGreen() {
		
		return this.imageGREEN ;
	}
	
	public final int[] getImageBlue() {
		
		return this.imageBLUE ;
	}
	
	public final int[] getStaticRed() {
		
		return this.staticRED ;
	}
	
    public final int[] getStaticGreen() {
		
		return this.staticGREEN ;
	}
    
    public final int[] getStaticBlue() {
		
		return this.staticBLUE ;
	}
    
    public final int[] getStaticArgb() {
		
		return this.staticARGB ;
	}
	
	///////setters only
	public final void setWidth( double value ) {
		
		this.width.set( value );
	}
	
	public final void setHeight( double value ) {
		
		this.height.set( value );
	}
}
