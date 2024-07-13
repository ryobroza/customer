package speedrun.customer.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import speedrun.customer.model.Course;
import speedrun.customer.model.Customer;
import speedrun.customer.model.CustomerCourse;
import speedrun.customer.repository.CustomerCourseRepository;
import speedrun.customer.service.CourseService;
import speedrun.customer.service.CustomerCourseService;
import speedrun.customer.service.CustomerService;
import speedrun.customer.utils.dto.CustomerCourseDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerCourseServiceImpl implements CustomerCourseService {
    private final CustomerCourseRepository repo;
    private final CustomerService customerService;
    private final CourseService courseService;

    @Override
    public CustomerCourse create(CustomerCourseDTO req) {
        Customer customer = customerService.getOne(req.getCustomer_id());
        Course course = courseService.getOne(req.getCourse_id());

        CustomerCourse newCC = new CustomerCourse();
        newCC.setCustomer(customer);
        newCC.setCourse(course);

        customer.setBalance(customer.getBalance() - course.getPrice());

        if (customer.getBalance() < 0) {
            throw new RuntimeException("oops balance tidak cukup");
        }

        customerService.update(customer.getId(), customer);

        return repo.save(newCC);
    }

    @Override
    public CustomerCourse getOne(Integer id) {
        return null;
    }

    @Override
    public List<CustomerCourse> getAll() {
        return List.of();
    }
}
