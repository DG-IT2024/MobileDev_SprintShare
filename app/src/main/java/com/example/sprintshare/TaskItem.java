package com.example.sprintshare;

public class TaskItem {
    private String name;
    private String description;
    private String assignee;
    private String deadline;
    private boolean completed;

    public TaskItem(String name, String description, String assignee, String deadline) {
        this.name = name;
        this.description = description;
        this.assignee = assignee;
        this.deadline = deadline;
        this.completed = false;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getAssignee() { return assignee; }
    public String getDeadline() { return deadline; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
