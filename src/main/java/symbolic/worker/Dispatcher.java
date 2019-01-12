package symbolic.worker;

import symbolic.model.Expression;
import symbolic.model.OperationType;
import symbolic.worker.impl.AdditionWorker;

public class Dispatcher {

    public static Expression resolveOperation(OperationType operationType, Expression firstArgument, Expression secondArgument) {
        return secondArgument != null ? ResolveBinaryOperation(operationType, firstArgument, secondArgument) : null;//ResolveUnaryOperation(operationType, firstArgument);
    }

    private static Expression ResolveBinaryOperation(OperationType operationType, Expression firstArgument, Expression secondArgument) {

        Worker worker;
        switch (operationType) {
            case ADDITION:
                worker = new AdditionWorker(firstArgument, secondArgument);
                break;
//            case SUBTRACTION:
//                worker = new SubtractionWorker(firstArgument, secondArgument);
//                break;
//            case MULTIPLICATION:
//                worker = new MultiplicationWorker(firstArgument, secondArgument);
//                break;
//            case DIVISION:
//                worker = new DivisionWorker(firstArgument, secondArgument);
//                break;
//            case INT:
//                worker = new IntegrationWorker(firstArgument, secondArgument);
//                break;
            default:
                throw new UnsupportedOperationException();
        }

        return worker.work();
    }
}
