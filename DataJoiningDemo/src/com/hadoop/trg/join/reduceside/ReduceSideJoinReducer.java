package com.hadoop.trg.join.reduceside;

import org.apache.hadoop.contrib.utils.join.DataJoinReducerBase;
import org.apache.hadoop.contrib.utils.join.TaggedMapOutput;
import org.apache.hadoop.io.Text;

public class ReduceSideJoinReducer extends DataJoinReducerBase {
	protected TaggedMapOutput combine(Object[] tags, Object[] values) {
		if (tags.length < 2)
			return null;
		String joinedStr = "";
		for (int i = 0; i < values.length; i++) {
			if (i > 0)
				joinedStr += ",";
			TaggedWritable tw = (TaggedWritable) values[i];
			String line = ((Text) tw.getData()).toString();
			String[] tokens = line.split(",", 2);
			joinedStr += tokens[1];
		}
		TaggedWritable retv = new TaggedWritable(new Text(joinedStr));
		retv.setTag((Text) tags[0]);
		return retv;
	}
}