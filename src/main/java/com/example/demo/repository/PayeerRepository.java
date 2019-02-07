package com.example.demo.repository;

import com.example.demo.schedulers.fiatprocessor.payeer.PayeerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayeerRepository extends JpaRepository<PayeerAccount, Integer> {
}
