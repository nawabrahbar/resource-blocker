package com.aaen.resourceblocker.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.aaen.resourceblocker.model.Reservation;
import com.aaen.resourceblocker.model.Resource;
import com.aaen.resourceblocker.repository.ReservationRepository;
import com.aaen.resourceblocker.service.ReservationService;
import com.aaen.resourceblocker.service.ResourceService;
import com.aaen.resourceblocker.util.Constant;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationRepository repository;

	@Autowired
	private ResourceService resourceService;

	@Override
	public HttpStatus createReservationRequest(Reservation reservation, String userId) throws IllegalArgumentException {

		try {

			reservation = Reservation.builder().id(UUID.randomUUID().toString()).resourceId(reservation.getResourceId())
					.resourceQuantiny(reservation.getResourceQuantiny()).requestBy(userId).state(Constant.PENDING)
					.build();

			repository.save(reservation);

			return HttpStatus.CREATED;

		} catch (IllegalArgumentException e) {

			throw new IllegalArgumentException();
		}
	}

	@Override
	public HttpStatus cancelReservationRequest(String id, String userId) throws IllegalArgumentException {

		try {

			Reservation response = repository.findByIdAndUserIdWithStateEqualToPending(id, userId);

			if (Objects.nonNull(response)) {

				response.setIsCancelled(Boolean.TRUE);
				response.setCancelDateTime(LocalDateTime.now());
				response.setState(Constant.CANCELLED);

				repository.save(response);

				return HttpStatus.NO_CONTENT;
			}

			return HttpStatus.NOT_FOUND;

		} catch (IllegalArgumentException e) {

			throw new IllegalArgumentException();
		}
	}

	@Override
	public HttpStatus approvedOrRejectRequest(String id, String state, String userId) throws IllegalArgumentException {

		try {

			Reservation response = repository.findByIdWithStateEqualToPending(id);

			if (Objects.nonNull(response) && state.equalsIgnoreCase(Constant.APPROVED)) {

				response.setAprovedDateTime(LocalDateTime.now());
				response.setApprovedBy(userId);
				response.setState(state.toLowerCase());

				Resource resource = resourceService.getById(response.getResourceId());

				resource.setNumberOfResource(resource.getNumberOfResource() - response.getResourceQuantiny());

				resourceService.update(resource);

				repository.save(response);

			} else if (Objects.nonNull(response) && state.equalsIgnoreCase(Constant.DENIED)) {

				response.setDeniedDateTime(LocalDateTime.now());
				response.setDeniedBy(userId);
				response.setState(state.toLowerCase());

				repository.save(response);
			}

			return HttpStatus.NO_CONTENT;

		} catch (IllegalArgumentException e) {

			throw new IllegalArgumentException();
		}
	}

	@Override
	public HttpStatus returnReservationRequest(String id, String userId) throws IllegalArgumentException {

		try {

			Reservation response = repository.findByIdAndUserIdWithStateEqualToApproved(id, userId);

			if (Objects.nonNull(response)) {

				response.setReturnRequestDateTime(LocalDateTime.now());
				response.setIsReturn(Boolean.TRUE);

				repository.save(response);

				return HttpStatus.NO_CONTENT;
			}

			return HttpStatus.NOT_FOUND;

		} catch (IllegalArgumentException e) {

			throw new IllegalArgumentException();
		}
	}

	@Override
	public HttpStatus approvedReturnReservationRequest(String id, String userId) throws IllegalArgumentException {

		try {

			Reservation response = repository
					.findByIdWithStateEqualToApprovedAndReturnRequestDateTimeExists(id);

			if (Objects.nonNull(response)) {

				response.setReturnApprovedBy(userId);
				response.setReturnRequestApprovedDateTime(LocalDateTime.now());
				response.setState(Constant.RETURNED);
				
				Resource resource = resourceService.getById(response.getResourceId());

				resource.setNumberOfResource(resource.getNumberOfResource() + response.getResourceQuantiny());

				resourceService.update(resource);

				repository.save(response);

				return HttpStatus.NO_CONTENT;
			}

			return HttpStatus.NOT_FOUND;

		} catch (IllegalArgumentException e) {

			throw new IllegalArgumentException();
		}
	}

	@Override
	public List<Reservation> getAllReservationRequest() {

		return repository.findAll();
	}

	@Override
	public List<Reservation> getPendingReservationRequest() {

		return repository.findByPendingReservationRequest();
	}

	@Override
	public List<Reservation> getApprovedReservationRequest() {

		return repository.findByApprovedReservationRequest();
	}

	@Override
	public List<Reservation> getDeniedReservationRequest() {

		return repository.findByDeniedReservationRequest();
	}

	@Override
	public List<Reservation> getCancelledReservationRequest() {

		return repository.findByCancelledReservationRequest();
	}

	@Override
	public List<Reservation> getReturnReservationRequest() {

		return repository.findByReturnReservationRequest();
	}
}
