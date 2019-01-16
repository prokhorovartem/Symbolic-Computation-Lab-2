package symbolic.worker;

import input.BinaryOperation;
import input.UnaryOperation;
import symbolic.model.Expression;
import symbolic.worker.impl.*;

public class Dispatcher {

    public static Expression resolveOperation(Expression operationType, Expression firstArgument, Expression secondArgument) {
        return secondArgument != null ? ResolveBinaryOperation((BinaryOperation) operationType, firstArgument, secondArgument)
                : ResolveUnaryOperation((UnaryOperation) operationType, firstArgument);
    }

    private static Expression ResolveUnaryOperation(UnaryOperation operationType, Expression firstArgument) {

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

    private static Expression ResolveBinaryOperation(BinaryOperation operationType, Expression firstArgument, Expression secondArgument) {

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
