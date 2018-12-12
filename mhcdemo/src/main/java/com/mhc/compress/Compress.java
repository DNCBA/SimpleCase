package com.mhc.compress;

import org.xeustechnologies.jtar.TarEntry;
import org.xeustechnologies.jtar.TarOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Compress {

    static File[] files = new File[3];

    static {
        ClassLoader classLoader = Compress.class.getClassLoader();
        String[] strings = new String[]{"application.properties","c3p0.properties","ecs.properties"};
        for (int i = 0; i < strings.length; i++) {
            URL resource = classLoader.getResource(strings[i]);
            files[i] = new File(resource.getFile());
        }
    }

    public static void main(String[] args) throws IOException {
        //打zip包
        testZip();
        //打tar包
        testTar();
    }

    private static void testTar() throws IOException {
        FileOutputStream fileOutputStream = null;
        TarOutputStream tarOutputStream = null;

        try {
            File desc = new File(UUID.randomUUID().toString().replaceAll("-", "")+".tar");
            if (!desc.exists()){
                desc.createNewFile();
            }

            fileOutputStream = new FileOutputStream(desc);
            tarOutputStream = new TarOutputStream(fileOutputStream);
            TarEntry tarEntry = null;
            for (File file : files){
                FileInputStream fileInputStream = new FileInputStream(file);
                tarEntry = new TarEntry(file,file.getName());
                tarOutputStream.putNextEntry(tarEntry);

                Integer len = null;
                byte[] bytes = new byte[1024];
                while (0 <= (len = fileInputStream.read(bytes))){
                    tarOutputStream.write(bytes,0,len);
                }
                fileInputStream.close();
            }

            System.out.println("tar:" + desc.getAbsolutePath());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (null != fileOutputStream){
                fileOutputStream.close();
            }
            if (null != tarOutputStream){
                try {
                    tarOutputStream.close();
                }catch (Exception e){

                }

            }
        }
    }

    private static void testZip() throws IOException {

        FileOutputStream fileOutputStream = null;
        ZipOutputStream zipOutputStream = null;


        try {
            File desc = new File(UUID.randomUUID().toString().replaceAll("-", "") + ".zip");
            if (!desc.exists()) {
                desc.createNewFile();
            }
            ZipEntry zipEntry = null;

            fileOutputStream = new FileOutputStream(desc);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            for (File file : files){
                FileInputStream fileInputStream = new FileInputStream(file);
                zipEntry = new ZipEntry(file.getName());
                zipOutputStream.putNextEntry(zipEntry);


                Integer len = null;
                byte[] bytes = new byte[1024];
                while (0 <= (len = fileInputStream.read(bytes))){
                    zipOutputStream.write(bytes,0,len);
                }

                fileInputStream.close();
            }
            System.out.println("zip:"+desc.getAbsolutePath());


        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            if (null != zipOutputStream){
                zipOutputStream.close();
            }
            if (null != fileOutputStream){
                fileOutputStream.close();
            }
        }




    }
}
