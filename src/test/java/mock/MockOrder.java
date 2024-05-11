package mock;

import org.acme.enums.OrderStatus;
import org.acme.persistence.dto.*;
import org.acme.persistence.model.Order;

import java.math.BigDecimal;
import java.util.List;

import static mock.MockCategory.CATEGORY_NAME_TEST;
import static mock.MockProduct.PRODUCT_NAME_TEST;

public class MockOrder {
    public static final String ORDER_OWNER_TEST = "ORDER_OWNER_TEST";
    public static final String ORDER_OWNER_EMAIL_TEST = "ORDER_OWNER_EMAIL_TEST";
    public static final String PRODUCT_DESCRIPTION_TEST = "PRODUCT_DESCRIPTION_TEST";

    public static final String INVALID_ORDER_STATUS = "INVALID_ORDER_STATUS";

    public static Order buildOrder() {
        return  Order.builder()
                .orderId(1L)
                .orderOwner(ORDER_OWNER_TEST)
                .orderOwnerEmail(ORDER_OWNER_EMAIL_TEST)
                .orderStatus(OrderStatus.PENDING.name())
                .build();
    }

    public static OrderDTO buildOrderDTO() {
        return  OrderDTO.builder()
                .productList(buildRequestProductList())
                .orderOwner(ORDER_OWNER_TEST)
                .orderOwnerEmail(ORDER_OWNER_EMAIL_TEST)
                .build();
    }

    public static KafkaOrderDTO buildKafkaOrderPayload() {
        return  KafkaOrderDTO.builder()
                .statusOrder(OrderStatus.PENDING)
                .confirmedProducts(buildProductPayloadList())
                .insufficientAmountProducts(List.of(ProductPayloadDTO.builder().name(PRODUCT_DESCRIPTION_TEST).build()))
                .orderDTO(buildOrderDTO())
                .totalOrderPrice(500)
                .build();
    }

    public static StatusOrderAndStockDTO buildStatusOrderAndStockDTO() {
        return StatusOrderAndStockDTO.builder()
                .orderStatus(OrderStatus.CONFIRMED.name())
                .productList(buildRequestProductList())
                .build();
    }

    public static StatusOrderAndStockDTO buildStatusOrderAndStockDTOWithInvalidOrderStatus() {
        return StatusOrderAndStockDTO.builder()
                .orderStatus(INVALID_ORDER_STATUS)
                .productList(buildRequestProductList())
                .build();
    }

    public static List<RequestProductDTO> buildRequestProductList() {
        return List.of(RequestProductDTO.builder()
                        .name(PRODUCT_NAME_TEST)
                        .category(CATEGORY_NAME_TEST)
                        .amount(100)
                        .build(),
                RequestProductDTO.builder()
                        .name(PRODUCT_NAME_TEST)
                        .category(CATEGORY_NAME_TEST)
                        .amount(20)
                        .build());
    }

    public static List<ProductPayloadDTO> buildProductPayloadList() {
        return List.of(ProductPayloadDTO.builder().name(PRODUCT_NAME_TEST).amount(10).unitPrice(BigDecimal.valueOf(20.0)).build());
    }

    public static OrderResponseDTO buildOrderResponseDTO() {
        return OrderResponseDTO.builder().orderOwner(ORDER_OWNER_TEST).orderOwnerEmail(ORDER_OWNER_EMAIL_TEST).productList(List.of(ProductPayloadDTO.builder().build())).build();
    }
}