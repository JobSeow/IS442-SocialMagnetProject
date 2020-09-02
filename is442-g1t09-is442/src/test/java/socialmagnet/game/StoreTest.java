package socialmagnet.game;

import socialmagnet.controller.StoreController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.*;

public class StoreTest {

    StoreController storeController = new StoreController();

    @Test
    public void populateStoreTest(){
        Store store = storeController.getStore();
        ArrayList<Crop> stocks = store.getStocks();

        assertEquals(4, stocks.size());
    }
}