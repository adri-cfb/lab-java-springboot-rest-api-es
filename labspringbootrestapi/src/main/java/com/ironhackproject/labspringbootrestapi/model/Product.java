package com.ironhackproject.labspringbootrestapi.model;

import jakarta.validation.constraints.*;

public class Product {

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 3, message = "El nombre debe tener al menos 3 caracteres")
    private String name;

    @Positive(message = "El precio debe ser un numero positivo")
    private double price;

    @NotBlank(message = "La categoria no puede estar vacia")
    private String category;

    @PositiveOrZero(message = "La cantidad no puede ser negativa")
    private int quantity;

    //constructor vacio
    public Product() {
    }

    //constructor con parametros
    public Product(String name, double price, String category, int quantity) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    //getters y setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
