package seedu.traveltrio.model.trip;

import org.junit.jupiter.api.Test;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TripTest {

    @Test
    public void constructor_validArgs_fieldsSetCorrectly() {
        Trip trip = new Trip("Japan", "2026-05-01", "2026-05-10");
        assertEquals("Japan", trip.getName());
        assertEquals("2026-05-01", trip.getStartDate());
        assertEquals("2026-05-10", trip.getEndDate());
        assertFalse(trip.isOpen());
    }

    @Test
    public void setters_updateFieldsCorrectly() {
        Trip trip = new Trip("Japan", "2026-05-01", "2026-05-10");
        trip.setName("Korea");
        trip.setStartDate("2026-06-01");
        trip.setEndDate("2026-06-15");
        assertEquals("Korea", trip.getName());
        assertEquals("2026-06-01", trip.getStartDate());
        assertEquals("2026-06-15", trip.getEndDate());
    }

    @Test
    public void setOpen_true_isOpenReturnsTrue() {
        Trip trip = new Trip("Japan", "2026-05-01", "2026-05-10");
        trip.setOpen(true);
        assertTrue(trip.isOpen());
    }

    @Test
    public void setOpen_false_isOpenReturnsFalse() {
        Trip trip = new Trip("Japan", "2026-05-01", "2026-05-10");
        trip.setOpen(true);
        trip.setOpen(false);
        assertFalse(trip.isOpen());
    }

    @Test
    public void toString_returnsNameAndDates() {
        Trip trip = new Trip("Japan", "2026-05-01", "2026-05-10");
        String result = trip.toString();
        assertTrue(result.contains("Japan"));
        assertTrue(result.contains("2026-05-01"));
        assertTrue(result.contains("2026-05-10"));
    }

    @Test
    public void getActivities_newTrip_emptyActivityList() {
        Trip trip = new Trip("Japan", "2026-05-01", "2026-05-10");
        assertNotNull(trip.getActivities());
        assertTrue(trip.getActivities().isEmpty());
    }

    @Test
    public void getBudgets_newTrip_emptyBudgetList() {
        Trip trip = new Trip("Japan", "2026-05-01", "2026-05-10");
        assertNotNull(trip.getBudgets());
        assertTrue(trip.getBudgets().isEmpty());
    }

    @Test
    public void getPackingList_newTrip_emptyPackingList() {
        Trip trip = new Trip("Japan", "2026-05-01", "2026-05-10");
        assertNotNull(trip.getPackingList());
        assertTrue(trip.getPackingList().isEmpty());
    }

    @Test
    public void formatForList_noBudget_noTotalSpent() {
        Trip trip = new Trip("Japan", "2026-05-01", "2026-05-10");
        String result = trip.formatForList();
        assertTrue(result.contains("Japan"));
        assertFalse(result.contains("Total Spent"));
    }

    @Test
    public void formatForList_withBudget_showsTotalSpent() throws TravelTrioException {
        Trip trip = new Trip("Japan", "2026-05-01", "2026-05-10");
        Activity a = new Activity("Hiking", "Fuji", "2026-05-05", "09:00", "11:00");
        trip.getActivities().add(a);
        trip.getBudgets().addBudget(a, new seedu.traveltrio.model.budget.Budget(100.0, a));
        String result = trip.formatForList();
        assertTrue(result.contains("Total Spent"));
    }
}
