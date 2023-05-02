package online.javaclass.bookstore.data.entities;

import lombok.Getter;
import lombok.Setter;
import online.javaclass.bookstore.data.converters.orderConverters.DeliveryTypeConverter;
import online.javaclass.bookstore.data.converters.orderConverters.OrderStatusConverter;
import online.javaclass.bookstore.data.converters.orderConverters.PaymentMethodConverter;
import online.javaclass.bookstore.data.converters.orderConverters.PaymentStatusConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "status_id")
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus orderStatus;

    @Column(name = "payment_method_id")
    @Convert(converter = PaymentMethodConverter.class)
    private PaymentMethod paymentMethod;

    @Column(name = "payment_status_id")
    @Convert(converter = PaymentStatusConverter.class)
    private PaymentStatus paymentStatus;

    @Column(name = "delivery_type_id")
    @Convert(converter = DeliveryTypeConverter.class)
    private DeliveryType deliveryType;

    @Column(name = "cost")
    private BigDecimal cost;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
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

    public Order() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(user, order.user) && orderStatus == order.orderStatus && paymentMethod == order.paymentMethod && paymentStatus == order.paymentStatus && deliveryType == order.deliveryType && Objects.equals(cost, order.cost) && Objects.equals(items, order.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, orderStatus, paymentMethod, paymentStatus, deliveryType, cost, items);
    }

    @Override
    public String toString() {
        return "Order{" +
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
