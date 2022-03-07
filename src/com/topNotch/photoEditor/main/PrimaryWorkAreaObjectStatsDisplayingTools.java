package com.topNotch.photoEditor.main;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public final class PrimaryWorkAreaObjectStatsDisplayingTools extends HBox {

	private static final PrimaryWorkAreaObjectStatsDisplayingTools stats = new PrimaryWorkAreaObjectStatsDisplayingTools() ;
	
	private final HBox hBox1 ;
	private final Label tfXLabel ;
	private final TextField tfX ;
	
	private final HBox hBox2 ;
	private final Label tfYLabel ;
	private final TextField tfY ;
	
	private final HBox hBox3 ;
	private final Label tfRLabel ;
	private final TextField tfR ;
	
	private final HBox hBox4 ;
	private final Label tfGLabel ;
	private final TextField tfG ;
	
	private final HBox hBox5 ;
	private final Label tfBLabel ;
	private final TextField tfB ;
	
	private PrimaryWorkAreaObjectStatsDisplayingTools() {
		
		hBox1    = new HBox() ;
		tfXLabel = new Label();
		tfX      = new TextField();
		
		hBox2    = new HBox() ;
		tfYLabel = new Label() ;
		tfY      = new TextField() ;
		
		hBox3    = new HBox() ;
		tfRLabel = new Label() ;
		tfR      = new TextField() ;
		
		hBox4    = new HBox() ;
		tfGLabel = new Label() ;
		tfG      = new TextField() ;
		
		hBox5    = new HBox() ;
		tfBLabel = new Label() ;
		tfB      = new TextField() ;
		
		createObject() ;
	}
	
	private void createObject() {
		
		this.setMaxWidth( 1114 );
		this.setPadding( new Insets( 0,5,0,5 ) );
		this.setSpacing(5);
		
		hBox1.setSpacing(5);
		this.getChildren().add(hBox1);
		
		tfXLabel.setText("X -");
		tfXLabel.setTextFill( Color.BLACK );
		tfXLabel.setFont( Font.font( "Comic Sans MS" , FontWeight.EXTRA_BOLD , 22 ));
		hBox1.getChildren().add( tfXLabel );
		
		tfX.setMinSize(80 , 30);
		tfX.setMaxSize(80, 30);
		tfX.setEditable(false);
		hBox1.getChildren().add(tfX);
		
		hBox2.setSpacing(5);
		this.getChildren().add(hBox2);
		
		tfYLabel.setText("Y -");
		tfYLabel.setTextFill( Color.BLACK );
		tfYLabel.setFont( Font.font( "Comic Sans MS" , FontWeight.EXTRA_BOLD , 22 ));
		hBox2.getChildren().add( tfYLabel );
		
		tfY.setMinSize(80 , 30);
		tfY.setMaxSize(80, 30);
		tfY.setEditable(false);
		hBox2.getChildren().add(tfY);
		
		hBox3.setSpacing(5);
		this.getChildren().add(hBox3);
		
		tfRLabel.setText("R -");
		tfRLabel.setTextFill( Color.BLACK );
		tfRLabel.setFont( Font.font( "Comic Sans MS" , FontWeight.EXTRA_BOLD , 22 ));
		hBox3.getChildren().add( tfRLabel );
		
		tfR.setMinSize(80 , 30);
		tfR.setMaxSize(80, 30);
		tfR.setEditable(false);
		hBox3.getChildren().add(tfR);
		
		hBox4.setSpacing(5);
		this.getChildren().add(hBox4);
		
		tfGLabel.setText("G -");
		tfGLabel.setTextFill( Color.BLACK );
		tfGLabel.setFont( Font.font( "Comic Sans MS" , FontWeight.EXTRA_BOLD , 22 ));
		hBox4.getChildren().add( tfGLabel );
		
		tfG.setMinSize(80 , 30);
		tfG.setMaxSize(80, 30);
		tfG.setEditable(false);
		hBox4.getChildren().add(tfG);
		
		hBox5.setSpacing(5);
		this.getChildren().add(hBox5);
		
		tfBLabel.setText("B -");
		tfBLabel.setTextFill( Color.BLACK );
		tfBLabel.setFont( Font.font( "Comic Sans MS" , FontWeight.EXTRA_BOLD , 22 ));
		hBox5.getChildren().add( tfBLabel );
		
		tfB.setMinSize(80 , 30);
		tfB.setMaxSize(80, 30);
		tfB.setEditable(false);
		hBox5.getChildren().add(tfB);
	}
	
	public static final PrimaryWorkAreaObjectStatsDisplayingTools getInstance() {
		
		return PrimaryWorkAreaObjectStatsDisplayingTools.stats ;
	}
	
	public final TextField getTextFieldX() {
		
		return this.tfX ;
	}
	
	public final TextField getTextFieldY() {
		
		return this.tfY ;
	}
	
	public final TextField getTextFieldR() {
		
		return this.tfR ;
	}
	
	public final TextField getTextFieldG() {
		
		return this.tfG ;
	}
	
	public final TextField getTextFieldB() {
		
		return this.tfB ;
	}
}
