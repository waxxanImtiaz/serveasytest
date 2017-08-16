package android.com.technicianclient.technician.beans;

/**
 * Created by Admin on 5/29/2017.
 */

public class Feedback {
    private String  feedback;
    private String recomended;
    private String date;
    private String id;
    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRecomended() {
        return recomended;
    }

    public void setRecomended(String recomended) {
        this.recomended = recomended;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
