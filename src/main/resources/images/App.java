package dad.enviar.email.main;

import dad.enviar.email.mvc.EnviarController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    private EnviarController controller;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		controller = new EnviarController();
		
		Scene scene = new Scene(controller.getView(), 642, 512);
		primaryStage.setTitle("Enviar email");
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image(App.class.getResourceAsStream("/email-send-icon-32x32.png")));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
