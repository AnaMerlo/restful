package com.api.restful.controllers;

import com.api.restful.DTO.ProductDTO;
import com.api.restful.exception.MyException;
import com.api.restful.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDTO> productList(){
        return productService.productList();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> productById(@PathVariable String id){
        ProductDTO productDTO = productService.productById(id);
        if (productDTO != null) {
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO){
        productService.createProduct(productDTO);
        return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable String id, @Validated @RequestBody ProductDTO productDTO)throws MyException {
        productService.updateProduct(id, productDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id){
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
