package marble;
	
import javafx.application.Application;
import javafx.stage.Stage;
import marble.scene.MainScene;
import marble.scene.MarbleScene;
import marble.scene.TitleScene;

import java.util.ArrayList;


public class MarbleApp extends Application {
	private Stage stage;
	private ArrayList<MarbleScene> scenes = new ArrayList<>();
	private int currentScene = 0;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			scenes.add(new TitleScene(this, 800, 600));
			scenes.add(new MainScene(this, 800, 600));
			stage = primaryStage;
			stage.setTitle("전주마블");
			stage.setResizable(false);
			stage.setScene(scenes.get(currentScene));
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeScene(int sceneNum, int type) {
		currentScene = sceneNum;
		scenes.get(currentScene).initialize(type);
		stage.setScene(scenes.get(currentScene));
	}

	public Stage getStage() {
		return stage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
