package com.techacademy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity // エンティティクラスであることを示す
@Table(name="country")
public class Country {
    @Id
    private String code; // @Idにより、codeが主キーとなる
    private String name;
    private int population;
}
