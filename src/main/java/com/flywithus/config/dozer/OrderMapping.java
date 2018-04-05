package com.flywithus.config.dozer;

import com.flywithus.converter.FlightConverter;
import com.flywithus.converter.UserConverter;
import com.flywithus.dto.OrderTO;
import com.flywithus.entity.Order;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderMapping {

    @Bean
    public BeanMappingBuilder orderToToOrderEntityMappingBuilder() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(OrderTO.class, Order.class, TypeMappingOptions.oneWay())
                        .fields("flight", "flight", fmb -> fmb.customConverter(FlightConverter.class))
                .fields("user", "user", fmb -> fmb.customConverter(UserConverter.class));

            }
        };
    }

}
