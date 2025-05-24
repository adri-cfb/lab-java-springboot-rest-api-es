package com.ironhackproject.labspringbootrestapi.model;
import jakarta.validation.constraints.*;

public class Customer {
    @NotBlank(message="el nombre no puede estar vacio")
    private String name;

    @Email (message = "El email debe tener formato valido")
    @NotBlank (message = "El email no puede estar vacio")
    private String email;

    @Min (value = 18, message = "La edad minima es 18 años")
    private int age;

    @NotBlank (message = "La direccion no puede estar vacia")
    private String address;

    public Customer() {
    }
    public Customer(String name, String email, int age, String address) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
