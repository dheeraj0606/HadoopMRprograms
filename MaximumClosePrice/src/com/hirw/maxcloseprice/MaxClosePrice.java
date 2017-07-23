package com.hirw.maxcloseprice;

/**
 * MaxClosePrice.java
 * www.hadoopinrealworld.com
 * This is a driver program to calculate Max Close Price from stock dataset using MapReduce
 */

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class MaxClosePrice {

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage: MaxClosePrice <input path> <output path>");
			System.exit(-1);
		}

		//Define MapReduce job
		Job job = new Job();
		job.setJarByClass(MaxClosePrice.class);
		job.setJobName("MaxClosePrice");

		//Set input and output locations
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		//Set Input and Output formats
	    job.setInputFormatClass(TextInputFormat.class);
	    job.setOutputFormatClass(TextOutputFormat.class);

	    //Set Mapper and Reduce classes
		job.setMapperClass(MaxClosePriceMapper.class);
		job.setReducerClass(MaxClosePriceReducer.class);
		
		//Combiner (optional)
		job.setCombinerClass(MaxClosePriceReducer.class);
		
		//Output types
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FloatWritable.class);

		//Submit job
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
