package com.cice.demoSpring.rest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
public class LoginResource {

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public String login(
            @RequestParam(name = "user") String user,
            @RequestParam(name = "pass") String pass) {

        String respuesta = null;
        //respuesta = user + " " + pass;
        pass = DigestUtils.sha256Hex(pass);


        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/login",
                        "root", "root");
                Statement statement = connection.createStatement();
                ResultSet busqueda = statement.executeQuery(
                        "SELECT * FROM usuarios WHERE user = '"+user+"' AND pass = '"+pass+"'");

                if(busqueda.first()){
                    respuesta = "Usuarios encontrado";
                } else {
                    respuesta = "Usuarios y/o contraseña no coincide";
                }
                busqueda.close();
                statement.close();
                connection.close();


            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return respuesta;
    }
}
