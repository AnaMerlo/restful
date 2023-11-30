package com.api.restful.services;


import com.api.restful.DTO.ClientDTO;
import com.api.restful.DTO.OrderDTO;
import com.api.restful.DTO.ProductDTO;
import com.api.restful.entities.Client;
import com.api.restful.entities.Order;
import com.api.restful.entities.Product;
import com.api.restful.exception.MyException;
import com.api.restful.mapper.OrderMapper;
import com.api.restful.mapper.ProductMapper;
import com.api.restful.repositories.ClientRepository;
import com.api.restful.repositories.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @PersistenceContext
    private EntityManager entityManager;

    private ModelMapper modelMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ClientRepository clientRepository;
    public OrderService(){
        this.modelMapper = new ModelMapper();
    }
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) throws MyException {
        Order order = orderMapper.converterToEntity(orderDTO);
        //Asignacion de cliente
        if (order.getClient() != null && order.getClient().getId() == null) {
            //clientes nuevos
        } else if (order.getClient() != null && order.getClient().getId() != null) {
            // El cliente ya tiene un ID, busca el cliente existente
            Client clientOlder = clientRepository.findById(order.getClient().getId()).orElse(null);

            // Asigna el cliente existente a la orden
            order.setClient(clientOlder);
        }

        /*if(this.modelMapper != null){
            Client clientOlder = clientRepository.findById(order.getClient().getId()).orElse(null);
            if(entityManager.contains(clientOlder)){
                clientOlder = entityManager.merge(clientOlder);
                order.setClient(clientOlder);
            }else{
                ClientDTO clientDTO = orderDTO.getClient();

                Client client = modelMapper.map(clientDTO, Client.class);
                order.setClient(client);


            }
            /*ClientDTO clientDTO = orderDTO.getClient();

            Client client = modelMapper.map(clientDTO, Client.class);

        }else{

            throw new MyException("No se puede modificar");
        }*/


        //Asignacion de productos en dicha orden
        List<ProductDTO> productDTOList = orderDTO.getProducts();

        List<Product> productList = new ArrayList<>();

        for (ProductDTO productDTO : productDTOList) {
            Product product = productMapper.converterToEntity(productDTO);
            productList.add(product);
        }

        order.setProducts(productList);
        //save
        Order savedOrder = orderRepository.save(order);
        return orderMapper.converterToDTO(savedOrder);
    }

    /*public List<Order> findAllOrdersClient(Client client){
        return orderRepository.findAllByClient(client);
    }*/
    @Transactional
    public List<OrderDTO> ordertList() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::converterToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAllOrdersByClient(Client client) {
        orderRepository.deleteByClient(client);
    }
    public void updateOrder(String id, OrderDTO orderDTO) throws MyException{
        Order orderExist = orderRepository.findById(id).orElse(null);
        if(this.modelMapper != null){
            if(orderExist != null){
                modelMapper.map(orderDTO, orderExist);
                // Actualizar productos de la orden
                List<ProductDTO> updatedProductDTOList = orderDTO.getProducts();
                List<Product> updatedProductList = new ArrayList<>();

                for (ProductDTO productDTO : updatedProductDTOList) {
                    Product product = productMapper.converterToEntity(productDTO);
                    updatedProductList.add(product);
                }
                // Actualizar cliente de la orden
                ClientDTO updatedClientDTO = orderDTO.getClient();
                orderExist.setClient(modelMapper.map(updatedClientDTO, Client.class));
                // Guardar los cambios en la base de datos
                orderRepository.save(orderExist);
            }else{
                throw new MyException("No se puede modificar");
            }
        }else{
            throw new MyException("modelMapper es nulo");
        }
    }

    public void deleteOrder(String id) throws MyException{
        Order orderExist = orderRepository.findById(id).orElse(null);
        if(orderExist != null){
            orderRepository.deleteById(id);
        }else{
            throw new MyException("No se puede eliminar");
        }

    }



}
