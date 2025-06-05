package com.example.wodozbior.repository;

import com.example.wodozbior.entity.River;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RiverRepository extends JpaRepository<River, Integer> {
    Optional<River> findByName(String name);

    Optional<River> findByNameIgnoreCase(String rzeka);
}
