package com.mhc.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.IOException;

public class HdfsDemo {

    @Test
    public void testApi() throws IOException {
        Configuration configuration = new Configuration();
        configuration.addResource(new Path("core-site.xml"));
        configuration.addResource(new Path("hdfs-site.xml"));
        configuration.addResource(new Path("yarn-site.xml"));

        FileSystem fileSystem = FileSystem.get(configuration);
        fileSystem.create(new Path("/"));
        fileSystem.getFileStatus(new Path("/"));
        fileSystem.listStatus(new Path("/"));
        fileSystem.copyFromLocalFile(new Path("/"), new Path("/"));
        fileSystem.copyToLocalFile(new Path("/"), new Path("/"));
    }
}
