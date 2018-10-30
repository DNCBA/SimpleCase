package com.mhc.rmi.service;

import com.mhc.rmi.service.impl.UserServiceImpl;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class App {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {

        UserService userService = new UserServiceImpl();

        Registry registry = LocateRegistry.createRegistry(8080);

        registry.bind("userService",userService);

        System.out.println("RMI:service is ready。。。");
    }
}
