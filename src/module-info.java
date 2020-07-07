module MyGame {
	requires jdk.jshell;
	requires javafx.fxml;
	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires javafx.graphics;
	requires transitive java.desktop;
	requires transitive javafx.media;
	requires transitive jaco.mp3.player;
	
	exports main;
	exports graphics.objects;
	exports controllers;
	opens controllers;
	exports graphics;
	exports gameStates;
}