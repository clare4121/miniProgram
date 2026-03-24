package com.minprogram.itinerary.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minprogram.itinerary.dto.SaveItineraryRequest;
import com.minprogram.itinerary.dto.SaveItineraryResponse;
import com.minprogram.itinerary.entity.Itinerary;
import com.minprogram.itinerary.entity.ItineraryItem;
import com.minprogram.itinerary.repository.ItineraryItemRepository;
import com.minprogram.itinerary.repository.ItineraryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItineraryWriteService {

    private final ItineraryRepository itineraryRepository;
    private final ItineraryItemRepository itineraryItemRepository;
    private final ObjectMapper objectMapper;

    public ItineraryWriteService(ItineraryRepository itineraryRepository,
                                 ItineraryItemRepository itineraryItemRepository,
                                 ObjectMapper objectMapper) {
        this.itineraryRepository = itineraryRepository;
        this.itineraryItemRepository = itineraryItemRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public SaveItineraryResponse save(Long userId, SaveItineraryRequest req) {
        Itinerary itinerary = new Itinerary();
        itinerary.setUserId(userId);
        itinerary.setTitle(req.getTitle());
        itinerary.setDestinationId(req.getDestinationId());
        itinerary.setDays(req.getDays());
        itinerary.setTravelMode(req.getTravelMode());
        itinerary.setIsPublic(req.getIsPublic() == null ? 0 : req.getIsPublic());
        itinerary.setPreferenceJson(toJson(req.getPreferences()));
        itinerary = itineraryRepository.save(itinerary);

        if (req.getItems() != null) {
            for (SaveItineraryRequest.Item input : req.getItems()) {
                ItineraryItem item = new ItineraryItem();
                item.setItineraryId(itinerary.getId());
                item.setDayNo(input.getDayNo());
                item.setSortNo(input.getSortNo());
                item.setAttractionId(input.getAttractionId());
                item.setStartTime(input.getStartTime());
                item.setEndTime(input.getEndTime());
                item.setTitle(input.getTitle());
                item.setNote(input.getNote());
                item.setTransportTip(input.getTransportTip());
                itineraryItemRepository.save(item);
            }
        }
        return new SaveItineraryResponse(itinerary.getId());
    }

    @Transactional
    public SaveItineraryResponse update(Long userId, Long itineraryId, SaveItineraryRequest req) {
        Itinerary itinerary = itineraryRepository.findById(itineraryId)
                .orElseThrow(() -> new IllegalArgumentException("行程不存在"));
        if (!itinerary.getUserId().equals(userId)) {
            throw new IllegalArgumentException("无权编辑该行程");
        }
        itinerary.setTitle(req.getTitle());
        itinerary.setDestinationId(req.getDestinationId());
        itinerary.setDays(req.getDays());
        itinerary.setTravelMode(req.getTravelMode());
        itinerary.setIsPublic(req.getIsPublic() == null ? 0 : req.getIsPublic());
        itinerary.setPreferenceJson(toJson(req.getPreferences()));
        itineraryRepository.save(itinerary);

        itineraryItemRepository.deleteByItineraryId(itineraryId);
        if (req.getItems() != null) {
            for (SaveItineraryRequest.Item input : req.getItems()) {
                ItineraryItem item = new ItineraryItem();
                item.setItineraryId(itineraryId);
                item.setDayNo(input.getDayNo());
                item.setSortNo(input.getSortNo());
                item.setAttractionId(input.getAttractionId());
                item.setStartTime(input.getStartTime());
                item.setEndTime(input.getEndTime());
                item.setTitle(input.getTitle());
                item.setNote(input.getNote());
                item.setTransportTip(input.getTransportTip());
                itineraryItemRepository.save(item);
            }
        }
        return new SaveItineraryResponse(itineraryId);
    }

    @Transactional
    public void delete(Long userId, Long itineraryId) {
        Itinerary itinerary = itineraryRepository.findById(itineraryId)
                .orElseThrow(() -> new IllegalArgumentException("行程不存在"));
        if (!itinerary.getUserId().equals(userId)) {
            throw new IllegalArgumentException("无权删除该行程");
        }
        itineraryItemRepository.deleteByItineraryId(itineraryId);
        itineraryRepository.deleteById(itineraryId);
    }

    private String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("preferences序列化失败");
        }
    }
}
