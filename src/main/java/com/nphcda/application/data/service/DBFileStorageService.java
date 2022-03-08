package com.nphcda.application.data.service;

import com.nphcda.application.data.entity.Document;
import com.nphcda.application.data.entity.EmployeeDetail;
import com.nphcda.application.exception.FileStorageException;
import com.nphcda.application.exception.MyFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DBFileStorageService {


    private DocumentRepository dbFileRepository;


    public DBFileStorageService(@Autowired DocumentRepository repository) {
        this.dbFileRepository = repository;
    }

    public void storeFile(Document doc) {

        try {

            dbFileRepository.save(doc);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public Optional<Document> getFile(Long id) {
        return dbFileRepository.findById(id);
    }


    public Page<Document> list(Pageable pageable) {
        return dbFileRepository.findAll(pageable);
    }
    public List<Document> listAll() {
        return dbFileRepository.findAll();
    }

}
