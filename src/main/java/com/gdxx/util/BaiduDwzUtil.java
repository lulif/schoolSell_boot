package com.gdxx.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class BaiduDwzUtil {
	final static String CREATE_API = "https://dwz.cn/admin/v2/create";
	final static String TOKEN = "e2fa0f1dfb9d438fe4511c0a895089b2"; // TODO:����Token

	class UrlResponse {
		@SerializedName("Code")
		private int code;

		@SerializedName("ErrMsg")
		private String errMsg;

		@SerializedName("LongUrl")
		private String longUrl;

		@SerializedName("ShortUrl")
		private String shortUrl;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getErrMsg() {
			return errMsg;
		}

		public void setErrMsg(String errMsg) {
			this.errMsg = errMsg;
		}

		public String getLongUrl() {
			return longUrl;
		}

		public void setLongUrl(String longUrl) {
			this.longUrl = longUrl;
		}

		public String getShortUrl() {
			return shortUrl;
		}

		public void setShortUrl(String shortUrl) {
			this.shortUrl = shortUrl;
		}
	}

	/**
	 * ��������ַ
	 *
	 * @param longUrl
	 *            ����ַ����ԭ��ַ
	 * @return �ɹ�������ַ ʧ�ܣ����ؿ��ַ���
	 */
	public static String createShortUrl(String longUrl) {
		String params = "{\"url\":\"" + longUrl + "\"}";

		BufferedReader reader = null;
		try {
			// ��������
			URL url = new URL(CREATE_API);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // ��������ʽ
			connection.setRequestProperty("Content-Type", "application/json"); // ���÷������ݵĸ�ʽ
			connection.setRequestProperty("Token", TOKEN); // ���÷������ݵĸ�ʽ");

			// ��������
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8����
			out.append(params);
			out.flush();
			out.close();

			// ��ȡ��Ӧ
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			String res = "";
			while ((line = reader.readLine()) != null) {
				res += line;
			}
			reader.close();

			// ��ȡ���ɶ���ַ
			UrlResponse urlResponse = new Gson().fromJson(res, UrlResponse.class);
			if (urlResponse.getCode() == 0) {
				return urlResponse.getShortUrl();
			} else {
				System.out.println(urlResponse.getErrMsg());
			}

			return ""; // TODO���Զ��������Ϣ
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
		return ""; // TODO���Զ��������Ϣ
	}

	public static void main(String[] args) {
		String res = createShortUrl("https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login");
		System.out.println(res);
	}

}