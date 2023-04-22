package online.javaclass.bookstore.data.converters.orderConverters;

import online.javaclass.bookstore.data.entities.Order;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PaymentStatusConverter implements AttributeConverter<Order.PaymentStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Order.PaymentStatus paymentStatus) {
        Integer paymentStatusId = null;
        switch (paymentStatus) {
            case UNPAID -> paymentStatusId = 1;
            case FAILED -> paymentStatusId = 2;
            case PAID -> paymentStatusId = 3;
            case REFUNDED -> paymentStatusId = 4;
        }
        return paymentStatusId;
    }

    @Override
    public Order.PaymentStatus convertToEntityAttribute(Integer paymentStatusId) {
        Order.PaymentStatus paymentStatus = null;
        switch (paymentStatusId) {
            case 1 -> paymentStatus = Order.PaymentStatus.UNPAID;
            case 2 -> paymentStatus = Order.PaymentStatus.FAILED;
            case 3 -> paymentStatus = Order.PaymentStatus.PAID;
            case 4 -> paymentStatus = Order.PaymentStatus.REFUNDED;
        }
        return paymentStatus;
    }
}
