package interpretation.instruction;

import interpretation.commands.Command;
import org.jetbrains.annotations.NotNull;

public class InstructionFactory {

    public static AssignmentInstruction createAssignmentCommand(
        @NotNull final String name,
        @NotNull final String value
    ) {
        return new AssignmentInstruction(name, value);
    }

    public static CommandInstruction createCommandInstruction() {
        return new CommandInstruction();
    }
}
