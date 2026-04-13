package seedu.traveltrio.command.finance.expense;

import org.junit.jupiter.api.Test;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.budget.BudgetList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetDailyLimitCommandTest {

    @Test
    public void execute_validLimit_limitSet() throws TravelTrioException {
        BudgetList budgetList = new BudgetList();
        SetDailyLimitCommand cmd = new SetDailyLimitCommand(budgetList, 100.0);
        String result = cmd.execute();
        assertEquals(100.0, budgetList.getDailySpendingLimit(), 0.001);
        assertTrue(result.contains("100.00"));
    }

    @Test
    public void execute_zeroLimit_allowed() throws TravelTrioException {
        BudgetList budgetList = new BudgetList();
        SetDailyLimitCommand cmd = new SetDailyLimitCommand(budgetList, 0.0);
        cmd.execute();
        assertEquals(0.0, budgetList.getDailySpendingLimit(), 0.001);
    }

    @Test
    public void execute_negativeLimit_throwsException() {
        BudgetList budgetList = new BudgetList();
        SetDailyLimitCommand cmd = new SetDailyLimitCommand(budgetList, -50.0);
        assertThrows(TravelTrioException.class, cmd::execute);
    }

    @Test
    public void execute_validLimit_resultContainsConfirmation() throws TravelTrioException {
        BudgetList budgetList = new BudgetList();
        SetDailyLimitCommand cmd = new SetDailyLimitCommand(budgetList, 200.0);
        String result = cmd.execute();
        assertTrue(result.contains("Daily spending limit"));
        assertTrue(result.contains("200.00"));
    }
}
