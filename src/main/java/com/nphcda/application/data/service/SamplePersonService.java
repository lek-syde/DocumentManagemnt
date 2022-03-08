package com.nphcda.application.data.service;

import com.nphcda.application.data.entity.EmployeeDetail;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SamplePersonService {

    private SamplePersonRepository repository;

    public SamplePersonService(@Autowired SamplePersonRepository repository) {
        this.repository = repository;
    }

    public Optional<EmployeeDetail> get(Long id) {
        return repository.findById(id);
    }

    public EmployeeDetail update(EmployeeDetail entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<EmployeeDetail> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
