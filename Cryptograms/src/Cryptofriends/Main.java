package Cryptofriends;

import core.GameManager;
import core.Spaces.Phrase;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
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
			primaryStage.setScene(scene);
			primaryStage.setTitle("Cryptograms");
			
			GameManager gameMan = new GameManager();
			String phraseToTest = "I love monkeys too! And you are super glue, bouncy monkeys baboon cheese.";
			controller.setupPuzzle(gameMan.getPuzzle(phraseToTest));
			
			
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
