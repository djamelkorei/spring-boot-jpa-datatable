package me.djamelkorei.springbootjpadatatable.web.rest;

import lombok.RequiredArgsConstructor;
import me.djamelkorei.springbootjpadatatable.domain.User;
import me.djamelkorei.springbootjpadatatable.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * REST controller for managing {@link User}.
 *
 * @author Djamel Eddine Korei
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);
    private final UserService userService;

    /**
     * {@code GET  /users} : get all the users.
     *
     * @return the {@link DataTablesOutput} with status {@code 200 (OK)} and the list of users in body.
     */
    @GetMapping("/users")
    public DataTablesOutput<User> findAllDatatable(@Valid DataTablesInput input) {
        log.debug("REST request to get a page of Users");
        return userService.findAllDatatable(input);
    }

}
