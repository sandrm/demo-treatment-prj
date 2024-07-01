package org.sandrm.demo.treatment.repository;

import org.sandrm.demo.treatment.model.TreatmentTask;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TreatmentTaskRepository extends CrudRepository<TreatmentTask, Long> {
    @Transactional
    @Modifying
    @Query(value = """
            DELETE FROM TREATMENT_TASK tt
            WHERE FORMATDATETIME(tt.START_TIME, 'yyyy-MM-dd') = :treatment_date""",
            nativeQuery = true)
    void deleteForDate(@Param("treatment_date") String treatment_date);
}
