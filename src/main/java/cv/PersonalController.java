package cv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import com.google.gson.JsonElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PersonalController {

	@FXML
	private GridPane viewPersonal;

	@FXML
	private TextField inputApellidos;

	@FXML
	private TextField inputCodigoPostal;

	@FXML
	private TextField inputDNI;

	@FXML
	private TextField inputDireccion;

	@FXML
	private DatePicker inputFecha;

	@FXML
	private TextField inputLocalidad;

	@FXML
	private TextField inputNombre;

	@FXML
	private ComboBox<String> inputPais;

	@FXML
	private ListView<String> listNacionalidad;

	public ObservableList<String> listPaises = FXCollections.observableArrayList();

	public ObservableList<String> listNacionalidades = FXCollections.observableArrayList();

	public PersonalController() throws IOException {
		URL fxml = getClass().getResource("/fxml/PersonalView.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(fxml);
		fxmlLoader.setController(this);
		fxmlLoader.load();

		cargarPaises();

	}

	// Cargar Paises
	public void cargarPaises() {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader("src/main/resources/csv/paises.csv"));
			String line = br.readLine();
			while (null != line) {
				listPaises.add(line);
				line = br.readLine();
			}

		} catch (Exception e) {
			System.out.println("Error ->" + e);
		}

		try {
			br = new BufferedReader(new FileReader("src/main/resources/csv/nacionalidades.csv"));
			String line = br.readLine();
			while (null != line) {
				listNacionalidades.add(line);
				line = br.readLine();
			}

		} catch (Exception e) {
			System.out.println("Error ->" + e);
		}
	}

	@FXML
	void addNacionalidad(ActionEvent event) {

		ChoiceDialog<String> dialog = new ChoiceDialog<String>(null, listNacionalidades);
		dialog.setTitle("Nueva nacionalidad");
		dialog.setHeaderText("Añadir nacionalidad");
		dialog.setContentText("Selecciona una nacionalidad:");

		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons()
				.add(new Image(this.getClass().getResource("/images/main.png").toString()));


		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			listNacionalidad.getItems().add(result.get());
			HashSet<String> hashSet = new HashSet<String>(listNacionalidad.getItems());
			listNacionalidad.getItems().clear();
			listNacionalidad.getItems().addAll(hashSet);
		}

	}

	@FXML
	void deleteNacionalidad(ActionEvent event) {
		// Comprobar si hay algo seleccionado y no hay elementos duplicados
		if (listNacionalidad.getSelectionModel().getSelectedItem() != null) {
			listNacionalidad.getItems().remove(listNacionalidad.getSelectionModel().getSelectedItem());
		}
	}

	// Function get Nacionalidades in arraylist
	public ArrayList<String> getNacionalidades() {
		ArrayList<String> nacionalidades = new ArrayList<String>();
		for (String nacionalidad : listNacionalidad.getItems()) {
			nacionalidades.add(nacionalidad);
		}
		return nacionalidades;
	}

	public HashMap<String, String> getDatosMap() {

		HashMap<String, String> data = new HashMap<String, String>();

		data.put("nombre", inputNombre.getText());
		data.put("apellidos", inputApellidos.getText());
		data.put("dni", inputDNI.getText());
		data.put("direccion", inputDireccion.getText());
		data.put("localidad", inputLocalidad.getText());
		data.put("codigoPostal", inputCodigoPostal.getText());

		try {
			data.put("fecha", inputFecha.getValue().toString());
		} catch (Exception e) {
			data.put("fecha", "");
		}
		if (inputPais.getValue() != null) {
			data.put("pais", inputPais.getValue());
		} else {
			data.put("pais", "");
		}

		return data;
	}

	public void resetear() {
		inputNombre.setText("");
		inputApellidos.setText("");
		inputDNI.setText("");
		inputDireccion.setText("");
		inputLocalidad.setText("");
		inputCodigoPostal.setText("");
		inputFecha.setValue(null);
		inputPais.setValue(null);
		inputPais.setPromptText("Selecciona un pais");
		listNacionalidad.getItems().clear();
	}

	public GridPane getView() {
		return viewPersonal;
	}

	public void setNombre(String nombre) {
		inputNombre.setText(nombre);
	}

	public void setApellidos(String apellidos) {
		inputApellidos.setText(apellidos);
	}

	public void setDni(String dni) {
		inputDNI.setText(dni);
	}

	public void setDireccion(String direccion) {
		inputDireccion.setText(direccion);
	}

	public void setLocalidad(String localidad) {
		inputLocalidad.setText(localidad);
	}

	public void setCodigoPostal(String codigoPostal) {
		inputCodigoPostal.setText(codigoPostal);
	}

	public void setFecha(String fecha) {
		inputFecha.setValue(LocalDate.parse(fecha));
	}

	public void setPais(String pais) {
		inputPais.setValue(pais);
	}

	public void setNacionalidades(ArrayList<String> nacionalidades) {
		listNacionalidad.getItems().clear();

		listNacionalidad.getItems().addAll(nacionalidades);
	}

	@FXML
	public void initialize() {
		inputPais.setItems(listPaises);
	}

}
