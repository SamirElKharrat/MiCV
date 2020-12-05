package dad.proyect.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.proyect.micv.main.App;
import dad.proyect.micv.model.Contacto.Contacto;
import dad.proyect.micv.model.Contacto.Email;
import dad.proyect.micv.model.Contacto.Telefono;
import dad.proyect.micv.model.Contacto.TipoTelefono;
import dad.proyect.micv.model.Contacto.Web;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

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
	
	public ContactoController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ContactoView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Configurar Tabla Telefonos
		numero.setCellValueFactory(v -> v.getValue().numeroProperty());
		numero.setCellFactory(TextFieldTableCell.forTableColumn());
		
		tipo.setCellValueFactory(v -> v.getValue().tipoTelefonoProperty());
		tipo.setCellFactory(ComboBoxTableCell.forTableColumn(TipoTelefono.values()));
		
		//Configurar Tabla Email
		email.setCellValueFactory(v -> v.getValue().direccionProperty());
		email.setCellFactory(TextFieldTableCell.forTableColumn());
		
		//Configurar Tabla Web
		url.setCellValueFactory(v -> v.getValue().urlProperty());
		url.setCellFactory(TextFieldTableCell.forTableColumn());

		this.contacto.addListener((o, ov, nv) -> onContactoChanged(o, ov, nv));
		
	}
	
	private void onContactoChanged(ObservableValue<? extends Contacto> o, Contacto ov, Contacto nv) {
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
	
	@FXML
	private void onActionAñadirT(ActionEvent event) {
		Dialog<Pair<String,TipoTelefono>> dialog = new Dialog<>();
		dialog.setTitle("Nuevo teléfono");
		dialog.setContentText("Introduzca el nuevo número de teléfono.");

		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));

		ButtonType btAnadir = new ButtonType("Añadir", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(btAnadir, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField tfNumero = new TextField();
		tfNumero.setPromptText("Número de teléfono");
		
		ComboBox<TipoTelefono> cbTt = new ComboBox<>();
		cbTt.getItems().addAll(TipoTelefono.values());
		cbTt.setPromptText("Selecciones un tipo:");
		
		Node nodeBtAnadir = dialog.getDialogPane().lookupButton(btAnadir);
		nodeBtAnadir.setDisable(true);
		
		nodeBtAnadir.disableProperty().bind(
				tfNumero.textProperty().isEmpty().or(
				cbTt.valueProperty().isNull()));
		
		grid.add(new Label("Número:"), 0, 0);
		grid.add(tfNumero, 1, 0);
		grid.add(new Label("Tipo:"), 0, 1);
		grid.add(cbTt, 1, 1);
		
		dialog.getDialogPane().setContent(grid);
		
		Platform.runLater(() -> tfNumero.requestFocus());
		
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == btAnadir) {
				return new Pair<>(tfNumero.getText(), cbTt.getSelectionModel().getSelectedItem());
			}
			
			return null;
		});
		
		Optional<Pair<String, TipoTelefono>> result = dialog.showAndWait();
		
		if (result.isPresent()) {
			Telefono tlf = new Telefono();
			tlf.setNumero(result.get().getKey());
			tlf.setTipoTelefono(result.get().getValue());
			contacto.get().getTelefono().add(tlf);
		}
		
	}
	
	@FXML
	public void OnEliminarT(ActionEvent event) {
		String title = "Eliminar teléfono";
		String header = "Antes de continuar, confirme";
		String content = "Esta operación es irreversible.\n¿Está seguro de borrar el teléfono?";
		Telefono telefono = telefonoSeleccionado.get();
		
		if (telefono != null && App.confirmacion(title, header, content))
			contacto.get().getTelefono().remove(telefono);
		
	}
	
	@FXML
	public void OnActionAñadirC(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		
		dialog.setTitle("Nuevo e-mail");
		dialog.setHeaderText("Crear una nueva dirección de correo.");
		dialog.setContentText("E-mail:");
		
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		
		dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(dialog.getEditor().textProperty().isEmpty());
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			Email email = new Email();
			email.setDireccion(result.get());
			contacto.get().emailProperty().add(email);
		}
		
	}
	
	@FXML
	public void OnEliminarC(ActionEvent event) {
		String title = "Eliminar correo";
		String header = "Antes de continuar.";
		String content = "¿Está seguro de borrar el correo?";
		Email email = emailSeleccionado.get();
		
		if (email != null && App.confirmacion(title, header, content))
			contacto.get().getTelefono().remove(email);
	}
	
	@FXML
	public void OnActionAñadirW(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		
		dialog.setTitle("Nueva Web");
		dialog.setHeaderText("Crear una nueva dirección web.");
		dialog.setContentText("URL:");
		
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		
		dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(dialog.getEditor().textProperty().isEmpty());
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			Web web = new Web();
			web.setUrl(result.get());
			contacto.get().webProperty().add(web);
		}
	}
	
	@FXML
	public void OnEliminarW(ActionEvent event) {
		String title = "Eliminar url";
		String header = "Antes de continuar.";
		String content = "¿Está seguro de borrar el url?";
		Web web = webSeleccionada.get();
		
		if (web != null && App.confirmacion(title, header, content))
			contacto.get().getWeb().remove(web);
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
	
	public void SetView(SplitPane view) {
		this.view = view;
	}
	
	
	
}
