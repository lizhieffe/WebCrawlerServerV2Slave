package com.zl.daemons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.zl.abstracts.AJob;
import com.zl.sockets.SocketSender;

@Component
public class ReportJobDaemonHelper {
	
	@Autowired
	private ApplicationContext appContext;
	
	@Value("${master.ip}")
	private String MASTER_NODE_IP;
	
	@Value("${server-socket-port}")
	private String MASTER_NODE_SOCKET_PORT;
	
	private SocketSender socketSender;
	
	private boolean connectedToMaster;
	
	
	public void reportJob(AJob job) {
		
		if (!connectedToMaster) {
			socketSender = new SocketSender(MASTER_NODE_IP, Integer.parseInt(MASTER_NODE_SOCKET_PORT));
			socketSender.connect();
			connectedToMaster = true;
		}
		socketSender.send(job);
	}
}
