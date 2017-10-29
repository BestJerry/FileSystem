package Model;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class FATForShow implements Serializable {

    private final SimpleStringProperty disk = new SimpleStringProperty();

    private final SimpleStringProperty value = new SimpleStringProperty();

    public String getDisk() {
        return disk.get();
    }

    public SimpleStringProperty diskProperty() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk.set(disk);
    }

    public String getValue() {
        return value.get();
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }
}
