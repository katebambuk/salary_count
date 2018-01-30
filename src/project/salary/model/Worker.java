package project.salary.model;

import javafx.beans.property.*;

/**
 * Created by Ekaterina Kiseleva on 25.03.2017.
 */
public class Worker {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty position = new SimpleStringProperty();
    private final IntegerProperty stableSalary = new SimpleIntegerProperty();
    private final IntegerProperty mountOfChildren = new SimpleIntegerProperty();
    private final StringProperty familyStatus = new SimpleStringProperty();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPosition() {
        return position.get();
    }

    public StringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public int getStableSalary() {
        return stableSalary.get();
    }

    public IntegerProperty stableSalaryProperty() {
        return stableSalary;
    }

    public void setStableSalary(int stableSalary) {
        this.stableSalary.set(stableSalary);
    }

    public int getMountOfChildren() {
        return mountOfChildren.get();
    }

    public IntegerProperty mountOfChildrenProperty() {
        return mountOfChildren;
    }

    public void setMountOfChildren(int mountOfChildren) {
        this.mountOfChildren.set(mountOfChildren);
    }

    public String getFamilyStatus() {
        return familyStatus.get();
    }

    public StringProperty familyStatusProperty() {
        return familyStatus;
    }

    public void setFamilyStatus(String familyStatus) {
        this.familyStatus.set(familyStatus);
    }

}
