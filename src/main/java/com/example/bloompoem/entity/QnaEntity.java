package com.example.bloompoem.entity;

import com.example.bloompoem.dto.QnaDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@DynamicUpdate
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "QNA")
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

    @JoinColumn(name = "userEmail")
    private String userEmail;

    @CreationTimestamp
    @Column
    private LocalDateTime qnaDate;

    @Column
    private char qnaStatus;

    @Column(length = 100)
    private String qnaTitle;

    @Column
    private int qnaGroup;

    @Column
    private int qnaIndent;

    @Column
    private int qnaParent;

    @Column
    private int qnaOrder;

    @Column(length = 50)
    private String qnaImage1;

//    @Column(length = 50)
//    private String qnaImage2;
//
//    @Column(length = 50)
//    private String qnaImage3;

    @Column(length = 500)
    private String qnaContent;

    // 1:1 문의 목록에 띄울 문의번호, 상태, 제목, 등록일만 가져오기
    public static QnaEntity getQnaListToEntity(QnaDTO qnaDTO){
        QnaEntity qnaEntity = new QnaEntity();
        qnaEntity.setQnaNumber(qnaDTO.getQnaNumber());
        qnaEntity.setQnaStatus(qnaDTO.getQnaStatus());
        qnaEntity.setQnaTitle(qnaDTO.getQnaTitle());
        qnaEntity.setQnaDate(qnaDTO.getQnaDate());
        return qnaEntity;
    }

    // Qna 작성에 필요한 정보 가져오기(일단 다 넣었음)
    public static QnaEntity toEntity(QnaDTO qnaDTO){
        QnaEntity qnaEntity = new QnaEntity();
        qnaEntity.setQnaNumber(qnaDTO.getQnaNumber());
        qnaEntity.setUserEmail(qnaDTO.getUserEmail());
        qnaEntity.setQnaDate(qnaDTO.getQnaDate());
        qnaEntity.setQnaStatus(qnaDTO.getQnaStatus());
        qnaEntity.setQnaTitle(qnaDTO.getQnaTitle());
        qnaEntity.setQnaGroup(qnaDTO.getQnaGroup());
        qnaEntity.setQnaIndent(qnaDTO.getQnaIndent());
        qnaEntity.setQnaParent(qnaDTO.getQnaParent());
        qnaEntity.setQnaOrder(qnaDTO.getQnaOrder());
        qnaEntity.setQnaImage1(qnaDTO.getQnaImage1());
//        qnaEntity.setQnaImage2(qnaDTO.getQnaImage2());
//        qnaEntity.setQnaImage3(qnaDTO.getQnaImage3());
        qnaEntity.setQnaContent(qnaDTO.getQnaContent());
        return toEntity(qnaDTO);
    }
}
