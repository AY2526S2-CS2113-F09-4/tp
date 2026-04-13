package seedu.traveltrio.command.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.trip.Trip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddRemarkCommandTest {

    private Trip trip;
    private ActivityList activityList;

    @BeforeEach
    public void setUp() throws TravelTrioException {
        trip = new Trip("Test Trip", "2026-06-01", "2026-06-30");
        activityList = new ActivityList(trip);
        activityList.add(new Activity("Hiking", "Fuji", "2026-06-10", "09:00", "11:00"));
    }

    @Test
    public void execute_validIndex_remarkAdded() throws TravelTrioException {
        AddRemarkCommand cmd = new AddRemarkCommand(activityList, 1, "Bring water");
        String result = cmd.execute("Test Trip");
        assertEquals("Bring water", activityList.get(0).getRemark());
        assertTrue(result.contains("Bring water"));
        assertTrue(result.contains("Hiking"));
    }

    @Test
    public void execute_invalidIndexTooHigh_throwsException() {
        AddRemarkCommand cmd = new AddRemarkCommand(activityList, 99, "Remark");
        assertThrows(TravelTrioException.class, () -> cmd.execute("Test Trip"));
    }

    @Test
    public void execute_indexZero_throwsException() {
        AddRemarkCommand cmd = new AddRemarkCommand(activityList, 0, "Remark");
        assertThrows(TravelTrioException.class, () -> cmd.execute("Test Trip"));
    }

    @Test
    public void execute_validIndex_resultContainsTripName() throws TravelTrioException {
        AddRemarkCommand cmd = new AddRemarkCommand(activityList, 1, "Bring jacket");
        String result = cmd.execute("Test Trip");
        assertTrue(result.contains("Test Trip"));
    }
}
