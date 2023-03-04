package com.example.bloompoem.domain.dto;

import com.example.bloompoem.dto.TestUserDTO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Getter
public class MemberDetails implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    TestUserDTO userDTO;
    public MemberDetails(TestUserDTO userDTO) {
        this.userDTO = userDTO;
    }

    /**
     * 해당 유저의 권한 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * 비밀번호
     */
    @Override
    public String getPassword() {
        return userDTO.getUserOtp();
    }

    /**
     * PK값
     */
    @Override
    public String getUsername() {
        return userDTO.getUserEmail();
    }

    /**
     * 계정 만료 여부
     * true : 만료 안됨
     * false : 만료
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 잠김 여부
     * true : 잠기지 않음
     * false : 잠김
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 비밀번호 만료 여부
     * true : 만료 안됨
     * false : 만료
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 사용자 활성화 여부
     * ture : 활성화
     * false : 비활성화
     * @return
     */
    @Override
    public boolean isEnabled() {
        return false;
    }
}