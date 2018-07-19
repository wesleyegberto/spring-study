package com.github.wesleyegberto.springmongodb.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Groups")
public class Group<T> {
	@Id
	private UUID id;
	private String name;
	private GroupType type;
	
	private T lastItem;
	private List<Entry<Integer, T>> items;

	public Group() {
	}

	public Group(UUID id, GroupType type, String name) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.items = new LinkedList<>();
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public GroupType getType() {
		return type;
	}
	
	public T getLastItem() {
		return lastItem;
	}
	
	public List<Entry<Integer, T>> getItems() {
		return items;
	}

	public void addItem(T item) {
		this.items.add(new Entry<>(items.size() + 1, item));
		this.lastItem = item;
	}
}
