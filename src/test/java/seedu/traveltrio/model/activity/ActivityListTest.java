package seedu.traveltrio.model.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.Trip;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActivityListTest {

    private Trip trip;
    private ActivityList list;

    @BeforeEach
    public void setUp() {
        trip = new Trip("Test Trip", "2026-06-01", "2026-06-30");
        list = new ActivityList(trip);
    }

    @Test
    public void add_singleActivity_sizeBecomesOne() throws TravelTrioException {
        Activity a = new Activity("Hiking", "Fuji", "2026-06-10", "09:00", "11:00");
        list.add(a);
        assertEquals(1, list.size());
    }

    @Test
    public void add_overlappingActivities_throwsException() throws TravelTrioException {
        Activity a1 = new Activity("Hiking", "Fuji", "2026-06-10", "09:00", "11:00");
        Activity a2 = new Activity("Diving", "Beach", "2026-06-10", "10:00", "12:00");
        list.add(a1);
        assertThrows(TravelTrioException.class, () -> list.add(a2));
    }

    @Test
    public void add_nonOverlappingActivities_bothAdded() throws TravelTrioException {
        Activity a1 = new Activity("Hiking", "Fuji", "2026-06-10", "09:00", "11:00");
        Activity a2 = new Activity("Diving", "Beach", "2026-06-10", "13:00", "15:00");
        list.add(a1);
        list.add(a2);
        assertEquals(2, list.size());
    }

    @Test
    public void add_activitiesSortedByDate() throws TravelTrioException {
        Activity a1 = new Activity("Later", "Loc", "2026-06-15", "09:00", "10:00");
        Activity a2 = new Activity("Earlier", "Loc", "2026-06-10", "09:00", "10:00");
        list.add(a1);
        list.add(a2);
        assertEquals("Earlier", list.get(0).getName());
        assertEquals("Later", list.get(1).getName());
    }

    @Test
    public void add_activitiesSortedByTimeOnSameDate() throws TravelTrioException {
        Activity a1 = new Activity("Afternoon", "Loc", "2026-06-10", "14:00", "15:00");
        Activity a2 = new Activity("Morning", "Loc", "2026-06-10", "09:00", "10:00");
        list.add(a1);
        list.add(a2);
        assertEquals("Morning", list.get(0).getName());
        assertEquals("Afternoon", list.get(1).getName());
    }

    @Test
    public void remove_validIndex_reducesSize() throws TravelTrioException {
        Activity a = new Activity("Hiking", "Fuji", "2026-06-10", "09:00", "11:00");
        list.add(a);
        list.remove(0);
        assertEquals(0, list.size());
    }

    @Test
    public void isEmpty_emptyList_returnsTrue() {
        assertTrue(list.isEmpty());
    }

    @Test
    public void isEmpty_nonEmptyList_returnsFalse() throws TravelTrioException {
        list.add(new Activity("Hiking", "Fuji", "2026-06-10", "09:00", "11:00"));
        assertFalse(list.isEmpty());
    }

    @Test
    public void findActivities_matchingKeyword_returnsResults() throws TravelTrioException {
        list.add(new Activity("Hiking trip", "Fuji", "2026-06-10", "09:00", "10:00"));
        list.add(new Activity("Diving", "Beach", "2026-06-11", "09:00", "10:00"));
        ArrayList<Activity> results = list.findActivities("hik");
        assertEquals(1, results.size());
        assertEquals("Hiking trip", results.get(0).getName());
    }

    @Test
    public void findActivities_caseInsensitive_returnsResults() throws TravelTrioException {
        list.add(new Activity("HIKING", "Fuji", "2026-06-10", "09:00", "10:00"));
        ArrayList<Activity> results = list.findActivities("hiking");
        assertEquals(1, results.size());
    }

    @Test
    public void findActivities_noMatch_returnsEmptyList() throws TravelTrioException {
        list.add(new Activity("Hiking", "Fuji", "2026-06-10", "09:00", "10:00"));
        ArrayList<Activity> results = list.findActivities("xyz");
        assertTrue(results.isEmpty());
    }

    @Test
    public void getTrip_returnsTripAssociated() {
        assertEquals(trip, list.getTrip());
    }

    @Test
    public void isTripOpen_closedTrip_returnsFalse() {
        assertFalse(list.isTripOpen());
    }

    @Test
    public void isTripOpen_openTrip_returnsTrue() {
        trip.setOpen(true);
        assertTrue(list.isTripOpen());
    }
}
