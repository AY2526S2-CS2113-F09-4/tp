package seedu.traveltrio.command.trip;

import org.junit.jupiter.api.Test;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.Trip;
import seedu.traveltrio.model.trip.TripList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListTripCommandTest {

    @Test
    public void execute_emptyList_throwsException() {
        TripList tripList = new TripList();
        ListTripCommand cmd = new ListTripCommand(tripList);
        assertThrows(TravelTrioException.class, cmd::execute);
    }

    @Test
    public void execute_singleTrip_containsTripName() throws TravelTrioException {
        TripList tripList = new TripList();
        tripList.add(new Trip("Japan", "2026-05-01", "2026-05-10"));
        ListTripCommand cmd = new ListTripCommand(tripList);
        String result = cmd.execute();
        assertTrue(result.contains("Japan"));
        assertTrue(result.contains("2026-05-01"));
        assertTrue(result.contains("2026-05-10"));
    }

    @Test
    public void execute_multipleTrips_allTripsListed() throws TravelTrioException {
        TripList tripList = new TripList();
        tripList.add(new Trip("Japan", "2026-05-01", "2026-05-10"));
        tripList.add(new Trip("Korea", "2026-06-01", "2026-06-10"));
        ListTripCommand cmd = new ListTripCommand(tripList);
        String result = cmd.execute();
        assertTrue(result.contains("Japan"));
        assertTrue(result.contains("Korea"));
        assertTrue(result.startsWith("Trips:"));
    }
}
