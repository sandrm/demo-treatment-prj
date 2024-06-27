package com.sandrm.demo.treatment.repository;

import com.sandrm.demo.treatment.model.TreatmentPlan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentPlanRepository extends CrudRepository<TreatmentPlan, Long> {
}
