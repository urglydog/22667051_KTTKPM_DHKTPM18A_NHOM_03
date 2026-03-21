package iuh.fit.design_pattern.state;

public interface OrderState {
    void handle(Order order);
    String getStateName();
}
