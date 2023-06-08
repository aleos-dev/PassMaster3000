package org.application.services;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.application.Main;
import org.application.objects.user.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class JSONService {
    private static JSONService service;
    private final File jsonFile = Path.of("src/main/resources/users.json").toFile();
    private final ObjectMapper mapper = new JsonMapper();


    private JSONService() {
    }

    public void writeToFile(User user) throws IOException {
        Objects.requireNonNull(jsonFile);
        Objects.requireNonNull(user);

        JsonNode node = readArrayOrCreateNew();
        if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            arrayNode.addPOJO(user);
        } else {
            ArrayNode rootArray = mapper.createArrayNode();
            rootArray.add(node);
            rootArray.addPOJO(user);
            node = rootArray;
        }
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(jsonFile, node);
    }

    private JsonNode readArrayOrCreateNew() throws IOException {
        if (jsonFile.exists() && jsonFile.length() > 0) {
            return mapper.readTree(jsonFile);
        }
        return mapper.createArrayNode();
    }

    public static JSONService getInstance() {
        if (service == null) {
            service = new JSONService();
        }
        return service;
    }
}
