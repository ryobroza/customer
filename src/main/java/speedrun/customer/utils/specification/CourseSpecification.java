package speedrun.customer.utils.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import speedrun.customer.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseSpecification {
    public static Specification<Course> getSpecification(
            String name,
            Integer price
    ) {
        return(root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }

            if (price != null && !price.toString().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("price"), price));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
