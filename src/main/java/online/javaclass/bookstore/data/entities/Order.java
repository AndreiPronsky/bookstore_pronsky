package online.javaclass.bookstore.data.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Order {
    private Long id;
    private User user;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private DeliveryType deliveryType;
    private BigDecimal cost;
    private List<OrderItem> items;

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
}
