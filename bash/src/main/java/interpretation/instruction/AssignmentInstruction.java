package interpretation.instruction;

import interpretation.Session;
import org.jetbrains.annotations.NotNull;

public class AssignmentInstruction implements Instruction {

    private final @NotNull String name;
    private final @NotNull String value;

    AssignmentInstruction(final @NotNull String name, final @NotNull String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void execute(final @NotNull Session session) {
        session.setVariable(name, value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof AssignmentInstruction) {
            final AssignmentInstruction o = (AssignmentInstruction) obj;
            return o.name.equals(name) && o.value.equals(value);
        }
        return false;
    }
}
