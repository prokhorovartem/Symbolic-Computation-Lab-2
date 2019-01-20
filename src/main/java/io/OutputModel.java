package io;

import symbolic.model.Expression;
import symbolic.model.impl.OperationImpl;
import symbolic.model.impl.Variable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class OutputModel {
    private Resource resource;

    public OutputModel(Resource outputResource) {
        this.resource = outputResource;
    }

    public void printResult(Expression expression) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(OperationImpl.class, Variable.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(expression, resource.getFile());
    }
}
