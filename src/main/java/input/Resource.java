package input;

import lombok.Getter;

import java.io.File;

@Getter
public class Resource {
    private File file;

    public Resource(String fileName) {
        file = new File(fileName);
    }
}
