package com.example.demo;

import com.example.demo.dao.TaskDAO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/search")
    public List<Task> searchTask(@RequestHeader("User-Id") int userid,@RequestParam String keyword){
        return taskDAO.searchTasks(keyword, userid);
    }

    @GetMapping("/filter")
    public List<Task> filterTask(@RequestHeader("User-Id") int userid,@RequestParam boolean status){
        return taskDAO.filterTasks(status, userid);
    }

    @GetMapping("/sortTask")
    public List<Task> sortTask(@RequestHeader("User-Id") int userid){
        return taskDAO.sortTasksAlphabetically(userid);
    }

    @GetMapping("/sortdeadlinetask")
    public List<Task> sortDeadlineTask(@RequestHeader("User-Id") int userid){
        return taskDAO.showDeadlinesSorted(userid);
    }
    @GetMapping("/dashboard")
    public Map<String, Integer> dashboard(@RequestHeader("User-Id" ) int userid){
        return taskDAO.showDashboard(userid);
    }
}
