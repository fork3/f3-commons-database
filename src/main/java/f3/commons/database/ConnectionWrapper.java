/*
 * Copyright (c) 2010-2016 fork2
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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

/**
 * @author n3k0nation
 *
 */
@RequiredArgsConstructor
public class ConnectionWrapper implements Connection {
	private interface Close {
		void close() throws SQLException;
	}
	
	private final Consumer<ConnectionWrapper> closeCallback;
	
	@Delegate(excludes=Close.class)
	private final Connection connection;
	private int counter = 1;
	
	public void use() {
		counter++;
	}
	
	@Override
	public void close() throws SQLException {
		if(counter <= 0) {
			throw new SQLException("Connect already closed!");
		}
		
		if(--counter < 1) {
			closeCallback.accept(this);
			connection.close();
		}
	}
	
}
