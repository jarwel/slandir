package com.slandir.account.dao;

import com.google.common.base.Predicate;
import com.google.inject.Inject;
import com.proofpoint.json.JsonCodec;
import com.slandir.account.model.Account;
import me.prettyprint.cassandra.model.QuorumAllConsistencyLevelPolicy;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.ThriftCfDef;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;

import static com.google.common.collect.Iterables.find;
import static com.proofpoint.json.JsonCodec.jsonCodec;

public class CassandraAccountDao implements AccountDao {

    private static final String KEY_SPACE = "ACCOUNT";
    private static final String COLUMN_FAMILY = "ACCOUNT";
    private static final String COLUMN_NAME = "VALUE";
    
    private static final JsonCodec<Account> accountCodec = jsonCodec(Account.class);
    
    private final Keyspace keyspace;
    
    @Inject
    public CassandraAccountDao(Cluster cluster) {

        KeyspaceDefinition keyspaceDefinition = cluster.describeKeyspace(KEY_SPACE);
        if(keyspaceDefinition == null) {
            cluster.addKeyspace(new ThriftKsDef(KEY_SPACE));
        }

        ColumnFamilyDefinition columnFamilyDefinition = find(cluster.describeKeyspace(KEY_SPACE).getCfDefs(), named(COLUMN_FAMILY), null);
        if(columnFamilyDefinition == null) {
            cluster.addColumnFamily(new ThriftCfDef(KEY_SPACE, COLUMN_FAMILY));
        }
        
        keyspace = HFactory.createKeyspace(KEY_SPACE, cluster);
        keyspace.setConsistencyLevelPolicy(new QuorumAllConsistencyLevelPolicy());
    }
    
    @Override
    public void save(Account account) {
        Mutator mutator = HFactory.createMutator(keyspace, StringSerializer.get());
        mutator.addInsertion(
            account.getEmail(),
            COLUMN_FAMILY,
            HFactory.createColumn(COLUMN_NAME, accountCodec.toJson(account), StringSerializer.get(), StringSerializer.get())
        );
        mutator.execute();
    }

    @Override
    public Account fetch(String email) {
        ColumnQuery<String, String, String> columnQuery = HFactory.createColumnQuery(keyspace, StringSerializer.get(), StringSerializer.get(), StringSerializer.get());
        columnQuery.setKey(email);
        columnQuery.setColumnFamily(COLUMN_FAMILY);
        columnQuery.setName(COLUMN_NAME);
        
        HColumn<String, String> hColumn =  columnQuery.execute().get();
        if(hColumn != null && hColumn.getValue() != null) {
            return accountCodec.fromJson(hColumn.getValue());
        }
        return null;
    }
    
    private static Predicate<ColumnFamilyDefinition> named(final String name) {
        return new Predicate<ColumnFamilyDefinition>() {
            public boolean apply(ColumnFamilyDefinition input) {
                return input.getName().equals(name);
            }
        };
    }
}
