package com.minprogram.itinerary.repository;

import com.minprogram.itinerary.entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {
}
