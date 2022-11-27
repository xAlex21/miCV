package cv;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import cv.ExperienciaController.Experiencia;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;

public class ContactoController {

    @FXML
    private GridPane viewContacto;

    @FXML
    private TableView<Email> emailTable;

    ObservableList<Email> emailData = FXCollections.observableArrayList();

    @FXML
    private TableColumn emailColumn;

    @FXML
    private TableColumn urlColumn;

    @FXML
    private TableView<Url> urlTable;

    ObservableList<Url> urlData = FXCollections.observableArrayList();

    @FXML
    private TableColumn numeroColumn;

    @FXML
    private TableView<Telefono> telefonoTable;

    @FXML
    private TableColumn tipoColumn;

    ObservableList<Telefono> telefonoData = FXCollections.observableArrayList();

    public static class Email {
        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Email(String email) {
            super();
            this.email = email;
        }
    }

    public static class Url {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Url(String url) {
            super();
            this.url = url;
        }
    }

    public static class Telefono {
        private String telefono;
        private String tipo;

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public Telefono(String telefono, String tipo) {
            super();
            this.telefono = telefono;
            this.tipo = tipo;
        }

    }

    public ContactoController() throws IOException {
        URL fxml = getClass().getResource("/fxml/ContactoView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxml);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        emailColumn.setCellValueFactory(new PropertyValueFactory<Email, String>("email"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit((EventHandler) new EventHandler<CellEditEvent<Email, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Email, String> t) {
                ((Email) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setEmail(t.getNewValue());
            }
        });
        emailTable.setItems(emailData);

