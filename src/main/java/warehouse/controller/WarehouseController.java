package warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import warehouse.model.ProductData;
import warehouse.model.Warehouse;
import warehouse.repository.WarehouseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WarehouseController {

    @Autowired
    private WarehouseRepository repository;

    // --- WAREHOUSE ENDPOINTS ---

    @PostMapping("/warehouse")
    public Warehouse addWarehouse(@RequestBody Warehouse warehouse) {
        return repository.save(warehouse);
    }

    @GetMapping("/warehouse")
    public List<Warehouse> getAllWarehouses() {
        return repository.findAll();
    }

    @GetMapping("/warehouse/{id}")
    public Warehouse getWarehouseById(@PathVariable String id) {
        return repository.findById(id).orElse(null);
    }

    @DeleteMapping("/warehouse/{id}")
    public void deleteWarehouse(@PathVariable String id) {
        repository.deleteById(id);
    }

    // --- PRODUCT ENDPOINTS ---

    @PostMapping("/product")
    public Warehouse addProductToWarehouse(@RequestParam String warehouseId, @RequestBody ProductData product) {
        Warehouse w = repository.findById(warehouseId).orElseThrow();
        w.getProductData().add(product);
        return repository.save(w);
    }

    @GetMapping("/product")
    public List<ProductData> getAllProducts() {
        return repository.findAll().stream()
                .flatMap(w -> w.getProductData().stream())
                .collect(Collectors.toList());
    }

    @GetMapping("/product/{id}")
    public List<Warehouse> getWarehousesByProductId(@PathVariable String id) {
        return repository.findAll().stream()
                .filter(w -> w.getProductData().stream().anyMatch(p -> p.getProductID().equals(id)))
                .collect(Collectors.toList());
    }
}