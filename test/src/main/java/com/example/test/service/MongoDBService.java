package com.example.test.service;

import com.example.test.entity.AEvent;
import com.example.test.entity.AFault;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public interface MongoDBService {

    void importAEvent();

    void importAFault();

    List<AEvent> findAEventAll();

    <T> List<T> findT(String key,String value,Class<T> tClass);

    void deleteAEventAll();

    void deleteAFaultAll();

    List<AFault> findAFaultAll();

    AEvent findAEventOne(String key, String value);

    PageImpl<AEvent> findAEvent(AEvent aEvent,int page, int size);

    PageImpl<AFault> findAFault(AFault aFault,int page, int size);

    void updateAEvent(AEvent aEvent);

    void updateAFault(AFault aFault);

    PageImpl<AEvent>  pageAEventList(int page, int size);

    PageImpl<AFault>  pageAFaultList(int page, int size);

    List<AEvent> getAEvent();

    void deleteAEventById(Integer eventid);

    void deleteAFaultById(Integer faultid);
}
