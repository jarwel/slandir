package com.slandir.identity;

import com.google.common.net.InetAddresses;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Scopes;

import com.proofpoint.cassandra.CassandraServerInfo;
import com.proofpoint.discovery.client.DiscoveryBinder;
import com.proofpoint.node.NodeInfo;
import com.slandir.identity.dao.CassandraPersonDao;
import com.slandir.identity.dao.InMemoryPersonDao;
import com.slandir.identity.dao.PersonDao;
import com.slandir.identity.resource.PersonResource;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.clock.MillisecondsClockResolution;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.factory.HFactory;

public class MainModule implements Module {
    
    private static final String SERVICE_NAME = "identity";
    
    @Override
    public void configure(Binder binder) {
        binder.requireExplicitBindings();
        binder.disableCircularProxies();
        
        binder.bind(PersonDao.class).to(CassandraPersonDao.class).in(Scopes.SINGLETON);
        
        binder.bind(PersonResource.class).in(Scopes.SINGLETON);
        
        //eventBinder(binder).bindEventClient(DescriptorEvent.class);

        DiscoveryBinder.discoveryBinder(binder).bindHttpAnnouncement(SERVICE_NAME);
    }

    @Provides
    public Cluster getCluster(CassandraServerInfo cassandraServerInfo, NodeInfo nodeInfo) {
        CassandraHostConfigurator configurator = new CassandraHostConfigurator(String.format("%s:%s", InetAddresses.toUriString(nodeInfo.getInternalIp()), cassandraServerInfo.getRpcPort()));
        configurator.setClockResolution(new MillisecondsClockResolution());
        return HFactory.getOrCreateCluster(SERVICE_NAME, configurator);
    }
}
