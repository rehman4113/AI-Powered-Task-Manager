package com.BinaryBros.Voice_Controlled.Task.Manager.Controllor;

import com.BinaryBros.Voice_Controlled.Task.Manager.Model.Task;
import com.BinaryBros.Voice_Controlled.Task.Manager.Model.TaskStatus;
import com.BinaryBros.Voice_Controlled.Task.Manager.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class TaskController {
// http://localhost:8080/api/tasks/all

    @Autowired
    private TaskService taskService;

    @PostMapping("/add")
    public ResponseEntity<Task> addTask(@RequestParam String desc) {
        return ResponseEntity.ok(taskService.addTask(desc));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAll() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestParam String desc,
            @RequestParam TaskStatus status) {
        return ResponseEntity.ok(taskService.updateTask(id, desc, status));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.deleteTask(id));
    }

    @PostMapping("/done/{id}")
    public ResponseEntity<String> markDone(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.markDone(id));
    }

}
