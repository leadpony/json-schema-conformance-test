/*
 * Copyright 2019 the original authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.leadpony.jsonschema.test.everit;

import java.math.BigInteger;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.everit.json.schema.loader.SchemaLoader.SchemaLoaderBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.leadpony.jsonschema.test.base.ConformanceTest;

/**
 * @author leadpony
 */
abstract class AbstractConformanceTest extends ConformanceTest {

    @Override
    protected boolean validate(String schemaJson, String dataJson) {
        Schema schema = readSchema(schemaJson);
        Object data = toJson(dataJson);
        try {
            schema.validate(data);
            return true;
        } catch (ValidationException e) {
            return false;
        }
    }

    private Schema readSchema(String json) {
        SchemaLoaderBuilder builder = SchemaLoader.builder();
        configure(builder);
        SchemaLoader loader = builder
                .schemaJson(toJson(json))
                .build();
        return loader.load().build();
    }

    private static Object toJson(String json) {
        switch (json) {
        case "true":
            return true;
        case "false":
            return false;
        case "null":
            return JSONObject.NULL;
        }
        Object value = null;
        switch (json.charAt(0)) {
        case '{':
            value = new JSONObject(json);
            break;
        case '[':
            value = new JSONArray(json);
            break;
        case '\"':
            value = json.substring(1, json.length() - 1);
            break;
        default:
            value = JSONObject.stringToValue(json);
            if (value instanceof String) {
                value = new BigInteger(json);
            }
            break;
        }
        return value;
    }

    protected abstract void configure(SchemaLoaderBuilder builder);
}
