package dad.proyect.micv.model.Personal;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class nacionalidad {
	private StringProperty denominacion = new SimpleStringProperty();
	
	public nacionalidad() {}
	
	public nacionalidad(String denominacion) {
		setDenominacion(denominacion);
	}

	public final StringProperty denominacionProperty() {
		return this.denominacion;
	}

	public final String getDenominacion() {
		return this.denominacionProperty().get();
	}

	public final void setDenominacion(final String denominacion) {
		this.denominacionProperty().set(denominacion);
	}

}