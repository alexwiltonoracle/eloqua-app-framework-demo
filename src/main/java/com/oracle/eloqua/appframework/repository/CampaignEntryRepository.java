package com.oracle.eloqua.appframework.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.oracle.eloqua.appframework.entity.CampaignEntry;
import com.oracle.eloqua.appframework.enums.RecordStatus;

public interface CampaignEntryRepository extends CrudRepository<CampaignEntry, Long> {

	List<CampaignEntry> findByInstanceIdAndStatus(String instanceId, RecordStatus status);
}