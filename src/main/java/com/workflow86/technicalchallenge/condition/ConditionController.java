package com.workflow86.technicalchallenge.condition;

import com.workflow86.technicalchallenge.model.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ConditionController {

    @Autowired
    private ConditionService conditionService;

    /** Rest API to modify the json condition
     * @param condition
     * @return
     * @throws IOException
     */
    @PostMapping("/condition")
    public ResponseEntity modifyCondition(@RequestBody Condition condition) throws IOException {
        return new ResponseEntity<>(conditionService.conditionChange(condition), HttpStatus.OK);
    }

    /**
     * Rest API to check the condition
     * @param a
     * @param b
     * @return
     * @throws Exception
     */
    @GetMapping("/checkCondition")
    public ResponseEntity getJson(@RequestParam String a, @RequestParam int b) throws Exception {
        return new ResponseEntity<>(conditionService.evaluateIfCondition(a, b), HttpStatus.OK);

    }

    /**
     * Rest API to translate to same format
     * @param condition
     * @return
     * @throws Exception
     */
    @PostMapping("/translateToSameFormat")
    public ResponseEntity translateToSameFormat(@RequestBody Condition condition) throws Exception {
        return new ResponseEntity<>(conditionService.translateToString(condition), HttpStatus.OK);
    }

    /**
     * Rest API to translate to different format
     * @return
     * @throws Exception
     */
    @PostMapping("/translateToDifferentFormat")
    public ResponseEntity translateToYmlFormat() throws Exception {
        return new ResponseEntity<>(conditionService.translteToYML(), HttpStatus.OK);

    }


}

