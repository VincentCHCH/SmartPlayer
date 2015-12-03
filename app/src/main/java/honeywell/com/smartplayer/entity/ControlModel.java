package honeywell.com.smartplayer.entity;

/**
 * Created by Vincent on 12/11/15.
 */
public class ControlModel {
    private String hon;
    private String sender;
    private String type;
    private int length;
    private String data;
    private String step;

    public final String HON = "hon";
    public final String SENDER = "mob";
    public final String TYPE = "ctr";

    public final String HEAD = HON + SENDER + TYPE;

    public ControlModel(String Data){
        this.data = data;
    }

    public String getStep() {
        return step;
    }
    public void setStep(String step) {
        this.step = step;
    }
    public String getHon() {
        return hon;
    }

    public void setHon(String hon) {
        this.hon = hon;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ControlModel(String hon, String sender, String type, int length, String data) {
        this.hon = hon;
        this.sender = sender;
        this.type = type;
        this.length = length;
        this.data = data;
    }

    public ControlModel(){}
}
