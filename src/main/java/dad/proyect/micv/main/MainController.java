package dad.proyect.micv.main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {

    @FXML
    private BorderPane view;

    @FXML
    private Tab personalTab;

    @FXML
    private Tab contactoTab;

    @FXML
    private Tab formacionTab;

    @FXML
    private Tab experienciaTab;

    @FXML
    private Tab conocimientosTab;

    @FXML
    void onAbrirAction(ActionEvent event) {

    }

    @FXML
    void onAcercaAction(ActionEvent event) {

    }

    @FXML
    void onGuardarAction(ActionEvent event) {

    }

    @FXML
    void onGuardarComoAction(ActionEvent event) {

    }

    @FXML
    void onNuevoAction(ActionEvent event) {

    }

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	

}

	public BorderPane getView() {
		return view;
	}
	
}
