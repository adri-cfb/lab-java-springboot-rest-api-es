package com.ironhackproject.labspringbootrestapi.service;

import com.ironhackproject.labspringbootrestapi.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final List<Product> productList = new ArrayList<>();


    // 1. Agregar un producto nuevo
    public void addProduct(Product product) {
        productList.add(product);
    }

    // 2. Obtener todos los productos
    public List<Product> getAllProducts() {
        return productList;
    }

    // 3. Buscar producto por nombre
    public Optional<Product> getProductByName(String name) {
        return productList.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    // 4. Actualizar producto por nombre
    public boolean updateProduct(String name, Product updatedProduct) {
        Optional<Product> existing = getProductByName(name);
        if (existing.isPresent()) {
            Product product = existing.get();
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setCategory(updatedProduct.getCategory());
            product.setQuantity(updatedProduct.getQuantity());
            return true;
        }
        return false;
    }

    // 5. Eliminar producto
    public boolean deleteProduct(String name) {
        return productList.removeIf(p -> p.getName().equalsIgnoreCase(name));
    }

    // 6. Obtener productos por categoria
    public List<Product> getByCategory(String category) {
        return productList.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // 7. Obtener productos por rango de precios
    public List<Product> getByPriceRange(double min, double max) {
        return productList.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }
}
