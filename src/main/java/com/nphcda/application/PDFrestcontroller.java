package com.nphcda.application;

import com.nphcda.application.data.entity.Document;
import com.nphcda.application.data.service.DBFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Optional;

@RestController
public class PDFrestcontroller {
    @Autowired
    ServletContext context;

    @Autowired
    DBFileStorageService fileStorageService;

    @RequestMapping(value = "/pdf/{fileid}.pdf", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> download(@PathVariable("fileid") long fileName) throws IOException {
        System.out.println("Calling Download:- " + fileName);


       Optional<Document> filss= fileStorageService.getFile(fileName);


        ClassPathResource pdfFile = null;
        OutputStream out = null;
        try {
           pdfFile = new ClassPathResource("/downloads/out.pdf");

            out = new FileOutputStream(pdfFile.getFile());
            out.write(filss.get().getData());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
        headers.add("Content-Disposition", "filename=" + fileName);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        headers.setContentLength(pdfFile.contentLength());
        ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
                new InputStreamResource(pdfFile.getInputStream()), headers, HttpStatus.OK);
        return response;

    }

}
