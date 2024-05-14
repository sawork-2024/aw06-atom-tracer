package orders.mapper;

import orders.model.Order;
import org.mapstruct.Mapper;
import webpos.rest.dto.OrderDto;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toOrderDto(Order order);
}
