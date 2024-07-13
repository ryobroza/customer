package speedrun.customer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import speedrun.customer.model.Customer;
import speedrun.customer.service.CustomerCourseService;
import speedrun.customer.service.CustomerService;
import speedrun.customer.utils.dto.CustomerCourseDTO;
import speedrun.customer.utils.response.PageWrapper;
import speedrun.customer.utils.response.Res;

import java.time.LocalDate;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerCourseService ccService;

    @PostMapping
    public Customer create(@RequestBody Customer request) {
        return customerService.create(request);
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buy(
            @RequestBody CustomerCourseDTO request
            ) {

        return Res.renderJson(
                ccService.create(request),
                "sukses membeli",
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String birthDate,
            @PageableDefault(page = 0, size = 10)Pageable pageable
            ) {
        LocalDate parseDate = null;
        Page<Customer> res= customerService.getAll(name, parseDate, pageable);
        PageWrapper<Customer> result = new PageWrapper<>(res);
        return Res.renderJson(
                result,
                "FOUND",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public Customer getOne(@PathVariable Integer id) {
        return customerService.getOne(id);
    }

    @PutMapping("/{id}")
    public Customer update(
            @PathVariable Integer id,
            @RequestBody Customer request) {
      return customerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        customerService.delete(id);
    }
}
