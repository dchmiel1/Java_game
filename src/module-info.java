module MyGame {
	requires javafx.fxml;
	requires transitive javafx.controls;
	requires transitive jaco.mp3.player;
	
	exports main;
	exports graphics.objects;
	exports controllers;
	opens controllers;
	exports graphics;
	exports gameStates;
}