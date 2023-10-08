package com.workflow86.technicalchallenge.condition;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workflow86.technicalchallenge.model.Condition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;


@Service
public class ConditionService {
    @Value("${condition.path}")
    private String jsonFileUrl;

    @Value("${yml.path}")
    private String ymlFileUrl;

    /**
     * @param changeCondition
     * @return
     * @throws IOException
     */
    public Condition conditionChange(Condition changeCondition) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Condition condition = mapper.readValue(new File(jsonFileUrl), Condition.class);
        if(changeCondition.getCondition() != "") {
            condition.setCondition(changeCondition.getCondition());
        }else {
            condition.setCondition(condition.getCondition());
        }
        condition.setThen(changeCondition.getThen());

        if(changeCondition.getElseIfCondition() != null) {
            condition.setElseIfCondition(changeCondition.getElseIfCondition());
        }else {
            condition.setElseIfCondition(condition.getElseIfCondition());
        }
        if(changeCondition.getElseCondition()!= null) {
            condition.setElseCondition(changeCondition.getElseCondition());
        }else {
            condition.setElseCondition(condition.getElseCondition());
        }
        mapper.writeValue(new File(jsonFileUrl), condition);
        return condition;
    }

    /**
     * To read the yml file
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    private String readFile(String fileName) throws Exception {
        File file = new File(fileName);
        byte[] bytes = Files.readAllBytes(file.toPath());
        return new String(bytes);
    }

    /**
     * Method to evaluate if condition
     *
     * @param a
     * @param b
     * @return
     * @throws Exception
     */
    public boolean evaluateIfCondition(String a, int b) throws Exception {

        String jsonStr = readFile(jsonFileUrl);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(jsonStr);

        if (!node.has("condition")) {
            return false; // Invalid JSON format
        }

        String condition = node.get("condition").asText();

        // Replace 'a' and 'b' with actual values
        condition = condition.replace("a", "\"" + a + "\"").replace("b", String.valueOf(b));


        // Use a scripting engine to evaluate the condition
        // Here, we are using simple JavaScript evaluation
        javax.script.ScriptEngineManager manager = new javax.script.ScriptEngineManager();
        javax.script.ScriptEngine engine = manager.getEngineByName("JavaScript");
        if (!(boolean) engine.eval(condition)) {
            condition = node.get("elseIfCondition").get("condition").asText();
            condition = condition.replace("b", String.valueOf(b));
            engine = manager.getEngineByName("JavaScript");
        }

        try {
            return (boolean) engine.eval(condition);
        } catch (Exception e) {
            return false; // Invalid condition
        }
    }


    /**
     * Method translate same format
     *
     * @param changeCondition
     * @return
     * @throws Exception
     */
    public Condition translateToString(Condition changeCondition) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Condition condition = mapper.readValue(new File(jsonFileUrl), Condition.class);
        condition.setCondition(changeCondition.getCondition());
        condition.setThen(changeCondition.getThen());
        condition.setElseIfCondition(changeCondition.getElseIfCondition());
        condition.setElseCondition(condition.getElseCondition());
        mapper.writeValue(new File(jsonFileUrl), condition);
        return condition;
    }

    /**
     * Method translate yml format
     *
     * @param
     * @return
     * @throws Exception
     */
    public String translteToYML() throws Exception {
        String json = readFile(jsonFileUrl);

        // Parse JSON to a Map
        Yaml yaml = new Yaml();
        Map<String, Object> jsonMap = yaml.load(json);

        // Convert Map to YAML
        String yamlOutput = yaml.dump(jsonMap);

        // Specify the output YAML file path
        String outputPath = ymlFileUrl;

        // Write the YAML content to the file
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(yamlOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "YAML content saved to " + outputPath;
    }

}
