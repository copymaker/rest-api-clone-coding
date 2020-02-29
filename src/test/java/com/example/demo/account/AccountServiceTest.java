package com.example.demo.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void findByUsername() {
        // given
        String username = "keesun@email";
        String password = "1234";

        Account account = Account.builder()
            .email(username)
            .password(password)
            .roles(new HashSet<>(Arrays.asList(AccountRole.ADMIN, AccountRole.USER)))
            .build();

        accountService.saveAccount(account);

        // when
        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // then
        assertThat(passwordEncoder.matches(password, userDetails.getPassword())).isTrue();
    }

    @Test
    void findByUsernameFail() {
        // given
        String username = "random@email.com";

        // when & then
        UsernameNotFoundException thrown = assertThrows(UsernameNotFoundException.class,
            () -> accountService.loadUserByUsername(username));

        assertThat(thrown.getMessage()).contains(username);
    }

}