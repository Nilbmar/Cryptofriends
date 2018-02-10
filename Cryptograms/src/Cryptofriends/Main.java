package Cryptofriends;
	
import Cryptofriends.SpaceContainer.SpaceBox;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Cgram.fxml"));
			HBox flowPane = new HBox();
			SpaceBox spaceBox = null;
			for (int x = 0; x < 10; x++) {
				spaceBox = new SpaceBox();
				spaceBox.setAnswerCharLabel('a');
				spaceBox.setDisplayCharLabel('F');
				spaceBox.setUnderlined(true);
				flowPane.getChildren().add(spaceBox);
				
				spaceBox = new SpaceBox();
				spaceBox.setAnswerCharLabel(' ');
				spaceBox.setDisplayCharLabel(' ');
				spaceBox.setUnderlined(false);
				flowPane.getChildren().add(spaceBox);
				
				spaceBox = new SpaceBox();
				spaceBox.setAnswerCharLabel('!');
				spaceBox.setDisplayCharLabel(' ');
				spaceBox.setUnderlined(false);
				flowPane.getChildren().add(spaceBox);
			}
			root.getChildren().add(flowPane);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Cryptograms");
			
			
			
			
			
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
