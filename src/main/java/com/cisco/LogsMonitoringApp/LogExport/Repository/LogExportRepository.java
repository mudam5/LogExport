package com.cisco.LogsMonitoringApp.LogExport.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cisco.LogsMonitoringApp.LogExport.model.LogEntity;

public interface LogExportRepository extends JpaRepository<LogEntity, Long> {

	@Query("SELECT l.level, COUNT(l) FROM LogEntity l GROUP BY l.level")
	List<Object[]> countLogsByLevel();

	@Query("SELECT l FROM LogEntity l")
	List<LogEntity> getAllLogs();

}
