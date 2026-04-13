package seedu.traveltrio.model.budget;

import org.junit.jupiter.api.Test;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BudgetTest {

    @Test
    void getRemainingBudget_noExpenseSet_equalsFullBudget() throws TravelTrioException {
        Activity a = new Activity("Shopping", "Mall", "2026-03-11", "11:30", "15:00");
        Budget b = new Budget(500.00, a);
        assertEquals(500.00, b.getRemainingBudget(), 0.001);
    }

    @Test
    void getRemainingBudget_afterExpenseSet_correctRemaining() throws TravelTrioException {
        Activity a = new Activity("Shopping", "Mall", "2026-03-11", "11:30", "15:00");
        Budget b = new Budget(500.00, a);
        b.setActualExpense(200.00);
        assertEquals(300.00, b.getRemainingBudget(), 0.001);
    }

    @Test
    void setActualExpense_nonNegativeAmount_success() throws TravelTrioException {
        Activity a = new Activity("Shopping", "Mall", "2026-03-11", "11:30", "15:00");
        double budgetAmount = 2000.00;
        Budget b = new Budget(budgetAmount, a);
        double actualExpense = 1500.00;
        b.setActualExpense(actualExpense);

        assertEquals(1500.00, b.getActualExpense());
    }

    @Test
    void setActualExpense_negativeAmount_throwsException() throws TravelTrioException {
        Activity a = new Activity("Shopping", "Mall", "2026-03-11", "11:30", "15:00");
        double budgetAmount = 2000.00;
        Budget b = new Budget(budgetAmount, a);

        TravelTrioException e = assertThrows(TravelTrioException.class, () -> {
            b.setActualExpense(-500.0);
        });

        assertEquals("Expense amount cannot be negative.", e.getMessage());
    }

    @Test
    void setActualExpense_exceedsBudget_throwsException() throws TravelTrioException {
        Activity a = new Activity("Dining", "Restaurant", "2026-03-11", "18:00", "20:00");
        Budget b = new Budget(100.00, a);

        assertThrows(TravelTrioException.class, () -> b.setActualExpense(150.00));
    }

    @Test
    void constructor_negativeBudget_throwsException() {
        assertThrows(TravelTrioException.class, () -> {
            Activity a = new Activity("Shopping", "Mall", "2026-03-11", "11:30", "15:00");
            new Budget(-100.0, a);
        });
    }

    @Test
    void toString_highExpense_containsWarning() throws TravelTrioException {
        Activity a = new Activity("Shopping", "Mall", "2026-03-11", "11:30", "15:00");
        Budget b = new Budget(100.00, a);
        b.setActualExpense(95.00);
        String result = b.toString();
        assertTrue(result.contains("Warning"));
    }

    @Test
    void toString_lowExpense_noWarning() throws TravelTrioException {
        Activity a = new Activity("Shopping", "Mall", "2026-03-11", "11:30", "15:00");
        Budget b = new Budget(100.00, a);
        b.setActualExpense(50.00);
        String result = b.toString();
        assertFalse(result.contains("Warning"));
    }
}

