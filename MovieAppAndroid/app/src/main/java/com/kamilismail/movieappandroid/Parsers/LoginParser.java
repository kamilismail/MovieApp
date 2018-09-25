package com.kamilismail.movieappandroid.Parsers;

import com.kamilismail.movieappandroid.DTO.UserDTO;
import com.kamilismail.movieappandroid.Enum.Role;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginParser {

    public UserDTO parse(String jsonData) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return getUserData(jsonObject);
    }

    private  UserDTO getUserData(JSONObject jsonObject) {
        try {
            String login = jsonObject.getString("username");
            String role = jsonObject.getString("role");
            int id = jsonObject.getInt("id");
            Role userRole = Role.USER;
            if (role.equals("admin"))
                userRole = Role.ADMIN;
            return new UserDTO(login, id, userRole);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
