package seedu.traveltrio.command.packing;

import org.junit.jupiter.api.Test;
import seedu.traveltrio.model.packing.PackingItem;
import seedu.traveltrio.model.packing.PackingList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListItemCommandTest {

    @Test
    public void execute_emptyList_returnsEmptyMessage() {
        PackingList packingList = new PackingList();
        ListItemCommand cmd = new ListItemCommand(packingList);
        String result = cmd.execute();
        assertEquals("Packing list is empty.", result);
    }

    @Test
    public void execute_oneItem_listShown() {
        PackingList packingList = new PackingList();
        packingList.addItem(new PackingItem("Passport"));
        ListItemCommand cmd = new ListItemCommand(packingList);
        String result = cmd.execute();
        assertTrue(result.contains("Passport"));
        assertTrue(result.startsWith("Packing List:"));
    }

    @Test
    public void execute_multipleItems_allShown() {
        PackingList packingList = new PackingList();
        packingList.addItem(new PackingItem("Passport"));
        packingList.addItem(new PackingItem("Sunscreen"));
        packingList.addItem(new PackingItem("Hat"));
        ListItemCommand cmd = new ListItemCommand(packingList);
        String result = cmd.execute();
        assertTrue(result.contains("Passport"));
        assertTrue(result.contains("Sunscreen"));
        assertTrue(result.contains("Hat"));
    }

    @Test
    public void execute_packedItem_showsCheckedStatus() {
        PackingList packingList = new PackingList();
        PackingItem item = new PackingItem("Passport");
        item.markPacked();
        packingList.addItem(item);
        ListItemCommand cmd = new ListItemCommand(packingList);
        String result = cmd.execute();
        assertTrue(result.contains("[X]"));
    }

    @Test
    public void execute_unpackedItem_showsUncheckedStatus() {
        PackingList packingList = new PackingList();
        packingList.addItem(new PackingItem("Sunscreen"));
        ListItemCommand cmd = new ListItemCommand(packingList);
        String result = cmd.execute();
        assertTrue(result.contains("[ ]"));
    }

    @Test
    public void execute_itemsNumbered_indexIsCorrect() {
        PackingList packingList = new PackingList();
        packingList.addItem(new PackingItem("First"));
        packingList.addItem(new PackingItem("Second"));
        ListItemCommand cmd = new ListItemCommand(packingList);
        String result = cmd.execute();
        assertTrue(result.contains("1."));
        assertTrue(result.contains("2."));
    }
}
