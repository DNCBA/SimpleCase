package com.mhc.rmi.service;

import com.mhc.jdbc.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote {

    public User find() throws RemoteException;

}
