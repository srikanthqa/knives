package com.github.knives.mime4j

import org.apache.james.mime4j.MimeException;
import org.apache.james.mime4j.parser.ContentHandler;
import org.apache.james.mime4j.stream.BodyDescriptor;
import org.apache.james.mime4j.stream.Field;
import org.apache.james.mime4j.parser.MimeStreamParser;
import org.apache.james.mime4j.stream.MimeConfig;

class Mine4jExample {
	public static void main(String[] args) {
		final ContentHandler handler = new ContentHandlerMultipartBuilder();
		final MimeConfig config = new MimeConfig();
		final MimeStreamParser parser = new MimeStreamParser(config);
		parser.setContentHandler(handler);
		parser.parse(System.in);
	}
	
	private static class ContentHandlerMultipartBuilder implements ContentHandler {
		@Override
		public void body(BodyDescriptor bodyDescriptor, InputStream inputStream)
				throws MimeException, IOException {
			
		}
	
		@Override
		public void endBodyPart() throws MimeException {
			println("endBodyPart");
		}
	
		@Override
		public void endHeader() throws MimeException {
			println("endHeader");
		}
	
		@Override
		public void endMessage() throws MimeException {
			println("endMessage");
		}
	
		@Override
		public void endMultipart() throws MimeException {
			println("endMultipart");
		}
	
		@Override
		public void epilogue(InputStream inputStream) throws MimeException, IOException {
			println("epilogue");
		}
	
		@Override
		public void field(Field field) throws MimeException {
			println("field");
			println(field.getName() + ": " + field.getBody());
		}
	
		@Override
		public void preamble(InputStream inputStream) throws MimeException, IOException {
			println("preamble");
		}
	
		@Override
		public void raw(InputStream inputStream) throws MimeException, IOException {
			println("raw");
			
		}
	
		@Override
		public void startBodyPart() throws MimeException {
			println("startBodyPart");
			
		}
	
		@Override
		public void startHeader() throws MimeException {
			println("startHeader");
			
		}
	
		@Override
		public void startMessage() throws MimeException {
			println("startMessage");
			
		}
	
		@Override
		public void startMultipart(BodyDescriptor bodyDescriptor) throws MimeException {
			println("startMultipart");
			println(bodyDescriptor.getBoundary());
		}
		
	}
	
}
