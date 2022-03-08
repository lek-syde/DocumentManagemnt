package com.nphcda.application.data.service;

import com.nphcda.application.data.entity.Document;
import com.nphcda.application.data.entity.PaySlipRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayslipRequestRepo extends JpaRepository<PaySlipRequest, Long> {
}
