package dad.proyect.micv.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.proyect.micv.model.Personal;
import dad.proyect.micv.model.nacionalidad;
import dad.proyect.micv.util.Lector;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PersonalController implements Initializable {
	

	// model
	private ObjectProperty<Personal> personal = new SimpleObjectProperty<Personal>();
	private ObjectProperty<nacionalidad> nacionalidadSeleccionada = new SimpleObjectProperty<>();

	// view
	@FXML
	private GridPane view;

	@FXML
	private TextField identificacionText;

	@FXML
	private TextField nombreText;

	@FXML
	private TextField apellidosText;
	
    @FXML
    private DatePicker fechaPicker;

    @FXML
    private TextArea diraccionText;

    @FXML
    private TextField postalText;

    @FXML
    private TextField localidadText;

    @FXML
    private ComboBox<String> paisBox;

    @FXML
    private ListView<nacionalidad> nacionalidadList;
    
    private List<String> listadonacionalidad = new ArrayList<>();    

    @FXML
    private Button masButton;

    @FXML
    private Button menosButton;

	public PersonalController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PersonalView.fxml"));
		loader.setController(this);
		loader.load();
	}

	public void initialize(URL location, ResourceBundle resources) {
		
		//Cargamos paises
		try {
			paisBox.getItems().addAll(Lector.leerFichero("/csv/paises.csv"));
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
			Platform.exit();
		}
		
		//Cargamos nacionalidad
		try {
			listadonacionalidad.addAll(Lector.leerFichero("/csv/nacionalidades.csv"));
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
			Platform.exit();
		}
		
		this.personal.addListener((o, ov, nv) -> OnPersonalChanged(o, ov, nv));

	}

	private void OnPersonalChanged(ObservableValue<? extends Personal> o, Personal ov, Personal nv) {

		if (ov != null) {
			nombreText.textProperty().unbindBidirectional(ov.nombreProperty());
			apellidosText.textProperty().unbindBidirectional(ov.apellidosProperty());
			identificacionText.textProperty().unbindBidirectional(ov.identificacionProperty());
			fechaPicker.valueProperty().unbindBidirectional(ov.fechaNacimientoProperty());
			diraccionText.textProperty().unbindBidirectional(ov.direccionProperty());
			postalText.textProperty().unbindBidirectional(ov.codigoPostalProperty());
			localidadText.textProperty().unbindBidirectional(ov.localidadProperty());
			paisBox.valueProperty().unbindBidirectional(ov.paisProperty());
			nacionalidadList.itemsProperty().unbind();
			nacionalidadSeleccionada.unbind();
			menosButton.disableProperty().unbind();
		}

		if (nv != null) {
			nombreText.textProperty().bindBidirectional(nv.nombreProperty());
			apellidosText.textProperty().bindBidirectional(nv.apellidosProperty());
			identificacionText.textProperty().bindBidirectional(nv.identificacionProperty());
			fechaPicker.valueProperty().bindBidirectional(nv.fechaNacimientoProperty());
			diraccionText.textProperty().bindBidirectional(nv.direccionProperty());
			postalText.textProperty().bindBidirectional(nv.codigoPostalProperty());
			localidadText.textProperty().bindBidirectional(nv.localidadProperty());
			paisBox.valueProperty().bindBidirectional(nv.paisProperty());
			nacionalidadList.itemsProperty().bind(nv.nacionalidadesProperty());
			nacionalidadSeleccionada.bind(nacionalidadList.getSelectionModel().selectedItemProperty());
			menosButton.disableProperty().bind(Bindings.isEmpty(nacionalidadList.getItems()));
		}
	}
	
	@FXML
	public void OnActionNacionaidad(ActionEvent event) {


		ChoiceDialog<String> dialog = new ChoiceDialog<>(listadonacionalidad.get(0), listadonacionalidad);
		dialog.setTitle("Nueva nacionalidad");
		dialog.setHeaderText("Añadir Nacionalidad");
		dialog.setContentText("Seleccione una nacionalidad:");

		Optional<String> result = dialog.showAndWait();
		
		if (result.isPresent()){
		    nacionalidad nacionalidades = new nacionalidad();
		    nacionalidades.setDenominacion(result.get());
			
			personal.get().getNacionalidades().add(nacionalidades);
		}
	}
	
	@FXML
	public void onQuitarNacionalidad(ActionEvent event) {
		personal.get().getNacionalidades().remove(nacionalidadSeleccionada.get());
	}
	
	
	public GridPane getView() {
		return view;
	}

	public final ObjectProperty<Personal> personalProperty() {
		return this.personal;
	}

	public final Personal getPersonal() {
		return this.personalProperty().get();
	}

	public final void setPersonal(final Personal personal) {
		this.personalProperty().set(personal);
	}

}
