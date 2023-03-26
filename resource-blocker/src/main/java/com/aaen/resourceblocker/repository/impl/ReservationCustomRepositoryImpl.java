package com.aaen.resourceblocker.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.aaen.resourceblocker.model.Reservation;
import com.aaen.resourceblocker.repository.ReservationCustomRepository;
import com.aaen.resourceblocker.util.Constant;

@Component
public class ReservationCustomRepositoryImpl implements ReservationCustomRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Reservation findByIdWithStateEqualToPending(String id) {

		return mongoTemplate.findOne(
				new Query().addCriteria(Criteria.where(Constant._ID).is(id).and(Constant.STATE).is(Constant.PENDING)),
				Reservation.class);
	}

	@Override
	public Reservation findByIdAndUserIdWithStateEqualToPending(String id, String userId) {

		return mongoTemplate.findOne(new Query().addCriteria(Criteria.where(Constant._ID).is(id)
				.and(Constant.REQUEST_BY).is(userId).and(Constant.STATE).is(Constant.PENDING).and(Constant.IS_CANCELLED)
				.isNull().and(Constant.CANCELLED_DATE_TIME).isNull()), Reservation.class);
	}

	@Override
	public Reservation findByIdAndUserIdWithStateEqualToApproved(String id, String userId) {

		return mongoTemplate.findOne(new Query().addCriteria(Criteria.where(Constant._ID).is(id)
				.and(Constant.REQUEST_BY).is(userId).and(Constant.STATE).is(Constant.APPROVED)), Reservation.class);
	}

	@Override
	public Reservation findByIdWithStateEqualToApprovedAndReturnRequestDateTimeExists(String id) {

		return mongoTemplate.findOne(
				new Query().addCriteria(Criteria.where(Constant._ID).is(id).and(Constant.STATE).is(Constant.APPROVED)
						.and(Constant.IS_RETURN).is(Boolean.TRUE).and(Constant.RETURN_REQUEST_DATE_TIME).exists(true)),
				Reservation.class);
	}

	@Override
	public List<Reservation> findByPendingReservationRequest() {

		return mongoTemplate.find(
				new Query().addCriteria(
						Criteria.where(Constant.STATE).is(Constant.PENDING)),
				Reservation.class);
	}

	@Override
	public List<Reservation> findByApprovedReservationRequest() {

		return mongoTemplate.find(
				new Query().addCriteria(
						Criteria.where(Constant.STATE).is(Constant.APPROVED)),
				Reservation.class);
	}

	@Override
	public List<Reservation> findByDeniedReservationRequest() {

		return mongoTemplate.find(
				new Query().addCriteria(
						Criteria.where(Constant.STATE).is(Constant.DENIED)),
				Reservation.class);
	}

	@Override
	public List<Reservation> findByCancelledReservationRequest() {

		return mongoTemplate.find(
				new Query().addCriteria(
						Criteria.where(Constant.STATE).is(Constant.CANCELLED)),
				Reservation.class);
	}

	@Override
	public List<Reservation> findByReturnReservationRequest() {

		return mongoTemplate.find(
				new Query().addCriteria(
						Criteria.where(Constant.STATE).is(Constant.RETURNED)),
				Reservation.class);
	}

}
