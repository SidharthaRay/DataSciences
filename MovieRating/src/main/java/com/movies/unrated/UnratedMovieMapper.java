package com.movies.unrated;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.movies.constant.MoviesConstants;

public class UnratedMovieMapper extends Mapper<LongWritable, Text, NullWritable, Text>{
	
	private static Map<String, Boolean> movieRecMap = new HashMap<String, Boolean>();
  
	@Override
	protected void setup(Context context) throws IOException,InterruptedException {
	  
	    Path[] cacheFilesLocal = DistributedCache.getLocalCacheFiles(context.getConfiguration());
	    BufferedReader brReader = null;
	    String strLineRead = "";
	    try {
	        brReader = new BufferedReader(new FileReader(cacheFilesLocal[0].toString()));
	        while ((strLineRead = brReader.readLine()) != null) {
	            String movieFieldArray[] = strLineRead.split("\\|");
	            movieRecMap.put(movieFieldArray[MoviesConstants.MOVIE_RATING_MOVIEID_FK_INDEX].trim(), Boolean.TRUE);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }finally {
	        if (brReader != null) {
	            brReader.close();    
	        }
	    }
	}
	
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String[] array = value.toString().split("\\|");
		if(!movieRecMap.containsKey(array[0])) {
			context.write(NullWritable.get(), new Text(array[1] + "," + array[2]));
		}
	}
}
