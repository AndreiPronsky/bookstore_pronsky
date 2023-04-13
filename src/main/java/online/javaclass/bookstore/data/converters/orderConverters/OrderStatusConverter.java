package online.javaclass.bookstore.data.converters.orderConverters;

import online.javaclass.bookstore.data.entities.Order;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OrderStatusConverter implements AttributeConverter<Order.OrderStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Order.OrderStatus orderStatus) {
        Integer orderStatusId = null;
        switch (orderStatus) {
            case OPEN -> orderStatusId = 1;
            case CONFIRMED -> orderStatusId = 2;
            case COMPLETED -> orderStatusId = 3;
            case CANCELLED -> orderStatusId = 4;
        }
        return orderStatusId;
    }

    @Override
    public Order.OrderStatus convertToEntityAttribute(Integer orderStatusId) {
        Order.OrderStatus orderStatus = null;
        switch (orderStatusId) {
            case 1 -> orderStatus = Order.OrderStatus.OPEN;
            case 2 -> orderStatus = Order.OrderStatus.CONFIRMED;
            case 3 -> orderStatus = Order.OrderStatus.COMPLETED;
            case 4 -> orderStatus = Order.OrderStatus.CANCELLED;
        }
        return orderStatus;
    }
}