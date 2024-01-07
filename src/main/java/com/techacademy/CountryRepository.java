package com.techacademy;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String>{
    // Countryはエンティティのことを指す、次にある「String」はエンティティクラスの主キーの型をさす
}
