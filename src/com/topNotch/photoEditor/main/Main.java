package com.topNotch.photoEditor.main;

import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import com.topNotch.photoEditor.effects.blackAndWhite.BlackAndWhiteEffect;
import com.topNotch.photoEditor.effects.brigtnsAndCntrst.BrightnessContrastEffect;
import com.topNotch.photoEditor.effects.chnlMxr.ChannelMixerEffect;
import com.topNotch.photoEditor.effects.clrBalance.ColorBalanceEffect;
import com.topNotch.photoEditor.effects.exposure.ExposureEffect;
import com.topNotch.photoEditor.effects.hueAndSat.HueSaturationEffect;
import com.topNotch.photoEditor.effects.invert.InvertEffect;
import com.topNotch.photoEditor.effects.levels.LevelsEffectNew;
import com.topNotch.photoEditor.effects.photoFltr.PhotoFilterEffect;
import com.topNotch.photoEditor.effects.posterize.PosterizeEffect;
import com.topNotch.photoEditor.effects.slctvClr.SelectiveColorEffect;
import com.topNotch.photoEditor.effects.threshold.ThresholdEffect;
import com.topNotch.photoEditor.effects.vibrance.VibranceEffect;
import com.topNotch.photoEditor.paint.Paint;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.DepthTest;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.ImageInput;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.CornerRadii ;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		
		BorderPane root = new BorderPane() ;
		root.setBackground( new Background( new BackgroundFill( Color.rgb( 48, 48, 48) , CornerRadii.EMPTY , Insets.EMPTY ) ) );
		
		VBox leftVBox = new VBox() ;
		VBox centerVBox = new VBox() ;
		VBox rightVBox = new VBox() ;
		
		createCenterObject( centerVBox ) ;
		createRightObject( rightVBox ) ;
		
		root.setCenter( centerVBox );
		root.setRight( rightVBox );
		
		Scene scene = new Scene( root , Screen.getPrimary().getVisualBounds().getWidth() , Screen.getPrimary().getVisualBounds().getHeight() ) ;
		primaryStage.setScene( scene );
		primaryStage.setTitle( "Photo Editor" );
		primaryStage.show(); 
	}
	
	private void createLeftObject() {
		
	}
	
	private void createCenterObject( VBox centerVBox ) {
		
		centerVBox.getChildren().add( PrimaryWorkArea.getInstance() ) ;
		centerVBox.getChildren().add( PrimaryWorkAreaObjectStatsDisplayingTools.getInstance() ) ;
		centerVBox.getChildren().add( PrimaryWorkObjectManipulatingTools.getInstance() ) ;
	}
	
	private void createRightObject( VBox rightVBox ) {
		
		HBox hBox = new HBox() ;
		
		hBox.getChildren().addAll( PrimaryWorkAreaObjectBlendTools.getInstance() , PWAOPropertiesTool.getInstance().getPropertiesToggleBtn() ) ;
		rightVBox.getChildren().add( hBox ) ;
		
		Separator separator1 = new Separator();
		separator1.setTranslateX(-2);
		rightVBox.getChildren().add( separator1 );
		
		rightVBox.getChildren().add( PrimaryWorkAreaObjectEffectTools.getInstance() ) ;
		
		Separator separator2 = new Separator();
		separator2.setTranslateX(-2);
		separator2.setTranslateY(2);
		rightVBox.getChildren().add( separator2 );
		
		rightVBox.getChildren().add( SecondaryWorkArea.getInstance() ) ;
	}
}