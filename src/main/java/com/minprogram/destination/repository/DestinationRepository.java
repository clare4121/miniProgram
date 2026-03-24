package com.minprogram.destination.repository;

import com.minprogram.destination.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByStatusOrderByIdDesc(Integer status);
}
