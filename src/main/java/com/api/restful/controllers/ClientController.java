package com.api.restful.controllers;

import com.api.restful.DTO.ClientDTO;
import com.api.restful.entities.Client;
import com.api.restful.exception.MyException;
import com.api.restful.services.ClientService;
import com.api.restful.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/client")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private OrderService orderService;
    @GetMapping
    public List<ClientDTO> clientList(){
        return clientService.clientList();
    }
    @GetMapping("/{id}")
    public Client findClientById(@PathVariable String id){
        return clientService.findClientById(id);
    }
    @PostMapping
    public ResponseEntity<String> createClient(@Validated @RequestBody ClientDTO clientDTO) throws MyException {
        clientService.createClientDTO(clientDTO);
        return new ResponseEntity<>("Client added successfully", HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateClient(@PathVariable String id, @Validated @RequestBody ClientDTO clientDTO) throws MyException{
        clientService.updateClient(id, clientDTO);
        return new ResponseEntity<>("Client added successfully", HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable String id, @Validated @RequestBody ClientDTO clientDTO){
        Client client = clientService.findClientById(id);
        orderService.deleteAllOrdersByClient(client);
        clientService.deleteClient(id);
        return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);
    }

}
