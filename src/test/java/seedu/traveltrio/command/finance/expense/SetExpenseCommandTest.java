package seedu.traveltrio.command.finance.expense;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.budget.Budget;
import seedu.traveltrio.model.budget.BudgetList;
import seedu.traveltrio.model.trip.Trip;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetExpenseCommandTest {

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
        budgetList.addBudget(activity, new Budget(200.0, activity));
    }

    @Test
    public void execute_validExpense_successMessage() throws TravelTrioException {
        SetExpenseCommand cmd = new SetExpenseCommand(budgetList, activityList, activity, 80.0, false);
        String result = cmd.execute();
        assertTrue(result.contains("Hiking"));
        assertTrue(result.contains("80.00"));
    }

    @Test
    public void execute_noBudgetSet_throwsException() throws TravelTrioException {
        Activity newActivity = new Activity("Diving", "Beach", "2026-06-11", "13:00", "15:00");
        activityList.add(newActivity);
        SetExpenseCommand cmd = new SetExpenseCommand(budgetList, activityList, newActivity, 50.0, false);
        assertThrows(TravelTrioException.class, cmd::execute);
    }

    @Test
    public void execute_highExpense_warningInResult() throws TravelTrioException {
        SetExpenseCommand cmd = new SetExpenseCommand(budgetList, activityList, activity, 190.0, false);
        String result = cmd.execute();
        assertTrue(result.contains("Warning"));
    }

    @Test
    public void execute_lowExpense_noWarning() throws TravelTrioException {
        SetExpenseCommand cmd = new SetExpenseCommand(budgetList, activityList, activity, 50.0, false);
        String result = cmd.execute();
        assertFalse(result.contains("Warning"));
    }

    @Test
    public void execute_foreignCurrency_convertsWithExchangeRate() throws TravelTrioException {
        budgetList.setExchangeRate(2.0);
        // 50 foreign * 2.0 exchange = 100 home, within 200 budget
        SetExpenseCommand cmd = new SetExpenseCommand(budgetList, activityList, activity, 50.0, true);
        String result = cmd.execute();
        // Actual expense stored should be 100.00
        assertTrue(result.contains("100.00"));
    }
}
