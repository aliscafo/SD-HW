package interpretation.commands;

import interpretation.Session;
import interpretation.commands.commandUnits.CommandUnit;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Queue;

public class CommandFactory {

    public static Command createNewCommand() {
        return new DummyCommand();
    }

    private static class DummyCommand implements Command {
        private final Queue<CommandUnit> unitCommandsQueue = new LinkedList<>();

        @Override
        public void addCommandUnit(@NotNull final CommandUnit c) {
            unitCommandsQueue.add(c);
        }

        @Override
        public void run(@NotNull Session session) {
            if (unitCommandsQueue.isEmpty()) {
                return;
            }
            String commandData = getInitialInput();
            while (!unitCommandsQueue.isEmpty()) {
                final CommandUnit commandUnit = unitCommandsQueue.poll();
                commandData = commandUnit.execute(commandData, session);
            }
            processOutput(commandData);
        }

        private String getInitialInput() {
            return null;
        }

        private void processOutput(final String output) {
            if (output != null) {
                System.out.println(output);
            }
        }
    }
}
