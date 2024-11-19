package com.eoi.spotyfav.seguridad;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class RestError {
    private int status;
    private String error; 
}
