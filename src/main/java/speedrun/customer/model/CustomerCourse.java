package speedrun.customer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer_courses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
