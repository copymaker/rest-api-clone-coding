package com.example.demo.config;

import com.example.demo.account.Account;
import com.example.demo.account.AccountRole;
import com.example.demo.account.AccountService;
import java.util.Arrays;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {

            @Autowired
            AccountService accountService;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                Account account = Account.builder()
                    .email("keesun@email.com")
                    .password("1234")
                    .roles(new HashSet<>(Arrays.asList(AccountRole.ADMIN, AccountRole.USER)))
                    .build();

                accountService.saveAccount(account);
            }
        };
    }

}
