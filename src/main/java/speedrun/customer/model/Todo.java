package speedrun.customer.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class Todo {

    private Integer id;
    private Integer userId;
    private String title;
    private Boolean completed;
}
