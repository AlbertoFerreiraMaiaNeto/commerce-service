package org.acme.persistence.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import mock.MockCategory;
import org.acme.persistence.model.Category;
import org.acme.persistence.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static io.smallrye.common.constraint.Assert.assertNotNull;
import static mock.MockCategory.CATEGORY_NAME_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {

    private CategoryRepository categoryRepository;

    private CategoryServiceImpl categoryService;

    private Category category;

    @BeforeEach
    public void setup() {
        this.category = MockCategory.buildCategory();
        this.categoryRepository = Mockito.mock(CategoryRepository.class);
        this.categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void save() {
        this.categoryService.save(category);

        Mockito.verify(categoryRepository, times(1)).persist(category);
    }

    @Test
    void update() {
        this.categoryService.update(category);

        ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Parameters> parametersCaptor = ArgumentCaptor.forClass(Parameters.class);
        Mockito.verify(categoryRepository).update(queryCaptor.capture(), parametersCaptor.capture());

        assertEquals("set description = :description, active = :active where name = :name", queryCaptor.getValue());
    }

    @Test
    void findByName() {
        when(this.categoryRepository.findByName(CATEGORY_NAME_TEST)).thenReturn(category);

        var output = this.categoryService.findByName(CATEGORY_NAME_TEST);

        assertNotNull(output);
        assertEquals(CATEGORY_NAME_TEST, output.getName());
    }

    @Test
    void findAll() {
        when(this.categoryRepository.findAll()).thenReturn(Mockito.mock(PanacheQuery.class));

        var output = this.categoryService.findAll();

        assertNotNull(output);
    }
}