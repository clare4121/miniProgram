package com.minprogram.itinerary.repository;

import com.minprogram.itinerary.entity.ItineraryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItineraryItemRepository extends JpaRepository<ItineraryItem, Long> {
    List<ItineraryItem> findByItineraryIdOrderByDayNoAscSortNoAsc(Long itineraryId);

    @Transactional
    void deleteByItineraryId(Long itineraryId);
}
