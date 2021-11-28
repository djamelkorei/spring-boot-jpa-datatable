package me.djamelkorei.springbootjpadatatable.repository;

import me.djamelkorei.springbootjpadatatable.domain.User;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring DataTables JPA repository for the {@link User} entity.
 *
 * @author Djamel Eddine Korei
 */
@Repository
public interface UserRepository extends DataTablesRepository<User, Long> {

}
