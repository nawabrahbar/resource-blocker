package com.aaen.resourceblocker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.aaen.resourceblocker.model.Reservation;

public interface ReservationRepository extends MongoRepository<Reservation, String>, ReservationCustomRepository {

}
