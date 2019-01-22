package symbolic.worker;

import symbolic.model.Expression;
import symbolic.model.OperationType;
import symbolic.worker.impl.*;

public class Dispatcher {

    public Expression resolveOperation(OperationType operationType, Expression firstArgument, Expression secondArgument) {
        return secondArgument != null ? resolveBinaryOperation(operationType, firstArgument, secondArgument)
                : resolveUnaryOperation(operationType, firstArgument);
    }

    private Expression resolveUnaryOperation(OperationType operationType, Expression firstArgument) {

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
            case EXP:
                worker = new ExpWorker(firstArgument);
                break;
            case LOG:
                worker = new LogWorker(firstArgument);
                break;
            default:
                throw new UnsupportedOperationException();
        }

        return worker.work();
    }

    private Expression resolveBinaryOperation(OperationType operationType, Expression firstArgument, Expression secondArgument) {

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
            case POW:
                worker = new PowerWorker(firstArgument, secondArgument);
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
