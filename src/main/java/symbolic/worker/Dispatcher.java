package symbolic.worker;

import symbolic.model.Expression;
import symbolic.model.OperationType;
import symbolic.worker.impl.*;

public class Dispatcher {

    public static Expression resolveOperation(OperationType operationType, Expression firstArgument, Expression secondArgument) {
        return secondArgument != null ? ResolveBinaryOperation(operationType, firstArgument, secondArgument) : ResolveUnaryOperation(operationType, firstArgument);
    }

    private static Expression ResolveUnaryOperation(OperationType operationType, Expression firstArgument) {

        Worker worker;
        switch (operationType) {
            case SIN:
                worker = new SinWorker(firstArgument);
                break;
            case COS:
                worker = new CosWorker(firstArgument);
                break;
            case TAN:
                worker = new TanWorker(firstArgument);
                break;
            case CTG:
                worker = new CtgWorker(firstArgument);
                break;
            default:
                throw new UnsupportedOperationException();
        }

        return worker.work();
    }

    private static Expression ResolveBinaryOperation(OperationType operationType, Expression firstArgument, Expression secondArgument) {

        Worker worker;
        switch (operationType) {
            case ADDITION:
                worker = new AdditionWorker(firstArgument, secondArgument);
                break;
            case SUBTRACTION:
                worker = new SubtractionWorker(firstArgument, secondArgument);
                break;
            case MULTIPLICATION:
                worker = new MultiplicationWorker(firstArgument, secondArgument);
                break;
            case DIVISION:
                worker = new DivisionWorker(firstArgument, secondArgument);
                break;
//            case INT:
//                worker = new IntegrationWorker(firstArgument, secondArgument);
//                break;
            default:
                throw new UnsupportedOperationException();
        }

        return worker.work();
    }
}
