package interpretation.instruction;

import interpretation.Session;
import org.jetbrains.annotations.NotNull;

public interface Instruction {

    void execute(@NotNull Session session);

}
