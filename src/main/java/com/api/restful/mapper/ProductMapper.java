package com.api.restful.mapper;

import com.api.restful.entities.Product;
import com.api.restful.DTO.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductDTO converterToDTO(Product product){
        return modelMapper.map(product, ProductDTO.class);
    }

    public Product converterToEntity(ProductDTO productDTO){
        return modelMapper.map(productDTO, Product.class);
    }
}
