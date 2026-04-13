package seedu.traveltrio.command.packing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.traveltrio.model.packing.PackingItem;
import seedu.traveltrio.model.packing.PackingList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteItemCommandTest {

    private PackingList packingList;

    @BeforeEach
    public void setUp() {
        packingList = new PackingList();
        packingList.addItem(new PackingItem("Passport"));
        packingList.addItem(new PackingItem("Sunscreen"));
    }

    @Test
    public void execute_validIndex_itemRemoved() {
        DeleteItemCommand cmd = new DeleteItemCommand(packingList, 1);
        String result = cmd.execute();
        assertEquals(1, packingList.size());
        assertTrue(result.contains("Passport"));
    }

    @Test
    public void execute_invalidIndexTooHigh_throwsException() {
        DeleteItemCommand cmd = new DeleteItemCommand(packingList, 99);
        assertThrows(IndexOutOfBoundsException.class, cmd::execute);
    }

    @Test
    public void execute_indexZero_throwsException() {
        DeleteItemCommand cmd = new DeleteItemCommand(packingList, 0);
        assertThrows(IndexOutOfBoundsException.class, cmd::execute);
    }

    @Test
    public void execute_lastItem_listBecomesEmpty() {
        packingList = new PackingList();
        packingList.addItem(new PackingItem("Hat"));
        new DeleteItemCommand(packingList, 1).execute();
        assertTrue(packingList.isEmpty());
    }

    @Test
    public void execute_secondItem_correctItemRemoved() {
        DeleteItemCommand cmd = new DeleteItemCommand(packingList, 2);
        cmd.execute();
        assertEquals(1, packingList.size());
        assertEquals("Passport", packingList.get(0).getName());
    }
}
