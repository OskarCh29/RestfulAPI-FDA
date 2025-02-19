package pl.fdaApi.restfulApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.fdaApi.restfulApi.model.enitity.DrugRecord;

@Repository
public interface DrugRecordRepository extends JpaRepository<DrugRecord,String> {
}
