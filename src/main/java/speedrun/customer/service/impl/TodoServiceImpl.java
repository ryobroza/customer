package speedrun.customer.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import speedrun.customer.model.Todo;

import java.util.List;

@Service
public class TodoServiceImpl {
    private final RestClient restClient;

    public TodoServiceImpl() {
        this.restClient = RestClient.builder().baseUrl("https://jsonplaceholder.typicode.com/todos").build();
    }

    public List<Todo> getAll(){
        return restClient.get().retrieve().body(new ParameterizedTypeReference<List<Todo>>() {});
    }

    public Todo getById(int id){
        return restClient.get().uri("/{id}", id).retrieve().body(Todo.class);
    }
}
