package com.github.wesleyegberto.springmongodb.converter;

import java.util.UUID;

import org.springframework.core.convert.converter.Converter;

import com.mongodb.DBObject;

public class UuidConverter implements Converter<UUID, DBObject> {

	@Override
	public DBObject convert(UUID source) {
		return null;
	}

}
