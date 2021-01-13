package com.example.test.dao;

import com.example.test.entity.AFault;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaultDao {
    void insertFault(AFault aFault);

    List<AFault> findAFaultList();
}
