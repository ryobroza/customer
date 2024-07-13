package speedrun.customer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import speedrun.customer.model.Customer;
import speedrun.customer.repository.CustomerRepository;
import speedrun.customer.service.CustomerService;
import speedrun.customer.utils.specification.CustomerSpecification;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repo;
    @Override
    public Customer create(Customer request) {
        return repo.save(request);
    }

    @Override
    public Page<Customer> getAll(String name, LocalDate birthDate, Pageable pageable) {
        Specification<Customer> specification = CustomerSpecification.getSpecification(name, birthDate);
        return repo.findAll(specification, pageable);
    }

    @Override
    public Customer getOne(Integer id) {
        return repo.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Customer not found")
                );
    }

    @Override
    public Customer update(Integer id, Customer request) {
        Customer updatedCustomer = this.getOne(id);
        updatedCustomer.setName(request.getName());
        updatedCustomer.setBirthDate(request.getBirthDate());
        updatedCustomer.setBalance(request.getBalance());
        return repo.save(updatedCustomer);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }
}

