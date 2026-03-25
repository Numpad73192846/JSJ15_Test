package com.aloha.product.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aloha.product.domain.Product;
import com.aloha.product.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;





@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "<http://localhost>:5173")
@RequiredArgsConstructor
public class ProductController {

    private final ProductMapper productMapper;

    @GetMapping()
    public List<Product> getAll() {
        return productMapper.selectAll();
    }

    @GetMapping("{id}")
    public Product getOne(@PathVariable("id") String id) {
        return productMapper.selectOne(id);
    }

    @PostMapping()
    public Product create(@RequestBody Product product) {
        productMapper.insert(product);
        return product;
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable("id") String id, @RequestBody Product product) {
        product.setId(id);
        productMapper.update(product);
        return productMapper.selectOne(id);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        productMapper.delete(id);
    }
    
    
    
}
