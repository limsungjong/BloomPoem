package com.example.bloompoem.domain.dto;

import com.example.bloompoem.entity.TestUserEntity;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@ToString
public class CustomUserDetail implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    TestUserEntity testUserEntity;

    public CustomUserDetail(TestUserEntity testUserEntity) {
        this.testUserEntity = testUserEntity;
    }

    /**
     * 해당 유저의 권한 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return testUserEntity.getUserRole();
            }
        });
        return collection;
    }

    /**
     * 비밀번호
     */
    @Override
    public String getPassword() {
        return testUserEntity.getUserOtp();
    }

    /**
     * PK값
     */
    @Override
    public String getUsername() {
        return testUserEntity.getUserEmail();
    }

    /**
     * 계정 만료 여부
     * true : 만료 안됨
     * false : 만료
     *
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
     *
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
     *
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
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}