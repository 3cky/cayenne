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
package org.apache.cayenne.project2.validate;

import org.apache.cayenne.dba.TypesMapping;
import org.apache.cayenne.map.DbAttribute;
import org.apache.cayenne.project.validator.MappingNamesHelper;
import org.apache.cayenne.util.Util;

class DbAttributeValidator {

    void validate(Object object, ConfigurationValidator validator) {
        DbAttribute attribute = (DbAttribute) object;

        // Must have name
        if (Util.isEmptyString(attribute.getName())) {
            validator.registerError("Unnamed DbAttribute.", object);
        }
        else {
            MappingNamesHelper helper = MappingNamesHelper.getInstance();
            String invalidChars = helper.invalidCharsInDbPathComponent(attribute
                    .getName());

            if (invalidChars != null) {
                validator.registerWarning(
                        "DbAttribute name contains invalid characters: " + invalidChars,
                        object);
            }
        }

        // all attributes must have type
        if (attribute.getType() == TypesMapping.NOT_DEFINED) {
            validator.registerWarning("DbAttribute has no type.", object);
        }

        // VARCHAR and CHAR attributes must have max length
        else if (attribute.getMaxLength() < 0
                && (attribute.getType() == java.sql.Types.VARCHAR || attribute.getType() == java.sql.Types.CHAR)) {

            validator.registerWarning(
                    "Character DbAttribute doesn't have max length.",
                    object);
        }
    }
}