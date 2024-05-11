package mock;

import org.acme.persistence.dto.ProductDTO;
import org.acme.persistence.model.Product;

import java.math.BigDecimal;
import java.util.List;

import static mock.MockCategory.CATEGORY_NAME_TEST;

public class MockProduct {

    public static final String PRODUCT_NAME_TEST = "PRODUCT_TEST";
    public static final String PRODUCT_NAME_TEST2 = "PRODUCT_TEST2";
    public static final String PRODUCT_DESCRIPTION_TEST = "PRODUCT_DESCRIPTION_TEST";

    public static Product buildProduct() {
        return  Product.builder()
                .name(PRODUCT_NAME_TEST)
                .description(PRODUCT_NAME_TEST)
                .amount(10)
                .unitPrice(BigDecimal.valueOf(10.20))
                .category(MockCategory.buildCategory())
                .build();
    }

    public static Product buildProductWithDeactivatedCategory() {
        return  Product.builder()
                .name(PRODUCT_NAME_TEST)
                .description(PRODUCT_NAME_TEST)
                .amount(10)
                .unitPrice(BigDecimal.valueOf(10.20))
                .category(MockCategory.buildDeactivatedCategory())
                .build();
    }

    public static List<Product> buildProductList() {
        var product1 = Product.builder().name(PRODUCT_NAME_TEST).category(MockCategory.buildCategory()).unitPrice(BigDecimal.valueOf(20)).category(MockCategory.buildDeactivatedCategory()).amount(30).build();
        var product2 = Product.builder().name(PRODUCT_NAME_TEST).category(MockCategory.buildCategory()).unitPrice(BigDecimal.valueOf(10)).category(MockCategory.buildCategory()).amount(20).build();
        var product3 = Product.builder().name(PRODUCT_NAME_TEST2).category(MockCategory.buildCategory()).unitPrice(BigDecimal.valueOf(30)).category(MockCategory.buildDeactivatedCategory()).amount(40).build();
        var product4 = Product.builder().name(PRODUCT_NAME_TEST2).category(MockCategory.buildCategory()).unitPrice(BigDecimal.valueOf(15)).category(MockCategory.buildCategory()).amount(10).build();
        return List.of(product1, product2, product3, product4);
    }

    public static List<ProductDTO> buildProductDTOList() {
        var product1 = ProductDTO.builder().name(PRODUCT_NAME_TEST).category(MockCategory.buildCategory().getName()).unitPrice(BigDecimal.valueOf(20)).category(MockCategory.buildDeactivatedCategory().getName()).amount(30).build();
        var product2 = ProductDTO.builder().name(PRODUCT_NAME_TEST).category(MockCategory.buildCategory().getName()).unitPrice(BigDecimal.valueOf(10)).category(MockCategory.buildCategory().getName()).amount(20).build();
        var product3 = ProductDTO.builder().name(PRODUCT_NAME_TEST2).category(MockCategory.buildCategory().getName()).unitPrice(BigDecimal.valueOf(30)).category(MockCategory.buildDeactivatedCategory().getName()).amount(40).build();
        var product4 = ProductDTO.builder().name(PRODUCT_NAME_TEST2).category(MockCategory.buildCategory().getName()).unitPrice(BigDecimal.valueOf(15)).category(MockCategory.buildCategory().getName()).amount(10).build();
        return List.of(product1, product2, product3, product4);
    }

    public static List<Product> buildProductListWithDeactivatedCategory() {
        var product = Product.builder().name(PRODUCT_NAME_TEST).unitPrice(BigDecimal.valueOf(20)).category(MockCategory.buildDeactivatedCategory()).amount(30).build();
        return List.of(product);
    }

    public static List<Product> buildProductListWithActivatedCategory() {
        var product = Product.builder().name(PRODUCT_NAME_TEST).unitPrice(BigDecimal.valueOf(20)).category(MockCategory.buildCategory()).amount(30).build();
        return List.of(product);
    }

    public static ProductDTO buildProductDTO() {
       return  ProductDTO.builder()
               .name(PRODUCT_NAME_TEST)
               .description(PRODUCT_DESCRIPTION_TEST)
               .amount(10)
               .unitPrice(BigDecimal.valueOf(10.20))
               .category(CATEGORY_NAME_TEST)
               .build();
    }
}