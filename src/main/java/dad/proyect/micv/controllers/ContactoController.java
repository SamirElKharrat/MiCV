package dad.proyect.micv.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import dad.proyect.micv.model.Personal;
import dad.proyect.micv.model.Contacto.Contacto;
import dad.proyect.micv.model.Contacto.Email;
import dad.proyect.micv.model.Contacto.Telefono;
import dad.proyect.micv.model.Contacto.TipoTelefono;
import dad.proyect.micv.model.Contacto.Web;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ContactoController implements Initializable {


	//model
	private ObjectProperty<Contacto> contacto = new SimpleObjectProperty<>();
	private ObjectProperty<Telefono> telefonoSeleccionado = new SimpleObjectProperty<>();
	private ObjectProperty<Email> emailSeleccionado = new SimpleObjectProperty<>();
	private ObjectProperty<Web> webSeleccionada = new SimpleObjectProperty<>();
	
	//view
	@FXML
	private SplitPane view;
	
	@FXML
	private TableView<Telefono> telefono;
	
	@FXML
	private TableColumn<Telefono, String> numero;
	
	@FXML
	private TableColumn<Telefono, TipoTelefono> tipo;
	
	@FXML
	private Button btEliminarT;
	
	@FXML
	private TableView<Email> correo;
	
	@FXML
	private TableColumn<Email, String> email;
	
	@FXML
	private Button btEliminarC;
	
	@FXML
	private TableView<Web> web;
	
	@FXML
	private TableColumn<Web, String> url;
	
	@FXML
	private Button btEliminarW;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Configurar Tabla Telefonos

		this.contacto.addListener((o, ov, nv) -> OnContactoChanged(o, ov, nv));
		
	}
	
	private void OnContactoChanged(ObservableValue<? extends Contacto> o, Contacto ov, Contacto nv) {
		if (ov != null) {
			telefono.itemsProperty().unbind();
			telefonoSeleccionado.unbind();
			correo.itemsProperty().unbind();
			emailSeleccionado.unbind();
			web.itemsProperty().unbind();
			webSeleccionada.unbind();
			btEliminarT.disableProperty().unbind();
			btEliminarC.disableProperty().unbind();
			btEliminarW.disableProperty().unbind();
		}
		
		if (nv != null) {
			telefono.itemsProperty().bind(nv.telefonoProperty());
			telefonoSeleccionado.bind(telefono.getSelectionModel().selectedItemProperty());
			correo.itemsProperty().bind(nv.emailProperty());
			emailSeleccionado.bind(correo.getSelectionModel().selectedItemProperty());
			web.itemsProperty().bind(nv.webProperty());
			webSeleccionada.bind(web.getSelectionModel().selectedItemProperty());
			btEliminarT.disableProperty().bind(Bindings.isEmpty(telefono.getItems()));
			btEliminarC.disableProperty().bind(Bindings.isEmpty(correo.getItems()));
			btEliminarW.disableProperty().bind(Bindings.isEmpty(web.getItems()));
		}
	}

	public final ObjectProperty<Contacto> contactoProperty() {
		return this.contacto;
	}
	

	public final Contacto getContacto() {
		return this.contactoProperty().get();
	}
	

	public final void setContacto(final Contacto contacto) {
		this.contactoProperty().set(contacto);
	}
	

	public SplitPane getView() {
		return view;
	}
	
	
	
}
