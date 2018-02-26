package Cryptofriends;

import core.GameManager;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Cgram.fxml"));
			BorderPane root = loader.load();
			CgramController controller = loader.getController();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
	        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
	        	
	            if (keyEvent.getCode().isLetterKey()) {
	            	System.out.println("Yep, letter " + keyEvent.getCode());
	            	controller.setAnswer(keyEvent.getCode().toString());
	            }
	        });
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Cryptograms");
			
			GameManager gameMan = new GameManager();
			controller.setGameManager(gameMan);
			controller.loadNewPuzzle();
			
			/*
			String phraseToTest = "I'm in a house full of baboons." 
					+ " They're driving me crazy! Help me! I'll pay you."
					+ "Like $4000? I'm in a house full of baboons." 
					+ " They're driving me crazy! Help me! I'll pay you."
					+ "Like $4000?";
			controller.setupPuzzle(gameMan.getPuzzle(phraseToTest));
			*/
			
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
