import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class WordFileMapper extends Mapper<LongWritable, Text, Text, Text> {
    private Text word = new Text();
    private Text filename = new Text();

    @Override
    protected void setup(Context context) {
        filename.set(((FileSplit) context.getInputSplit()).getPath().getName());
    }

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        for (String token : value.toString().split("\\W+")) {
            if (!token.isEmpty()) {
                word.set(token.toLowerCase());
                context.write(word, filename);
            }
        }
    }
}
