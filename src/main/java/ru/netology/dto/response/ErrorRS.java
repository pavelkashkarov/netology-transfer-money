package ru.netology.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorRS {

    private String message;
    private Integer id;
}