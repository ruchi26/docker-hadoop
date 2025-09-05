
import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class Job3Reducer extends Reducer<IntWritable, Text, Text, IntWritable> {
    private int counter = 0;

    @Override
    protected void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text val : values) {
            if (counter < 30) {
                context.write(val, key);
                counter++;
            } else {
                break;
            }
        }
    }
}
