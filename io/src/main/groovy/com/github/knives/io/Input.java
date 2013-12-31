package com.github.knives.io;

import java.io.File;
import java.io.InputStream;

public interface Input<R extends Input, T> {
	public T read(File file);
	
	public T read(InputStream inputStream);
}

