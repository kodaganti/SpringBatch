package org.springframework.batch.admin.zipfeed;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;

import com.impetus.client.cassandra.common.CassandraConstants;
import com.impetus.client.cassandra.thrift.ThriftClient;
import com.impetus.kundera.client.Client;

public class ZipLocationFeedWriter implements ItemWriter<ZipLocationBean> {

	private static final Log log = LogFactory.getLog(ZipLocationFeedWriter.class);
	private boolean fail = false;
	public ZipLocationFeedWriter(){
		System.out.println("");
	}
	public void setFail(boolean fail) {
		this.fail = fail;
	}
	static{
		EntityManagerFactory emf = null;
		EntityManager manager = null;

		System.setProperty("cassandra.join_ring", "false");
		emf = Persistence.createEntityManagerFactory("cassandra_pu");
		manager = emf.createEntityManager();
		 Map<String, Client> clientMap = (Map<String, Client>) manager.getDelegate();
	        ThriftClient pc = (ThriftClient)clientMap.get("cassandra_pu");
	        pc.setCqlVersion(CassandraConstants.CQL_VERSION_3_0);		
	}
	public void write(List<? extends ZipLocationBean> arg0) throws Exception {
		
		log.info(arg0);
		if (fail) {
			throw new RuntimeException("Planned failure");
		}
		for (ZipLocationBean bean : arg0) {
//			manager.persist(bean);
			System.out.println(""+bean.getZipcode() +"--"+bean.getCity());
//			manager.close();
		}
	}
}