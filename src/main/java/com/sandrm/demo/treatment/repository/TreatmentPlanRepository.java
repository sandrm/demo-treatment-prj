package com.sandrm.demo.treatment.repository;

import com.sandrm.demo.treatment.model.TreatmentPlan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TreatmentPlanRepository extends CrudRepository<TreatmentPlan, Long> {

    @Query(value = """
            SELECT * FROM TREATMENT_PLAN tp
            WHERE :treatment_date BETWEEN tp.START_TIME and tp.END_TIME and tp.END_TIME is not null""",
            nativeQuery = true)
    List<TreatmentPlan> getPlanForDate(@Param("treatment_date") Date treatment_date);
}
