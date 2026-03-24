package com.minprogram.itinerary.service;

import com.minprogram.itinerary.dto.ItineraryDetailResponse;
import com.minprogram.itinerary.entity.Itinerary;
import com.minprogram.itinerary.entity.ItineraryItem;
import com.minprogram.itinerary.repository.ItineraryItemRepository;
import com.minprogram.itinerary.repository.ItineraryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItineraryQueryService {
    private final ItineraryRepository itineraryRepository;
    private final ItineraryItemRepository itineraryItemRepository;

    public ItineraryQueryService(ItineraryRepository itineraryRepository, ItineraryItemRepository itineraryItemRepository) {
        this.itineraryRepository = itineraryRepository;
        this.itineraryItemRepository = itineraryItemRepository;
    }

    public ItineraryDetailResponse getDetail(Long userId, Long itineraryId) {
        Itinerary itinerary = itineraryRepository.findById(itineraryId)
                .orElseThrow(() -> new IllegalArgumentException("行程不存在"));
        if (!itinerary.getUserId().equals(userId)) {
            throw new IllegalArgumentException("无权访问该行程");
        }
        List<ItineraryItem> items = itineraryItemRepository.findByItineraryIdOrderByDayNoAscSortNoAsc(itineraryId);
        return map(itinerary, items);
    }

    private ItineraryDetailResponse map(Itinerary itinerary, List<ItineraryItem> items) {
        ItineraryDetailResponse resp = new ItineraryDetailResponse();
        resp.setId(itinerary.getId());
        resp.setTitle(itinerary.getTitle());
        resp.setDestinationId(itinerary.getDestinationId());
        resp.setDays(itinerary.getDays());
        resp.setTravelMode(itinerary.getTravelMode());
        resp.setPreferenceJson(itinerary.getPreferenceJson());
        resp.setIsPublic(itinerary.getIsPublic());
        for (ItineraryItem item : items) {
            ItineraryDetailResponse.Item out = new ItineraryDetailResponse.Item();
            out.setId(item.getId());
            out.setDayNo(item.getDayNo());
            out.setSortNo(item.getSortNo());
            out.setAttractionId(item.getAttractionId());
            out.setStartTime(item.getStartTime());
            out.setEndTime(item.getEndTime());
            out.setTitle(item.getTitle());
            out.setNote(item.getNote());
            out.setTransportTip(item.getTransportTip());
            resp.getItems().add(out);
        }
        return resp;
    }
}
