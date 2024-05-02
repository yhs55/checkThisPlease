package com.ssg.dsilbackend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "imform")
public class Imform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inform_id")
    private Long id;

    @Column(name = "inform_category", length = 100, nullable = false)
    private String category;

    @Column(name = "inform_title", length = 100, nullable = false)
    private String title;

    @Column(name = "inform_contents", nullable = false)
    private String contents;

    @Column(name = "inform_post_date", nullable = false)
    private LocalDate postDate;

    @Column(name = "inform_modified_date")
    private LocalDate modifiedDate;

    @Column(name = "inform_file_path", length = 512)
    private String filePath;
}
