package online.javaclass.bookstore.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class OrderDto {
    private Long id;
    @NotNull(message = "{error.default_client}")
    private UserDto user;
    private OrderStatus orderStatus;
    @NotNull(message = "{error.invalid_payment_method}")
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    @NotNull(message = "{error.invalid_delivery_type}")
    private DeliveryType deliveryType;
    @NotNull(message = "{error.invalid_cost}")
    private BigDecimal cost;
    @NotEmpty(message = "{error.invalid_items}")
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
//                ", items=" + items.size() +
                '}';
    }
}