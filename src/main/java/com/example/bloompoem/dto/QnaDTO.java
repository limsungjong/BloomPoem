package com.example.bloompoem.dto;

import com.example.bloompoem.entity.QnaEntity;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QnaDTO {
    private int qnaNumber;

    private String userEmail;

    private Date qnaDate;

    private char qnaStatus;

    private String qnaTitle;

    private int qnaGroup;

    private int qnaIndent;

    private int qnaParent;

    private int qnaOrder;

    private String qnaImage1;

    private String qnaImage2;

    private String qnaImage3;

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
}
