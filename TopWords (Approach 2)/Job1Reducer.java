
import java.io.IOException;
import java.util.HashSet;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class Job1Reducer extends Reducer<Text, Text, Text, Text> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        HashSet<String> uniqueFiles = new HashSet<>();
        for (Text val : values) {
            uniqueFiles.add(val.toString());
        }
        for (String file : uniqueFiles) {
            context.write(key, new Text(file));
        }
    }
}
