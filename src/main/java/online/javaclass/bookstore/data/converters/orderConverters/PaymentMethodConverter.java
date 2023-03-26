package online.javaclass.bookstore.data.converters.orderConverters;

import online.javaclass.bookstore.data.entities.Order;

import javax.persistence.AttributeConverter;

public class PaymentMethodConverter implements AttributeConverter<Order.PaymentMethod, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Order.PaymentMethod paymentMethod) {
        Integer paymentMethodId = null;
        switch (paymentMethod) {
            case CASH -> paymentMethodId = 1;
            case CARD -> paymentMethodId = 2;
            case BANK_TRANSFER -> paymentMethodId = 3;
        }
        return paymentMethodId;
    }

    @Override
    public Order.PaymentMethod convertToEntityAttribute(Integer paymentMethodId) {
        Order.PaymentMethod paymentMethod = null;
        switch (paymentMethodId) {
            case 1 -> paymentMethod = Order.PaymentMethod.CASH;
            case 2 -> paymentMethod = Order.PaymentMethod.CARD;
            case 3 -> paymentMethod = Order.PaymentMethod.BANK_TRANSFER;
        }
        return paymentMethod;
    }
}