package me.djamelkorei.springbootjpadatatable.web.view;

import me.djamelkorei.springbootjpadatatable.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for managing {@link User}.
 *
 * @author Djamel Eddine Korei
 */
@Controller
public class UserView {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
