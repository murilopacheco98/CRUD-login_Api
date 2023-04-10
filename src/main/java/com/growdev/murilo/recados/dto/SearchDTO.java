package com.growdev.murilo.recados.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SearchDTO {
    private String search;
    private String status;
}
