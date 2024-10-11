package advent.day19;

import java.util.List;
import java.util.Map;

public class Workflow {
    List<Operation> operations;
//    String defaultOp;

    public Workflow(List<Operation> operations) {
        this.operations = operations;

    }

    public String process(Map<Character, Long> part) {
        for (Operation operation : operations) {
            if (operation.applies(part)) {
                return operation.targetWorkflow;
            }
        }

        throw new IllegalStateException();
    }

    @Override
    public String toString() {
        return "{" +
            "operations=" + operations +
            '}';
    }
}
