package com.hadoop.trg.join.reduceside;

import org.apache.hadoop.contrib.utils.join.DataJoinMapperBase;
import org.apache.hadoop.contrib.utils.join.TaggedMapOutput;
import org.apache.hadoop.io.Text;

public class ReduceSideJoinMapper extends DataJoinMapperBase {

	protected Text generateInputTag(String inputFile) {
		String datasource = inputFile.split(".")[0];
		return new Text(datasource);
	}

	protected Text generateGroupKey(TaggedMapOutput aRecord) {
		String line = ((Text) aRecord.getData()).toString();
		String[] tokens = line.split(",");
		String groupKey = tokens[0];
		return new Text(groupKey);
	}

	protected TaggedMapOutput generateTaggedMapOutput(Object value) {
		TaggedWritable retv = new TaggedWritable((Text) value);
		retv.setTag(this.inputTag);
		return retv;
	}
}