import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

import java.io.IOException;
import java.util.*;

public class Reducer2 extends Reducer<Text, IntWritable, Text, IntWritable> {

    private TreeMap<Integer, List<String>> countToWordsMap = new TreeMap<>(Collections.reverseOrder());

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) {
        int total = 0;
        for (IntWritable val : values) {
            total += val.get(); 
        }
        System.out.println("Reducing word: " + key + ", count: " + total);

        countToWordsMap.computeIfAbsent(total, k -> new ArrayList<>()).add(key.toString());
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        int emitted = 0;

        System.out.println("Cleanup called. Emitting top 30...");

        for (Map.Entry<Integer, List<String>> entry : countToWordsMap.entrySet()) {
            for (String word : entry.getValue()) {
                if (emitted < 30) {
                    context.write(new Text(word), new IntWritable(entry.getKey()));
                    emitted++;
                } else {
                    return;
                }
            }
        }
    }
}
