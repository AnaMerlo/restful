package com.api.restful.controllers;


import com.api.restful.DTO.OrderDTO;
import com.api.restful.entities.Client;
import com.api.restful.entities.Order;
import com.api.restful.exception.MyException;
import com.api.restful.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderDTO> orderList(){
        return orderService.ordertList();
    }
    /*public List<Order> findAllOrderClient(Client client){
        return orderService.findAllOrdersClient(client);
    }*/
    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO)throws MyException{
        orderService.createOrder(orderDTO);
        return new ResponseEntity<>("Order added successfully", HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateClient(@PathVariable String id, @Validated @RequestBody OrderDTO orderDTO) throws MyException {
        orderService.updateOrder(id, orderDTO);
        return new ResponseEntity<>("Order added successfully", HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable String id, @Validated @RequestBody OrderDTO orderDTO)throws MyException{
        orderService.deleteOrder(id);
        return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);

    }

}
