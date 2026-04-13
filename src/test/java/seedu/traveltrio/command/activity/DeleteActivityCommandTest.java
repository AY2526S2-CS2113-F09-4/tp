package seedu.traveltrio.command.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.budget.Budget;
import seedu.traveltrio.model.budget.BudgetList;
import seedu.traveltrio.model.trip.Trip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteActivityCommandTest {

    private Trip trip;
    private ActivityList activityList;
    private BudgetList budgetList;
    private Activity activity;

    @BeforeEach
    public void setUp() throws TravelTrioException {
        trip = new Trip("Test Trip", "2026-06-01", "2026-06-30");
        trip.setOpen(true);
        activityList = new ActivityList(trip);
        budgetList = new BudgetList();
        activity = new Activity("Hiking", "Fuji", "2026-06-10", "09:00", "11:00");
        activityList.add(activity);
    }

    @Test
    public void execute_validIndex_activityRemoved() throws TravelTrioException {
        DeleteActivityCommand cmd = new DeleteActivityCommand(activityList, budgetList, 1);
        String result = cmd.execute("Test Trip");
        assertEquals(0, activityList.size());
        assertTrue(result.contains("Hiking"));
    }

    @Test
    public void execute_invalidIndex_throwsException() {
        DeleteActivityCommand cmd = new DeleteActivityCommand(activityList, budgetList, 99);
        assertThrows(TravelTrioException.class, () -> cmd.execute("Test Trip"));
    }

    @Test
    public void execute_indexZero_throwsException() {
        DeleteActivityCommand cmd = new DeleteActivityCommand(activityList, budgetList, 0);
        assertThrows(TravelTrioException.class, () -> cmd.execute("Test Trip"));
    }

    @Test
    public void execute_activityWithBudget_budgetAlsoRemoved() throws TravelTrioException {
        Budget b = new Budget(100.0, activity);
        budgetList.addBudget(activity, b);
        DeleteActivityCommand cmd = new DeleteActivityCommand(activityList, budgetList, 1);
        cmd.execute("Test Trip");
        assertNull(budgetList.getBudget(activity));
    }

    @Test
    public void execute_multipleActivities_onlyCorrectOneRemoved() throws TravelTrioException {
        Activity activity2 = new Activity("Diving", "Beach", "2026-06-15", "13:00", "15:00");
        activityList.add(activity2);
        DeleteActivityCommand cmd = new DeleteActivityCommand(activityList, budgetList, 1);
        cmd.execute("Test Trip");
        assertEquals(1, activityList.size());
        assertEquals("Diving", activityList.get(0).getName());
    }
}
