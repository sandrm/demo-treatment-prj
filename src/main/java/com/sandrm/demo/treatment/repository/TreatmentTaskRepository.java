package com.sandrm.demo.treatment.repository;

import com.sandrm.demo.treatment.model.TreatmentTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentTaskRepository extends CrudRepository<TreatmentTask, Long> {
}
