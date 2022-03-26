package com.github.wesleyegberto.springstream.samples.models;

import java.util.Date;

public class WordCount {
	private String word;
	private Long count;
	private Date start;
	private Date end;

	public WordCount(String word, Long count, Date start, Date end) {
		this.word = word;
		this.count = count;
		this.start = start;
		this.end = end;
	}

	public String getWord() {
		return word;
	}

	public Long getCount() {
		return count;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}
}