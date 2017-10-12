package us.codecraft.tinyioc.io;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import us.codecraft.tinyioc.beans.io.Resource;
import us.codecraft.tinyioc.beans.io.ResourceLoader;

public class ResourceLoaderTest {

	@Test
	public void test() throws IOException {
		ResourceLoader resourceLoader = new ResourceLoader();
		Resource resource =resourceLoader.getResource("tinyioc.xml");
		InputStream inputStream = resource.getInputStream();
		assertNotNull(inputStream);
	}

}
