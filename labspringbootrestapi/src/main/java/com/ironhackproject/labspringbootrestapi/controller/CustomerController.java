package com.ironhackproject.labspringbootrestapi.controller;

import com.ironhackproject.labspringbootrestapi.model.Customer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final List <Customer> customerList = new ArrayList<>();
    private static final String VALID_API_KEY = "123456";

    private void validateApiKey(String apiKey) {
        if (apiKey == null || !apiKey.equals(VALID_API_KEY)) {
            throw new RuntimeException("API-Key inválida o no enviada.");
        }
    }

    @PostMapping
    public ResponseEntity<String> addCustomer(
            @RequestHeader (value = "API-Key", required = false) String apiKey,
            @Valid@RequestBody Customer customer) {
        validateApiKey(apiKey);
        customerList.add(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado correctamente.");
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(@RequestHeader(value="API-Key", required = false) String apiKey){
        validateApiKey(apiKey);
        return ResponseEntity.ok(customerList);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(
            @RequestHeader(value = "API-Key", required = false) String apiKey,
            @PathVariable String email) {
        validateApiKey(apiKey);
        Optional<Customer> customer = customerList.stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst();

        return customer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{email}")
    public ResponseEntity<String> updateCustomer(
            @RequestHeader(value = "API-Key", required = false) String apiKey,
            @PathVariable String email,
            @Valid @RequestBody Customer updateCustomer) {
        validateApiKey(apiKey);
        Optional<Customer> existing = customerList.stream()
                .filter (c-> c.getEmail().equalsIgnoreCase(email))
                .findFirst();

        if (existing.isPresent()) {
            Customer customer = existing.get();
            customer.setName(updateCustomer.getName());
            customer.setEmail(updateCustomer.getEmail());
            customer.setAge(updateCustomer.getAge());
            customer.setAddress(updateCustomer.getAddress());
            return ResponseEntity.ok("Cliente actualizado correctamente.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado.");

    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteCustomer(
            @RequestHeader(value = "API-Key", required = false) String apiKey,
            @PathVariable String email) {
        validateApiKey(apiKey);
        boolean removed = customerList.removeIf(c -> c.getEmail().equalsIgnoreCase(email));
        if (removed){
            return ResponseEntity.ok("Cliente eliminado correctamente.");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado.");
        }
    }

}
