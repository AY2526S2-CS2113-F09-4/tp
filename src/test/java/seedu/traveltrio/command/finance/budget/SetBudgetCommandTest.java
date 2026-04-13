package seedu.traveltrio.command.finance.budget;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.budget.BudgetList;
import seedu.traveltrio.model.trip.Trip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetBudgetCommandTest {

    private Trip trip;
    private ActivityList activityList;
    private BudgetList budgetList;
    private Activity activity;

    @BeforeEach
    public void setUp() throws TravelTrioException {
        trip = new Trip("Test Trip", "2026-06-01", "2026-06-30");
        activityList = new ActivityList(trip);
        budgetList = new BudgetList();
        activity = new Activity("Hiking", "Fuji", "2026-06-10", "09:00", "11:00");
        activityList.add(activity);
    }

    @Test
    public void execute_validBudget_budgetAdded() throws TravelTrioException {
        SetBudgetCommand cmd = new SetBudgetCommand(budgetList, activityList, activity, 200.0);
        String result = cmd.execute();
        assertNotNull(budgetList.getBudget(activity));
        assertEquals(200.0, budgetList.getBudget(activity).getActivityBudget(), 0.001);
        assertTrue(result.contains("200.00"));
    }

    @Test
    public void execute_zeroBudget_budgetRemoved() throws TravelTrioException {
        budgetList.addBudget(activity, new seedu.traveltrio.model.budget.Budget(100.0, activity));
        SetBudgetCommand cmd = new SetBudgetCommand(budgetList, activityList, activity, 0.0);
        String result = cmd.execute();
        assertNull(budgetList.getBudget(activity));
        assertTrue(result.contains("$0.00"));
    }

    @Test
    public void execute_nullActivity_throwsException() {
        SetBudgetCommand cmd = new SetBudgetCommand(budgetList, activityList, null, 200.0);
        assertThrows(TravelTrioException.class, cmd::execute);
    }

    @Test
    public void execute_negativeBudget_throwsException() {
        SetBudgetCommand cmd = new SetBudgetCommand(budgetList, activityList, activity, -50.0);
        assertThrows(TravelTrioException.class, cmd::execute);
    }

    @Test
    public void execute_replaceBudget_updatesAmount() throws TravelTrioException {
        SetBudgetCommand cmd1 = new SetBudgetCommand(budgetList, activityList, activity, 100.0);
        cmd1.execute();
        SetBudgetCommand cmd2 = new SetBudgetCommand(budgetList, activityList, activity, 300.0);
        cmd2.execute();
        assertEquals(300.0, budgetList.getBudget(activity).getActivityBudget(), 0.001);
    }
}
