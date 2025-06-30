package mx.plusnetwork.services;

import mx.plusnetwork.TestConfig;
import mx.plusnetwork.data.model.Role;
import mx.plusnetwork.data.model.User;
import mx.plusnetwork.services.UserService;
import mx.plusnetwork.services.exceptions.ForbiddenException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testCreateUserForbidden() {
        User user = User.builder().mobile("666000666").firstName("k").role(Role.ADMIN).build();
        assertThrows(ForbiddenException.class, () -> this.userService.createUser(user, Role.MANAGER));
    }
}
