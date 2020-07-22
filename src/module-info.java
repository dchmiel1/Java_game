module MyGame {
	requires transitive javafx.controls;
	requires transitive jaco.mp3.player;
	requires javafx.fxml;
	
	exports main;
	exports graphics.objects;
	exports controllers;
	opens controllers;
	exports graphics;
	exports gameStates;
}