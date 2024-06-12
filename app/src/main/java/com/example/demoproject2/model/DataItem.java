package com.example.demoproject2.model;

import java.util.List;

public class DataItem{
	private List<ImagesItem> images;
	private Price price;
	private String id;
	private String title;

	public List<ImagesItem> getImages(){
		return images;
	}

	public Price getPrice(){
		return price;
	}

	public String getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}
}
