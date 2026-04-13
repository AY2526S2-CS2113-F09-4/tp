package seedu.traveltrio.model.packing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PackingItemTest {

    @Test
    public void constructor_newItem_isNotPacked() {
        PackingItem item = new PackingItem("Passport");
        assertFalse(item.isPacked());
        assertEquals("Passport", item.getName());
    }

    @Test
    public void markPacked_unpackedItem_becomesPackedTrue() {
        PackingItem item = new PackingItem("Passport");
        item.markPacked();
        assertTrue(item.isPacked());
    }

    @Test
    public void markUnpacked_packedItem_becomesPackedFalse() {
        PackingItem item = new PackingItem("Passport");
        item.markPacked();
        item.markUnpacked();
        assertFalse(item.isPacked());
    }

    @Test
    public void toFileFormat_unpackedItem_startsWithZero() {
        PackingItem item = new PackingItem("Sunscreen");
        assertEquals("0|Sunscreen", item.toFileFormat());
    }

    @Test
    public void toFileFormat_packedItem_startsWithOne() {
        PackingItem item = new PackingItem("Sunscreen");
        item.markPacked();
        assertEquals("1|Sunscreen", item.toFileFormat());
    }

    @Test
    public void toString_unpackedItem_hasEmptyCheckbox() {
        PackingItem item = new PackingItem("Hat");
        assertEquals("[ ] Hat", item.toString());
    }

    @Test
    public void toString_packedItem_hasFilledCheckbox() {
        PackingItem item = new PackingItem("Hat");
        item.markPacked();
        assertEquals("[X] Hat", item.toString());
    }
}
