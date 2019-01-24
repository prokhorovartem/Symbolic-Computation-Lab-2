package io;

import java.util.List;
import java.util.Objects;

public class Template {
    private String name;
    private List<String> arguments;

    public Template(String name, List<String> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public List<String> getArguments() {
        return arguments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template) o;
        return name.equals(template.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
