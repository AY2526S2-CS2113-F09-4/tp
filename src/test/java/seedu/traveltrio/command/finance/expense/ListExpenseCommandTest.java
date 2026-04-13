package seedu.traveltrio.command.finance.expense;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.budget.Budget;
import seedu.traveltrio.model.trip.Trip;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListExpenseCommandTest {

    private Trip trip;

    @BeforeEach
    public void setUp() throws TravelTrioException {
        trip = new Trip("Test Trip", "2026-06-01", "2026-06-30");
    }

    @Test
    public void execute_noActivities_returnsHeader() {
        ListExpenseCommand cmd = new ListExpenseCommand(trip);
        String result = cmd.execute();
        assertTrue(result.contains("Test Trip"));
        assertTrue(result.contains("Total expense"));
    }

    @Test
    public void execute_withBudgetedActivity_containsActivityName() throws TravelTrioException {
        Activity a = new Activity("Hiking", "Fuji", "2026-06-10", "09:00", "11:00");
        trip.getActivities().add(a);
        trip.getBudgets().addBudget(a, new Budget(200.0, a));

        ListExpenseCommand cmd = new ListExpenseCommand(trip);
        String result = cmd.execute();
        assertTrue(result.contains("Hiking"));
    }

    @Test
    public void execute_withDailyLimit_showsLimit() throws TravelTrioException {
        trip.getBudgets().setDailySpendingLimit(100.0);
        ListExpenseCommand cmd = new ListExpenseCommand(trip);
        String result = cmd.execute();
        assertTrue(result.contains("100.00"));
    }

    @Test
    public void execute_withNoDailyLimit_showsNotSet() {
        ListExpenseCommand cmd = new ListExpenseCommand(trip);
        String result = cmd.execute();
        assertTrue(result.contains("Not set"));
    }

    @Test
    public void execute_multipleActivities_totalCorrect() throws TravelTrioException {
        Activity a1 = new Activity("Hiking", "Fuji", "2026-06-10", "09:00", "11:00");
        Activity a2 = new Activity("Dining", "Restaurant", "2026-06-11", "18:00", "20:00");
        trip.getActivities().add(a1);
        trip.getActivities().add(a2);

        Budget b1 = new Budget(100.0, a1);
        b1.setActualExpense(60.0);
        Budget b2 = new Budget(100.0, a2);
        b2.setActualExpense(40.0);

        trip.getBudgets().addBudget(a1, b1);
        trip.getBudgets().addBudget(a2, b2);

        ListExpenseCommand cmd = new ListExpenseCommand(trip);
        String result = cmd.execute();
        // Total should be $100.00
        assertTrue(result.contains("$100.00"));
    }
}
