package seedu.traveltrio.model.activity;

import org.junit.jupiter.api.Test;
import seedu.traveltrio.TravelTrioException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActivityTest {

    @Test
    public void overlapsWith_sameDateOverlappingTime_returnsTrue() throws TravelTrioException {
        Activity a1 = new Activity("Diving", "Okinawa Sea", "2026-12-10", "09:00", "11:00");
        Activity a2 = new Activity("Hiking", "Mount Fuji", "2026-12-10", "10:00", "12:00");

        assertTrue(a1.overlapsWith(a2));
    }

    @Test
    public void overlapsWith_sameDateNonOverlappingTime_returnsFalse() throws TravelTrioException {
        Activity a1 = new Activity("Diving", "Okinawa Sea", "2026-12-10", "09:00", "11:00");
        Activity a2 = new Activity("Hiking", "Mount Fuji", "2026-12-10", "11:30", "14:00");

        assertFalse(a1.overlapsWith(a2));
    }

    @Test
    public void overlapsWith_differentDates_returnsFalse() throws TravelTrioException {
        Activity a1 = new Activity("Diving", "Okinawa Sea", "2026-12-10", "09:00", "11:00");
        Activity a2 = new Activity("Hiking", "Mount Fuji", "2026-12-11", "09:00", "11:00");

        assertFalse(a1.overlapsWith(a2));
    }

    @Test
    public void overlapsWith_nullDate_returnsFalse() throws TravelTrioException {
        Activity a1 = new Activity("Diving", "Okinawa Sea", null, "09:00", "11:00");
        Activity a2 = new Activity("Hiking", "Mount Fuji", "2026-12-10", "09:00", "11:00");

        assertFalse(a1.overlapsWith(a2));
    }

    @Test
    public void overlapsWith_nullTime_returnsFalse() throws TravelTrioException {
        Activity a1 = new Activity("Diving", "Okinawa Sea", "2026-12-10", null, null);
        Activity a2 = new Activity("Hiking", "Mount Fuji", "2026-12-10", "09:00", "11:00");

        assertFalse(a1.overlapsWith(a2));
    }

    @Test
    public void constructor_invalidDateFormat_throwsException() {
        assertThrows(TravelTrioException.class, () ->
                new Activity("Hiking", "Fuji", "10-12-2026", "09:00", "11:00"));
    }

    @Test
    public void constructor_invalidStartTimeFormat_throwsException() {
        assertThrows(TravelTrioException.class, () ->
                new Activity("Hiking", "Fuji", "2026-12-10", "9am", "11:00"));
    }

    @Test
    public void constructor_invalidEndTimeFormat_throwsException() {
        assertThrows(TravelTrioException.class, () ->
                new Activity("Hiking", "Fuji", "2026-12-10", "09:00", "11pm"));
    }

    @Test
    public void constructor_nullFields_noException() throws TravelTrioException {
        Activity a = new Activity("Hiking", null, null, null, null);
        assertEquals("Hiking", a.getName());
        assertNull(a.getDate());
        assertNull(a.getStart());
        assertNull(a.getEnd());
    }

    @Test
    public void setters_updateFieldsCorrectly() throws TravelTrioException {
        Activity a = new Activity("Hiking", "Fuji", "2026-12-10", "09:00", "11:00");
        a.setName("Swimming");
        a.setLocation("Beach");
        a.setDate("2026-12-15");
        a.setStart("10:00");
        a.setEnd("12:00");
        a.setRemark("Fun!");

        assertEquals("Swimming", a.getName());
        assertEquals("Beach", a.getLocation());
        assertEquals("2026-12-15", a.getDate());
        assertEquals("10:00", a.getStart());
        assertEquals("12:00", a.getEnd());
        assertEquals("Fun!", a.getRemark());
    }

    @Test
    public void toString_allFields_correctFormat() throws TravelTrioException {
        Activity a = new Activity("Hiking", "Fuji", "2026-12-10", "09:00", "11:00");
        String result = a.toString();
        assertTrue(result.contains("Hiking"));
        assertTrue(result.contains("Fuji"));
        assertTrue(result.contains("2026-12-10"));
        assertTrue(result.contains("09:00 to 11:00"));
    }

    @Test
    public void toString_missingFields_showsDashes() throws TravelTrioException {
        Activity a = new Activity("Hiking", null, null, null, null);
        String result = a.toString();
        assertTrue(result.contains("---"));
    }

    @Test
    public void formatForDisplay_withRemark_includesRemark() throws TravelTrioException {
        Activity a = new Activity("Hiking", "Fuji", "2026-12-10", "09:00", "11:00");
        a.setRemark("Bring sunscreen");
        String result = a.formatForDisplay();
        assertTrue(result.contains("Bring sunscreen"));
    }

    @Test
    public void formatForDisplay_withoutRemark_noRemarkLine() throws TravelTrioException {
        Activity a = new Activity("Hiking", "Fuji", "2026-12-10", "09:00", "11:00");
        String result = a.formatForDisplay();
        assertFalse(result.contains("Remark:"));
    }

    @Test
    public void formatForTableRow_correctColumnFormat() throws TravelTrioException {
        Activity a = new Activity("Hiking", "Fuji", "2026-12-10", "09:00", "11:00");
        String row = a.formatForTableRow(1);
        assertTrue(row.contains("1"));
        assertTrue(row.contains("Hiking"));
        assertTrue(row.contains("Fuji"));
        assertTrue(row.contains("2026-12-10"));
        assertTrue(row.contains("09:00 to 11:00"));
    }

    @Test
    public void overlapsWith_overnightActivity_detectsOverlapNextDay() throws TravelTrioException {
        // a1 runs from 22:00 to 02:00 (overnight, crosses midnight)
        Activity a1 = new Activity("Night tour", "City", "2026-12-10", "22:00", "02:00");
        // a2 starts at 01:00 the next day — should overlap with overnight a1
        Activity a2 = new Activity("Early hike", "Park", "2026-12-11", "01:00", "03:00");

        assertTrue(a1.overlapsWith(a2));
    }
}

