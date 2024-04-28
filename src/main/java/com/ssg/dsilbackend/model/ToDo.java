package com.ssg.dsilbackend.model;

import jakarta.persistence.*;
import lombok.Data;

import jakarta.persistence.*;

@Entity
@Data
@Table(name = "todos")
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Boolean completed;
}
