    package org.crud.todo.dto.tasks;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import jakarta.validation.constraints.*;
    import org.crud.todo.model.Tasks;
    import org.springframework.format.annotation.DateTimeFormat;

    import java.time.LocalDateTime;

    public class UpdateTaskRequest {

        @NotBlank(message = "Title is required")
        @Size(min = 10, message = "Title must be minimum 10 characters")
        private String title;

        @NotBlank(message = "Description is required")
        private String description;


        @NotNull(message = "Due date is required")
        @Future(message = "Due date must be in future")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDateTime dueDate;

        @NotNull(message = "Status is required")
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
