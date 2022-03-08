package com.nphcda.application.data.service;


import com.nphcda.application.data.entity.Document;
import com.nphcda.application.data.entity.PaySlipRequest;
import com.nphcda.application.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaySlipRequestService {

    private PayslipRequestRepo repository;

    public PaySlipRequestService(@Autowired PayslipRequestRepo repository) {
        this.repository = repository;
    }

    public Optional<PaySlipRequest> get(long id) {
        return repository.findById(id);
    }

    public PaySlipRequest update(PaySlipRequest entity) {
        return repository.save(entity);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public Page<PaySlipRequest> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public List<PaySlipRequest> listAll() {
        return repository.findAll();
    }
}
