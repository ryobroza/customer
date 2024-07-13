package speedrun.customer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import speedrun.customer.model.Course;
import speedrun.customer.service.CourseService;
import speedrun.customer.utils.response.PageWrapper;
import speedrun.customer.utils.response.Res;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Course request) {
        return Res.renderJson(
                courseService.create(request),
                "created",
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault(size = 5) Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer price
            ) {
        Page<Course> courses = courseService.getAll(name, price, pageable);
        PageWrapper<Course> res = new PageWrapper<>(courses);
        return Res.renderJson(
                res,
                "success",
                HttpStatus.OK
        );
    }
}
