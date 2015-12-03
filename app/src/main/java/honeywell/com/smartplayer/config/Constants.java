package honeywell.com.smartplayer.config;

public class Constants {
	public static final String URL = "192.168.0.111";
	public static final int PORT_CTR = 12345;
	public static final int PORT_REG = 12346;
	public static final class TAGS {
		public static final String NEWS_TAG = "news";
		public static final String BLOG_TAG = "blog";
		public static final String WIKI_TAG = "wiki";
	}

	public static final class DBContentType {
		public static final String Content_list = "list";
		public static final String Content_content = "content";
		public static final String Discuss="discuss";
	}

	public static final class WebSourceType{
		public static final String Json="json";
		public static final String Xml="xml";
	}
}
