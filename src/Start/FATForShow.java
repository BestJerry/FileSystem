package Start;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class FATForShow implements Serializable {

    /*private final SimpleIntegerProperty disk = new SimpleIntegerProperty();

    private final SimpleIntegerProperty value = new SimpleIntegerProperty();


    public int getDisk() {
        return disk.get();
    }

    public SimpleIntegerProperty diskProperty() {
        return disk;
    }

    public void setDisk(int disk) {
        this.disk.set(disk);
    }

    public int getValue() {
        return value.get();
    }

    public SimpleIntegerProperty valueProperty() {
        return value;
    }

    public void setValue(int value) {
        this.value.set(value);
    }
*/
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
