package com.mykafka.microservices.user_service.dto;

import lombok.Data;

@Data
public class CreateUserDto {
    private Long id;
    private String name;
    private String email;
}
