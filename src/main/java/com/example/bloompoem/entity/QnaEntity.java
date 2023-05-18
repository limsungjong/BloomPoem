package com.example.bloompoem.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@DynamicUpdate
@Entity(name = "qna")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QnaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer qnaNumber;

    private String userEmail;
    private LocalDateTime qnaDate;
    private char qnaStatus;
    private String qnaTitle;
    private int qnaGroup;
    private int qnaIndent;
    private int qnaParent;
    private int qnaOrder;
    private String qnaImage1;
    private String qnaContent;


}
