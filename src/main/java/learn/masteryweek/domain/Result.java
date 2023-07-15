package learn.masteryweek.domain;
import java.util.ArrayList;

public class Result<T> {
    private T payload;
    private ArrayList<String> messages;

    public Result() {
        messages = new ArrayList<>();
    }

    public boolean isSuccess() {
        return messages.isEmpty();
    }

    public ArrayList<String> getErrorMessages() {
        return messages;
    }

    public void addErrorMessage(String errorMessage) {
        messages.add(errorMessage);
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}