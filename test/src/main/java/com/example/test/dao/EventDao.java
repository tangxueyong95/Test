package com.example.test.dao;

import com.example.test.entity.AEvent;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventDao {
    void insertEvent(AEvent aEvent);

    List<AEvent> findAEventList();
}
