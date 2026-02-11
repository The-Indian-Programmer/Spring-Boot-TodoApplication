package org.crud.todo.controller.tasks;

import jakarta.validation.Valid;
import org.crud.todo.dto.common.ApiResponse;
import org.crud.todo.dto.tasks.CreateTaskRequest;
import org.crud.todo.helper.ServiceReturnHandler;
import org.crud.todo.service.tasks.TasksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    private final TasksService tasksService;

    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<?>> create(@Valid @RequestBody CreateTaskRequest createTaskRequest) {
        System.out.println("Controllere");
        ServiceReturnHandler returnHandler = tasksService.createTask(createTaskRequest);
        return ResponseEntity.status(returnHandler.getStatusCode()).body(new ApiResponse<>(returnHandler.isStatus(), returnHandler.getMessage(), returnHandler.getStatusCode(), returnHandler));
    }



}
