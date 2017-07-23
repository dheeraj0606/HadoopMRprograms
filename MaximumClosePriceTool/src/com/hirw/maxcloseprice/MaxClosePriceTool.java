package com.hirw.maxcloseprice;

/**
 * MaxClosePrice.java
 * www.hadoopinrealworld.com
 * This is a driver program to calculate Max Close Price from stock dataset using MapReduce
 */

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MaxClosePriceTool extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {
	
			if (args.length != 2) {
			System.err.println("Usage: MaxClosePrice <input path> <output path>");
			System.exit(-1);
		}

		//Define MapReduce job
		Job job = new Job(getConf(), "maxcloseprice-tool");
		job.setJarByClass(MaxClosePriceTool.class);

		//Set input and output locations
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		//Enable file output compression
//		FileOutputFormat.setCompressOutput(job, true);
//		FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);
		
		//Set Input and Output formats
	    job.setInputFormatClass(TextInputFormat.class);
	    job.setOutputFormatClass(TextOutputFormat.class);

	    //Set Mapper and Reduce classes
		job.setMapperClass(MaxClosePriceMapper.class);
		job.setReducerClass(MaxClosePriceReducer.class);
		

		//Output types
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FloatWritable.class);

		//Submit job
		return job.waitForCompletion(true) ? 0 : 1;
	}
	
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new MaxClosePriceTool(), args);
		System.exit(exitCode);
	}
}
