package com.cml.lib.rxvolley.framework;

public interface VolleyConstant {
	public interface Network {
		// 网络请求超时设定
		int TIMEOUT = 10000; // 10 seconds
		// 网络请求失败重试
		int RETRY = 0; // 0: never retry
	}
}
