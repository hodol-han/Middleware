package kr.ac.ajou.lazybones.templates;

import java.io.Serializable;

/**
 *
 * @author Han
 */
public class Service implements Serializable {
	private String name;
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}