package com.topNotch.photoEditor.main;

import javafx.scene.layout.VBox;

public interface LinkableEffect {
	
	public abstract int[] getPostModifiedRED() ;
	public abstract int[] getPostModifiedGREEN() ;
	public abstract int[] getPostModifiedBLUE() ;
	public abstract int[] getPostModifiedARGB() ;
	
	public abstract VBox getRoot() ;
}
