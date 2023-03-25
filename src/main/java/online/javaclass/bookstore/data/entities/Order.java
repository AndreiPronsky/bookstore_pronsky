package online.javaclass.bookstore.data.entities;

import online.javaclass.bookstore.data.converters.orderConverters.DeliveryTypeConverter;
import online.javaclass.bookstore.data.converters.orderConverters.OrderStatusConverter;
import online.javaclass.bookstore.data.converters.orderConverters.PaymentMethodConverter;
import online.javaclass.bookstore.data.converters.orderConverters.PaymentStatusConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
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
