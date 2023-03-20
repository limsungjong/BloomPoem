package com.example.bloompoem.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "FLORIST_REVIEW")
@NoArgsConstructor
@Builder
@Getter
@AllArgsConstructor
@SequenceGenerator(
        name = "SEQ_FLORIST_REVIEW_NUMBER",
        sequenceName = "SEQ_FLORIST_REVIEW_NUMBER",
        initialValue = 1,
        allocationSize = 1
)
public class FloristReviewEntity{
        @Id
        @GeneratedValue(
                strategy = GenerationType.SEQUENCE,
                generator = "SEQ_FLORIST_REVIEW_NUMBER"
        )
        private int floristReviewNumber;
        @Column
        private String userEmail;
        @Column
        private int pickUpOrderNumber;
        @Column
        private int floristNumber;
        @Column
        private String floristReviewContent;
        @Column
        private Character floristReviewScore;
        @Column
        private LocalDate floristReviewRegDate;
        @Column
        @Setter
        private String floristReviewImage;
}
