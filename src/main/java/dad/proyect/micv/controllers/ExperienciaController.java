package dad.proyect.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.proyect.micv.main.App;
import dad.proyect.micv.model.Experiencia;
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
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

public class ExperienciaController implements Initializable {

	// model
	private ListProperty<Experiencia> experiencia = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Experiencia> experienciaSeleccionada = new SimpleObjectProperty<>();

	// view
	@FXML
	private BorderPane view;

	@FXML
	private TableView<Experiencia> formacion;

	@FXML
	private TableColumn<Experiencia, LocalDate> desde;

	@FXML
	private TableColumn<Experiencia, LocalDate> hasta;

	@FXML
	private TableColumn<Experiencia, String> deno;

	@FXML
	private TableColumn<Experiencia, String> emp;

	@FXML
	private Button eliminar;

	@FXML
	private Button añadir;

	public ExperienciaController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExperienciaView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// desde
		desde.setCellValueFactory(v -> v.getValue().desdeProperty());
		desde.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));

		// hasta
		hasta.setCellValueFactory(v -> v.getValue().hastaProperty());
		hasta.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));

		// denominacion
		deno.setCellValueFactory(v -> v.getValue().denominacionProperty());
		deno.setCellFactory(TextFieldTableCell.forTableColumn());

		// organizacion
		emp.setCellValueFactory(v -> v.getValue().empleadorProperty());
		emp.setCellFactory(TextFieldTableCell.forTableColumn());
		
		this.experiencia.addListener((o, ov, nv) -> onExperienciaChanged(o, ov, nv));

	}
	
	private void onExperienciaChanged(ObservableValue<? extends ObservableList<Experiencia>> o, ObservableList<Experiencia> ov, ObservableList<Experiencia> nv) {
		if(ov != null) {
			formacion.setItems(null);
			experienciaSeleccionada.unbind();
			eliminar.disableProperty().unbind();
		}
		
		if(nv != null) {
			formacion.setItems(nv);
			experienciaSeleccionada.bind(formacion.getSelectionModel().selectedItemProperty());
			eliminar.disableProperty().bind(Bindings.isEmpty(formacion.getItems()));
		}
	}
	
	@FXML
	private void OnAñadir(ActionEvent event) {
		Dialog<Experiencia> dialog = new Dialog<>();
		dialog.setTitle("Nueva experiencia");
		
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		
		ButtonType crear = new ButtonType("Crear", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(crear, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 5, 5, 5));
		
		TextField denominacion = new TextField();
		TextField empleador = new TextField();
		DatePicker desde = new DatePicker();
		DatePicker hasta = new DatePicker();
		
		Node nodeBtAnadir = dialog.getDialogPane().lookupButton(crear);
		nodeBtAnadir.setDisable(true);
		
		nodeBtAnadir.disableProperty().bind(
				denominacion.textProperty().isEmpty().or(
						empleador.textProperty().isEmpty()).or(
				desde.valueProperty().isNull()).or(
				hasta.valueProperty().isNull()));
		
		grid.add(new Label("Denominacion"), 0, 0);
		grid.add(denominacion, 1, 0);
		grid.add(new Label("Empleador"), 0, 1);
		grid.add(empleador, 1, 1);
		grid.add(new Label("Desde"), 0, 2);
		grid.add(desde, 1, 2);
		grid.add(new Label("Hasta"), 0, 3);
		grid.add(hasta, 1, 3);
		
		GridPane.setColumnSpan(denominacion, 2);
		GridPane.setColumnSpan(empleador, 2);
		
		ColumnConstraints[] cols = {
				new ColumnConstraints(),
				new ColumnConstraints(),
				new ColumnConstraints()
		};
		
		cols[0].setHalignment(HPos.RIGHT);
		cols[1].setHgrow(Priority.ALWAYS);
		cols[1].setFillWidth(true);
		
		grid.getColumnConstraints().setAll(cols);
		
		dialog.getDialogPane().setContent(grid);
		
		Platform.runLater(() -> denominacion.requestFocus());
		
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == crear) {
				Experiencia resultado = new Experiencia();
				resultado.setDenominacion(denominacion.getText());
				resultado.setEmpleador(empleador.getText());
				resultado.setDesde(desde.getValue());
				resultado.setHasta(hasta.getValue());
				return resultado;
			}
			return null;
		});
		
		Optional<Experiencia> result = dialog.showAndWait();
		
		if (result.isPresent())
			experiencia.get().add(result.get());
	}

	
	@FXML
	void onEliminar(ActionEvent event) {
		String title = "Eliminar experiencia";
		String content = "¿Está seguro que quiere borrar la Experiencia?";
		Experiencia formacion = experienciaSeleccionada.get();
		
		if (formacion != null && App.confirmacion(title, content))
			experiencia.get().remove(formacion);
	}

	public BorderPane getView() {
		return view;
	}

	public void setView(BorderPane view) {
		this.view = view;
	}

	public final ListProperty<Experiencia> experienciaProperty() {
		return this.experiencia;
	}
	

	public final ObservableList<Experiencia> getExperiencia() {
		return this.experienciaProperty().get();
	}
	

	public final void setExperiencia(final ObservableList<Experiencia> experiencia) {
		this.experienciaProperty().set(experiencia);
	}
	
	
	

}
