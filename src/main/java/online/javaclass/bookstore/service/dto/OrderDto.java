package online.javaclass.bookstore.service.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class OrderDto {
    private Long id;
    @NotNull
    private UserDto user;
    @NotNull
    private OrderStatus orderStatus;
    @NotNull
    private PaymentMethod paymentMethod;
    @NotNull
    private PaymentStatus paymentStatus;
    @NotNull
    private DeliveryType deliveryType;
    @NotNull
    private BigDecimal cost;
    @NotEmpty
    private List<OrderItemDto> items;

    public enum OrderStatus {
        OPEN,
        CONFIRMED,
        COMPLETED,
        CANCELLED
    }

    public enum PaymentMethod {
        CASH,
        CARD,
        BANK_TRANSFER
    }

    public enum PaymentStatus {
        UNPAID,
        FAILED,
        PAID,
        REFUNDED
    }

    public enum DeliveryType {
        COURIER,
        BIKE,
        CAR,
        MAIL,
        SELF_PICKUP
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return Objects.equals(id, orderDto.id) && Objects.equals(user, orderDto.user) && orderStatus == orderDto.orderStatus && paymentMethod == orderDto.paymentMethod && paymentStatus == orderDto.paymentStatus && deliveryType == orderDto.deliveryType && Objects.equals(cost, orderDto.cost) && Objects.equals(items, orderDto.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, orderStatus, paymentMethod, paymentStatus, deliveryType, cost, items);
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", user=" + user +
                ", orderStatus=" + orderStatus +
                ", paymentMethod=" + paymentMethod +
                ", paymentStatus=" + paymentStatus +
                ", deliveryType=" + deliveryType +
                ", cost=" + cost +
                ", items=" + items +
                '}';
    }
}