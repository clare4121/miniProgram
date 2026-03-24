package com.minprogram.itinerary.controller;

import com.minprogram.common.api.ApiResponse;
import com.minprogram.common.security.UserContext;
import com.minprogram.itinerary.dto.ItineraryDetailResponse;
import com.minprogram.itinerary.dto.ItineraryGenerateRequest;
import com.minprogram.itinerary.dto.ItineraryGenerateResponse;
import com.minprogram.itinerary.dto.SaveItineraryRequest;
import com.minprogram.itinerary.dto.SaveItineraryResponse;
import com.minprogram.itinerary.service.ItineraryGenerateService;
import com.minprogram.itinerary.service.ItineraryQueryService;
import com.minprogram.itinerary.service.ItineraryWriteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/itineraries")
public class ItineraryController {

    private final ItineraryGenerateService itineraryGenerateService;
    private final ItineraryQueryService itineraryQueryService;
    private final ItineraryWriteService itineraryWriteService;

    public ItineraryController(ItineraryGenerateService itineraryGenerateService,
                               ItineraryQueryService itineraryQueryService,
                               ItineraryWriteService itineraryWriteService) {
        this.itineraryGenerateService = itineraryGenerateService;
        this.itineraryQueryService = itineraryQueryService;
        this.itineraryWriteService = itineraryWriteService;
    }

    @PostMapping("/generate")
    public ApiResponse<ItineraryGenerateResponse> generate(@Valid @RequestBody ItineraryGenerateRequest req) {
        return ApiResponse.ok(itineraryGenerateService.generate(req));
    }

    @PostMapping
    public ApiResponse<SaveItineraryResponse> save(@Valid @RequestBody SaveItineraryRequest req) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return ApiResponse.fail("未登录");
        }
        return ApiResponse.ok(itineraryWriteService.save(userId, req));
    }

    @GetMapping("/{id}")
    public ApiResponse<ItineraryDetailResponse> detail(@PathVariable("id") Long id) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return ApiResponse.fail("未登录");
        }
        return ApiResponse.ok(itineraryQueryService.getDetail(userId, id));
    }

    @PutMapping("/{id}")
    public ApiResponse<SaveItineraryResponse> update(@PathVariable("id") Long id,
                                                     @Valid @RequestBody SaveItineraryRequest req) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return ApiResponse.fail("未登录");
        }
        return ApiResponse.ok(itineraryWriteService.update(userId, id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Object> delete(@PathVariable("id") Long id) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return ApiResponse.fail("未登录");
        }
        itineraryWriteService.delete(userId, id);
        return ApiResponse.ok(null);
    }
}
