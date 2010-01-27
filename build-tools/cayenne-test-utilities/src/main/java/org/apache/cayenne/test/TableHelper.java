/*****************************************************************
 *   Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 ****************************************************************/
package org.apache.cayenne.test;

import java.sql.SQLException;

/**
 * JDBC utilities class for setting up and analyzing the DB data sets for a single table.
 * TableHelper intentionally bypasses Cayenne stack.
 */
public class TableHelper {

    protected String tableName;
    protected DBHelper dbHelper;
    protected String[] columns;

    public TableHelper(DBHelper dbHelper, String tableName) {
        this.dbHelper = dbHelper;
        this.tableName = tableName;
    }

    public TableHelper(DBHelper dbHelper, String tableName, String... columns) {
        this(dbHelper, tableName);
        setColumns(columns);
    }

    public TableHelper deleteAll() throws SQLException {
        dbHelper.deleteAll(tableName);
        return this;
    }

    public TableHelper setColumns(String... columns) {
        this.columns = columns;
        return this;
    }

    public TableHelper insert(Object... values) throws SQLException {
        if (this.columns == null) {
            throw new IllegalStateException("Call 'setColumns' to prepare insert");
        }

        if (this.columns.length != values.length) {
            throw new IllegalArgumentException(
                    "Columns and values arrays are of different size");
        }

        dbHelper.insert(tableName, columns, values);
        return this;
    }

}