package com.example.smilecli;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.dataformat.smile.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	byte[] buffer = new byte[32 * 1024];

	int bytesRead;
	while ((bytesRead = System.in.read(buffer)) > 0) {
	            baos.write(buffer, 0, bytesRead);
	}
	byte[] jsonBytes = baos.toByteArray();
	SmileFactory sf = new SmileFactory();
        byte[] encoded = _smileDoc(sf, jsonBytes);
	System.out.write(encoded);
    }
    private static byte[] _smileDoc(SmileFactory smileFactory, byte[] json) throws IOException
    {
        JsonFactory jf = new JsonFactory();
        JsonParser p = jf.createParser(json);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JsonGenerator g = smileGenerator(out, true);
    	
        while (p.nextToken() != null) {
        	g.copyCurrentEvent(p);
        }
        p.close();
        g.close();
        return out.toByteArray();
    }

    private static SmileGenerator smileGenerator(OutputStream result, boolean addHeader)
        throws IOException
    {
        return smileGenerator(new SmileFactory(), result, addHeader);
    }
    private static SmileGenerator smileGenerator(SmileFactory f, OutputStream result, boolean addHeader)
        throws IOException
    {
        f.configure(SmileGenerator.Feature.WRITE_HEADER, addHeader);
        f.configure(SmileGenerator.Feature.CHECK_SHARED_NAMES, false);
        f.configure(SmileGenerator.Feature.CHECK_SHARED_STRING_VALUES, false);
        return f.createGenerator(result, null);
    }
}
