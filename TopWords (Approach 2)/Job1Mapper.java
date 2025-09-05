
import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class Job1Mapper extends Mapper<LongWritable, Text, Text, Text> {
    private Text word = new Text();
    private Text filename = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String fileName = ((FileSplit) context.getInputSplit()).getPath().getName();
        String[] tokens = value.toString().split("\\W+");

        for (String token : tokens) {
            if (!token.isEmpty()) {
                word.set(token.toLowerCase());
                filename.set(fileName);
                context.write(word, filename);
            }
        }
    }
}
