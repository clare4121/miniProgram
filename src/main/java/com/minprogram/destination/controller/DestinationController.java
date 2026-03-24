package com.minprogram.destination.controller;

import com.minprogram.common.api.ApiResponse;
import com.minprogram.destination.entity.Destination;
import com.minprogram.destination.repository.DestinationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {
    private final DestinationRepository destinationRepository;

    public DestinationController(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @GetMapping
    public ApiResponse<List<Destination>> list() {
        return ApiResponse.ok(destinationRepository.findByStatusOrderByIdDesc(1));
    }

    @GetMapping("/{id}")
    public ApiResponse<Destination> detail(@PathVariable("id") Long id) {
        Destination destination = destinationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("目的地不存在"));
        if (!Integer.valueOf(1).equals(destination.getStatus())) {
            throw new IllegalArgumentException("目的地不可见");
        }
        return ApiResponse.ok(destination);
    }
}
