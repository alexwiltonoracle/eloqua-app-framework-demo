package com.oracle.eloqua.appframework.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.oracle.eloqua.appframework.entity.Instance;
import com.oracle.eloqua.appframework.enums.ServiceType;

public interface InstanceRepository extends CrudRepository<Instance, String> {

	List<Instance> findByServiceType(ServiceType serviceType);

}