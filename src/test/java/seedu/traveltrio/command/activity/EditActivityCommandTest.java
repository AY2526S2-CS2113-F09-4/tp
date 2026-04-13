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

public class EditActivityCommandTest {

    private Trip trip;
    private ActivityList activityList;
    private Activity activity;

    @BeforeEach
    public void setUp() throws TravelTrioException {
        trip = new Trip("Test Trip", "2026-06-01", "2026-06-30");
        activityList = new ActivityList(trip);
        activity = new Activity("Hiking", "Fuji", "2026-06-10", "09:00", "11:00");
        activityList.add(activity);
    }

    @Test
    public void execute_updateName_nameChanged() throws TravelTrioException {
        EditActivityCommand cmd = new EditActivityCommand(activityList, 1,
                "Swimming", null, null, null, null);
        String result = cmd.execute("Test Trip");
        assertTrue(result.contains("Swimming"));
        assertEquals("Swimming", activityList.get(0).getName());
    }

    @Test
    public void execute_updateLocation_locationChanged() throws TravelTrioException {
        EditActivityCommand cmd = new EditActivityCommand(activityList, 1,
                null, "Okinawa", null, null, null);
        cmd.execute("Test Trip");
        assertEquals("Okinawa", activityList.get(0).getLocation());
    }

    @Test
    public void execute_updateDate_withinTripRange_dateChanged() throws TravelTrioException {
        EditActivityCommand cmd = new EditActivityCommand(activityList, 1,
                null, null, "2026-06-15", null, null);
        cmd.execute("Test Trip");
        assertEquals("2026-06-15", activityList.get(0).getDate());
    }

    @Test
    public void execute_updateDate_outsideTripRange_throwsException() {
        EditActivityCommand cmd = new EditActivityCommand(activityList, 1,
                null, null, "2026-07-05", null, null);
        assertThrows(TravelTrioException.class, () -> cmd.execute("Test Trip"));
    }

    @Test
    public void execute_updateStartAndEnd_timesChanged() throws TravelTrioException {
        EditActivityCommand cmd = new EditActivityCommand(activityList, 1,
                null, null, null, "10:00", "12:00");
        cmd.execute("Test Trip");
        assertEquals("10:00", activityList.get(0).getStart());
        assertEquals("12:00", activityList.get(0).getEnd());
    }

    @Test
    public void execute_invalidIndex_throwsException() {
        EditActivityCommand cmd = new EditActivityCommand(activityList, 99,
                "Test", null, null, null, null);
        assertThrows(TravelTrioException.class, () -> cmd.execute("Test Trip"));
    }

    @Test
    public void execute_indexZero_throwsException() {
        EditActivityCommand cmd = new EditActivityCommand(activityList, 0,
                "Test", null, null, null, null);
        assertThrows(TravelTrioException.class, () -> cmd.execute("Test Trip"));
    }

    @Test
    public void execute_nullName_nameNotChanged() throws TravelTrioException {
        EditActivityCommand cmd = new EditActivityCommand(activityList, 1,
                null, null, null, null, null);
        cmd.execute("Test Trip");
        assertEquals("Hiking", activityList.get(0).getName());
    }
}
