package seedu.traveltrio.command.finance.budget;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.budget.BudgetList;
import seedu.traveltrio.model.trip.Trip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetCurrencyCommandTest {

    private BudgetList budgetList;
    private ActivityList activityList;
    private Activity activity;

    @BeforeEach
    public void setUp() throws TravelTrioException {
        Trip trip = new Trip("Test Trip", "2026-06-01", "2026-06-30");
        activityList = new ActivityList(trip);
        budgetList = new BudgetList();
        activity = new Activity("Hiking", "Fuji", "2026-06-10", "09:00", "11:00");
    }

    @Test
    public void execute_validExchangeRate_rateSet() throws TravelTrioException {
        SetCurrencyCommand cmd = new SetCurrencyCommand(budgetList, activityList, activity, 1.35);
        String result = cmd.execute();
        assertEquals(1.35, budgetList.getExchangeRate(), 0.001);
        assertTrue(result.contains("1.3500"));
    }

    @Test
    public void execute_zeroExchangeRate_throwsException() {
        SetCurrencyCommand cmd = new SetCurrencyCommand(budgetList, activityList, activity, 0.0);
        assertThrows(TravelTrioException.class, cmd::execute);
    }

    @Test
    public void execute_negativeExchangeRate_throwsException() {
        SetCurrencyCommand cmd = new SetCurrencyCommand(budgetList, activityList, activity, -1.0);
        assertThrows(TravelTrioException.class, cmd::execute);
    }

    @Test
    public void execute_result_containsFormatMessage() throws TravelTrioException {
        SetCurrencyCommand cmd = new SetCurrencyCommand(budgetList, activityList, activity, 2.0);
        String result = cmd.execute();
        assertTrue(result.contains("Foreign Currency"));
        assertTrue(result.contains("Home Currency"));
    }
}
