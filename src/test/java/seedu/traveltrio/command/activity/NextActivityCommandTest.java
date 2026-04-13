package seedu.traveltrio.command.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.trip.Trip;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NextActivityCommandTest {

    private Trip trip;
    private ActivityList activityList;

    @BeforeEach
    public void setUp() {
        trip = new Trip("Test Trip", "2020-01-01", "2099-12-31");
        activityList = new ActivityList(trip);
    }

    @Test
    public void execute_emptyList_returnsNoActivitiesMessage() throws TravelTrioException {
        NextActivityCommand cmd = new NextActivityCommand(activityList);
        String result = cmd.execute("Test Trip");
        assertEquals("No activities found in this trip.", result);
    }

    @Test
    public void execute_allActivitiesInPast_returnsNoneFound() throws TravelTrioException {
        activityList.add(new Activity("Old hike", "Past", "2020-01-05", "09:00", "11:00"));
        NextActivityCommand cmd = new NextActivityCommand(activityList);
        String result = cmd.execute("Test Trip");
        assertEquals("No upcoming activities found.", result);
    }

    @Test
    public void execute_futureActivity_returnsNextActivity() throws TravelTrioException {
        // Build a date far in the future using current time + some days
        String futureDate = LocalDate.now().plusDays(10)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // Ensure trip dates encompass the future activity
        Trip futureTrip = new Trip("Future Trip",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ActivityList futureList = new ActivityList(futureTrip);
        futureList.add(new Activity("Future Hike", "Mountain", futureDate, "09:00", "11:00"));

        NextActivityCommand cmd = new NextActivityCommand(futureList);
        String result = cmd.execute("Future Trip");
        assertTrue(result.contains("Future Hike"));
        assertTrue(result.contains("Future Trip"));
    }

    @Test
    public void execute_activitiesWithNullDate_skippedCorrectly() throws TravelTrioException {
        activityList.add(new Activity("No date", null, null, null, null));
        NextActivityCommand cmd = new NextActivityCommand(activityList);
        String result = cmd.execute("Test Trip");
        assertEquals("No upcoming activities found.", result);
    }

    @Test
    public void execute_multipleFutureActivities_returnsEarliest() throws TravelTrioException {
        String nearFuture = LocalDate.now().plusDays(5)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String farFuture = LocalDate.now().plusDays(15)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String tripEnd = LocalDate.now().plusDays(30)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String tripStart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Trip futureTrip = new Trip("Future Trip", tripStart, tripEnd);
        ActivityList futureList = new ActivityList(futureTrip);
        futureList.add(new Activity("Later Hike", "Mountain", farFuture, "09:00", "11:00"));
        futureList.add(new Activity("Earlier Hike", "Valley", nearFuture, "09:00", "11:00"));

        NextActivityCommand cmd = new NextActivityCommand(futureList);
        String result = cmd.execute("Future Trip");
        assertTrue(result.contains("Earlier Hike"));
    }
}
