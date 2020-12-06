package dad.proyect.micv.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import dad.proyect.micv.model.Conocimientos.Conocimiento;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class ConocimientoController implements Initializable {
	
	//model
	private ListProperty<Conocimiento> conocimiento = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Conocimiento> conocimientoSeleccionada = new SimpleObjectProperty<>();
	
	//view
	
	@FXML
	private BorderPane view;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	

	public BorderPane getView() {
		return view;
	}



	public void setView(BorderPane view) {
		this.view = view;
	}



	public final ListProperty<Conocimiento> conocimientoProperty() {
		return this.conocimiento;
	}
	

	public final ObservableList<Conocimiento> getConocimiento() {
		return this.conocimientoProperty().get();
	}
	

	public final void setConocimiento(final ObservableList<Conocimiento> conocimiento) {
		this.conocimientoProperty().set(conocimiento);
	}
	
	
}
