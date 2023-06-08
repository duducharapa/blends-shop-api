package com.charapadev.blendsshop.modules.orders;

import com.charapadev.blendsshop.exceptions.SearchProductsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<ShowOrderDTO>> list() {
        List<ShowOrderDTO> showDTOs = orderService.list().stream()
            .map(orderService::convert)
            .toList();

        return ResponseEntity.ok(showDTOs);
    }

    @PostMapping
    public ResponseEntity<ShowOrderDTO> create(@Valid @RequestBody CreateOrderDTO createDTO) {
        try {
            Order orderCreated = orderService.create(createDTO);
            ShowOrderDTO showDTO = orderService.convert(orderCreated);

            return ResponseEntity.status(HttpStatus.CREATED.value()).body(showDTO);
        } catch (SearchProductsException ex) {
            return ResponseEntity.notFound().build();
        }
    }

}
