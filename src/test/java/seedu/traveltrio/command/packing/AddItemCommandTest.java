package seedu.traveltrio.command.packing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.traveltrio.model.packing.PackingList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddItemCommandTest {

    private PackingList packingList;

    @BeforeEach
    public void setUp() {
        packingList = new PackingList();
    }

    @Test
    public void execute_validName_itemAdded() {
        AddItemCommand cmd = new AddItemCommand(packingList, "Passport");
        String result = cmd.execute();
        assertEquals(1, packingList.size());
        assertEquals("Passport", packingList.get(0).getName());
        assertTrue(result.contains("Passport"));
    }

    @Test
    public void execute_nameWithWhitespace_trimmed() {
        AddItemCommand cmd = new AddItemCommand(packingList, "  Sunscreen  ");
        cmd.execute();
        assertEquals("Sunscreen", packingList.get(0).getName());
    }

    @Test
    public void execute_emptyName_throwsException() {
        AddItemCommand cmd = new AddItemCommand(packingList, "");
        assertThrows(IllegalArgumentException.class, cmd::execute);
    }

    @Test
    public void execute_nullName_throwsException() {
        AddItemCommand cmd = new AddItemCommand(packingList, null);
        assertThrows(IllegalArgumentException.class, cmd::execute);
    }

    @Test
    public void execute_whitespaceOnlyName_throwsException() {
        AddItemCommand cmd = new AddItemCommand(packingList, "   ");
        assertThrows(IllegalArgumentException.class, cmd::execute);
    }

    @Test
    public void execute_multipleItems_allAdded() {
        new AddItemCommand(packingList, "Passport").execute();
        new AddItemCommand(packingList, "Sunscreen").execute();
        new AddItemCommand(packingList, "Hat").execute();
        assertEquals(3, packingList.size());
    }
}
