package speedrun.customer.utils.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse<T> { // sebuah kelas
    // properties / field / attributes
    private String status; // -> String
    private String message; // -> String
    private T data; // -> String, Integer, Array, Object, HashMap
}
