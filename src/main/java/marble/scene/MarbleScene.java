package marble.scene;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import marble.MarbleApp;

public abstract class MarbleScene extends Scene {
	protected MarbleApp app;
	protected AnchorPane root;
	
	public MarbleScene(MarbleApp app, double width, double height) {
		super(new AnchorPane(), width, height);
		this.app = app;
		root = (AnchorPane) getRoot();
		root.setPrefSize(width, height);
	}
	
	public abstract void initialize(int type);

}
