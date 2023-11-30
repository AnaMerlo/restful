package com.api.restful.services;


import com.api.restful.DTO.ProductDTO;
import com.api.restful.entities.Client;
import com.api.restful.entities.Product;
import com.api.restful.exception.MyException;
import com.api.restful.mapper.ProductMapper;
import com.api.restful.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    public ProductService(){
        this.modelMapper = new ModelMapper();
    }
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO){
        Product product = productMapper.converterToEntity(productDTO);
        Product productSaved = productRepository.save(product);
        return productMapper.converterToDTO(productSaved);
    }

    @Transactional
    public List<ProductDTO> productList() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::converterToDTO)
                .collect(Collectors.toList());
    }
    @Transactional
    public ProductDTO productById(String id){
        Optional<Product> response = productRepository.findById(id);
        return response.map(productMapper::converterToDTO).orElse(null);
    }

    public void updateProduct(String id, ProductDTO productDTO) throws MyException{
        Product productExist = productRepository.findById(id).orElse(null);
        if(this.modelMapper != null){
            if(productExist != null){

                modelMapper.map(productDTO, productExist);
                productRepository.save(productExist);
            }
        }else{
            throw new MyException("modelMapper es nulo");
        }

    }

    public void deleteProduct(Client client){
        productRepository.deleteByClient(client);
    }

    public void delete(String id){
        productRepository.deleteById(id);
    }


}
