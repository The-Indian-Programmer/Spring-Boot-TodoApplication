package org.crud.todo.service.tasks;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.apache.juli.logging.Log;
import org.crud.todo.dto.tasks.CreateTaskRequest;
import org.crud.todo.dto.tasks.PartialUpdateTaskRequest;
import org.crud.todo.dto.tasks.TaskListRequest;
import org.crud.todo.dto.tasks.UpdateTaskRequest;
import org.crud.todo.helper.ServiceReturnHandler;
import org.crud.todo.model.Tasks;
import org.crud.todo.repository.tasks.TaskRepository;
import org.crud.todo.security.service.CustomUserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.config.Task;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class TasksService {
    private final TaskRepository taskRepository;

    public TasksService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public ServiceReturnHandler createTask(@Valid CreateTaskRequest createTaskRequest, Authentication authenticatedUser) {
        try {

            CustomUserPrincipal authenticatedUserPrincipal = (CustomUserPrincipal) authenticatedUser.getPrincipal();
            Tasks task = new Tasks();
            task.setUserId(authenticatedUserPrincipal.getId());
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
            e.printStackTrace();
            return ServiceReturnHandler.returnError("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }

    public ServiceReturnHandler getTasks(@Valid TaskListRequest request, Authentication authenticatedUser) {
        try {
            CustomUserPrincipal authenticatedUserPrincipal = (CustomUserPrincipal) authenticatedUser.getPrincipal();

            Long userId = authenticatedUserPrincipal.getId();
            int page = request.getPage() != null ? request.getPage() - 1 : 0;
            int limit = request.getLimit() != null ? request.getLimit() : 10;

            Sort.Direction direction = "asc".equalsIgnoreCase(request.getSort()) ? Sort.Direction.ASC : Sort.Direction.DESC;
            Sort sort = Sort.by(direction, request.getSort());

            Pageable pageable = PageRequest.of(page, limit, sort);

            Page<Tasks> tasksPage;

            if (request.getQuery() != null && !request.getQuery().isEmpty()) {
                tasksPage = (Page<Tasks>) taskRepository.findByUserIdAndTitleContainingIgnoreCaseAndIsDeletedFalse(
                        userId,
                        request.getQuery(),
                        pageable
                );
            } else {
                tasksPage = (Page<Tasks>) taskRepository.findByUserIdAndIsDeletedFalse(userId, pageable);
            }

            return new ServiceReturnHandler(true, "Tasks fetched successfully", HttpStatus.OK.value(), tasksPage);
        } catch (Exception e) {
            return ServiceReturnHandler.returnError("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public ServiceReturnHandler getTask(Long id, Authentication authenticatedUser) {
        try {
            CustomUserPrincipal authenticatedUserPrincipal = (CustomUserPrincipal) authenticatedUser.getPrincipal();
            Long userId = authenticatedUserPrincipal.getId();

            if (!taskRepository.existsById(id)) {
                return ServiceReturnHandler.returnError("Task not found", HttpStatus.NOT_FOUND.value());
            }

            Tasks task = taskRepository.findById(id).orElse(null);

            if (task == null) return ServiceReturnHandler.returnError("Task not found", HttpStatus.NOT_FOUND.value());

            if (task.isDeleted()) return ServiceReturnHandler.returnError("Task has been deleted", HttpStatus.BAD_REQUEST.value());

            if (!task.getUserId().equals(userId)) {
                return ServiceReturnHandler.returnError("Task has been deleted", HttpStatus.BAD_REQUEST.value());
            }
            return ServiceReturnHandler.returnSuccess("Task found", HttpStatus.OK.value(), task);
        } catch (Exception e) {
            return ServiceReturnHandler.returnError("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public ServiceReturnHandler updateTask(@Min(1) Long id, @Valid UpdateTaskRequest updateTaskRequest, Authentication authenticatedUser) {
        try {
            CustomUserPrincipal authenticatedUserPrincipal = (CustomUserPrincipal) authenticatedUser.getPrincipal();
            Long userId = authenticatedUserPrincipal.getId();

            if (!taskRepository.existsById(id))  return ServiceReturnHandler.returnError("Task not found", HttpStatus.NOT_FOUND.value());

            Tasks task = taskRepository.findById(id).orElse(null);
            if (task == null) return ServiceReturnHandler.returnError("Task not found", HttpStatus.NOT_FOUND.value());

            if (task.isDeleted()) return ServiceReturnHandler.returnError("Task has been deleted", HttpStatus.BAD_REQUEST.value());

            if (!task.getUserId().equals(userId)) return  ServiceReturnHandler.returnError("Task has been deleted", HttpStatus.BAD_REQUEST.value());

            if (task.getStatus().equals(Tasks.TaskStatus.COMPLETED)) return ServiceReturnHandler.returnError("Task has been completed", HttpStatus.BAD_REQUEST.value());

            task.setTitle(updateTaskRequest.getTitle());
            task.setDescription(updateTaskRequest.getDescription());
            task.setDueDate(updateTaskRequest.getDueDate());
            task.setStatus(updateTaskRequest.getStatus());
            task.setDeleted(false);

            taskRepository.save(task);
            return ServiceReturnHandler.returnSuccess("Task updated successfully", HttpStatus.OK.value(), task);
        } catch (Exception e) {
            return ServiceReturnHandler.returnError("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public ServiceReturnHandler patchUpdateTask(@Min(1) Long id, @Valid PartialUpdateTaskRequest updateTaskRequest, Authentication authenticatedUser) {
        try {
            CustomUserPrincipal authenticatedUserPrincipal = (CustomUserPrincipal) authenticatedUser.getPrincipal();
            Long userId = authenticatedUserPrincipal.getId();

            Optional<Tasks> optionalTask = taskRepository.findById(id);

            if (optionalTask.isEmpty())  return ServiceReturnHandler.returnError("Task not found", HttpStatus.NOT_FOUND.value());
            if (!optionalTask.get().getUserId().equals(userId))  return ServiceReturnHandler.returnError("Task has been deleted", HttpStatus.BAD_REQUEST.value());
            if (optionalTask.get().isDeleted())  return ServiceReturnHandler.returnError("Task has been deleted", HttpStatus.BAD_REQUEST.value());


            if (updateTaskRequest.getTitle() != null) optionalTask.get().setTitle(updateTaskRequest.getTitle());
            if  (updateTaskRequest.getDescription() != null) optionalTask.get().setDescription(updateTaskRequest.getDescription());
            if (updateTaskRequest.getDueDate() != null) optionalTask.get().setDueDate(updateTaskRequest.getDueDate());
            if (updateTaskRequest.getStatus() != null) optionalTask.get().setStatus(updateTaskRequest.getStatus());
            optionalTask.get().setDeleted(false);
            optionalTask.get().setUpdatedAt(LocalDateTime.now());
            taskRepository.save(optionalTask.get());
            return ServiceReturnHandler.returnSuccess("Task updated successfully", HttpStatus.OK.value(), optionalTask.get());
        } catch (Exception e) {
            e.printStackTrace();
            return ServiceReturnHandler.returnError("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public ServiceReturnHandler deleteTask(@Min(1) Long id, Authentication authenticatedUser) {
        try {
            CustomUserPrincipal authenticatedUserPrincipal = (CustomUserPrincipal) authenticatedUser.getPrincipal();
            Long userId = authenticatedUserPrincipal.getId();

            if  (!taskRepository.existsById(id)) return  ServiceReturnHandler.returnError("Task not found", HttpStatus.NOT_FOUND.value());

            Tasks task = taskRepository.findById(id).orElse(null);
            if (task == null) return ServiceReturnHandler.returnError("Task not found", HttpStatus.NOT_FOUND.value());

            if (task.isDeleted()) return ServiceReturnHandler.returnError("Task has been deleted", HttpStatus.BAD_REQUEST.value());
            if (!task.getUserId().equals(userId)) return  ServiceReturnHandler.returnError("Task has been deleted", HttpStatus.BAD_REQUEST.value());

            task.setDeleted(true);
            taskRepository.save(task);
            return ServiceReturnHandler.returnSuccess("Task has been deleted", HttpStatus.OK.value(), task);
        }catch (Exception e) {
            e.printStackTrace();
            return ServiceReturnHandler.returnError("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
