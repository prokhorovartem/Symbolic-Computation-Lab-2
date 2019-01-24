package symbolic.visitor.integration;

public class IntegrationParamHolder {
    private String name = "";

    private static IntegrationParamHolder instance;

    private IntegrationParamHolder() {
    }

    public static IntegrationParamHolder getInstance() {
        if (instance == null) {
            instance = new IntegrationParamHolder();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
