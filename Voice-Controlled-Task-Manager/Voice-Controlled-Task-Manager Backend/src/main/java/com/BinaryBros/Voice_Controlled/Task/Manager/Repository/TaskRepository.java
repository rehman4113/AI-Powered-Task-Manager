package com.BinaryBros.Voice_Controlled.Task.Manager.Repository;

import com.BinaryBros.Voice_Controlled.Task.Manager.Model.Task;
import com.BinaryBros.Voice_Controlled.Task.Manager.Model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
}
