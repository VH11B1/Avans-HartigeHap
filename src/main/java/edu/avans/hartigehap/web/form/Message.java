package edu.avans.hartigehap.web.form;

public class Message {

    private String type;

    private String mess;

    public Message () {
    }

    public Message (String type, String message) {
        this.type = type;
        this.mess = message;
    }

    public String getType () {
        return type;
    }

    public void setType (String type) {
        this.type = type;
    }

    public String getMessage () {
        return mess;
    }

    public void setMessage (String message) {
        this.mess = message;
    }

}
