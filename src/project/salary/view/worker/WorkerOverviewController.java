package project.salary.view.worker;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import project.salary.Main;
import project.salary.model.Worker;
import project.salary.model.Connect;

/**
 * Created by Ekaterina Kiseleva on 27.03.2017.
 */
public class WorkerOverviewController {
    @FXML
    private TableView<Worker> workerTable;
    @FXML
    private TableColumn<Worker, String> nameColumn;

    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label positionLabel;
    @FXML
    private Label stableSalaryLabel;
    @FXML
    private Label familyStatusLabel;
    @FXML
    private Label mountOfChildrenLabel;

    private Main main;
    public void setMain(Main main) {
        this.main = main;
        // Добавление в таблицу данных из наблюдаемого списка
        workerTable.setItems(main.getWorkerData());
    }

    private void showWorkersDetails(Worker worker) {
        if (worker != null) {
            // Заполняем метки информацией из объекта worker
            idLabel.setText(Integer.toString(worker.getId()));
            nameLabel.setText(worker.getName());
            positionLabel.setText(worker.getPosition());
            stableSalaryLabel.setText(Integer.toString(worker.getStableSalary()));
            familyStatusLabel.setText(worker.getFamilyStatus());
            mountOfChildrenLabel.setText(Integer.toString(worker.getMountOfChildren()));

        } else {
            // Если Worker = null, то убираем весь текст.
            idLabel.setText("");
            nameLabel.setText("");
            positionLabel.setText("");
            stableSalaryLabel.setText("");
            familyStatusLabel.setText("");
            mountOfChildrenLabel.setText("");
        }
    }

    @FXML
    private void handleNewWorker() {
        Worker tempWorker = new Worker();
        boolean okClicked = main.showWorkerEditDialog(tempWorker);
        if (okClicked) {
            int id = Connect.addWorker(tempWorker);
            tempWorker.setId(id);
            main.getWorkerData().add(tempWorker);
        }
    }

    @FXML
    private void initialize() {
        // Инициализация таблицы c одним столбцом
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        // Очистка дополнительной информации об адресате.
        showWorkersDetails(null);

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об адресате.
        workerTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showWorkersDetails(newValue));
    }

    @FXML
    public void handleEditWorker() {
        Worker selectedWorker = workerTable.getSelectionModel().getSelectedItem();
        if (selectedWorker != null) {
            boolean okClicked = main.showWorkerEditDialog(selectedWorker);
            if (okClicked) {
                showWorkersDetails(selectedWorker);
            }

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteWorker() {
        int selectedIndex = workerTable.getSelectionModel().getSelectedIndex();
        try {
            //удаляем все премии и больничные, относящиеся к этому работнику
            Connect.deleteDisease(workerTable.getSelectionModel().getSelectedItem().getId());
            Connect.deleteBonus(workerTable.getSelectionModel().getSelectedItem().getId());
            Connect.deleteWorker(workerTable.getSelectionModel().getSelectedItem());
            workerTable.getItems().remove(selectedIndex);
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleDiseaseList() {
        Worker selectedWorker = workerTable.getSelectionModel().getSelectedItem();
        try {
            main.showDiseaseDialog(selectedWorker.getId());
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleBonusList() {
        Worker selectedWorker = workerTable.getSelectionModel().getSelectedItem();
        try {
            main.showBonusDialog(selectedWorker.getId());
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void countRealSalary() {
        main.showSalaryCountDialog();
    }
}


