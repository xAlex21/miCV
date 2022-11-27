package cv;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class FormacionController {

    @FXML
    private GridPane view;

    private TextField denominacion;
    private TextField organizador;
    private DatePicker desde;
    private DatePicker hasta;

    @FXML
    private TableColumn columnDenominacion;

    @FXML
    private TableColumn columnDesde;

    @FXML
    private TableColumn columnHasta;

    @FXML
    private TableColumn columnOrganizador;

    @FXML
    private TableView<Formacion> tableFormacion;
    ObservableList<Formacion> formacionData = FXCollections.observableArrayList();

    public FormacionController() throws IOException {
        URL fxml = getClass().getResource("/fxml/FormacionView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxml);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        columnDenominacion.setCellValueFactory(new PropertyValueFactory<Formacion, String>("denominacion"));
        columnDesde.setCellValueFactory(new PropertyValueFactory<Formacion, String>("desde"));
        columnHasta.setCellValueFactory(new PropertyValueFactory<Formacion, String>("hasta"));
        columnOrganizador.setCellValueFactory(new PropertyValueFactory<Formacion, String>("organizador"));
        tableFormacion.setItems(formacionData);
    }

    @FXML
    void addFormacion(ActionEvent event) {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Nuevo titulo");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons()
                .add(new Image(this.getClass().getResource("/images/main.png").toString()));

        ButtonType confirmButoonType = new ButtonType("Crear", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButoonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        denominacion = new TextField();
        organizador = new TextField();
        desde = new DatePicker();
        hasta = new DatePicker();

        grid.add(new Label("Denominación"), 0, 0);
        grid.add(denominacion, 1, 0);
        grid.add(new Label("Organizador"), 0, 1);
        grid.add(organizador, 1, 1);
        grid.add(new Label("Desde"), 0, 2);
        grid.add(desde, 1, 2);
        grid.add(new Label("Hasta"), 0, 3);
        grid.add(hasta, 1, 3);

        Node telefonoButton = dialog.getDialogPane().lookupButton(confirmButoonType);
        telefonoButton.setDisable(true);

        denominacion.textProperty().addListener((observable, oldValue, newValue) -> {
            if (comprobarSiEstaVacio()) {
                telefonoButton.setDisable(false);
            } else {
                telefonoButton.setDisable(true);
            }
        });

        organizador.textProperty().addListener((observable, oldValue, newValue) -> {
            if (comprobarSiEstaVacio()) {
                telefonoButton.setDisable(false);
            } else {
                telefonoButton.setDisable(true);
            }
        });

        desde.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (comprobarSiEstaVacio()) {
                telefonoButton.setDisable(false);
            } else {
                telefonoButton.setDisable(true);
            }
        });

        hasta.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (comprobarSiEstaVacio()) {
                telefonoButton.setDisable(false);
            } else {
                telefonoButton.setDisable(true);
            }
        });

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButoonType) {

                String desdeString = desde.getValue().toString();
                String hastaString = hasta.getValue().toString();

                formacionData
                        .add(new Formacion(denominacion.getText(), organizador.getText(), desdeString, hastaString));

            }
            return null;
        });

        dialog.showAndWait();
    }

    @FXML
    void deleteFormacion(ActionEvent event) {

        if (tableFormacion.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Eliminar Formacion");
        alert.setHeaderText("Eliminar la formacion seleccionada");
        alert.setContentText("¿Estás seguro?");

        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
                .add(new Image(this.getClass().getResource("/images/main.png").toString()));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            formacionData.remove(tableFormacion.getSelectionModel().getSelectedItem());
        }
    }

    public boolean comprobarSiEstaVacio() {
        if (denominacion.getText().isEmpty() || organizador.getText().isEmpty() || desde.getValue() == null
                || hasta.getValue() == null || desde.getValue().isAfter(hasta.getValue())) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<ArrayList<String>> getFormacion() {

        ArrayList<ArrayList<String>> formacion = new ArrayList<ArrayList<String>>();
        for (Formacion f : formacionData) {
            ArrayList<String> formacionAux = new ArrayList<String>();
            formacionAux.add(f.getDenominacion());
            formacionAux.add(f.getOrganizador());
            formacionAux.add(f.getDesde());
            formacionAux.add(f.getHasta());
            formacion.add(formacionAux);
        }
        return formacion;

    }

    public class Formacion {

        private String denominacion;
        private String organizador;
        private String desde;
        private String hasta;

        public Formacion(String denominacion, String organizador, String desde, String hasta) {
            this.denominacion = denominacion;
            this.organizador = organizador;
            this.desde = desde;
            this.hasta = hasta;
        }

        public String getDenominacion() {
            return denominacion;
        }

        public void setDenominacion(String denominacion) {
            this.denominacion = denominacion;
        }

        public String getOrganizador() {
            return organizador;
        }

        public void setOrganizador(String organizador) {
            this.organizador = organizador;
        }

        public String getDesde() {
            return desde;
        }

        public void setDesde(String desde) {
            this.desde = desde;
        }

        public String getHasta() {
            return hasta;
        }

        public void setHasta(String hasta) {
            this.hasta = hasta;
        }

    }

    public void setFormacion(ArrayList<ArrayList<String>> formacion) {
        formacionData.clear();
        for (ArrayList<String> f : formacion) {
            formacionData.add(new Formacion(f.get(0), f.get(1), f.get(2), f.get(3)));
        }

    }

    public void resetear() {
        formacionData.clear();
    }

    public GridPane getView() {
        return view;
    }

}