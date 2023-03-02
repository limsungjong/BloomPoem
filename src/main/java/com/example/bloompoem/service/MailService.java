package com.example.bloompoem.service;

import com.example.bloompoem.domain.dto.MailDTO;
import com.example.bloompoem.util.MailHandler;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "sung8881@naver.com";

    public void mailSend(MailDTO mailDTO) {
        try {
            MailHandler mailHandler = new MailHandler(javaMailSender);
            // 받는 사람
            mailHandler.setTo(mailDTO.getEmailAddress());
            // 보내는 사람
            mailHandler.setFrom(MailService.FROM_ADDRESS);
            // 제목
            mailHandler.setSubject(mailDTO.getTitle());
            // HTML Layout
            mailHandler.setText("    <table\n" +
                    "      border=\"0\"\n" +
                    "      cellpadding=\"0\"\n" +
                    "      cellspacing=\"0\"\n" +
                    "      style=\"font-family: Arial, Helvetica, sans-serif\"\n" +
                    "      width=\"100%\"\n" +
                    "      bgcolor=\"#f3f3f3\"\n" +
                    "    >\n" +
                    "      <tbody>\n" +
                    "        <tr>\n" +
                    "          <td align=\"center\">\n" +
                    "            <table\n" +
                    "              cellpadding=\"0\"\n" +
                    "              cellspacing=\"0\"\n" +
                    "              style=\"\n" +
                    "                -webkit-border-radius: 3px;\n" +
                    "                -moz-border-radius: 3px;\n" +
                    "                border-radius: 3px;\n" +
                    "                -moz-box-shadow: 1px 1px 0 #eaeaea;\n" +
                    "                -webkit-box-shadow: 1px 1px 0 #eaeaea;\n" +
                    "                box-shadow: 1px 1px 0 #eaeaea;\n" +
                    "                width: 500px;\n" +
                    "                margin: 25px auto;\n" +
                    "              \"\n" +
                    "              bgcolor=\"#ffffff\"\n" +
                    "            >\n" +
                    "              <tbody>\n" +
                    "                <tr style=\"border: 0\">\n" +
                    "                  <td\n" +
                    "                    align=\"left\"\n" +
                    "                    style=\"\n" +
                    "                      border-top-left-radius: 3px;\n" +
                    "                      border-top-right-radius: 3px;\n" +
                    "                      padding: 20px;\n" +
                    "                      border: 0px;\n" +
                    "                    \"\n" +
                    "                    bgcolor=\"000000\"\n" +
                    "                  >\n" +
                    "                    <a\n" +
                    "                      href=\"https://academy.dream-coding.com/\"\n" +
                    "                      style=\"\n" +
                    "                        text-decoration: none;\n" +
                    "                        font-size: 22px;\n" +
                    "                        border: none;\n" +
                    "                      \"\n" +
                    "                      target=\"_blank\"\n" +
                    "                      rel=\"noreferrer noopener\"\n" +
                    "                      ><span style=\"color: rgb(255, 141, 137)\"\n" +
                    "                        >&nbsp;Bloom Poem&nbsp;</span\n" +
                    "                      ></a\n" +
                    "                    ><span style=\"color: rgb(255, 141, 137)\"> </span>\n" +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "                <tr style=\"border: 0\">\n" +
                    "                  <td style=\"padding: 20px; border: 0\" bgcolor=\"#fff\">\n" +
                    "                    <h3 style=\"text-align: center\">\n" +
                    "                      <img\n" +
                    "                        src=\"http://localhost:9000/image/logo%EC%B5%9C%EC%A2%852.png\"\n" +
                    "                        style=\"width: 300px\"\n" +
                    "                        loading=\"lazy\"\n" +
                    "                      />\n" +
                    "                    </h3>\n" +
                    "\n" +
                    "                    <h3>안녕하세요 "+mailDTO.getUserName()+"님</h3>\n" +
                    "\n" +
                    "                    <p>저희 블룸포엠의 회원이 되시기로 한 것을 감사드립니다.</p>\n" +
                    "\n" +
                    "                    <p>아래의 인증번호를 입력해주세요</p>\n" +
                    "                    <br />\n" +
                    "                    <p style=\"text-align: center\">\n" +
                    "                      <span style=\"font-size: large; font-size: 2em\"\n" +
                    "                        >"+mailDTO.getOTP()+"</span\n" +
                    "                      >\n" +
                    "                    </p>\n" +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "                <tr style=\"border: 0\">\n" +
                    "                  <td\n" +
                    "                    align=\"center\"\n" +
                    "                    style=\"padding: 20px 40px; border: 0px\"\n" +
                    "                    bgcolor=\"FFEBF1\"\n" +
                    "                  >\n" +
                    "                    <span style=\"color: rgb(255, 141, 137)\">\n" +
                    "                      <a\n" +
                    "                        href=\"http://localhost:9000/sign/sign_in\"\n" +
                    "                        rel=\"noreferrer noopener\"\n" +
                    "                        target=\"_blank\"\n" +
                    "                        >로그인 하기</a\n" +
                    "                      >\n" +
                    "                    </span>\n" +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "              </tbody>\n" +
                    "            </table>\n" +
                    "          </td>\n" +
                    "        </tr>\n" +
                    "      </tbody>\n" +
                    "    </table>",true);

            mailHandler.send();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
