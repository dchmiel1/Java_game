module MyGame {
	requires transitive javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.media;
	
	exports main;
	exports graphics.objects;
	exports controllers;
	opens controllers;
	exports graphics;
	exports gameStates;
	
}