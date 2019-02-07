package com.example.demo.repository;

import com.example.demo.schedulers.fiatprocessor.perfectmoney.PerfectmoneyAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfectmoneyRepository extends JpaRepository<PerfectmoneyAccount, Long> {
}
