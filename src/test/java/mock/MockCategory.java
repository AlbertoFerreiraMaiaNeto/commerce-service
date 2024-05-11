package mock;

import org.acme.persistence.model.Category;

import java.util.List;

public class MockCategory {

    public static final String CATEGORY_NAME_TEST = "CATEGORY_TEST";
    public static final String CATEGORY_NAME_TEST2 = "CATEGORY_TEST2";


    public static Category buildCategory() {
       return  Category.builder()
               .name(CATEGORY_NAME_TEST)
               .description(CATEGORY_NAME_TEST)
               .active(true)
               .build();
    }

    public static Category buildDeactivatedCategory() {
        return  Category.builder()
                .name(CATEGORY_NAME_TEST2)
                .description(CATEGORY_NAME_TEST2)
                .active(false)
                .build();
    }

    public static List<Category> buildCategoryListWithActivatedAndDeactivatedCategories() {
        var categoryWithStatusActiveTrue = Category.builder().name(CATEGORY_NAME_TEST).active(true).build();
        var categoryWithStatusActiveFalse = Category.builder().name(CATEGORY_NAME_TEST).active(false).build();
        return List.of(categoryWithStatusActiveTrue, categoryWithStatusActiveFalse);
    }

    public static List<Category> buildCategoryListWithActivatedCategories() {
        var category1 = Category.builder().name(CATEGORY_NAME_TEST).active(true).build();
        var category2 = Category.builder().name(CATEGORY_NAME_TEST2).active(true).build();
        return List.of(category1, category2);
    }

}