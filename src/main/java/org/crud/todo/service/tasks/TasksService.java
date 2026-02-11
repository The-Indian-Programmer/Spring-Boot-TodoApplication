package org.crud.todo.service.tasks;


import jakarta.validation.Valid;
import org.crud.todo.dto.tasks.CreateTaskRequest;
import org.crud.todo.helper.ServiceReturnHandler;
import org.crud.todo.model.Tasks;
import org.crud.todo.repository.tasks.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class TasksService {
    private final TaskRepository taskRepository;

    public TasksService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public ServiceReturnHandler createTask(@Valid CreateTaskRequest createTaskRequest) {
        try {

            Tasks task = new Tasks();
            task.setTitle(createTaskRequest.getTitle());
            task.setDescription(createTaskRequest.getDescription());
            task.setDueDate(createTaskRequest.getDueDate());
            task.setStatus(Tasks.TaskStatus.PENDING);
            task.setDeleted(false);
            task.setCreatedAt(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            task.setUpdatedAt(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

            Tasks saveTasksResponse = taskRepository.save(task);

            System.out.println(saveTasksResponse);
            return ServiceReturnHandler.returnSuccess("Task created successfully", HttpStatus.OK.value(), saveTasksResponse);
        } catch (Exception e) {
            return ServiceReturnHandler.returnError("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }
}
