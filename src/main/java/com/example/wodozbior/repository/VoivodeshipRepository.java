package com.example.wodozbior.repository;

import com.example.wodozbior.entity.Voivodeship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoivodeshipRepository extends JpaRepository<Voivodeship, Integer> {
    Optional<Voivodeship> findByName(String name);
}
