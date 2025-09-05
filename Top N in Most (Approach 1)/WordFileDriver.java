import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordFileDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Word File Count");

        job.setJarByClass(WordFileDriver.class);
        job.setMapperClass(WordFileMapper.class);
        job.setReducerClass(WordFileReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean success = job.waitForCompletion(true);
                if (!success) {
                    System.err.println("Job 1 failed.");
                    System.exit(1);
                }
        System.err.println("Job 1 succeeded");
        Job job2 = Job.getInstance(conf, "Top 30 Words");
        job2.setJarByClass(WordFileDriver.class);
        job2.setMapperClass(Mapper2.class); 
        job2.setReducerClass(Reducer2.class);

        job2.setMapOutputKeyClass(Text.class);
        job2.setMapOutputValueClass(IntWritable.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job2, new Path(args[1]));
        FileOutputFormat.setOutputPath(job2, new Path(args[2]));

        success = job2.waitForCompletion(true);
        if (!success) {
                    System.err.println("Job 2 failed.");
                    System.exit(1);
                }
        else {
            System.err.println("Job 2 succeeded");
        }
        System.exit(success ? 0 : 1);

    }
}

