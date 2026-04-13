package seedu.traveltrio.command.trip;

import org.junit.jupiter.api.Test;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.Trip;
import seedu.traveltrio.model.trip.TripList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteTripCommandTest {

    @Test
    public void execute_validIndex_tripRemoved() throws TravelTrioException {
        TripList tripList = new TripList();
        tripList.add(new Trip("Japan", "2026-05-01", "2026-05-10"));
        DeleteTripCommand cmd = new DeleteTripCommand(tripList, 1);
        String result = cmd.execute();
        assertEquals(0, tripList.size());
        assertTrue(result.contains("Japan"));
    }

    @Test
    public void execute_invalidIndexTooHigh_throwsException() {
        TripList tripList = new TripList();
        tripList.add(new Trip("Japan", "2026-05-01", "2026-05-10"));
        DeleteTripCommand cmd = new DeleteTripCommand(tripList, 99);
        assertThrows(TravelTrioException.class, cmd::execute);
    }

    @Test
    public void execute_indexZero_throwsException() {
        TripList tripList = new TripList();
        tripList.add(new Trip("Japan", "2026-05-01", "2026-05-10"));
        DeleteTripCommand cmd = new DeleteTripCommand(tripList, 0);
        assertThrows(TravelTrioException.class, cmd::execute);
    }

    @Test
    public void execute_multipleTrips_onlyCorrectTripRemoved() throws TravelTrioException {
        TripList tripList = new TripList();
        tripList.add(new Trip("Japan", "2026-05-01", "2026-05-10"));
        tripList.add(new Trip("Korea", "2026-06-01", "2026-06-10"));
        DeleteTripCommand cmd = new DeleteTripCommand(tripList, 1);
        cmd.execute();
        assertEquals(1, tripList.size());
        assertEquals("Korea", tripList.get(0).getName());
    }

    @Test
    public void execute_emptyList_throwsException() {
        TripList tripList = new TripList();
        DeleteTripCommand cmd = new DeleteTripCommand(tripList, 1);
        assertThrows(TravelTrioException.class, cmd::execute);
    }
}
