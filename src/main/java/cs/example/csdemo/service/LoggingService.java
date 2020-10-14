package cs.example.csdemo.service;

import java.util.Date;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs.example.csdemo.entity.LogEntity;
import cs.example.csdemo.repository.IlogRepository;
import cs.example.csdemo.utils.Constraint.LogConstraint;
import cs.example.csdemo.utils.Constraint.LogType;

@Service
public class LoggingService {
	@Autowired
	IlogRepository log;
	public void insertlog(String message , LogType logType ) {
		LogEntity logObj = new LogEntity(ThreadContext.get(LogConstraint.REQUEST_ID.value()), ThreadContext.get(LogConstraint.IP_KEY.value()), message, logType, new Date());
		log.save(logObj);
	}
}
