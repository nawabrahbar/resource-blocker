package com.aaen.resourceblocker.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.aaen.resourceblocker.model.Reservation;

@Component
public interface ReservationService {
	
	public HttpStatus createReservationRequest(Reservation reservation, String userId) throws IllegalArgumentException;
	
	public HttpStatus cancelReservationRequest(String id, String userId) throws IllegalArgumentException;
	
	public HttpStatus approvedOrRejectRequest(String id, String state, String userId) throws IllegalArgumentException;

	public HttpStatus returnReservationRequest(String id, String userId) throws IllegalArgumentException;

	public HttpStatus approvedReturnReservationRequest(String id, String userId) throws IllegalArgumentException;
	
	public List<Reservation> getAllReservationRequest();
	
	public List<Reservation> getPendingReservationRequest();
	
	public List<Reservation> getApprovedReservationRequest();

	public List<Reservation> getDeniedReservationRequest();

	public List<Reservation> getCancelledReservationRequest();

	public List<Reservation> getReturnReservationRequest();
}
