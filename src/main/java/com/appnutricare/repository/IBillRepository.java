package com.appnutricare.repository;

import com.appnutricare.entities.Bill;
import com.appnutricare.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IBillRepository extends JpaRepository<Bill, Integer> {
    @Query("Select b from Bill b where b.client.id = :client_id")
    public List<Bill> findAllByClient(@Param("client_id") Integer client_id);

    @Query("Select b from Bill b where b.billDate BETWEEN :date1 AND :date2")
    public List<Bill> findBetweenDates(@Param("date1") Date date1,
                                         @Param("date2") Date date2);
}
