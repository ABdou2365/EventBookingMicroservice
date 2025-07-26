package com.abdellah.inventoryservice.controller;




import com.abdellah.inventoryservice.response.EventInventoryResponse;
import com.abdellah.inventoryservice.response.VenueInventoryResponse;
import com.abdellah.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(final InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/inventory/events")
    public @ResponseBody List<EventInventoryResponse> inventoryGetAllEvents() {
        return inventoryService.getAllEvents();
    }

    @GetMapping("/inventory/venue/{venueId}")
    public @ResponseBody VenueInventoryResponse inventoryByVenueId(@PathVariable("venueId") Long venueId) {
        return inventoryService.getVenueInformation(venueId);
    }

    @GetMapping("/inventory/events/{eventId}")
    public @ResponseBody EventInventoryResponse inventoryByEventId(@PathVariable("eventId") Long eventId) {
        return inventoryService.getEventByEventId(eventId);
    }

    @PutMapping("/inventory/events/{eventId}/book/{quantity}")
    public @ResponseBody ResponseEntity<Void> updateEventCapacity(@PathVariable("eventId") Long eventId,
                                                    @PathVariable("quantity") Long ticketsBooked) {
        inventoryService.updateEventCapacity(eventId, ticketsBooked);
        return ResponseEntity.ok().build();
    }
}

