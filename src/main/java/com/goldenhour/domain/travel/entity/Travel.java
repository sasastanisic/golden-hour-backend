package com.goldenhour.domain.travel.entity;

import com.goldenhour.domain.destination.entity.Destination;
import com.goldenhour.domain.travel.enums.TransportType;
import com.goldenhour.domain.travel.enums.TravelDuration;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "travel")
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate departureDay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TravelDuration travelDuration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransportType transportType;

    @Column(nullable = false)
    private int numberOfNights;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;

    public Travel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDepartureDay() {
        return departureDay;
    }

    public void setDepartureDay(LocalDate departureDay) {
        this.departureDay = departureDay;
    }

    public TravelDuration getTravelDuration() {
        return travelDuration;
    }

    public void setTravelDuration(TravelDuration travelDuration) {
        this.travelDuration = travelDuration;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

}
