package seedu.traveltrio.command.finance.budget;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.budget.Budget;
import seedu.traveltrio.model.budget.BudgetList;
import seedu.traveltrio.model.trip.Trip;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BudgetSummaryCommandTest {

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
    public void execute_noBudgets_throwsException() {
        BudgetSummaryCommand cmd = new BudgetSummaryCommand(budgetList, activityList);
        assertThrows(TravelTrioException.class, cmd::execute);
    }

    @Test
    public void execute_withBudget_containsActivityName() throws TravelTrioException {
        budgetList.addBudget(activity, new Budget(200.0, activity));
        BudgetSummaryCommand cmd = new BudgetSummaryCommand(budgetList, activityList);
        String result = cmd.execute();
        assertTrue(result.contains("Hiking"));
        assertTrue(result.contains("200.00"));
    }

    @Test
    public void execute_withBudgetAndExpense_showsExpense() throws TravelTrioException {
        Budget b = new Budget(200.0, activity);
        b.setActualExpense(100.0);
        budgetList.addBudget(activity, b);
        BudgetSummaryCommand cmd = new BudgetSummaryCommand(budgetList, activityList);
        String result = cmd.execute();
        assertTrue(result.contains("100.00"));
    }

    @Test
    public void execute_result_containsTotalBudgetLine() throws TravelTrioException {
        budgetList.addBudget(activity, new Budget(500.0, activity));
        BudgetSummaryCommand cmd = new BudgetSummaryCommand(budgetList, activityList);
        String result = cmd.execute();
        assertTrue(result.contains("Total trip budget"));
        assertTrue(result.contains("500.00"));
    }
}
