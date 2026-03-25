package com.aloha.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aloha.product.domain.Product;

@Mapper
public interface ProductMapper {
    
    List<Product> selectAll();

    Product selectOne(String id);

    int insert(Product product);

    int update(Product product);

    int delete(String id);

}
