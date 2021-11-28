package me.djamelkorei.springbootjpadatatable.service;

import lombok.RequiredArgsConstructor;
import me.djamelkorei.springbootjpadatatable.domain.User;
import me.djamelkorei.springbootjpadatatable.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

/**
 * Service for managing {@link User}.
 *
 * @author Djamel Eddine Korei
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public DataTablesOutput<User> findAllDatatable(DataTablesInput input) {
        log.debug("Request to get all Users");
        return userRepository.findAll(input);
    }

}