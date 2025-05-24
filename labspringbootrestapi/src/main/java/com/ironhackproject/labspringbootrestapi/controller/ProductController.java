package com.ironhackproject.labspringbootrestapi.controller;

import com.ironhackproject.labspringbootrestapi.model.Product;
import com.ironhackproject.labspringbootrestapi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private static final String VALID_API_KEY = "123456";

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    private void validateApiKey(String apiKey) {
        if (apiKey == null || !apiKey.equals(VALID_API_KEY)) {
            throw new RuntimeException("API-Key inválida o no enviada.");
        }
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestHeader(value = "API-Key", required = false) String apiKey,
                                             @Valid @RequestBody Product product) {
        validateApiKey(apiKey);
        productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado exitosamente.");
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@RequestHeader(value = "API-Key", required = false) String apiKey) {
        validateApiKey(apiKey);
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Product> getProductByName(@RequestHeader(value = "API-Key", required = false) String apiKey,
                                                    @PathVariable String name) {
        validateApiKey(apiKey);
        return productService.getProductByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{name}")
    public ResponseEntity<String> updateProduct(@RequestHeader(value = "API-Key", required = false) String apiKey,
                                                @PathVariable String name,
                                                @Valid @RequestBody Product updatedProduct) {
        validateApiKey(apiKey);
        boolean updated = productService.updateProduct(name, updatedProduct);
        if (updated) {
            return ResponseEntity.ok("Producto actualizado.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado.");
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteProduct(@RequestHeader(value = "API-Key", required = false) String apiKey,
                                                @PathVariable String name) {
        validateApiKey(apiKey);
        boolean deleted = productService.deleteProduct(name);
        if (deleted) {
            return ResponseEntity.ok("Producto eliminado.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado.");
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getByCategory(@RequestHeader(value = "API-Key", required = false) String apiKey,
                                                       @PathVariable String category) {
        validateApiKey(apiKey);
        return ResponseEntity.ok(productService.getByCategory(category));
    }

    @GetMapping("/price")
    public ResponseEntity<List<Product>> getByPriceRange(@RequestHeader(value = "API-Key", required = false) String apiKey,
                                                         @RequestParam double min,
                                                         @RequestParam double max) {
        validateApiKey(apiKey);
        if (min < 0 || max < 0 || max < min) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(productService.getByPriceRange(min, max));
    }

}