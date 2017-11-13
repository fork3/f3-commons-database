/*
 * Copyright (c) 2010-2016 fork3
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES 
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE 
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR 
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package f3.commons.database;

import java.lang.reflect.Constructor;

import f3.commons.IBuilder;

/**
 * @author n3k0nation
 *
 */
public class ConnectionFactoryBuilder implements IBuilder<AbstractConnectionFactory>, Cloneable {
	public static ConnectionFactoryBuilder builder() {
		return new ConnectionFactoryBuilder();
	}
	
	private Class<? extends AbstractConnectionFactory> poolType;
	private int maxConnections;
	private String databaseDriver;
	private String databaseUrl;
	private String databaseLogin;
	private String databasePassword;
	private String catalog;
	
	public ConnectionFactoryBuilder() {
		
	}
	
	public ConnectionFactoryBuilder setPoolType(String poolType) {
		Class<?> clazz;
		try {
			clazz = Class.forName(poolType);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Pool type not found " + poolType, e);
		}
		this.poolType = clazz.asSubclass(AbstractConnectionFactory.class);
		return this;
	}
	
	public Class<? extends AbstractConnectionFactory> getPoolType() {
		return poolType;
	}

	public ConnectionFactoryBuilder setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
		return this;
	}
	
	public int getMaxConnections() {
		return maxConnections;
	}
	
	public ConnectionFactoryBuilder setDatabaseDriver(String databaseDriver) {
		this.databaseDriver = databaseDriver;
		return this;
	}
	
	public String getDatabaseDriver() {
		return databaseDriver;
	}
	
	public ConnectionFactoryBuilder setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
		return this;
	}
	
	public String getDatabaseUrl() {
		return databaseUrl;
	}
	
	public ConnectionFactoryBuilder setDatabaseLogin(String databaseLogin) {
		this.databaseLogin = databaseLogin;
		return this;
	}
	
	public String getDatabaseLogin() {
		return databaseLogin;
	}
	
	public ConnectionFactoryBuilder setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
		return this;
	}
	
	public String getDatabasePassword() {
		return databasePassword;
	}
	
	public ConnectionFactoryBuilder setCatalog(String catalog) {
		this.catalog = catalog;
		return this;
	}
	
	public String getCatalog() {
		return catalog;
	}
	
	@Override
	public AbstractConnectionFactory build() {
		AbstractConnectionFactory factory;
		try {
			Constructor<? extends AbstractConnectionFactory> ctor = poolType.getConstructor();
			ctor.setAccessible(true);
			factory = ctor.newInstance();
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException("Failed create pool", e);
		}
		factory.setMaxConnections(maxConnections);
		factory.setDatabaseDriver(databaseDriver);
		factory.setDatabaseUrl(databaseUrl);
		factory.setDatabaseLogin(databaseLogin);
		factory.setDatabasePassword(databasePassword);
		factory.setCatalog(catalog);
		factory.init();
		return factory;
	}
	
	@Override
	public ConnectionFactoryBuilder clone() {
		final ConnectionFactoryBuilder cfb = new ConnectionFactoryBuilder();
		cfb.databaseDriver = databaseDriver;
		cfb.databaseLogin = databaseLogin;
		cfb.databasePassword = databasePassword;
		cfb.databaseUrl = databaseUrl;
		cfb.maxConnections = maxConnections;
		cfb.poolType = poolType;
		return cfb;
	}
}
