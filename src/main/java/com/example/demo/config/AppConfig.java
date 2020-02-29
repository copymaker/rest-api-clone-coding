package com.example.demo.config;

import com.example.demo.account.Account;
import com.example.demo.account.AccountRole;
import com.example.demo.account.AccountService;
import com.example.demo.common.AppProperties;
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
public class AppConfig {

    private final AppProperties appProperties;

    public AppConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

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
                Account admin = Account.builder()
                    .email(appProperties.getAdminUsername())
                    .password(appProperties.getAdminPassword())
                    .roles(new HashSet<>(Arrays.asList(AccountRole.ADMIN, AccountRole.USER)))
                    .build();

                accountService.saveAccount(admin);

                Account user = Account.builder()
                    .email(appProperties.getUserUsername())
                    .password(appProperties.getUserPassword())
                    .roles(new HashSet<>(Arrays.asList(AccountRole.USER)))
                    .build();

                accountService.saveAccount(user);
            }
        };
    }

}
