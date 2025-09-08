package com.cisco.LogsMonitoringApp.LogExport.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.cisco.LogsMonitoringApp.LogExport.Repository.LogExportRepository;
import com.cisco.LogsMonitoringApp.LogExport.model.LogEntity;

@Service
public class LogExportService {
	LogExportRepository logExportRepository;
	public LogExportService(LogExportRepository logExportRepository) {
		this.logExportRepository=logExportRepository;
	}
	public List<LogEntity> getAllLogs() {
        return logExportRepository.findAll();
    }

	public Map<String, Long> getLogCountsByLevel() {
        List<Object[]> results = logExportRepository.countLogsByLevel();
        Map<String, Long> counts = new HashMap<>();
        for (Object[] row : results) {
            counts.put((String) row[0], (Long) row[1]);
        }
        return counts;
    }
	
	public List<Map<String, Object>> getLogCountsPer5Minutes() {
	    List<LogEntity> logs = logExportRepository.getAllLogs();

	    Map<LocalDateTime, Map<String, Long>> grouped = new TreeMap();

	    for (LogEntity log : logs) {
	        LocalDateTime ts = log.getTimestamp();

	        int minute = (ts.getMinute() / 5) * 5;
	        LocalDateTime bucket = ts.withMinute(minute).withSecond(0).withNano(0);

	        grouped.putIfAbsent(bucket, new HashMap<>());
	        Map<String, Long> levelCounts = grouped.get(bucket);

	        levelCounts.put(log.getLevel(),
	            levelCounts.getOrDefault(log.getLevel(), 0L) + 1);
	    }

	    List<Map<String, Object>> output = new ArrayList<>();
	    for (var entry : grouped.entrySet()) {
	        Map<String, Object> map = new LinkedHashMap<>();
	        map.put("timestamp", entry.getKey().toString());
	        map.put("ERROR", entry.getValue().getOrDefault("ERROR", 0L));
	        map.put("WARN", entry.getValue().getOrDefault("WARN", 0L));
	        map.put("INFO", entry.getValue().getOrDefault("INFO", 0L));
	        map.put("DEBUG", entry.getValue().getOrDefault("DEBUG", 0L));
	        output.add(map);
	    }
	    return output;
	}

}
