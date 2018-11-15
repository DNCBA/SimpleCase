package com.mhc.proxy.myImpl;

import java.io.*;

public class MyclassLoader extends ClassLoader {


    public Class loadClass(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(bytes)) !=  -1){
            outputStream.write(bytes,0,len);
        }

        return defineClass("com.mhc.proxy.myImpl.$Proxy0",outputStream.toByteArray(),0,outputStream.size());
    }
}
