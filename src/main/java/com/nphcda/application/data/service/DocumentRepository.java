package com.nphcda.application.data.service;

import com.nphcda.application.data.entity.Document;
import com.nphcda.application.data.entity.EmployeeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
