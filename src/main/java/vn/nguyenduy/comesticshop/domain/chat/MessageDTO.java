package vn.nguyenduy.comesticshop.domain.chat;

public class MessageDTO {
    private String message;
    private int fromLogin;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message; // Corrected assignment
    }

    public int getFromLogin() {
        return fromLogin;
    }

    public void setFromLogin(int fromLogin) {
        this.fromLogin = fromLogin; // Corrected assignment
    }

    @Override
    public String toString() {
        return "MessageDTO{" + "message='" + message + '\'' + ", fromLogin=" + fromLogin + '}';
    }
}
