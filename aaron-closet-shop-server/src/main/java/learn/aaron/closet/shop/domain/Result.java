package learn.aaron.closet.shop.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Result<T> {
    private T payload;
    private ArrayList<String> messages = new ArrayList<>();
    private ResultType type = ResultType.SUCCESS;
    public ResultType getType() {
        return type;
    }


    public void addMessage(String message) {
        messages.add(message);
    }


    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public boolean isSuccess() {
        return messages.isEmpty();
    }

    public void addMessage(String message, ResultType type) {
        messages.add(message);
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result<?> result = (Result<?>) o;
        return Objects.equals(payload, result.payload) && Objects.equals(messages, result.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payload, messages);
    }

    @Override
    public String toString() {
        return "Result{" +
                "payload=" + payload +
                ", messages=" + messages +
                '}';
    }
}