        urlColumn.setCellValueFactory(new PropertyValueFactory<Url, String>("url"));
        urlColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        urlColumn.setOnEditCommit((EventHandler) new EventHandler<CellEditEvent<Url, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Url, String> t) {
                ((Url) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setUrl(t.getNewValue());
            }
        });
        urlTable.setItems(urlData);

        numeroColumn.setCellValueFactory(new PropertyValueFactory<Telefono, String>("telefono"));
        numeroColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        numeroColumn.setOnEditCommit((EventHandler) new EventHandler<CellEditEvent<Telefono, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Telefono, String> t) {
                ((Telefono) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setTelefono(t.getNewValue());
            }
        });

        tipoColumn.setCellValueFactory(new PropertyValueFactory<Telefono, String>("tipo"));
        tipoColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        tipoColumn.setOnEditCommit((EventHandler) new EventHandler<CellEditEvent<Telefono, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Telefono, String> t) {
                ((Telefono) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setTipo(t.getNewValue());
            }
        });
        telefonoTable.setItems(telefonoData);
    }

    @FXML
    void addEmail(ActionEvent event) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nuevo email");
        dialog.setHeaderText("Crear una nueva dirección de email");
        dialog.setContentText("Email:");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons()
                .add(new Image(this.getClass().getResource("/images/main.png").toString()));

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().isEmpty() && !result.get().isBlank()) {
            emailData.add(new Email(result.get()));
        }

    }

    @FXML
    void addUrl(ActionEvent event) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nueva URL");
        dialog.setHeaderText("Crear una nueva dirección web");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons()
                .add(new Image(this.getClass().getResource("/images/main.png").toString()));

        dialog.setContentText("URL:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().isEmpty() && !result.get().isBlank()) {
            urlData.add(new Url(result.get()));
        }

    }

    @FXML
    void deleteUrl(ActionEvent event) {
        if (urlTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Eliminar Url");
        alert.setHeaderText("Eliminar la url seleccionada");

        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
                .add(new Image(this.getClass().getResource("/images/main.png").toString()));

        alert.setContentText("¿Estás seguro?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Url url = urlTable.getSelectionModel().getSelectedItem();
            urlData.remove(url);
        }

    }

    @FXML
    void deleteEmail(ActionEvent event) {

        if (emailTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Eliminar email");
        alert.setHeaderText("Eliminar el email seleccionado");
        alert.setContentText("¿Estás seguro?");

        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
                .add(new Image(this.getClass().getResource("/images/main.png").toString()));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Email email = emailTable.getSelectionModel().getSelectedItem();
            emailData.remove(email);
        }

    }

    @FXML
    void addTelefono(ActionEvent event) {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("Fijo", "Movil");

        ComboBox<String> cbx = new ComboBox<>(items);
        cbx.getSelectionModel().selectFirst();
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Nuevo teléfono");
        dialog.setHeaderText(" Introduca el nuevo número de teléfono");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons()
                .add(new Image(this.getClass().getResource("/images/main.png").toString()));

        ButtonType confirmButoonType = new ButtonType("Añadir", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButoonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField telefono = new TextField();
        telefono.setPromptText("Número de teléfono");

        grid.add(new Label("Telefono:"), 0, 0);
        grid.add(telefono, 1, 0);
        grid.add(new Label("Tipo:"), 0, 1);
        grid.add(cbx, 1, 1);

        Node telefonoButton = dialog.getDialogPane().lookupButton(confirmButoonType);
        telefonoButton.setDisable(true);

        telefono.textProperty().addListener((observable, oldValue, newValue) -> {
            telefonoButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> telefono.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButoonType) {
                System.out.println("tipo " + cbx.getValue() + " telefono " + telefono.getText());
                telefonoData.add(new Telefono(telefono.getText(), cbx.getValue()));

            }
            return null;
        });

        dialog.showAndWait();

    }

    @FXML
    void deleteTelefono(ActionEvent event) {
        if (telefonoTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Eliminar teléfono");
        alert.setHeaderText("Eliminar el teléfono seleccionado");
        alert.setContentText("¿Estás seguro?");
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
                .add(new Image(this.getClass().getResource("/images/main.png").toString()));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Telefono telefono = telefonoTable.getSelectionModel().getSelectedItem();
            telefonoData.remove(telefono);
        }

    }

    public HashMap<String, ObservableList> getDatosMap() {
        HashMap<String, ObservableList> data = new HashMap<String, ObservableList>();

        data.put("email", emailData);
        data.put("url", urlData);
        data.put("telefono", telefonoData);

        return data;
    }

    public ArrayList<String> getEmail() {
        ArrayList<String> emails = new ArrayList<String>();
        for (Email email : emailData) {
            emails.add(email.getEmail());
        }
        return emails;
    }

    public ArrayList<String> getUrl() {
        ArrayList<String> urls = new ArrayList<String>();
        for (Url url : urlData) {
            urls.add(url.getUrl());
        }
        return urls;
    }

    public ArrayList<ArrayList<String>> getTelefono() {
        ArrayList<ArrayList<String>> telefonos = new ArrayList<ArrayList<String>>();
        for (Telefono telefono : telefonoData) {
            ArrayList<String> telefonoArray = new ArrayList<String>();
            telefonoArray.add(telefono.getTelefono());
            telefonoArray.add(telefono.getTipo());
            telefonos.add(telefonoArray);
        }
        return telefonos;
    }

    @FXML
    void prueba(ActionEvent event) {
        // Imprimir todos los datos
        System.out.println("Emails");
        for (Email email : emailData) {
            System.out.println(email.getEmail());
        }
        System.out.println("URLs");
        for (Url url : urlData) {
            System.out.println(url.getUrl());
        }
        System.out.println("Telefonos");
        for (Telefono telefono : telefonoData) {
            System.out.println(telefono.getTelefono() + " " + telefono.getTipo());
        }

    }

    public void setEmail(ArrayList<String> emails) {
        emailData.clear();
        for (String email : emails) {
            emailData.add(new Email(email));
        }
    }

    public void setUrl(ArrayList<String> urls) {
        urlData.clear();
        for (String url : urls) {
            urlData.add(new Url(url));
        }
    }

    public void setTelefono(ArrayList<ArrayList<String>> telefonos) {
        telefonoData.clear();
        for (ArrayList<String> telefono : telefonos) {
            telefonoData.add(new Telefono(telefono.get(0), telefono.get(1)));
        }
    }

    public void resetear() {
        emailData.clear();
        urlData.clear();
        telefonoData.clear();
    }

    public GridPane getView() {
        return viewContacto;
    }
}
