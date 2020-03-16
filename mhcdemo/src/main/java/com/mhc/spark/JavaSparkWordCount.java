//package com.mhc.spark;
//
//import java.util.Arrays;
//import java.util.Iterator;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.FlatMapFunction;
//import org.apache.spark.api.java.function.Function2;
//import org.apache.spark.api.java.function.PairFunction;
//import org.apache.spark.api.java.function.VoidFunction;
//
//import scala.Tuple2;
//
//public class JavaSparkWordCount {
//	public static void main(String[] args) {
//		/**
//		 * conf
//		 * 	1.可以设置spark的运行模式
//		 * 	2.可以设置spark在webui中显示的application的名称。
//		 * 	3.可以设置当前spark application 运行的资源(内存+core)
//		 *
//		 * Spark运行模式：
//		 * 	1.local --在eclipse ，IDEA中开发spark程序要用local模式，本地模式，多用于测试
//		 *  2.stanalone -- Spark 自带的资源调度框架，支持分布式搭建,Spark任务可以依赖standalone调度资源
//		 *  3.yarn -- hadoop 生态圈中资源调度框架。Spark 也可以基于yarn 调度资源
//		 *  4.mesos -- 资源调度框架
//		 */
//		SparkConf conf = new SparkConf();
//		conf.setMaster("local");
//		conf.setAppName("JavaSparkWordCount");
//
//		/**
//		 * SparkContext 是通往集群的唯一通道
//		 */
//		JavaSparkContext sc  = new JavaSparkContext(conf);
//		/**
//		 * sc.textFile 读取文件
//		 */
//		JavaRDD<String> lines = sc.textFile("./words");
//
//		/**
//		 * flatMap 进一条数据出多条数据，一对多关系
//		 */
//		JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
//
//			/**
//			 *
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public Iterator<String> call(String line) throws Exception {
//				return (Iterator<String>) Arrays.asList(line.split(" "));
//			}
//		});
//
//		/**
//		 * 在java中 如果想让某个RDD转换成K,V格式 使用xxxToPair
//		 * K,V格式的RDD:JavaPairRDD<String, Integer>
//		 */
//		JavaPairRDD<String, Integer> pairWords = words.mapToPair(new PairFunction<String, String, Integer>() {
//
//			/**
//			 *
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public Tuple2<String, Integer> call(String word) throws Exception {
//				return new Tuple2<String, Integer>(word,1);
//			}
//		});
//
//		/**
//		 * reduceByKey
//		 * 1.先将相同的key分组
//		 * 2.对每一组的key对应的value去按照你的逻辑去处理
//		 */
//		JavaPairRDD<String, Integer> result = pairWords.reduceByKey(new Function2<Integer, Integer, Integer>() {
//
//			/**
//			 *
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public Integer call(Integer v1, Integer v2) throws Exception {
//				return v1+v2;
//			}
//		});
//
//		result.foreach(new VoidFunction<Tuple2<String,Integer>>() {
//
//			/**
//			 *
//			 */
//			private static final long serialVersionUID = 639147013282066178L;
//
//			@Override
//			public void call(Tuple2<String, Integer> tuple) throws Exception {
//				System.out.println(tuple);
//			}
//		});
//
//
//		sc.stop();
//
//	}
//}
