package com.nphcda.application.data.generator;

import com.nphcda.application.data.Role;
import com.nphcda.application.data.entity.EmployeeDetail;
import com.nphcda.application.data.entity.User;
import com.nphcda.application.data.service.SamplePersonRepository;
import com.nphcda.application.data.service.UserRepository;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(PasswordEncoder passwordEncoder, SamplePersonRepository samplePersonRepository,
            UserRepository userRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (samplePersonRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");
//
//            logger.info("... generating 100 Sample Person entities...");
//            ExampleDataGenerator<EmployeeDetail> samplePersonRepositoryGenerator = new ExampleDataGenerator<>(
//                    EmployeeDetail.class, LocalDateTime.of(2022, 3, 5, 0, 0, 0));
//            samplePersonRepositoryGenerator.setData(EmployeeDetail::setFirstName, DataType.FIRST_NAME);
//            samplePersonRepositoryGenerator.setData(EmployeeDetail::setLastName, DataType.LAST_NAME);
//            samplePersonRepositoryGenerator.setData(EmployeeDetail::setEmail, DataType.EMAIL);
//            samplePersonRepositoryGenerator.setData(EmployeeDetail::setPhone, DataType.PHONE_NUMBER);
//            samplePersonRepositoryGenerator.setData(EmployeeDetail::setDateOfBirth, DataType.DATE_OF_BIRTH);
//            samplePersonRepositoryGenerator.setData(EmployeeDetail::setDepartment, DataType.OCCUPATION);
//            samplePersonRepositoryGenerator.setData(EmployeeDetail::setImportant, DataType.BOOLEAN_10_90);
//            samplePersonRepository.saveAll(samplePersonRepositoryGenerator.create(100, seed));
//
//            logger.info("... generating 2 User entities...");
//            User user = new User();
//            user.setName("John Normal");
//            user.setUsername("user");
//            user.setHashedPassword(passwordEncoder.encode("user"));
//            user.setProfilePictureUrl(
//                    "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
//            user.setRoles(Collections.singleton(Role.USER));
//            userRepository.save(user);
//            User admin = new User();
//            admin.setName("Emma Powerful");
//            admin.setUsername("admin");
//            admin.setHashedPassword(passwordEncoder.encode("admin"));
//            admin.setProfilePictureUrl(
//                    "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
//            admin.setRoles(Stream.of(Role.USER, Role.ADMIN).collect(Collectors.toSet()));
//            userRepository.save(admin);

            logger.info("Generated demo data");
        };
    }

}