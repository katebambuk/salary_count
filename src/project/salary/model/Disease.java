package project.salary.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.sql.Date;

/**
 * Created by Ekaterina Kiseleva on 28.03.2017.
 */
public class Disease {
    private IntegerProperty idWorker = new SimpleIntegerProperty();
    private IntegerProperty idMonth = new SimpleIntegerProperty(); //MMyy
    private IntegerProperty idDisease = new SimpleIntegerProperty(); //какое это заболевание в месяц по счету
    private ObjectProperty<Date> dateBegin = new SimpleObjectProperty<Date>();
    private ObjectProperty<Date> dateEnd = new SimpleObjectProperty<Date>();

    public int getIdWorker() {
        return idWorker.get();
    }

    public IntegerProperty idWorkerProperty() {
        return idWorker;
    }

    public void setIdWorker(int idWorker) {
        this.idWorker.set(idWorker);
    }

    public int getIdMonth() {
        return idMonth.get();
    }

    public IntegerProperty idMonthProperty() {
        return idMonth;
    }

    public void setIdMonth(int idMonth) {
        this.idMonth.set(idMonth);
    }

    public int getIdDisease() {
        return idDisease.get();
    }

    public IntegerProperty idDiseaseProperty() {
        return idDisease;
    }

    public void setIdDisease(int idDisease) {
        this.idDisease.set(idDisease);
    }

    public Date getDateBegin() {
        return dateBegin.get();
    }

    public ObjectProperty<Date> dateBeginProperty() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin.set(dateBegin);
    }

    public Date getDateEnd() {
        return dateEnd.get();
    }

    public ObjectProperty<Date> dateEndProperty() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd.set(dateEnd);
    }
}
