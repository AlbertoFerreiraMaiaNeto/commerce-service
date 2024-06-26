package org.acme.flow.order;

import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.acme.enums.OrderStatus;
import org.acme.flow.order.items.CalculatePricePerOrderProductFlowItem;
import org.acme.flow.order.items.CalculateTotalOrderPriceFlowItem;
import org.acme.flow.order.items.ValidateProductAndAmountFlowItem;
import org.acme.kafka.KafkaOrderProducer;
import org.acme.persistence.dto.KafkaOrderDTO;
import org.acme.persistence.dto.OrderDTO;
import org.acme.persistence.dto.OrderResponseDTO;
import org.acme.persistence.model.Category;
import org.acme.persistence.model.Order;
import org.acme.persistence.model.OrderProduct;
import org.acme.persistence.model.Product;
import org.acme.persistence.service.OrderService;
import org.acme.persistence.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Singleton
public class CreateOrderFlow {

    private final OrderService orderService;
    private final ProductService productService;
    private final KafkaOrderProducer kafkaOrderProducer;
    private final ValidateProductAndAmountFlowItem validateProductAndAmountFlowItem;
    private final CalculatePricePerOrderProductFlowItem calculatePricePerOrderProductFlowItem;
    private final CalculateTotalOrderPriceFlowItem calculateTotalOrderPriceFlowItem;

    @Transactional
    public OrderResponseDTO createOrder (OrderDTO orderDTO) {
        try {
            var productList = this.productService.findAll();

            var kafkaPayload = this.validateProductAndAmountFlowItem.validate(orderDTO, productList);
            kafkaPayload = this.calculatePricePerOrderProductFlowItem.calculatePricePerProduct(kafkaPayload);
            kafkaPayload = this.calculateTotalOrderPriceFlowItem.calculateTotalOrderPrice(kafkaPayload);

            var order = Order.builder()
                    .orderOwner(orderDTO.getOrderOwner())
                    .orderOwnerEmail(orderDTO.getOrderOwnerEmail())
                    .orderStatus(OrderStatus.PENDING.name())
                    .totalOrderPrice(kafkaPayload.getTotalOrderPrice())
                    .build();

            List<OrderProduct> orderProducts = getOrderProducts(order, kafkaPayload);

            order.setOrderProducts(orderProducts);

            this.orderService.save(order);

            kafkaPayload.setOrderId(order.getOrderId());
            this.kafkaOrderProducer.publish(kafkaPayload);

            return OrderResponseDTO.builder()
                    .orderOwner(orderDTO.getOrderOwner())
                    .orderOwnerEmail(orderDTO.getOrderOwnerEmail())
                    .status(OrderStatus.PENDING)
                    .productList(kafkaPayload.getConfirmedProducts())
                    .build();
        }
        catch(Exception e) {
            throw new RuntimeException("Error processing the order.", e);
        }
    }

    private static List<OrderProduct> getOrderProducts(Order order, KafkaOrderDTO kafkaPayload) {
        List<OrderProduct> orderProducts = new ArrayList<>();

        kafkaPayload.getConfirmedProducts().forEach(product -> {
            var categoryEntity = Category.builder().name(product.getCategory()).build();
            var productEntity = Product.builder().name(product.getName()).category(categoryEntity).build();

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(productEntity);
            orderProduct.setAmount(product.getAmount());
            orderProduct.setTotalPricePerProduct(product.getTotalPricePerProduct());

            orderProducts.add(orderProduct);
        });

        return orderProducts;
    }
}
