package com.slandir.account;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

import com.proofpoint.discovery.client.DiscoveryBinder;
import com.slandir.account.dao.AccountDao;
import com.slandir.account.resource.AccountResource;

public class MainModule implements Module {

    private static final String SERVICE_NAME = "account";

    @Override
    public void configure(Binder binder) {
        binder.requireExplicitBindings();
        binder.disableCircularProxies();

        binder.bind(AccountDao.class).in(Scopes.SINGLETON);

        binder.bind(AccountResource.class).in(Scopes.SINGLETON);

        //eventBinder(binder).bindEventClient(DescriptorEvent.class);

        DiscoveryBinder.discoveryBinder(binder).bindHttpAnnouncement(SERVICE_NAME);
    }
}