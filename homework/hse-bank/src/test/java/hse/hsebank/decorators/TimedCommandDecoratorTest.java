package hse.hsebank.decorators;

import hse.hsebank.commands.Command;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TimedCommandDecoratorTest {

    @Test
    void testTimedCommandExecution() {
        TestCommand testCommand = new TestCommand();
        TimedCommandDecorator timedCommand = new TimedCommandDecorator(testCommand);

        timedCommand.execute();

        assertTrue(testCommand.executed);
    }

    static class TestCommand implements Command {
        boolean executed = false;

        @Override
        public void execute() {
            executed = true;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}