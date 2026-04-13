package seedu.traveltrio.model.budget;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BudgetListTest {

    private BudgetList budgetList;
    private Activity activityA;
    private Activity activityB;

    @BeforeEach
    public void setUp() throws TravelTrioException {
        budgetList = new BudgetList();
        activityA = new Activity("Hiking", "Fuji", "2026-06-10", "09:00", "11:00");
        activityB = new Activity("Diving", "Beach", "2026-06-11", "13:00", "15:00");
    }

    @Test
    public void addBudget_newActivity_budgetAdded() throws TravelTrioException {
        Budget b = new Budget(200.0, activityA);
        budgetList.addBudget(activityA, b);
        assertEquals(b, budgetList.getBudget(activityA));
    }

    @Test
    public void addBudget_replaceExisting_totalBudgetUpdated() throws TravelTrioException {
        Budget b1 = new Budget(200.0, activityA);
        Budget b2 = new Budget(300.0, activityA);
        budgetList.addBudget(activityA, b1);
        budgetList.addBudget(activityA, b2);
        assertEquals(300.0, budgetList.getTotalTripBudget(), 0.001);
        assertEquals(b2, budgetList.getBudget(activityA));
    }

    @Test
    public void removeBudget_existingActivity_budgetRemoved() throws TravelTrioException {
        Budget b = new Budget(200.0, activityA);
        budgetList.addBudget(activityA, b);
        budgetList.removeBudget(activityA);
        assertNull(budgetList.getBudget(activityA));
        assertEquals(0.0, budgetList.getTotalTripBudget(), 0.001);
    }

    @Test
    public void removeBudget_nonExistingActivity_noException() {
        // Should not throw
        budgetList.removeBudget(activityA);
    }

    @Test
    public void getTotalTripBudget_multipleBudgets_sumsCorrectly() throws TravelTrioException {
        budgetList.addBudget(activityA, new Budget(100.0, activityA));
        budgetList.addBudget(activityB, new Budget(200.0, activityB));
        assertEquals(300.0, budgetList.getTotalTripBudget(), 0.001);
    }

    @Test
    public void getTotalRemainingTripBudget_afterExpense_correctRemaining() throws TravelTrioException {
        Budget b = new Budget(200.0, activityA);
        b.setActualExpense(50.0);
        budgetList.addBudget(activityA, b);
        assertEquals(150.0, budgetList.getTotalRemainingTripBudget(), 0.001);
    }

    @Test
    public void setExpense_noBudgetSet_throwsException() {
        assertThrows(TravelTrioException.class, () -> budgetList.setExpense(activityA, 50.0));
    }

    @Test
    public void setExpense_validAmount_updatesTotalExpense() throws TravelTrioException {
        budgetList.addBudget(activityA, new Budget(200.0, activityA));
        budgetList.setExpense(activityA, 80.0);
        assertEquals(80.0, budgetList.getTotalTripExpense(), 0.001);
    }

    @Test
    public void setDailySpendingLimit_negativeAmount_throwsException() {
        assertThrows(TravelTrioException.class, () -> budgetList.setDailySpendingLimit(-10.0));
    }

    @Test
    public void setDailySpendingLimit_validAmount_success() throws TravelTrioException {
        budgetList.setDailySpendingLimit(100.0);
        assertEquals(100.0, budgetList.getDailySpendingLimit(), 0.001);
        assertTrue(budgetList.hasDailyLimitSet());
    }

    @Test
    public void hasDailyLimitSet_notSet_returnsFalse() {
        assertFalse(budgetList.hasDailyLimitSet());
    }

    @Test
    public void willExceedDailyLimit_noLimitSet_returnsFalse() throws TravelTrioException {
        budgetList.addBudget(activityA, new Budget(500.0, activityA));
        assertFalse(budgetList.willExceedDailyLimit(activityA, 999.0));
    }

    @Test
    public void willExceedDailyLimit_withinLimit_returnsFalse() throws TravelTrioException {
        budgetList.setDailySpendingLimit(200.0);
        budgetList.addBudget(activityA, new Budget(100.0, activityA));
        assertFalse(budgetList.willExceedDailyLimit(activityA, 100.0));
    }

    @Test
    public void willExceedDailyLimit_exceedsLimit_returnsTrue() throws TravelTrioException {
        budgetList.setDailySpendingLimit(50.0);
        budgetList.addBudget(activityA, new Budget(200.0, activityA));
        assertTrue(budgetList.willExceedDailyLimit(activityA, 100.0));
    }

    @Test
    public void setExpense_wouldExceedDailyLimit_throwsException() throws TravelTrioException {
        budgetList.setDailySpendingLimit(50.0);
        budgetList.addBudget(activityA, new Budget(200.0, activityA));
        assertThrows(TravelTrioException.class, () -> budgetList.setExpense(activityA, 100.0));
    }

    @Test
    public void isEmpty_emptyList_returnsTrue() {
        assertTrue(budgetList.isEmpty());
    }

    @Test
    public void isEmpty_withBudget_returnsFalse() throws TravelTrioException {
        budgetList.addBudget(activityA, new Budget(100.0, activityA));
        assertFalse(budgetList.isEmpty());
    }

    @Test
    public void setExchangeRate_updatesRate() {
        budgetList.setExchangeRate(1.35);
        assertEquals(1.35, budgetList.getExchangeRate(), 0.001);
    }
}
