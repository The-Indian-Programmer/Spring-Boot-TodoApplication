package org.crud.todo.dto.tasks;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import org.crud.todo.model.Tasks;

import java.time.LocalDateTime;

public class PartialUpdateTaskRequest {

    @Size(min = 10, message = "Title must be minimum 10 characters")
    private String title;

    private String description;

    @Future(message = "Due date must be in future")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;

    private Tasks.TaskStatus status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Tasks.TaskStatus getStatus() {
        return status;
    }

    public void setStatus(Tasks.TaskStatus status) {
        this.status = status;
    }
}
