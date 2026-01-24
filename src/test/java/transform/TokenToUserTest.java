package transform;


import com.adrign93.tokenGenerator.domain.entity.User;
import com.adrign93.tokenGenerator.transform.TokenToUser;
import com.adrign93.tokenGenerator.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TokenToUserTest {

    @Test
    void testTokenToUser() {
        User user = TokenToUser.mapTokenToUser(TestUtils.generateTokenRequest());
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getUsername());
        Assertions.assertNotNull(user.getPassword());
        Assertions.assertNotNull(user.getEntity());
        Assertions.assertEquals("username", user.getUsername());
        Assertions.assertEquals("password", user.getPassword());
        Assertions.assertEquals("entity", user.getEntity());
    }

}