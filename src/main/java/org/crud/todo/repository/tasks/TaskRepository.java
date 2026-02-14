package org.crud.todo.repository.tasks;

import org.crud.todo.model.Tasks;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Long> {

    Page<Tasks> findByUserIdAndIsDeletedFalse(
            Long userId,
            Pageable pageable
    );

    Page<Tasks> findByUserIdAndTitleContainingIgnoreCaseAndIsDeletedFalse(
            Long userId,
            String title,
            Pageable pageable
    );
}
