package speedrun.customer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import speedrun.customer.model.Course;
import speedrun.customer.repository.CourseRepository;
import speedrun.customer.service.CourseService;
import speedrun.customer.utils.specification.CourseSpecification;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    @Override
    public Course create(Course request) {
        return courseRepository.save(request);
    }

    @Override
    public Page<Course> getAll(
            String name,
            Integer price,
            Pageable pageable) {
        Specification<Course> spec = CourseSpecification.getSpecification(name, price);
        return courseRepository.findAll(spec, pageable);
    }

    @Override
    public Course getOne(Integer id) {
        return courseRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("not found")
                );
    }
}
