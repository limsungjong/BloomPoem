package com.example.bloompoem.dto;

import com.example.bloompoem.entity.QnaEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QnaDTO {
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

    // 1:1 문의 목록에 띄울 문의번호, 상태, 제목, 등록일만 가져오기
    public static QnaDTO getQnaListToDTO(QnaEntity qnaEntity){
        QnaDTO qnaDTO = new QnaDTO();
        qnaDTO.setQnaNumber(qnaEntity.getQnaNumber());
        qnaDTO.setQnaStatus(qnaEntity.getQnaStatus());
        qnaDTO.setQnaTitle(qnaEntity.getQnaTitle());
        qnaDTO.setQnaDate(qnaEntity.getQnaDate());
        return qnaDTO;
    }

    public static QnaDTO toDTO(QnaEntity qnaEntity){
        QnaDTO qnaDTO = new QnaDTO();
        qnaDTO.setUserEmail(qnaEntity.getUserEmail());
        qnaDTO.setQnaDate(qnaEntity.getQnaDate());
        qnaDTO.setQnaStatus(qnaEntity.getQnaStatus());
        qnaDTO.setQnaTitle(qnaEntity.getQnaTitle());
        qnaDTO.setQnaGroup(qnaEntity.getQnaGroup());
        qnaDTO.setQnaIndent(qnaEntity.getQnaIndent());
        qnaDTO.setQnaParent(qnaEntity.getQnaParent());
        qnaDTO.setQnaOrder(qnaEntity.getQnaOrder());
        qnaDTO.setQnaImage1(qnaEntity.getQnaImage1());
        qnaDTO.setQnaContent(qnaEntity.getQnaContent());
        return qnaDTO;
    }
}
