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

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import f3.commons.collection.ConcurrentMultiValueSet;
import f3.commons.collection.MultiValueSet;

/**
 * @author n3k0nation
 *
 */
public class ResultSetConverter {
	private ResultSetConverter() {
		throw new RuntimeException();
	}
	
	public static void copyToMap(ResultSet rset, Map<String, Object> map) throws SQLException {
		final ResultSetMetaData meta = rset.getMetaData();
		for(int i = 1; i <= meta.getColumnCount(); i++) {
			String name = meta.getColumnName(i);
			Object value = rset.getObject(i);
			
			map.put(name, value);
		}
	}
	
	public static MultiValueSet<String> toMultiValueSet(ResultSet rset) throws SQLException {
		final MultiValueSet<String> collection = new MultiValueSet<>();
		copyToMap(rset, collection);
		return collection;
	}
	
	public static ConcurrentMultiValueSet<String> toConcurrentMultiValueSet(ResultSet rset) throws SQLException {
		ConcurrentMultiValueSet<String> collection = new ConcurrentMultiValueSet<>();
		copyToMap(rset, collection);
		return collection;
	}
}
