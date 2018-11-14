package com.mhc.proxy.myImpl;

import com.mhc.proxy.FunctionImpl;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.function.Supplier;

public class MyProxy {

    public static void main(String[] args) throws Exception {

        Supplier supplier = (Supplier) MyProxy.newProxyInstance(new MyclassLoader(), FunctionImpl.class.getInterfaces(), new MyInvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return "66666";
            }
        });

        Object o = supplier.get();

        System.out.println(o);


    }


    public static Object newProxyInstance(MyclassLoader loader,
                                          Class<?>[] interfaces,
                                          MyInvocationHandler h) throws Exception {

        //1.根据接口生成字符串
        String src = generateSrc(interfaces[0]);
        //2.将字符串写出到磁盘
        File file = writerToDsc(src);
        //3.将文件进行编译
        file = complier(file);
        //4.将编译后的文件加载
        Class clazz = load(file,loader);
        //5.根据class对象进行实例化，并返回
        Object instance = clazz.newInstance();
        return instance;
    }

    private static Class load(File file,MyclassLoader loader) throws IOException {
        return loader.loadClass(file);
    }

    private static File complier(File file) throws IOException {
        JavaCompiler  compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
        Iterable iterable = manager.getJavaFileObjects(file);

        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, iterable);
        task.call();
        manager.close();
        File parentFile = file.getParentFile();
        file.delete();
        return new File(parentFile, "$Proxy0.class");
    }

    private static File writerToDsc(String src) throws IOException {
        File file = new File("mhcdemo\\src\\main\\java\\com\\mhc\\proxy\\myImpl\\$Proxy0.java");
        file.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(src.getBytes());
        outputStream.flush();
        outputStream.close();
        return file;
    }

    private static String generateSrc(Class<?> interfaces){
        StringBuffer src = new StringBuffer();
        String ln = "\r\n";
        src.append("package com.mhc.proxy.myImpl;" + ln);
        src.append("import java.lang.reflect.Method;" + ln);
        src.append("public class $Proxy0 implements " + interfaces.getName() + "{" + ln);
        src.append("MyInvocationHandler h;" + ln);
        src.append("public $Proxy0(MyInvocationHandler h) {" + ln);
        src.append("this.h = h;" + ln);
        src.append("}" + ln);
        for (Method m : interfaces.getMethods()) {

            src.append("public " + m.getReturnType().getName() + " " + m.getName() + "(){" + ln);
            src.append("try{" + ln);
            src.append("Method m = " + interfaces.getName() + ".class.getMethod(\"" +m.getName()+"\",new Class[]{});" + ln);
            src.append("return this.h.invoke(this,m,null);" + ln);
            src.append("}catch(Throwable e){e.printStackTrace();}" + ln);
            src.append("return null;");
            src.append("}" + ln);
        }
        src.append("}");

        System.out.println(src.toString());

        return src.toString();
    }

}
