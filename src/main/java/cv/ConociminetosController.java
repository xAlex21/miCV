package cv;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ConociminetosController {

    @FXML
    private GridPane viewConocimientos;

    @FXML
    private TableColumn denominacionConocimientosColumn;

    @FXML
    private TableColumn nivelConocimientosColumn;

    @FXML
    private TableView<Conocimientos> conocimientosTable;
    ObservableList<Conocimientos> conocimientosData = FXCollections.observableArrayList();

    public static class Conocimientos {
        private String denomicacion;
        private String nivel;

        public Conocimientos(String denomicacion, String nivel) {
            super();
            this.denomicacion = denomicacion;
            this.nivel = nivel;
        }

        public String getDenomicacion() {
            return denomicacion;
        }

        public void setDenomicacion(String denomicacion) {
            this.denomicacion = denomicacion;
        }

        public String getNivel() {
            return nivel;
        }

        public void setNivel(String nivel) {
            this.nivel = nivel;
        }

    }

    public ConociminetosController() throws IOException {
        URL fxml = getClass().getResource("/fxml/ConocimientosView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxml);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        denominacionConocimientosColumn
                .setCellValueFactory(new PropertyValueFactory<Conocimientos, String>("denomicacion"));

        nivelConocimientosColumn.setCellValueFactory(new PropertyValueFactory<Conocimientos, String>("nivel"));

        conocimientosTable.setItems(conocimientosData);
    }

    public GridPane getView() {
        return viewConocimientos;
    }

    @FXML
    void addConocimiento(ActionEvent event) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Nuevo Conocimiento");
        dialog.setHeaderText("Introduce los datos del nuevo conocimiento");

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/images/main.png").toString()));

        ButtonType loginButtonType = new ButtonType("Añadir", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPrefWidth(400);

        TextField denominacion = new TextField();
        denominacion.setPromptText("Denominación");
        TextField nivel = new TextField();
        nivel.setPromptText("Nivel");

        ComboBox<String> comboBox = new ComboBox<String>();
        comboBox.getItems().addAll("Basico", "Medio", "Avanzado");

        Button resset = new Button("X");

        resset.setOnAction(e -> {
            nivel.setText("");
            comboBox.setValue("");
        });

        grid.add(new Label("Denominación:"), 0, 0);
        grid.add(denominacion, 1, 0);
        grid.add(new Label("Nivel:"), 0, 1);
        grid.add(comboBox, 1, 1);
        grid.add(resset, 2, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        denominacion.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> denominacion.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(denominacion.getText(), nivel.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(denominacionNivel -> {
            conocimientosData.add(new Conocimientos(denominacionNivel.getKey(), comboBox.getValue()));
        });
    }

    @FXML
    void deleteConocimiento(ActionEvent event) {

        if (conocimientosTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Eliminar conocimiento");
        alert.setHeaderText("¿Estás seguro de que quieres eliminar el conocimiento?");
        alert.setContentText("¿Estas seguro?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            conocimientosData.remove(conocimientosTable.getSelectionModel().getSelectedItem());
        }
    }

}
