package com.example.demo.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface ControllerInterface <T> {
    public ResponseEntity<List<T>> get();
    public ResponseEntity<String> add(@RequestBody T addDetails);
    public ResponseEntity<String> modify(@RequestBody Map<String,String> modifyDetails);
    public ResponseEntity<String> delete(@RequestBody T deleteDetails);
}
