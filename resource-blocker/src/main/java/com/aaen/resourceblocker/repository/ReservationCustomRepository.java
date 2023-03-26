package com.aaen.resourceblocker.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.aaen.resourceblocker.model.Reservation;

@Component
public interface ReservationCustomRepository {

	public Reservation findByIdWithStateEqualToPending(String id);

	public Reservation findByIdAndUserIdWithStateEqualToPending(String id, String userId);

	public Reservation findByIdAndUserIdWithStateEqualToApproved(String id, String userId);

	public Reservation findByIdWithStateEqualToApprovedAndReturnRequestDateTimeExists(String id);
	
	public List<Reservation> findByPendingReservationRequest();
	
	public List<Reservation> findByApprovedReservationRequest();

	public List<Reservation> findByDeniedReservationRequest();

	public List<Reservation> findByCancelledReservationRequest();

	public List<Reservation> findByReturnReservationRequest();
}
