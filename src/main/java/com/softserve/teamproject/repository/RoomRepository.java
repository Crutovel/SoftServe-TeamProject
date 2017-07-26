package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface RoomRepository extends JpaRepository<Room, Integer>,
    QueryDslPredicateExecutor {

}
