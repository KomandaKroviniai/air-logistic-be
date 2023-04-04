package com.university.logisticsappbe.service;

import com.university.logisticsappbe.config.WebSecurity;
import com.university.logisticsappbe.model.api.CreateUserRequest;
import com.university.logisticsappbe.model.domain.DtoUser;
import com.university.logisticsappbe.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void whenUserCreated_ShouldReturnEncryptedPassword() {
        String password = "password";
        CreateUserRequest request = new CreateUserRequest(
                "firstName",
                "lastName",
                "male",
                "email@email.email",
                "+37011111111",
                password,
                "manager",
                "address");

        UserRepository repository = Mockito.mock(UserRepository.class);
        WebSecurity webSecurity = Mockito.mock(WebSecurity.class);

        Mockito.when(webSecurity.passwordEncoder())
                .thenReturn(new BCryptPasswordEncoder());
        UserService service = new UserService(repository, passwordEncoder);

        DtoUser actual = service.createUser(request);

        assertNotEquals(password, actual.getPassword());
    }
}