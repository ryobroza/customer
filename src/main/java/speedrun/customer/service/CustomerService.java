package speedrun.customer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import speedrun.customer.model.Customer;

import java.time.LocalDate;
import java.util.List;

public interface CustomerService {
    Customer create(Customer request);
    Page<Customer> getAll(String name, LocalDate birthDate, Pageable pageable);
    Customer getOne(Integer id);
    Customer update(Integer id, Customer request);
    void delete(Integer id);
}
