package interpretation.instruction;

import interpretation.Session;
import interpretation.commands.Command;
import interpretation.commands.CommandFactory;
import interpretation.commands.commandUnits.CommandUnit;
import org.jetbrains.annotations.NotNull;

public class CommandInstruction implements Instruction {

    private final Command command;

    CommandInstruction() {
        this.command = CommandFactory.createNewCommand();
    }

    public void addUnitCommand(final CommandUnit commandUnit) {
        this.command.addCommandUnit(commandUnit);
    }

    @Override
    public void execute(@NotNull final Session session) {
        command.run(session);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof CommandInstruction) {
            return command.equals(obj);
        }
        return false;
    }
}
