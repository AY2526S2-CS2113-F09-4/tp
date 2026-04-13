package seedu.traveltrio.command.packing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.traveltrio.model.packing.PackingItem;
import seedu.traveltrio.model.packing.PackingList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckItemCommandTest {

    private PackingList packingList;

    @BeforeEach
    public void setUp() {
        packingList = new PackingList();
        packingList.addItem(new PackingItem("Passport"));
        packingList.addItem(new PackingItem("Sunscreen"));
    }

    @Test
    public void execute_validIndex_itemMarkedPacked() {
        CheckItemCommand cmd = new CheckItemCommand(packingList, 1);
        String result = cmd.execute();
        assertTrue(packingList.get(0).isPacked());
        assertTrue(result.contains("Passport"));
    }

    @Test
    public void execute_invalidIndexTooHigh_throwsException() {
        CheckItemCommand cmd = new CheckItemCommand(packingList, 99);
        assertThrows(IndexOutOfBoundsException.class, cmd::execute);
    }

    @Test
    public void execute_indexZero_throwsException() {
        CheckItemCommand cmd = new CheckItemCommand(packingList, 0);
        assertThrows(IndexOutOfBoundsException.class, cmd::execute);
    }

    @Test
    public void execute_secondItem_correctItemPacked() {
        CheckItemCommand cmd = new CheckItemCommand(packingList, 2);
        cmd.execute();
        assertTrue(packingList.get(1).isPacked());
    }

    @Test
    public void execute_result_containsMarkedAsPacked() {
        CheckItemCommand cmd = new CheckItemCommand(packingList, 1);
        String result = cmd.execute();
        assertTrue(result.toLowerCase().contains("packed"));
    }
}
