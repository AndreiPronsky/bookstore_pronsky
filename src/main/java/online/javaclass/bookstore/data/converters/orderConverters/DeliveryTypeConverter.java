package online.javaclass.bookstore.data.converters.orderConverters;

import online.javaclass.bookstore.data.entities.Order;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DeliveryTypeConverter implements AttributeConverter<Order.DeliveryType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Order.DeliveryType deliveryType) {
        Integer deliveryTypeId = null;
        switch (deliveryType) {
            case COURIER -> deliveryTypeId = 1;
            case BIKE -> deliveryTypeId = 2;
            case CAR -> deliveryTypeId = 3;
            case MAIL -> deliveryTypeId = 4;
            case SELF_PICKUP -> deliveryTypeId = 5;
        }
        return deliveryTypeId;
    }

    @Override
    public Order.DeliveryType convertToEntityAttribute(Integer deliveryTypeId) {
        Order.DeliveryType deliveryType = null;
        switch (deliveryTypeId) {
            case 1 -> deliveryType = Order.DeliveryType.COURIER;
            case 2 -> deliveryType = Order.DeliveryType.BIKE;
            case 3 -> deliveryType = Order.DeliveryType.CAR;
            case 4 -> deliveryType = Order.DeliveryType.MAIL;
            case 5 -> deliveryType = Order.DeliveryType.SELF_PICKUP;
        }
        return deliveryType;
    }
}
