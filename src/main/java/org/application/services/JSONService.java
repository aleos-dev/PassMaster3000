package org.application.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.application.objects.user.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class JSONService {
    public static final JSONService service = new JSONService();
    private final File jsonFile = Path.of("src/main/resources/users.json").toFile();
    private final ObjectMapper mapper = new JsonMapper();


    private JSONService() {
    }

    public void writeToFile(User user) throws IOException {
        Objects.requireNonNull(jsonFile);
        Objects.requireNonNull(user);

        ObjectNode userNode = mapper.convertValue(user, ObjectNode.class);
        JsonNode userLogin = userNode.get("login");
        AtomicBoolean canWrite = new AtomicBoolean(true);

        JsonNode node = readOrCreateNewArray();
        node.forEach(elm -> {
            JsonNode login = elm.get("login");
            if (elm instanceof ObjectNode obj && login.equals(userLogin)) {
                obj.setAll(userNode);
                canWrite.set(false);
            }
        });

        if (!node.isArray()) {
            ArrayNode rootArray = mapper.createArrayNode();
            rootArray.add(node);
            node = rootArray;
        }
        if (canWrite.get()) {
            ((ArrayNode) node).addPOJO(user);
        }

        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        writer.writeValue(jsonFile, node);
    }

    public Map<String, User> getDatabase() {
        try {
            return mapper.readValue(jsonFile, new TypeReference<Set<User>>() {
                    })
                    .stream()
                    .collect(Collectors.toMap(User::getLogin, user -> user));
        } catch (IOException e) {
//            throw new RuntimeException("Database load error", e);
            System.out.println("Database load error, new DB was created.");
            return new HashMap<>();
        }
    }

    private JsonNode readOrCreateNewArray() throws IOException {
        if (jsonFile.exists() && jsonFile.length() > 0) {
            return mapper.readTree(jsonFile);
        }
        return mapper.createArrayNode();
    }
}