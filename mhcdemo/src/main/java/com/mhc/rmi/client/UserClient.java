package com.mhc.rmi.client;

import com.mhc.jdbc.User;
import com.mhc.rmi.service.UserService;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class UserClient {

    public static void main(String[] args) throws RemoteException, NotBoundException {

        Registry registry = LocateRegistry.getRegistry("localhost", 8080);

        UserService userService = (UserService) registry.lookup("userService");

        User user = userService.find();

        System.out.println(user);

    }
}
