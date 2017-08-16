package android.com.technicianclient.technician.beans;

/**
 * Created by Admin on 5/29/2017.
 */

public class PaymentHistory {
    private String id;
    private String amountRecieved;
    private String model;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmountRecieved() {
        return amountRecieved;
    }

    public void setAmountRecieved(String amountRecieved) {
        this.amountRecieved = amountRecieved;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
