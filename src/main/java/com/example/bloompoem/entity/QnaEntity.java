package com.example.bloompoem.entity;

import com.example.bloompoem.dto.QnaDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@DynamicUpdate
@Entity(name = "qna")
@AllArgsConstructor
@NoArgsConstructor
@Data
@SequenceGenerator(
        name = "SEQ_QNA_NUMBER",
        sequenceName = "SEQ_QNA_NUMBER",
        initialValue = 2,
        allocationSize = 1
)
public class QnaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_QNA_NUMBER")
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
