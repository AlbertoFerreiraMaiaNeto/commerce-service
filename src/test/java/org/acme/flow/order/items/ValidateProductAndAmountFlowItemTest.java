package org.acme.flow.order.items;

import mock.MockOrder;
import mock.MockProduct;
import org.acme.persistence.dto.OrderDTO;
import org.acme.persistence.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidateProductAndAmountFlowItemTest {

    private ValidateProductAndAmountFlowItem validateProductAndAmountFlowItem;
    private OrderDTO orderDTO;
    private List<Product> productList;

    @BeforeEach
    public void setup() {
        this.productList = MockProduct.buildProductList();
        this.orderDTO = MockOrder.buildOrderDTO();
        this.validateProductAndAmountFlowItem = new ValidateProductAndAmountFlowItem();
    }

    @Test
    void validateProductsAndAmount() {

        var output = this.validateProductAndAmountFlowItem.validate(orderDTO, productList);

        assertEquals(1, output.getConfirmedProducts().size());
        assertEquals(1, output.getInsufficientAmountProducts().size());
    }
}