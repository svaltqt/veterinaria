package com.app.veterinaria.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    private String name;
    private String type;
    private String breed;
    private int age;
    private List<MedicalHistory> MedicalHistory;


}
