package com.example.junitandmocktioexample2;

import com.example.junitandmocktioexample2.entity.Product;
import com.example.junitandmocktioexample2.repository.ProductRepository;
import com.example.junitandmocktioexample2.service.ProductService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class productServiceTests {
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Before All");
    }
    @BeforeEach
    public void beforeEach() {
        System.out.println("Before Each");
    }
    @AfterAll
    public static void afterAll() {
        System.out.println("After all");
    }
    @AfterEach
    public void afterEach() {
        System.out.println("After Each");
    }

    @Test
    void addProductMustSaveProductSuccessfully() {
        Product product = new Product();
        product.setId(1);
        product.setName("Book");
        product.setDescription("Comic");
        product.setPrice(100);
        Mockito.when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.addProduct(product);
        System.out.println("In test method");
        Assertions.assertEquals(savedProduct, product);
    }
    //testing excpetions
    @Test
    void addProductShouldThrowExceptionForInvalidProductName() {
        Product product = new Product();
        product.setId(1);
        product.setName("");
        product.setDescription("Comic");
        product.setPrice(100);
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class,
                () -> productService.addProduct(product));
        Assertions.assertEquals("Product name is invalid", runtimeException.getMessage());
    }

    //testing using doNothing()
    @Test
    void deleteProductMustDeleteProductSuccessfully() {
        doNothing().when(productRepository).deleteById(1);
        productService.deleteProductById(1);
        verify(productRepository,times(1)).deleteById(1);
    }
    
    //testing private methods using reflections
    @Test
    void testPrivateMethodValidateProductNameSuccessfully() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method validateProductName = productService.getClass().getDeclaredMethod("validateProductName", String.class);
        validateProductName.setAccessible(true);
        Assertions.assertTrue((Boolean) validateProductName.invoke(productService,"Book"));
    }
    @Test
    void testPrivateMethodValidateProductNameShouldFail() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method validateProductName = productService.getClass().getDeclaredMethod("validateProductName", String.class);
        validateProductName.setAccessible(true);
        boolean result = (boolean) validateProductName.invoke(productService, "");

        Assertions.assertFalse(result);
    }
}
