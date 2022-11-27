package cv;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	private MainController controller;

	@Override
	public void start(Stage stage) throws Exception {

		controller = new MainController();

		stage.setScene(new Scene(controller.getView()));
		stage.setTitle("MiCV");
		stage.sizeToScene();
		stage.getIcons().add(new Image("file:src/main/resources/images/main.png"));
		stage.show();

	}

	public static void main(String[] args) {
		launch();
	}

}