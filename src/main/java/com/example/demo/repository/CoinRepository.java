package com.example.demo.repository;


import com.example.demo.domain.Coin;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface CoinRepository extends JpaRepository<Coin, Long> {

    Coin findByName(String name);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE COIN SET MIN_AMOUNT=:newLimit where NAME=:name")
    int updateMinLimit(@Param("newLimit") BigDecimal newLimit, @Param("name") String name);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE COIN SET MAX_AMOUNT=:newLimit where NAME=:name")
    int updateMaxLimit(@Param("newLimit") BigDecimal newLimit, @Param("name") String name);

    List<Coin> findByEnableTrue();

    List<Coin> findByEnableTrue(Sort name);

    @Query(value = " SELECT * FROM COIN WHERE NAME IN (:names)" , nativeQuery = true)
    List<Coin> findByNameInNames(@Param("names")List<String> names);

    List<Coin> findByMainFalse(Sort name);

    List<Coin> findByMainFalse();

    List<Coin> findAllByName(String name);

    @Modifying
    @Transactional
    int deleteByNameAndEthTokenContractAndCoinAddressAndMain(String name, String ethTokenContract, String coinAddress, boolean isMain);
}
