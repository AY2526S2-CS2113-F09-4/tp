package seedu.traveltrio.command.trip;

import org.junit.jupiter.api.Test;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.Trip;
import seedu.traveltrio.model.trip.TripList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpenTripCommandTest {

    @Test
    public void execute_validIndex_tripIsOpen() throws TravelTrioException {
        TripList tripList = new TripList();
        Trip trip = new Trip("Japan", "2026-05-01", "2026-05-10");
        tripList.add(trip);
        OpenTripCommand cmd = new OpenTripCommand(tripList, 1);
        cmd.execute();
        assertTrue(trip.isOpen());
    }

    @Test
    public void execute_invalidIndex_throwsException() {
        TripList tripList = new TripList();
        tripList.add(new Trip("Japan", "2026-05-01", "2026-05-10"));
        OpenTripCommand cmd = new OpenTripCommand(tripList, 99);
        assertThrows(TravelTrioException.class, cmd::execute);
    }

    @Test
    public void execute_indexZero_throwsException() {
        TripList tripList = new TripList();
        tripList.add(new Trip("Japan", "2026-05-01", "2026-05-10"));
        OpenTripCommand cmd = new OpenTripCommand(tripList, 0);
        assertThrows(TravelTrioException.class, cmd::execute);
    }

    @Test
    public void execute_openSecondTrip_firstTripClosed() throws TravelTrioException {
        TripList tripList = new TripList();
        Trip trip1 = new Trip("Japan", "2026-05-01", "2026-05-10");
        Trip trip2 = new Trip("Korea", "2026-06-01", "2026-06-10");
        tripList.add(trip1);
        tripList.add(trip2);

        // Open trip1 first
        new OpenTripCommand(tripList, 1).execute();
        assertTrue(trip1.isOpen());

        // Open trip2 — trip1 should be closed
        new OpenTripCommand(tripList, 2).execute();
        assertFalse(trip1.isOpen());
        assertTrue(trip2.isOpen());
    }

    @Test
    public void execute_resultContainsTripName() throws TravelTrioException {
        TripList tripList = new TripList();
        tripList.add(new Trip("Japan", "2026-05-01", "2026-05-10"));
        OpenTripCommand cmd = new OpenTripCommand(tripList, 1);
        String result = cmd.execute();
        assertTrue(result.contains("Japan"));
    }
}
