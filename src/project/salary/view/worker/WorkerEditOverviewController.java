package project.salary.view.worker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.salary.model.Worker;
import project.salary.model.Connect;

/**
 * Created by Ekaterina Kiseleva on 27.03.2017.
 */
public class WorkerEditOverviewController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField positionField;
    @FXML
    private TextField salaryField;
    @FXML
    private ComboBox familyStatusBox;
    @FXML
    private ComboBox kidsBox;

    ObservableList<String> familyStatusList = FXCollections.observableArrayList("В браке", "Не в браке");
    ObservableList<String> kidsList = FXCollections.observableArrayList("Нет детей", "1", "2", "3", ">3");

    private Stage dialogStage;
    private Worker worker;
    private boolean okClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public boolean isOkClicked() {
        return okClicked;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;

        nameField.setText(worker.getName());
        positionField.setText(worker.getPosition());
        salaryField.setText(Integer.toString(worker.getStableSalary()));
        //для combobox
        try {
            if (worker.getFamilyStatus().equals("В браке")) familyStatusBox.setValue("В браке");
            else familyStatusBox.setValue("Не в браке");

            if (worker.getMountOfChildren() == 0) kidsBox.setValue("Нет детей");
            else if (worker.getMountOfChildren() == 1) kidsBox.setValue("1");
            else if (worker.getMountOfChildren() == 2) kidsBox.setValue("2");
            else if (worker.getMountOfChildren() == 3) kidsBox.setValue("3");
            else if (worker.getMountOfChildren() > 3) kidsBox.setValue(">3");
        }
        catch (Exception e){
            kidsBox.setValue("Нет детей");
            familyStatusBox.setValue("Не в браке");
        }
    }

    @FXML
    private void initialize() {
        familyStatusBox.setValue("Не в браке");
        familyStatusBox.setItems(familyStatusList);

        kidsBox.setValue("Нет детей");
        kidsBox.setItems(kidsList);
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            int key = worker.getId();

            worker.setName(nameField.getText());
            worker.setPosition(positionField.getText());
            worker.setStableSalary(Integer.parseInt(salaryField.getText()));

            worker.setFamilyStatus(familyStatusBox.getValue().toString());
            try {
                worker.setMountOfChildren(Integer.parseInt(String.valueOf(kidsBox.getValue())));
            }
            catch (NumberFormatException e) {
                if (kidsBox.getValue().equals("Нет детей")) worker.setMountOfChildren(0);
                if (kidsBox.getValue().equals(">3")) worker.setMountOfChildren(4);
            }

            Connect.editWorker(key, worker);
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Недопустимое имя!\n";
        }
        if (positionField.getText() == null || positionField.getText().length() == 0) {
            errorMessage += "Недопустимая должность!\n";
        }

        if (salaryField.getText() == null || salaryField.getText().length() == 0) {
            errorMessage += "Недопустимое значение оклада!\n";
        } else {
            try {
                Integer.parseInt(salaryField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Недопустмое значение оклада (должно быть числом)!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Неверный ввод");
            alert.setHeaderText("Пожалуйста, заполните поля корректно");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
