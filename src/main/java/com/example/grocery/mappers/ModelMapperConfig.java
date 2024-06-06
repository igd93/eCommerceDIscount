package com.example.grocery.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.grocery.dto.CartItemDTO;
import com.example.grocery.entities.CartItem;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
        modelMapper.addMappings(new PropertyMap<CartItem,CartItemDTO>() {
            @Override
            protected void configure() {
                map().setInventoryId(source.getInventory().getId());
                map().setProductType(source.getInventory().getProductType());;
            }
            
        });

        return modelMapper;
    }
    
}
