package Cryptofriends;

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
			
			//* TODO: REMOVE - DOESN'T WORK
			// CURRENTLY TRYING TO LOAD ON STARTUP TO TEST
			primaryStage.setOnShown(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent event) {
			    	controller.addLotsOfBoxes();
			    }
			});
			//*/
			
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
