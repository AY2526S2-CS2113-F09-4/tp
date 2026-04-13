package seedu.traveltrio.model.packing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PackingListTest {

    private PackingList packingList;

    @BeforeEach
    public void setUp() {
        packingList = new PackingList();
    }

    @Test
    public void isEmpty_newList_returnsTrue() {
        assertTrue(packingList.isEmpty());
    }

    @Test
    public void addItem_singleItem_sizeIsOne() {
        packingList.addItem(new PackingItem("Passport"));
        assertEquals(1, packingList.size());
    }

    @Test
    public void get_validIndex_returnsItem() {
        PackingItem item = new PackingItem("Passport");
        packingList.addItem(item);
        assertEquals(item, packingList.get(0));
    }

    @Test
    public void remove_validIndex_reducesSize() {
        packingList.addItem(new PackingItem("Passport"));
        packingList.remove(0);
        assertTrue(packingList.isEmpty());
    }

    @Test
    public void getItems_multipleItems_returnsAllItems() {
        packingList.addItem(new PackingItem("Passport"));
        packingList.addItem(new PackingItem("Sunscreen"));
        List<PackingItem> items = packingList.getItems();
        assertEquals(2, items.size());
    }

    @Test
    public void toFileFormat_mixedPackedItems_correctFormat() {
        PackingItem packed = new PackingItem("Passport");
        packed.markPacked();
        PackingItem unpacked = new PackingItem("Sunscreen");
        packingList.addItem(packed);
        packingList.addItem(unpacked);
        String result = packingList.toFileFormat();
        assertTrue(result.contains("1|Passport"));
        assertTrue(result.contains("0|Sunscreen"));
    }

    @Test
    public void isEmpty_afterAddAndRemove_returnsTrue() {
        packingList.addItem(new PackingItem("Hat"));
        packingList.remove(0);
        assertTrue(packingList.isEmpty());
    }

    @Test
    public void addItem_multipleItems_sizeCorrect() {
        for (int i = 0; i < 5; i++) {
            packingList.addItem(new PackingItem("Item " + i));
        }
        assertEquals(5, packingList.size());
        assertFalse(packingList.isEmpty());
    }
}
