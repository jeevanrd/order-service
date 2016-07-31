package mongo;


import config.ServicesConfiguration;

public class Connection {
    private String host;
    private int port;
    private String dbName;

    public Connection(String dbName){
        this.dbName = dbName;
    }
    
    public Connection(ServicesConfiguration configuration) {
        this.dbName = configuration.mongodb;
        this.host = configuration.mongohost;
        this.port = configuration.mongoport;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
    
    public String getDbName() {
       return dbName;
    }

}