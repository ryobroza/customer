package speedrun.customer.service;

import speedrun.customer.model.CustomerCourse;
import speedrun.customer.utils.dto.CustomerCourseDTO;

import java.util.List;

public interface CustomerCourseService {
    CustomerCourse create(CustomerCourseDTO req);
    CustomerCourse getOne(Integer id);
    List<CustomerCourse> getAll();
}
