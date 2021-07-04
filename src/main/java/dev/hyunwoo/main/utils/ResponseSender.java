package dev.hyunwoo.main.utils;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseSender {
    public ResponseEntity<Object> send(int status, Object body) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        if(status == 200){
            response.put("suceess", true);
            response.put("response", body);
            response.put("error", null);
    
            return ResponseEntity.ok(response);
        } else if (status == 400) {
            HashMap<String, Object> error = new HashMap<String, Object>();
            if(body != null){
                error.put("message", body);
            } else {
                error.put("message", "400 Bad Request");
            }
            error.put("status", 400);

            response.put("suceess", false);
            response.put("response", null);
            response.put("error", error);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else if (status == 500) {
            HashMap<String, Object> error = new HashMap<String, Object>();
            if(body != null){
                error.put("message", body);
            } else {
                error.put("message", "500 Internal Server Error");
            }
            error.put("status", 500);

            response.put("suceess", false);
            response.put("response", null);
            response.put("error", error);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            HashMap<String, Object> error = new HashMap<String, Object>();
            error.put("message", "404 Not Found");
            error.put("status", 404);

            response.put("suceess", false);
            response.put("response", null);
            response.put("error", error);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}