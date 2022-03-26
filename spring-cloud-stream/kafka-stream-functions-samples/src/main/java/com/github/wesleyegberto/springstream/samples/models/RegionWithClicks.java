package com.github.wesleyegberto.springstream.samples.models;

public class RegionWithClicks {
	private String region;
	private Long clicks;

	public RegionWithClicks(String region, String clicks) {
		if (region == null)
			this.region = "UNKNOWN";
		else
			this.region = region;
		this.clicks = Long.parseLong(clicks);
	}
	
	public String getRegion() {
		return region;
	}
	public Long getClicks() {
		return clicks;
	}
}
