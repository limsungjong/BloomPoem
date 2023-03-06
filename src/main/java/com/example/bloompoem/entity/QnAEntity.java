package com.example.bloompoem.entity;

import com.example.bloompoem.dto.QnADTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "QNA")
public class QnAEntity {
    @Id
    private int qnaNumber;

    @Column(length = 40, nullable = false)
    private String userEmail;

    @Column(length = 40, nullable = false)
    private Date qnaDate;

    @Column(nullable = false)
    private char qnaStatus;

    @Column(length = 100, nullable = false)
    private String qnaTitle;

    @Column(nullable = false)
    private int qnaGroup;

    @Column(nullable = false)
    private int qnaIndent;

    @Column(nullable = false)
    private int qnaParent;

    @Column(nullable = false)
    private int qnaOrder;

    @Column(length = 50, nullable = false)
    private String qnaImage1;

    @Column(length = 50, nullable = false)
    private String qnaImage2;

    @Column(length = 50, nullable = false)
    private String qnaImage3;

    @Column(length = 300, nullable = false)
    private String qnaContent;

    // 1:1 문의 목록에 띄울 문의번호, 상태, 제목, 등록일만 가져오기
    public static QnAEntity getQnAListToEntity(QnADTO qnaDTO){
        QnAEntity qnaEntity = new QnAEntity();
        qnaEntity.setQnaNumber(qnaDTO.getQnaNumber());
        qnaEntity.setQnaStatus(qnaDTO.getQnaStatus());
        qnaEntity.setQnaTitle(qnaDTO.getQnaTitle());
        qnaEntity.setQnaDate(qnaDTO.getQnaDate());
        return qnaEntity;
    }

    // QnA 작성에 필요한 정보 가져오기(일단 다 넣었음)
    public static QnAEntity toEntity(QnADTO qnaDTO){
        QnAEntity qnaEntity = new QnAEntity();
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
        qnaEntity.setQnaImage2(qnaDTO.getQnaImage2());
        qnaEntity.setQnaImage3(qnaDTO.getQnaImage3());
        qnaEntity.setQnaContent(qnaDTO.getQnaContent());
        return toEntity(qnaDTO);
    }
}
