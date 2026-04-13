package seedu.traveltrio.model.trip;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TripListTest {

    @Test
    public void add_singleTrip_sizeIsOne() {
        TripList list = new TripList();
        list.add(new Trip("Japan", "2026-05-01", "2026-05-10"));
        assertEquals(1, list.size());
    }

    @Test
    public void isEmpty_emptyList_returnsTrue() {
        TripList list = new TripList();
        assertTrue(list.isEmpty());
    }

    @Test
    public void isEmpty_nonEmptyList_returnsFalse() {
        TripList list = new TripList();
        list.add(new Trip("Japan", "2026-05-01", "2026-05-10"));
        assertFalse(list.isEmpty());
    }

    @Test
    public void remove_validIndex_reducesSize() {
        TripList list = new TripList();
        list.add(new Trip("Japan", "2026-05-01", "2026-05-10"));
        list.remove(0);
        assertTrue(list.isEmpty());
    }

    @Test
    public void get_validIndex_returnsTrip() {
        TripList list = new TripList();
        Trip trip = new Trip("Japan", "2026-05-01", "2026-05-10");
        list.add(trip);
        assertEquals(trip, list.get(0));
    }

    @Test
    public void findTrips_matchingKeyword_returnsResults() {
        TripList list = new TripList();
        list.add(new Trip("Japan Trip", "2026-05-01", "2026-05-10"));
        list.add(new Trip("Korea Tour", "2026-06-01", "2026-06-10"));
        ArrayList<Trip> results = list.findTrips("japan");
        assertEquals(1, results.size());
        assertEquals("Japan Trip", results.get(0).getName());
    }

    @Test
    public void findTrips_caseInsensitive_returnsResults() {
        TripList list = new TripList();
        list.add(new Trip("JAPAN TRIP", "2026-05-01", "2026-05-10"));
        ArrayList<Trip> results = list.findTrips("japan");
        assertEquals(1, results.size());
    }

    @Test
    public void findTrips_noMatch_returnsEmptyList() {
        TripList list = new TripList();
        list.add(new Trip("Japan Trip", "2026-05-01", "2026-05-10"));
        ArrayList<Trip> results = list.findTrips("xyz");
        assertTrue(results.isEmpty());
    }

    @Test
    public void contains_existingTrip_returnsTrue() {
        TripList list = new TripList();
        Trip trip = new Trip("Japan", "2026-05-01", "2026-05-10");
        list.add(trip);
        assertTrue(list.contains(trip));
    }

    @Test
    public void contains_nonExistingTrip_returnsFalse() {
        TripList list = new TripList();
        Trip trip = new Trip("Japan", "2026-05-01", "2026-05-10");
        assertFalse(list.contains(trip));
    }
}
