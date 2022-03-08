package com.nphcda.application.data.service;

import com.nphcda.application.data.entity.EmployeeDetail;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SamplePersonRepository extends JpaRepository<EmployeeDetail, Long> {

}