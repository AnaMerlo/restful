package com.api.restful.mapper;

import com.api.restful.entities.Client;
import com.api.restful.DTO.ClientDTO;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;
@Component
public class ClientMapper {

    private final ModelMapper modelMapper;

    public ClientMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ClientDTO converterToDTO(Client client){
        return modelMapper.map(client, ClientDTO.class);
    }

    public Client converterToEntity(ClientDTO clientDTO){
        return modelMapper.map(clientDTO, Client.class);
    }
}

