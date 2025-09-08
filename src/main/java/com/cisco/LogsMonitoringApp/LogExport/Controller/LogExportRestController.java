package com.cisco.LogsMonitoringApp.LogExport.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.LogsMonitoringApp.LogExport.model.LogEntity;
import com.cisco.LogsMonitoringApp.LogExport.service.LogExportService;

@RestController
@RequestMapping("/logs")
public class LogExportRestController {

	@Autowired
	LogExportService logExportService;
	
	@GetMapping
    public List<LogEntity> getAllLogs() {
        return logExportService.getAllLogs();
    }
	 
	@GetMapping("/count")
	public Map<String, Long> countLogsByLevel(){
		return logExportService.getLogCountsByLevel();
	}
	
	@GetMapping("/linegraph")
	public List<Map<String, Object>> getLogCountsPer5Minutes(){
		return logExportService.getLogCountsPer5Minutes();
	}
}
