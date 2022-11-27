package cv;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class MainController {

	@FXML
	private GridPane view;
	@FXML
	private Tab tabPersonal;
	@FXML
	private Tab tabContacto;
	@FXML
	private Tab tabFormacion;
	@FXML
	private Tab tabExperiencia;

	@FXML
	private Tab tabConocimientos;

	@FXML
	private MenuItem guardarCV;

	@FXML
	private MenuItem nuevoCV;

	@FXML
	private MenuItem openCV;
	private File archivo = null;

	PersonalController controllerPersonal = new PersonalController();
	ContactoController controllerContacto = new ContactoController();
	FormacionController controllerFormacion = new FormacionController();
	ExperienciaController controllerExperiencia = new ExperienciaController();
	ConociminetosController controllerConocimientos = new ConociminetosController();

	Model modelo = new Model();

	public MainController() throws IOException {
		URL fxml = getClass().getResource("/fxml/MainView.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(fxml);
		fxmlLoader.setController(this);
		fxmlLoader.load();

		tabPersonal.setContent(controllerPersonal.getView());
		tabContacto.setContent(controllerContacto.getView());
		tabFormacion.setContent(controllerFormacion.getView());
		tabExperiencia.setContent(controllerExperiencia.getView());
		tabConocimientos.setContent(controllerConocimientos.getView());
		ImageView iconGuardar = new ImageView(new Image(this.getClass().getResource("/images/guardar.gif").toString()));
		guardarCV.setGraphic(iconGuardar);

		ImageView iconNuevo = new ImageView(new Image(this.getClass().getResource("/images/nuevo.gif").toString()));
		nuevoCV.setGraphic(iconNuevo);

		ImageView iconAbrir = new ImageView(new Image(this.getClass().getResource("/images/abrir.gif").toString()));
		openCV.setGraphic(iconAbrir);

	}

	// setDataPersonal

	public void setDataPersonal() {
		System.out.println("setDataPersonal");
		HashMap<String, String> data = controllerPersonal.getDatosMap();
		modelo.setNombre(data.get("nombre"));
		modelo.setApellidos(data.get("apellidos"));
		modelo.setDni(data.get("dni"));
		modelo.setFecha(data.get("fecha"));
		modelo.setDireccion(data.get("direccion"));
		modelo.setCodigoPostal(data.get("codigoPostal"));
		modelo.setLocalidad(data.get("localidad"));
		modelo.setPais(data.get("pais"));

		modelo.setNacionalidad(controllerPersonal.getNacionalidades());
	}

	public void setDataContacto() {

		ArrayList<String> email = controllerContacto.getEmail();
		ArrayList<String> url = controllerContacto.getUrl();
		ArrayList<ArrayList<String>> telefonos = controllerContacto.getTelefono();

		modelo.setEmail(email);
		modelo.setWeb(url);
		modelo.setTelefono(telefonos);

	}

	public void setDataExperiencia() {

		ArrayList<ArrayList<String>> experiencia = controllerExperiencia.getExperienciaData();
		modelo.setExperiencia(experiencia);

	}

	public void setDataFormacion() {
		ArrayList<ArrayList<String>> formacion = controllerFormacion.getFormacion();
		modelo.setFormacion(formacion);
	}

	@FXML
	void guardarComo(ActionEvent event) {
		setDataPersonal();
		setDataContacto();
		setDataFormacion();
		setDataExperiencia();

		archivo = null;

		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		Gson gson = builder.create();
		String json = gson.toJson(modelo);

		System.out.println(json);

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Guardar como");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CV Files", "*.cv"));
		Window stage = view.getScene().getWindow();
		archivo = fileChooser.showSaveDialog(stage);
		if (archivo != null) {
			try {
				Files.write(archivo.toPath(), Arrays.asList(json), StandardCharsets.UTF_8, StandardOpenOption.CREATE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void guardar(ActionEvent event) {

		setDataPersonal();
		setDataContacto();
		setDataFormacion();
		setDataExperiencia();

		if (archivo == null) {
			guardarComo(event);
		} else {
			GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
			Gson gson = builder.create();
			String json = gson.toJson(modelo);

			System.out.println(json);

			try {
				Files.write(archivo.toPath(), Arrays.asList(json), StandardCharsets.UTF_8, StandardOpenOption.CREATE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@FXML
	void open(ActionEvent event) throws IOException {

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CV Files", "*.cv"));

		archivo = fileChooser.showOpenDialog(null);

		if (archivo != null) {

			final String json = new String(Files.readAllBytes(Paths.get(archivo.getAbsolutePath())));

			Gson g = new Gson();

			Model cargarModelo = g.fromJson(json, modelo.getClass());

			controllerPersonal.setNombre(cargarModelo.getNombre());
			controllerPersonal.setApellidos(cargarModelo.getApellidos());
			controllerPersonal.setDni(cargarModelo.getDni());
			controllerPersonal.setFecha(cargarModelo.getFecha());
			controllerPersonal.setDireccion(cargarModelo.getDireccion());
			controllerPersonal.setCodigoPostal(cargarModelo.getCodigoPostal());
			controllerPersonal.setLocalidad(cargarModelo.getLocalidad());
			controllerPersonal.setPais(cargarModelo.getPais());
			controllerPersonal.setNacionalidades(cargarModelo.getNacionalidad());

			controllerContacto.setEmail(cargarModelo.getEmail());
			controllerContacto.setUrl(cargarModelo.getWeb());
			controllerContacto.setTelefono(cargarModelo.getTelefono());

			controllerFormacion.setFormacion(cargarModelo.getFormacion());

			controllerExperiencia.setExperiencia(cargarModelo.getExperiencia());
		}

	}

	@FXML
	void salir(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void newCV(ActionEvent event) {
		archivo = null;
		controllerPersonal.resetear();
		controllerContacto.resetear();
		controllerFormacion.resetear();
		controllerExperiencia.resetear();

	}

	public GridPane getView() {
		return view;
	}

}
