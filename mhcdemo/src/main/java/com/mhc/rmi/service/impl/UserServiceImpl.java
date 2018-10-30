package com.mhc.rmi.service.impl;

import com.mhc.jdbc.User;
import com.mhc.rmi.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {

    public UserServiceImpl() throws RemoteException {
    }

    @Override
    public User find() throws RemoteException{
        User user = new User();
        user.setId(1);
        user.setName("zs");
        user.setAge(18);
        return user;
    }
}
