package pl.fda.restfulapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.fda.restfulapi.model.enitity.DrugRecord;

@Repository
public interface DrugRecordRepository extends JpaRepository<DrugRecord,String> {
}
