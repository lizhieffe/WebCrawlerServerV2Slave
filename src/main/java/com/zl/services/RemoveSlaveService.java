package com.zl.services;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.zl.utils.AppProperties;
import com.zl.utils.ConfigUtil;
import com.zl.abstracts.AService;

import com.zl.daemons.SlaveMgntDaemon;
import com.zl.interfaces.IRemoveSlaveService;

@Service
@Scope("prototype")
public class RemoveSlaveService extends AService implements IRemoveSlaveService {
	
	@Autowired
	public SlaveMgntDaemon slaveMgntDaemon;
	
	@Async
	@Override
	public void removeSlave() {
		this.start();
	}

	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.POST;
	}

	@Override
	public String getUri() {
		return "/removeslave";
	}

	@Override
	public String constructRequestUrl() {
		String url = "http://" + AppProperties.getInstance().get("master.ip") + ":"
				+ AppProperties.getInstance().get("master.port") + getUri();
		return url;
	}

	@Override
	public HttpEntity<String> constructRequestHttpEntity() {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		JSONObject item = new JSONObject();
		item.put("ip", ConfigUtil.getLocalIp());
		item.put("port", Integer.parseInt(AppProperties.getInstance().get("server.port")));
		return new HttpEntity<String>(item.toString(), header);
	}

	@Override
	public void onSuccess(ResponseEntity<String> response) {
		slaveMgntDaemon.onRemoveSlaveSuccess();
	}

	@Override
	public void onFailure(ResponseEntity<String> response) {
		slaveMgntDaemon.onRemoveSlaveFailure();
	}
}
