package Cryptofriends;

import core.Managers.GameManager;
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
			controller.setScene(scene);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Cryptograms");
			
			// For when user exits with the X window button
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					controller.exitProgram();
				}
				
			});
			
			primaryStage.setOnShowing(new EventHandler<WindowEvent>(){
				@Override
				public void handle(WindowEvent event) {
					controller.setStageShowing(true);
				}
			});
			
			GameManager gameMan = new GameManager(controller);
			controller.setGameManager(gameMan);
			controller.createBoard();
			controller.loadNewPuzzle();
			
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
