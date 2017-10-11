package us.codecraft.tinyioc.io;


import java.io.IOException;
import java.io.InputStream;
/**
 * Resource是spring内部定资源的接口
 * @author zhujie
 *
 */
public interface Resource {

	InputStream getInputStream() throws IOException;
}
