package com.slandir.account;

import com.google.common.net.InetAddresses;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Scopes;

import com.proofpoint.cassandra.CassandraServerInfo;
import com.proofpoint.discovery.client.DiscoveryBinder;
import com.proofpoint.node.NodeInfo;
import com.slandir.account.dao.AccountDao;
import com.slandir.account.dao.CassandraAccountDao;
import com.slandir.account.dao.InMemoryAccountDao;
import com.slandir.account.resource.AccountResource;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.clock.MillisecondsClockResolution;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.factory.HFactory;

public class MainModule implements Module {

    private static final String SERVICE_NAME = "account";

    @Override
    public void configure(Binder binder) {
        binder.requireExplicitBindings();
        binder.disableCircularProxies();

        binder.bind(AccountDao.class).to(CassandraAccountDao.class).in(Scopes.SINGLETON);

        binder.bind(AccountResource.class).in(Scopes.SINGLETON);

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