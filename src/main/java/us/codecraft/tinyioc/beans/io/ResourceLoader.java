package us.codecraft.tinyioc.beans.io;

import java.net.URL;

/**
 * 
 * @author zhujie
 *
 */
public class ResourceLoader {

	public Resource getResource(String location) {
		URL resource = this.getClass().getClassLoader().getResource(location);
		return new UrlResource(resource);
	}
}
