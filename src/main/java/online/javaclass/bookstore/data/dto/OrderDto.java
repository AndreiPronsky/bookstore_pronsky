package online.javaclass.bookstore.data.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private DeliveryType deliveryType;
    private BigDecimal cost;
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
        BANK_TRANSFER,
        CASH_TO_COURIER,
        CARD_TO_COURIER
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
