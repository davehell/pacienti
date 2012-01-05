import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;
 
public class UserTest extends UnitTest {
    
    @Before
    public void setup() {
        Fixtures.deleteAll();
    }
 
    @Test
    public void createAndRetrieveUser() {
        // Create a new user and save it
        new User("bob", "secret", "Bobík", "Ostrava").save();

        // Retrieve the user with bob username
        User bob = User.find("byUsername", "bob").first();

        // Test 
        assertNotNull(bob);
        assertEquals("Ostrava", bob.modul);
    }
    
    @Test
    public void tryConnectAsUser() {
        // Create a new user and save it
        new User("bob", "secret", "Bobík", "Ostrava").save();

        // Test 
        assertNotNull(User.connect("bob", "secret"));
        assertNull(User.connect("bob", "badpassword"));
        assertNull(User.connect("tom", "secret"));
    }

 
}