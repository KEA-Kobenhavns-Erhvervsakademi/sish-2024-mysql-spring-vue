package com.example.restservice;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @NotBlank(message = "Title is required")
    public String title;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    public TodoStatus status = TodoStatus.Pending;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    public TodoItem() {
    }

    public TodoItem(String title, TodoStatus status) {
        this.title = title;
        this.status = status;
    }
}