
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Driver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        // Job 1
        Job job1 = Job.getInstance(conf, "Deduplicate word-file pairs");
        job1.setJarByClass(Driver.class);
        job1.setMapperClass(Job1Mapper.class);
        job1.setReducerClass(Job1Reducer.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(Text.class);
        FileInputFormat.setInputPaths(job1, new Path(args[0]));
        FileOutputFormat.setOutputPath(job1, new Path("job1_output"));
        job1.waitForCompletion(true);

        // Job 2
        Job job2 = Job.getInstance(conf, "Count files per word");
        job2.setJarByClass(Driver.class);
        job2.setMapperClass(Job2Mapper.class);
        job2.setReducerClass(Job2Reducer.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(IntWritable.class);
        FileInputFormat.setInputPaths(job2, new Path("job1_output"));
        FileOutputFormat.setOutputPath(job2, new Path("job2_output"));
        job2.waitForCompletion(true);

        // Job 3
        Job job3 = Job.getInstance(conf, "Top 30 words");
        job3.setJarByClass(Driver.class);
        job3.setMapperClass(Job3Mapper.class);
        job3.setReducerClass(Job3Reducer.class);
        job3.setSortComparatorClass(DescendingIntComparator.class);
        job3.setOutputKeyClass(IntWritable.class);
        job3.setOutputValueClass(Text.class);
        FileInputFormat.setInputPaths(job3, new Path("job2_output"));
        FileOutputFormat.setOutputPath(job3, new Path("job3_output"));
        job3.waitForCompletion(true);
    }
}
