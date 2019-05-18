package entities;

import lombok.Data;

import java.util.HashMap;

@Data
public class LanceAccess {

    private String role;

    private HashMap<String, Boolean> access;
}
