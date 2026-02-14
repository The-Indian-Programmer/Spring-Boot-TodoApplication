package org.crud.todo.controller.tasks;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.crud.todo.dto.common.ApiResponse;
import org.crud.todo.dto.tasks.CreateTaskRequest;
import org.crud.todo.dto.tasks.PartialUpdateTaskRequest;
import org.crud.todo.dto.tasks.TaskListRequest;
import org.crud.todo.dto.tasks.UpdateTaskRequest;
import org.crud.todo.helper.ServiceReturnHandler;
import org.crud.todo.service.tasks.TasksService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    private final TasksService tasksService;
    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<?>> create(@Valid @RequestBody CreateTaskRequest createTaskRequest) {
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        ServiceReturnHandler returnHandler = tasksService.createTask(createTaskRequest, authenticatedUser);
        return ResponseEntity.status(returnHandler.getStatusCode()).body(new ApiResponse<>(returnHandler.isStatus(), returnHandler.getMessage(), returnHandler.getStatusCode(), returnHandler.getData()));
    }


    @GetMapping("")
    public ResponseEntity<ApiResponse<?>> getTaskList(@Valid @ModelAttribute TaskListRequest taskListRequest) {
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        ServiceReturnHandler returnHandler = tasksService.getTasks(taskListRequest, authenticatedUser);
        return ResponseEntity.status(returnHandler.getStatusCode()).body(new ApiResponse<>(returnHandler.isStatus(), returnHandler.getMessage(), returnHandler.getStatusCode(), returnHandler.getData()));
    }



    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<?>> getTask(@PathVariable Long id) {
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        ServiceReturnHandler returnHandler = tasksService.getTask(id, authenticatedUser);
        return ResponseEntity.status(returnHandler.getStatusCode()).body(new ApiResponse<>(returnHandler.isStatus(), returnHandler.getMessage(), returnHandler.getStatusCode(), returnHandler.getData()));
    }


    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<?>> updateTask(@PathVariable @Min(1) Long id, @Valid @RequestBody UpdateTaskRequest updateTaskRequest) {
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        ServiceReturnHandler returnHandler = tasksService.updateTask(id, updateTaskRequest, authenticatedUser);
        return ResponseEntity.status(returnHandler.getStatusCode()).body(new ApiResponse<>(returnHandler.isStatus(), returnHandler.getMessage(), returnHandler.getStatusCode(), returnHandler.getData()));
    }

    @PatchMapping("{id}")
    public ResponseEntity<ApiResponse<?>> patchUpdateTask(@PathVariable @Min(1) Long id, @Valid @RequestBody PartialUpdateTaskRequest updateTaskRequest) {
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        ServiceReturnHandler returnHandler = tasksService.patchUpdateTask(id, updateTaskRequest, authenticatedUser);
        return ResponseEntity.status(returnHandler.getStatusCode()).body(new ApiResponse<>(returnHandler.isStatus(), returnHandler.getMessage(), returnHandler.getStatusCode(), returnHandler.getData()));
    }



    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<?>> deleteTask(@PathVariable @Min(1) Long id) {
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        ServiceReturnHandler returnHandler = tasksService.deleteTask(id, authenticatedUser);
        return ResponseEntity.status(returnHandler.getStatusCode()).body(new ApiResponse<>(returnHandler.isStatus(), returnHandler.getMessage(), returnHandler.getStatusCode(), returnHandler.getData()));
    }



}
