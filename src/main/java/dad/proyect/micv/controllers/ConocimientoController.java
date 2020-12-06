package dad.proyect.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.proyect.micv.main.App;
import dad.proyect.micv.model.Experiencia;
import dad.proyect.micv.model.Conocimientos.Conocimiento;
import dad.proyect.micv.model.Conocimientos.Nivel;
import dad.proyect.micv.model.Conocimientos.Idioma;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ConocimientoController implements Initializable {

	// model
	private ListProperty<Conocimiento> conocimiento = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Conocimiento> conocimientoSeleccionada = new SimpleObjectProperty<>();

	// view

	@FXML
	private BorderPane view;

	@FXML
	private TableView<Conocimiento> conocimientos;

	@FXML
	private TableColumn<Conocimiento, String> deno;

	@FXML
	private TableColumn<Conocimiento, Nivel> nivel;
	
	@FXML
	private Button eliminar;

	public ConocimientoController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConocimientosView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//denominacion
		deno.setCellValueFactory(v -> v.getValue().denominacionProperty());
		deno.setCellFactory(TextFieldTableCell.forTableColumn());
		
		//nivel
		nivel.setCellValueFactory(v -> v.getValue().nivelProperty());
		nivel.setCellFactory(ComboBoxTableCell.forTableColumn(Nivel.values()));
		
		this.conocimiento.addListener((o, ov, nv) -> onConocimientoChanged(o, ov, nv));
	}
	
	private void onConocimientoChanged(ObservableValue<? extends ObservableList<Conocimiento>> o, ObservableList<Conocimiento> ov, ObservableList<Conocimiento> nv) {
		if(ov != null) {
			conocimientos.setItems(null);
			conocimientoSeleccionada.unbind();
			eliminar.disableProperty().unbind();
		}
		
		if(nv != null) {
			conocimientos.setItems(nv);
			conocimientoSeleccionada.bind(conocimientos.getSelectionModel().selectedItemProperty());
			eliminar.disableProperty().bind(Bindings.isEmpty(conocimientos.getItems()));
		}
		
	}
	
	@FXML
	private void OnAñadirC(ActionEvent event) {
		Dialog<Conocimiento> dialog = new Dialog<>();
		dialog.setTitle("Nuevo conocimiento");

		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));

		ButtonType crear = new ButtonType("Crear", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(crear, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField deno = new TextField();
		
		ComboBox<Nivel> cbTt = new ComboBox<>();
		cbTt.getItems().addAll(Nivel.values());
		cbTt.setPromptText("Selecciones un nivel:");
		
		Node nodeBtAnadir = dialog.getDialogPane().lookupButton(crear);
		nodeBtAnadir.setDisable(true);
		
		nodeBtAnadir.disableProperty().bind(
				deno.textProperty().isEmpty().or(
				cbTt.valueProperty().isNull()));
		
		grid.add(new Label("Denominación:"), 0, 0);
		grid.add(deno, 1, 0);
		grid.add(new Label("Nivel:"), 0, 1);
		grid.add(cbTt, 1, 1);
		
		dialog.getDialogPane().setContent(grid);
		
		Platform.runLater(() -> deno.requestFocus());
		
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == crear) {
				Conocimiento result = new Conocimiento();
				result.setDenominacion(deno.getText());
				result.setNivel(cbTt.getValue());
				return result;
			}
			
			return null;
		});
		
		Optional<Conocimiento> result = dialog.showAndWait();
		
		if (result.isPresent()) {
			conocimiento.get().add(result.get());
		}
		
	}
	
	@FXML
	private void OnAñadirI(ActionEvent event) {
		Dialog<Idioma> dialog = new Dialog<>();
		dialog.setTitle("Nuevo conocimiento");

		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));

		ButtonType crear = new ButtonType("Crear", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(crear, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField deno = new TextField();
		TextField cer = new TextField();
		
		ComboBox<Nivel> cbTt = new ComboBox<>();
		cbTt.getItems().addAll(Nivel.values());
		cbTt.setPromptText("Selecciones un nivel:");
		
		Button x = new Button("X");
		
		HBox hbox = new HBox();
		hbox.getChildren().addAll(cbTt, x);
		hbox.setSpacing(5);
		
		
		
		Node nodeBtAnadir = dialog.getDialogPane().lookupButton(crear);
		nodeBtAnadir.setDisable(true);
		
		nodeBtAnadir.disableProperty().bind(deno.textProperty().isEmpty().or(cbTt.valueProperty().isNull())
				.or(cer.textProperty().isEmpty()));
		
		x.setOnAction(e -> {
			cbTt.setValue(null);
		});
		
		grid.add(new Label("Denominación:"), 0, 0);
		grid.add(deno, 1, 0);
		grid.add(new Label("Nivel:"), 0, 1);
		grid.add(hbox, 1, 1);
		grid.add(new Label("Certificación"), 0, 2);
		grid.add(cer, 1, 2);
		
		dialog.getDialogPane().setContent(grid);
		
		Platform.runLater(() -> deno.requestFocus());
		
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == crear) {
				Idioma result = new Idioma();
				result.setDenominacion(deno.getText());
				result.setNivel(cbTt.getValue());
				result.setCertificacion(cer.getText());
				return result;
			}
			
			return null;
		});
		
		Optional<Idioma> result = dialog.showAndWait();
		
		if (result.isPresent()) {
			conocimiento.get().add(result.get());
		}
		
	}
	
	@FXML
	private void OnEliminar(ActionEvent evenet) {
		String title = "Eliminar experiencia";
		String content = "¿Está seguro que quiere borrar el Conocimiento?";
		Conocimiento formacion = conocimientoSeleccionada.get();
		
		if (formacion != null && App.confirmacion(title, content))
			conocimiento.get().remove(formacion);
		
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
