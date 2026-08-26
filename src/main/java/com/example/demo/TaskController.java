package com.example.demo;

import com.example.demo.dao.TaskDAO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private TaskDAO taskDAO = new TaskDAO();

    @GetMapping
    public List<Task> getAllTasks(@RequestHeader("User-Id") int userId) {
        return taskDAO.showAllTasks(userId);
    }

    @PostMapping
    public void createTask(@RequestBody Task newTask, @RequestHeader("User-Id") int userId) {
        taskDAO.addTask(newTask, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id) {
        taskDAO.deleteTask(id);
    }

    @PutMapping("/{id}/done")
    public void markTaskAsDone(@PathVariable int id) {
        taskDAO.markTaskAsDone(id);
    }

    @PutMapping("/{id}/undone")
    public void markTaskAsUndone(@PathVariable int id) {
        taskDAO.markTaskAsUndone(id);
    }
}
