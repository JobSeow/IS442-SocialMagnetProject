package socialmagnet.game;

import socialmagnet.dao.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.*;

public class CropTest {

    CropDAO cropDAO = new CropDAO();

    @Test
    public void populateListTest(){
        ArrayList<Crop> cropList = cropDAO.readCropList();
        assertEquals(4, cropList.size());
    }
}