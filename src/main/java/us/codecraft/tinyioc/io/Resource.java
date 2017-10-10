package us.codecraft.tinyioc.io;
/**
 * Resource是spring内部定资源的接口
 * @author zhujie
 *
 */

import java.io.IOException;
import java.io.InputStream;

public interface Resource {

	InputStream getInputStream() throws IOException;
}
