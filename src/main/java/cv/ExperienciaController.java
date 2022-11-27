package cv;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

public class ExperienciaController {

    @FXML
    private GridPane view;

    private TextField denominacion;
    private TextField empleador;
    private DatePicker desde;
    private DatePicker hasta;

    @FXML
    private TableColumn denominacionExperienciaColumn;

    @FXML
    private TableColumn desdeExperienciaColumn;

    @FXML
    private TableColumn empleadorExperienciaColumn;

    @FXML
    private TableColumn hastaExperienciaColumn;

    @FXML
    private TableView<Experiencia> tableExperiencia;
    ObservableList<Experiencia> experienciaData = FXCollections.observableArrayList();

    @SuppressWarnings("unchecked")
	public ExperienciaController() throws IOException {
        URL fxml = getClass().getResource("/fxml/ExperienciaView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxml);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        tableExperiencia.setEditable(true);

        denominacionExperienciaColumn.setCellValueFactory(new PropertyValueFactory<>("denominacion"));
        denominacionExperienciaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        denominacionExperienciaColumn.setOnEditCommit(new EventHandler<CellEditEvent<Experiencia, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Experiencia, String> t) {
                ((Experiencia) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setDenominacion(t.getNewValue());
            }
        });

        desdeExperienciaColumn.setCellValueFactory(new PropertyValueFactory<>("desde"));
        desdeExperienciaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        desdeExperienciaColumn.setOnEditCommit(new EventHandler<CellEditEvent<Experiencia, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Experiencia, String> t) {
                String fecha = t.getNewValue().toString();
                ((Experiencia) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setDesde(fecha);
            }
        });

        hastaExperienciaColumn.setCellValueFactory(new PropertyValueFactory<>("hasta"));
        // Modificar hastaExperienciaColumn para que se edite con datepicker
        hastaExperienciaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        hastaExperienciaColumn.setOnEditCommit(new EventHandler<CellEditEvent<Experiencia, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Experiencia, String> t) {
                String fecha = t.getNewValue().toString();
                ((Experiencia) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setHasta(fecha);
            }
        });

        empleadorExperienciaColumn.setCellValueFactory(new PropertyValueFactory<>("empleador"));
        empleadorExperienciaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        empleadorExperienciaColumn.setOnEditCommit(new EventHandler<CellEditEvent<Experiencia, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Experiencia, String> t) {
                ((Experiencia) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setEmpleador(t.getNewValue());
            }
        });

        tableExperiencia.setItems(experienciaData);

    }

    @FXML
    void addExperiencia(ActionEvent event) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Nueva experiencia");
        dialog.setHeaderText("Introduce los datos de la nueva experiencia");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons()
                .add(new Image(this.getClass().getResource("/images/main.png").toString()));

        ButtonType confirmButoonType = new ButtonType("Añadir", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButoonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        denominacion = new TextField();
        denominacion.setPromptText("Denominación");
        empleador = new TextField();
        empleador.setPromptText("Empleador");
        desde = new DatePicker();
        desde.setPromptText("Desde");
        hasta = new DatePicker();
        hasta.setPromptText("Hasta");

        grid.add(new Label("Denominación:"), 0, 0);
        grid.add(denominacion, 1, 0);
        grid.add(new Label("Empleador:"), 0, 1);
        grid.add(empleador, 1, 1);
        grid.add(new Label("Desde:"), 0, 2);
        grid.add(desde, 1, 2);
        grid.add(new Label("Hasta:"), 0, 3);
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

        empleador.textProperty().addListener((observable, oldValue, newValue) -> {
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

                experienciaData
                        .add(new Experiencia(denominacion.getText(), empleador.getText(), desdeString, hastaString));
            }
            return null;
        });

        dialog.showAndWait();

    }

    @FXML
    void deleteExperiencia(ActionEvent event) {
        Experiencia experiencia = tableExperiencia.getSelectionModel().getSelectedItem();
        if (experiencia != null) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Eliminar experiencia");
            dialog.setHeaderText("¿Estás seguro de que quieres eliminar esta experiencia?");
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons()
                    .add(new Image(this.getClass().getResource("/images/main.png").toString()));
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                experienciaData.remove(experiencia);
            }
        }
    }

    public boolean comprobarSiEstaVacio() {
        if (denominacion.getText().isEmpty() || empleador.getText().isEmpty() || desde.getValue() == null
                || hasta.getValue() == null || desde.getValue().isAfter(hasta.getValue())) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<ArrayList<String>> getExperienciaData() {
        ArrayList<ArrayList<String>> experienciaData = new ArrayList<>();
        for (Experiencia experiencia : tableExperiencia.getItems()) {
            ArrayList<String> experienciaArray = new ArrayList<>();
            experienciaArray.add(experiencia.getDenominacion());
            experienciaArray.add(experiencia.getEmpleador());
            experienciaArray.add(experiencia.getDesde());
            experienciaArray.add(experiencia.getHasta());
            experienciaData.add(experienciaArray);
        }
        return experienciaData;
    }

    public class Experiencia {
        private String denominacion;
        private String empleador;
        private String desde;
        private String hasta;

        public Experiencia(String denominacion, String empleador, String desde, String hasta) {
            this.denominacion = denominacion;
            this.empleador = empleador;
            this.desde = desde;
            this.hasta = hasta;
        }

        public String getDenominacion() {
            return denominacion;
        }

        public void setDenominacion(String denominacion) {
            this.denominacion = denominacion;
        }

        public String getEmpleador() {
            return empleador;
        }

        public void setEmpleador(String empleador) {
            this.empleador = empleador;
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

    public void setExperiencia(ArrayList<ArrayList<String>> experienciaList) {
        experienciaData.clear();
        for (ArrayList<String> experiencia : experienciaList) {
            this.experienciaData.add(new Experiencia(experiencia.get(0), experiencia.get(1), experiencia.get(2),
                    experiencia.get(3)));
        }
    }

    public void resetear() {
        tableExperiencia.getItems().clear();
    }

    public GridPane getView() {
        return view;
    }
}
