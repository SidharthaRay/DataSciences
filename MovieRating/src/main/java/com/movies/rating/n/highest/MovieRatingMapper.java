package com.movies.rating.n.highest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.movies.constant.MoviesConstants;

public class MovieRatingMapper extends
		Mapper<LongWritable, Text, Text, Text> {

	private static Map<String, Rating> movieRetingMap = new HashMap<String, Rating>();

	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {

		Path[] cacheFilesLocal = DistributedCache.getLocalCacheFiles(context
				.getConfiguration());
		BufferedReader brReader = null;
		String strLineRead = "";
		try {
			brReader = new BufferedReader(new FileReader(
					cacheFilesLocal[0].toString()));
			while ((strLineRead = brReader.readLine()) != null) {
				String movieFieldArray[] = strLineRead.split("\\|");
				String movieId = movieFieldArray[MoviesConstants.MOVIE_RATING_MOVIEID_FK_INDEX]
						.trim();
				if (!movieRetingMap.containsKey(movieId)) {
					movieRetingMap.put(movieId, new Rating(1, Integer.parseInt(movieFieldArray[MoviesConstants.MOVIE_RATING_INDEX])));
				} else {
					movieRetingMap.get(movieId).setRating(
							movieRetingMap.get(movieId).getRating() + Integer.parseInt(movieFieldArray[MoviesConstants.MOVIE_RATING_INDEX]));
					movieRetingMap.get(movieId).setRatingCount(
							movieRetingMap.get(movieId).getRatingCount() + 1);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (brReader != null) {
				brReader.close();
			}
		}
	}

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String[] array = value.toString().split("\\|");
		if (movieRetingMap.containsKey(array[0])) {
			context.write(new Text("Rating"),
					new Text(
							array[0]
									+ ","
									+ array[1]
									+ ","
									+ array[2]
									+ ","
									+ movieRetingMap.get(array[0]).getRatingCount()
									+ ","
									+ (movieRetingMap.get(array[0]).getRating() / movieRetingMap.get(array[0]).getRatingCount())));
		} else {
			context.write(new Text("Rating"),
					new Text(
							array[0]
									+ ","
									+ array[1]
									+ ","
									+ array[2]
									+ ",0,0"));
		}
	}

	public static class Rating {

		private int ratingCount;
		private int rating;

		public Rating() {
		}

		public Rating(int ratingCount, int rating) {
			this.ratingCount = rating;
			this.rating = rating;
		}

		public int getRatingCount() {
			return ratingCount;
		}

		public void setRatingCount(int ratingCount) {
			this.ratingCount = ratingCount;
		}

		public int getRating() {
			return rating;
		}

		public void setRating(int rating) {
			this.rating = rating;
		}

	}
}
