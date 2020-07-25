module MyGame {
	requires transitive javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.media;
	requires javafx.graphics;
	
	exports main;
	exports graphics.objects;
	exports controllers;
	opens controllers;
	exports graphics;
	exports gameStates;
	
}