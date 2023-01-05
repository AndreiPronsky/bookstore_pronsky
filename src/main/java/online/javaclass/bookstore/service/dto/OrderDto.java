package online.javaclass.bookstore.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private UserDto user;
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