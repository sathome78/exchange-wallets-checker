package com.example.demo.repository;

import com.example.demo.schedulers.fiatprocessor.nix.NixAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NixRepository extends JpaRepository<NixAccount, Long> {
}
