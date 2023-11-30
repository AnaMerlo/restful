package com.api.restful.services;

import com.api.restful.DTO.ClientDTO;
import com.api.restful.entities.Client;
import com.api.restful.exception.MyException;
import com.api.restful.mapper.ClientMapper;
import com.api.restful.repositories.ClientRepository;

import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private ModelMapper modelMapper;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    public ClientService(){
        this.modelMapper = new ModelMapper();
    }
    @Transactional
    public ClientDTO createClientDTO(ClientDTO clientDTO){
        Client client = clientMapper.converterToEntity(clientDTO);
        Client clientSave = clientRepository.save(client);
        return clientMapper.converterToDTO(clientSave);
    }

    @Transactional
    public List<ClientDTO> clientList() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(clientMapper::converterToDTO)
                .collect(Collectors.toList());
    }
    public Client findClientById(String id) {
        return clientRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }

    public void updateClient(String id, ClientDTO clientDTO) throws MyException {

        Client clientExist = clientRepository.findById(id).orElse(null);
        if(this.modelMapper != null){
            if (clientExist != null) {

                modelMapper.map(clientDTO, clientExist);
                clientRepository.save(clientExist);
            }
        }else{
            throw new MyException("modelMapper es nulo");
        }

    }
    public void deleteClient(String id){
        Client client = findClientById(id);
        //Client client = clientRepository.findById(id).orElse(null);
        if(client != null){
            orderService.deleteAllOrdersByClient(client);
            productService.deleteProduct(client);
            clientRepository.delete(client);
        }

    }
    public void validation(String name, @NotNull String email, Integer phone) throws MyException{
        if(email.isEmpty() || name.isEmpty()){
            throw new MyException("Debe ingresar los datos");
        }
        if(phone == null){
            throw new MyException("El telefono no puede estar vacio");
        }
    }


}
