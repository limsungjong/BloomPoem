package com.example.bloompoem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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
        @Temporal(TemporalType.TIMESTAMP)
        private Date floristRegDate;
        @Column
        private String floristReviewImage;
}
