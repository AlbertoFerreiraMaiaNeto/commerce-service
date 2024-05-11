package org.acme.flow.order;


import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.enums.OrderStatus;
import org.acme.exception.OrderNotFoundException;
import org.acme.exception.OrderStatusInvalidException;
import org.acme.persistence.dto.StatusOrderAndStockDTO;
import org.acme.persistence.model.Product;
import org.acme.persistence.service.OrderService;
import org.acme.persistence.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Singleton
public class UpdateOrderStatusAndStockFlow {

    private final OrderService orderService;

    private final ProductService productService;

    @Transactional
    public void updateOrderStatusAndStock(Long orderId, StatusOrderAndStockDTO statusOrderAndStockDTO) {
        try {
            var order = this.orderService.findById(orderId);
            var allProducts = this.productService.findAll();

            var updatedProducts = updateAmountProducts(statusOrderAndStockDTO, allProducts);

            if(order == null) {
                throw new OrderNotFoundException();
            }

            var status = OrderStatus.valueOf(statusOrderAndStockDTO.getOrderStatus());

            this.orderService.updateStatus(orderId, status.name());

            this.productService.updateProductsAmountByNameAndCategory(updatedProducts);
        } catch (IllegalArgumentException e) {
            throw new OrderStatusInvalidException();
        } catch (OrderNotFoundException e) {
            throw new OrderNotFoundException();
        } catch (Exception e) {
            log.error("Error when update order and products.");
        }
    }

    private static  List<Product> updateAmountProducts(StatusOrderAndStockDTO statusOrderAndStockDTO, List<Product> allProducts) {
        List<Product> updatedProducts = new ArrayList<>();

        allProducts.forEach(product -> {
            statusOrderAndStockDTO.getProductList().forEach(requestProductDTO -> {
                if(requestProductDTO.getName().equalsIgnoreCase(product.getName()) && requestProductDTO.getCategory().equalsIgnoreCase(product.getCategory().getName())) {
                    product.setAmount(product.getAmount() - requestProductDTO.getAmount());
                    updatedProducts.add(product);
                }
            });
        });

        return updatedProducts;
    }
}
