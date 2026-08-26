package com.example.demo.dao;

import com.example.demo.DatabaseManager;
import com.example.demo.DeadlineTask;
import com.example.demo.Task;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskDAO {
    private final ArrayList<Task> currentTasks = new ArrayList<>();
    private final ArrayList<DeadlineTask> deadlineTasks = new ArrayList<>();
    public void addTask(Task task, int userID) {
        String sql = "INSERT INTO tasks (user_id, task_type, title, description, is_done, deadline_date,priority,kategorie) VALUES (?, ?, ?, ?, ?, ?,?,?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            if (task instanceof DeadlineTask) {
                pstmt.setString(2, "DEADLINE");
                LocalDate localDate = ((DeadlineTask) task).getDate();
                pstmt.setDate(6, java.sql.Date.valueOf(localDate));
            } else {
                pstmt.setString(2, "NORMAL");
                pstmt.setNull(6, java.sql.Types.DATE);
            }
            pstmt.setString(3, task.getTitle());
            pstmt.setString(4, task.getDescription());
            pstmt.setBoolean(5, task.isDone());
            pstmt.setString(7, task.getPrio().name());
            pstmt.setString(8, task.getKat().name());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Task> showAllTasks(int userID) {
        currentTasks.clear();
        String sql = "SELECT * FROM tasks WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Task fertigerTask = buildTask(rs);
                currentTasks.add(fertigerTask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentTasks;
    }

    public void editDescription(String newDescription, int taskChoice) {
        Task taskTodone = currentTasks.get(taskChoice - 1);
        int dbId = taskTodone.getId();
        String sql = "UPDATE tasks SET description = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newDescription);
            pstmt.setInt(2, dbId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editTitle(String newTitle, int taskChoice) {
        Task taskTodone = currentTasks.get(taskChoice - 1);
        int dbId = taskTodone.getId();
        String sql = "UPDATE tasks SET title = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newTitle);
            pstmt.setInt(2, dbId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void markTaskAsDone(int dbId) {
        String sql = "UPDATE tasks SET is_done = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, true);
            pstmt.setInt(2, dbId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void markTaskAsUndone(int dbId) {
        String sql = "UPDATE tasks SET is_done = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, false);
            pstmt.setInt(2, dbId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int dbId) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dbId);
            pstmt.executeUpdate();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTaskCount(int id) {
        String sql = "SELECT COUNT(*) FROM tasks WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Task> searchTasks(String keyword, int userID) {
        currentTasks.clear();
        String sql = "SELECT * FROM tasks WHERE user_id = ? AND title LIKE ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.setString(2, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Task fertigerTask = buildTask(rs);
                currentTasks.add(fertigerTask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentTasks;
    }

    public ArrayList<Task> filterTasks(boolean status, int userID) {
        currentTasks.clear();
        String sql = "SELECT * FROM tasks WHERE user_id = ? AND is_done = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.setBoolean(2, status);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                currentTasks.add(buildTask(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentTasks;
    }

    public ArrayList<Task> sortTasksAlphabetically(int userID) {
        currentTasks.clear();
        String sql = "SELECT * FROM tasks WHERE user_id = ? ORDER BY title ASC";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                currentTasks.add(buildTask(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentTasks;
    }

    public ArrayList<Task> showDeadlinesSorted(int userID) {
        currentTasks.clear();
        String sql = "SELECT * FROM tasks WHERE user_id = ? AND task_type = 'DEADLINE' ORDER BY deadline_date ASC";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                currentTasks.add(buildTask(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentTasks;
    }

    public Map<String, Integer> showDashboard(int userID) {
        int totalCount = 0;
        int doneCount = 0;
        int openCount = 0;
        int overdueCount = 0;
        Map<String, Integer> dashboardStats = new HashMap<>();
        String sql = "SELECT * FROM tasks WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String dbType = rs.getString("task_type");
                boolean done = rs.getBoolean("is_done");


                totalCount++;
                if (dbType.equals("DEADLINE")) {
                    LocalDate deadline = rs.getDate("deadline_date").toLocalDate();
                    if (!done && deadline.isBefore(LocalDate.now())) {
                        overdueCount++;
                    }
                }
                if (done) {
                    doneCount++;
                } else {
                    openCount++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dashboardStats.put("Total Tasks",totalCount);
        dashboardStats.put("Completed",doneCount);
        dashboardStats.put("Open", openCount);
        dashboardStats.put("OVERDUE", overdueCount);
        return dashboardStats;
    }

    private Task buildTask(ResultSet rs) throws SQLException {
        String dbType = rs.getString("task_type");
        if (dbType.equals("DEADLINE")) {
            DeadlineTask dt = new DeadlineTask(rs.getString("title"), rs.getString("description"), rs.getBoolean("is_done"), rs.getDate("deadline_date").toLocalDate());
            dt.setId(rs.getInt("id"));
            dt.setPrio(Task.Priority.valueOf(rs.getString("priority")));
            dt.setKat(Task.Kategorie.valueOf(rs.getString("kategorie")));
            dt.showTask();
            return dt;
        } else {
            Task tk = new Task(rs.getString("title"), rs.getString("description"), rs.getBoolean("is_done"));
            tk.setId(rs.getInt("id"));
            tk.setPrio(Task.Priority.valueOf(rs.getString("priority")));
            tk.setKat(Task.Kategorie.valueOf(rs.getString("kategorie")));
            tk.showTask();
            return tk;
        }
    }
}