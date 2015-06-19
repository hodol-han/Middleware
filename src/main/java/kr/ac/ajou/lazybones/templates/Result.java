package kr.ac.ajou.lazybones.templates;

import java.io.Serializable;

/**
 *
 * @author Ethan
 */
public class Result implements Serializable {
	private String result;
	private String[] errors = new String[0];

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String[] getErrors() {
		return errors;
	}

	public void setErrors(String[] errors) {
		this.errors = errors;
	}

}