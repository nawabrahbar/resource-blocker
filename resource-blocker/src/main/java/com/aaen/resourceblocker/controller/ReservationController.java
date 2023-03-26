package com.aaen.resourceblocker.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aaen.resourceblocker.model.Reservation;
import com.aaen.resourceblocker.security.JwtUtil;
import com.aaen.resourceblocker.service.ReservationService;
import com.aaen.resourceblocker.util.Constant;
import com.aaen.resourceblocker.util.ExcelGenerator;

@RestController
@RequestMapping(path = { "/api/v1/reservation" })
public class ReservationController {

	@Autowired
	private ReservationService service;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@PostMapping(path = "/request")
	@PreAuthorize("hasAuthority('reservation.request')")
	public ResponseEntity<?> createReservationRequest(@RequestBody Reservation reservation,
			@RequestHeader(name = "Authorization") String token) {

		try {

			return ResponseEntity
					.status(service.createReservationRequest(reservation, jwtTokenUtil.extractId(token.substring(7))))
					.body(Constant.CREATED);

		} catch (IllegalArgumentException e) {

			throw new IllegalArgumentException();
		}
	}

	@PutMapping(path = "/cancel")
	@PreAuthorize("hasAuthority('reservation.cancel.request')")
	public ResponseEntity<?> cancelReservationRequest(@RequestParam(value = "id") String id,
			@RequestHeader(name = "Authorization") String token) {

		try {

			return ResponseEntity
					.status(service.cancelReservationRequest(id, jwtTokenUtil.extractId(token.substring(7)))).build();

		} catch (IllegalArgumentException e) {

			throw new IllegalArgumentException();
		}
	}

	@PutMapping(path = "/action")
	@PreAuthorize("hasAuthority('reservation.approve.and.reject')")
	public ResponseEntity<?> approvedOrRejectRequest(@RequestParam(value = "id") String id,
			@RequestParam(value = "state") String state, @RequestHeader(name = "Authorization") String token) {

		try {

			return ResponseEntity
					.status(service.approvedOrRejectRequest(id, state, jwtTokenUtil.extractId(token.substring(7))))
					.build();

		} catch (IllegalArgumentException e) {

			throw new IllegalArgumentException();
		}
	}

	@PutMapping(path = "/return")
	@PreAuthorize("hasAuthority('reservation.return.request')")
	public ResponseEntity<?> returnReservationRequest(@RequestParam(value = "id") String id,
			@RequestHeader(name = "Authorization") String token) {

		try {

			return ResponseEntity
					.status(service.returnReservationRequest(id, jwtTokenUtil.extractId(token.substring(7)))).build();

		} catch (IllegalArgumentException e) {

			throw new IllegalArgumentException();
		}
	}

	@PutMapping(path = "/return/approved")
	@PreAuthorize("hasAuthority('reservation.return.request.approved')")
	public ResponseEntity<?> approvedReturnReservationRequest(@RequestParam(value = "id") String id,
			@RequestHeader(name = "Authorization") String token) {

		try {

			return ResponseEntity
					.status(service.approvedReturnReservationRequest(id, jwtTokenUtil.extractId(token.substring(7))))
					.build();

		} catch (IllegalArgumentException e) {

			throw new IllegalArgumentException();
		}
	}

	@GetMapping
	@PreAuthorize("hasAuthority('reservation.get.all')")
	public ResponseEntity<?> getAll() {

		return ResponseEntity.ok(service.getAllReservationRequest());
	}
	
	@GetMapping(path = "/export-excel")
	@PreAuthorize("hasAuthority('resevation.export.excel')")
	public ResponseEntity<?> getAllReservationRequestAsExcel() throws IOException {

		try {
			
			List<Reservation> reservations = service.getAllReservationRequest();
						
			ByteArrayInputStream in = ExcelGenerator.customersToExcel(reservations);
			
			HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "attachment; filename=reservation.xlsx");
			
			 return ResponseEntity
		                .ok()
		                .headers(headers)
		                .body(new InputStreamResource(in));
			
		} catch (IOException e) {

			throw new IOException();
		}		
	}
	
	@GetMapping(path = "/pending")
	@PreAuthorize("hasAuthority('reservation.get.pending')")
	public ResponseEntity<?> getPendingReservationRequest() {

		return ResponseEntity.ok(service.getPendingReservationRequest());
	}

	@GetMapping(path = "/approved")
	@PreAuthorize("hasAuthority('reservation.get.approved')")
	public ResponseEntity<?> getApprovedReservationRequest() {

		return ResponseEntity.ok(service.getApprovedReservationRequest());
	}

	@GetMapping(path = "/denied")
	@PreAuthorize("hasAuthority('reservation.get.denied')")
	public ResponseEntity<?> getDeniedReservationRequest() {

		return ResponseEntity.ok(service.getDeniedReservationRequest());
	}

	@GetMapping(path = "/cancelled")
	@PreAuthorize("hasAuthority('reservation.get.cancelled')")
	public ResponseEntity<?> getCancelledReservationRequest() {

		return ResponseEntity.ok(service.getCancelledReservationRequest());
	}

	@GetMapping(path = "/return")
	@PreAuthorize("hasAuthority('reservation.get.return')")
	public ResponseEntity<?> getReturnReservationRequest() {

		return ResponseEntity.ok(service.getReturnReservationRequest());
	}

}
