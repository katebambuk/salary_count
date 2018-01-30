package project.salary.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Ekaterina Kiseleva on 01.04.2017.
 */
public class Bonus {
    private IntegerProperty idWorker = new SimpleIntegerProperty();
    private StringProperty idMonth = new SimpleStringProperty();
    private IntegerProperty bonus = new SimpleIntegerProperty();

    public int getIdWorker() {
        return idWorker.get();
    }

    public IntegerProperty idWorkerProperty() {
        return idWorker;
    }

    public void setIdWorker(int idWorker) {
        this.idWorker.set(idWorker);
    }

    public String getIdMonth() {
        return idMonth.get();
    }

    public StringProperty idMonthProperty() {
        return idMonth;
    }

    public void setIdMonth(String idMonth) {
        this.idMonth.set(idMonth);
    }

    public int getBonus() {
        return bonus.get();
    }

    public IntegerProperty bonusProperty() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus.set(bonus);
    }
}
