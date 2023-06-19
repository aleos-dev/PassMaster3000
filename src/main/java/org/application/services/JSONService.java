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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class JSONService {
    public static final JSONService service = new JSONService();
    private final File jsonFile;
    private final ObjectMapper mapper = new JsonMapper();

    private JSONService() {
        String appName = "PassMaster3000";
        String os = System.getProperty("os.name").toLowerCase();
        String homeDir = System.getProperty("user.home");

        Path path;
        if (os.toLowerCase().contains("win")) {
            path = Path.of(homeDir + "/AppData/Local/" + appName + "/users.json");
        } else if (os.contains("nux") || os.contains("nix")) {
            path = Path.of(homeDir + "/.config/" + appName + "/users.json");
        } else if (os.contains("mac")) {
            path = Path.of(homeDir + "/Library/Application Support/" + appName + "/users.json");
        } else {
            throw new UnsupportedOperationException("Unsupported operating system");
        }

        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            throw new RuntimeException("Could not create directories for path: " + path.getParent(), e);
        }

        if (Files.exists(path)) {
            this.jsonFile = path.toFile();
        } else {
            this.jsonFile = new File(path.toString());
        }
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

    public void removeUserFromDatabase(String userLogin) throws IOException {
        Objects.requireNonNull(userLogin);

        JsonNode node = readOrCreateNewArray();

        if (!node.isArray()) {
            return;
        }

        ArrayNode newArray = mapper.createArrayNode();
        node.forEach(elm -> {
            JsonNode login = elm.get("login");
            if (!login.textValue().equals(userLogin)) {
                newArray.add(elm);
            }
        });

        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        writer.writeValue(jsonFile, newArray);
    }
}