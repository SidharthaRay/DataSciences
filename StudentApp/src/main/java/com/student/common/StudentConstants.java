package com.student.common;

public class StudentConstants {
	public static enum DATA_INDEX {
		ROLL_NO(0), SCHOOL_NAME(1), NAME(2), AGE(3), GENDER(4), CLAZZ(5), SUBJECT(6), MARKS(7);
		
		private int index;
		private DATA_INDEX(int index) {
			this.index = index;
		}
		public int getIndex() {
			return index;
		}
		
	}
}
