package com.abdellah.inventoryservice.service;


import com.abdellah.inventoryservice.entity.Event;
import com.abdellah.inventoryservice.entity.Venue;
import com.abdellah.inventoryservice.repository.EventRepository;
import com.abdellah.inventoryservice.repository.VenueRepository;
import com.abdellah.inventoryservice.response.EventInventoryResponse;
import com.abdellah.inventoryservice.response.VenueInventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    @Autowired
    public InventoryService(final EventRepository eventRepository, final VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }


    public List<EventInventoryResponse> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().
                map(event -> EventInventoryResponse.builder().event(event.getName())
                        .venue(event.getVenue()).capacity(event.getTotalCapacity()).
                        ticketPrice(event.getTicketPrice()).build()).collect(Collectors.toList());
    }

    public VenueInventoryResponse getVenueInformation(Long venueId) {
        Venue venue = venueRepository.findById(venueId).orElse(null);
        return VenueInventoryResponse.builder()
                .venueId(venue.getId())
                .venueName(venue.getName())
                .totalCapacity(venue.getTotalCapacity())
                .build();
    }

    public EventInventoryResponse getEventByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        return EventInventoryResponse.builder()
                .event(event.getName())
                .venue(event.getVenue())
                .capacity(event.getTotalCapacity())
                .ticketPrice(event.getTicketPrice())
                .eventId(event.getId())
                .build();
    }

    public void updateEventCapacity(Long eventId, Long ticketsBooked) {
        final Event event = eventRepository.findById(eventId).orElse(null);
        if (event != null) {
            event.setTotalCapacity(event.getTotalCapacity() - ticketsBooked);
            eventRepository.saveAndFlush(event);
            log.info("Updated event capacity for event ID: {}, tickets booked: {}, new left capacity: {}",
                    eventId, ticketsBooked, event.getLeftCapacity());
        }

    }
}
