package com.BinaryBros.Voice_Controlled.Task.Manager.Service;

import com.BinaryBros.Voice_Controlled.Task.Manager.Model.Task;
import com.BinaryBros.Voice_Controlled.Task.Manager.Model.TaskStatus;
import com.BinaryBros.Voice_Controlled.Task.Manager.Repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepo;

    @Transactional
    public Task addTask(String desc) {
        Task task = new Task(desc, TaskStatus.PENDING);
        return taskRepo.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepo.findById(id);
    }

    public Task updateTask(Long id, String newDesc, TaskStatus newStatus) {
        Task task = taskRepo.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setDescription(newDesc);
        task.setStatus(newStatus);
        return taskRepo.save(task);
    }

    public String deleteTask(Long id) {
        if (taskRepo.existsById(id)) {
            taskRepo.deleteById(id);
            return "Task deleted successfully.";
        }
        return "Task not found.";
    }

    public String markDone(Long id) {
        Task task = taskRepo.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(TaskStatus.DONE);
        taskRepo.save(task);
        return "Task marked as DONE.";
    }

}
