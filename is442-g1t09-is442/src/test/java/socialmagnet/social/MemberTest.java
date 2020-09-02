package socialmagnet.social;

import socialmagnet.dao.MemberDAO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MemberTest {
    MemberDAO memberDAO = new MemberDAO();
    
    @Test
    public void existInDBTest(){
        boolean result = memberDAO.existInDB("daryl");
        assertTrue(result);
    }

    @Test
    public void checkFriendshipTest(){
        boolean check1 = memberDAO.checkFriendship("daryl","job");
        assertTrue(check1);
    }

    @Test
    public void checkPasswordTest(){
        boolean checkPassword = memberDAO.checkPassword("daryl","password");
        assertTrue(checkPassword);
    }
}