package com.pokejava.pokejava.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseListDTO<T> {
    private boolean result;
    private String msg;
    private List<T> list;
}
