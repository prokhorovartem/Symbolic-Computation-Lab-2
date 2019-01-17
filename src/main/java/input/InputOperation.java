package input;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class InputOperation {
    @NonNull
    private InputExpression operationType;
    @NonNull
    private InputExpression firstArgument;
    private InputExpression secondArgument;
}