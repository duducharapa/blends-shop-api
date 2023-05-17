package com.charapadev.blendsshop.modules.products;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ShowProductDTO>> list() {
        List<ShowProductDTO> showDTOs = productService.list()
            .stream().map(productService::convert)
            .toList();

        return ResponseEntity.ok(showDTOs);
    }

    @PostMapping
    public ResponseEntity<ShowProductDTO> create(@Valid @RequestBody CreateProductDTO createDTO) {
        Product productCreated = productService.create(createDTO);
        ShowProductDTO showDTO = productService.convert(productCreated);

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(showDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowProductDTO> searchOne(@PathVariable("id") Integer productId) {
        try {
            Product productFound = productService.findOneOrFail(productId);
            ShowProductDTO showDTO = productService.convert(productFound);

            return ResponseEntity.ok(showDTO);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
