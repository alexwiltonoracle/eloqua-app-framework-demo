package com.oracle.eloqua.appframework.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.oracle.eloqua.appframework.entity.SMSActivity;
import com.oracle.eloqua.appframework.enums.RecordStatus;

public interface CampaignEntryRepository extends CrudRepository<SMSActivity, Long> {

	List<SMSActivity> findByInstanceIdAndStatus(String instanceId, RecordStatus status);
}