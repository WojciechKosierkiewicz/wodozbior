package com.example.wodozbior.entity;

import jakarta.persistence.*;


import java.time.LocalDateTime;


@Entity
@Table(name = "error_logs")
public class ErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "function_name", nullable = false)
    private String functionName;

    @Column(name = "error_message", nullable = false)
    private String errorMessage;

    @Column(name = "error_time", nullable = false)
    private LocalDateTime errorTime = LocalDateTime.now();

    public ErrorLog() {
    }

    public ErrorLog(Long id, String functionName, String errorMessage, LocalDateTime errorTime) {
        this.id = id;
        this.functionName = functionName;
        this.errorMessage = errorMessage;
        this.errorTime = errorTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(LocalDateTime errorTime) {
        this.errorTime = errorTime;
    }
}