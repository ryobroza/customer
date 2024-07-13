package speedrun.customer.utils.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import speedrun.customer.model.Customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerSpecification {
    public static Specification<Customer> hasName(String name) {
        return(root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Customer> hasBirthDate(LocalDate birthDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("birthDate"), birthDate);
    }

    public static Specification<Customer> getSpecification(
            String name,
            LocalDate birthDate
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }

            if (birthDate != null) {
                predicates.add(criteriaBuilder.equal(root.get("birthDate"), birthDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
