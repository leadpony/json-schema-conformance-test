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
package org.leadpony.jsonschema.test.networknt;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.leadpony.jsonschema.test.base.ConformanceTest;
import org.leadpony.jsonschema.test.base.Fixture;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.ValidationMessage;
import com.networknt.schema.SpecVersion;

/**
 * @author leadpony
 */
public class Draft06Test extends ConformanceTest {

    private static ObjectMapper mapper;
    private static JsonSchemaFactory factory;

    @BeforeAll
    public static void setUpOnce() {
        mapper = new ObjectMapper();
        factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V6);
    }

    public static Stream<Fixture> fixtures() throws IOException {
        return fromSpec("draft6");
    }

    @Override
    protected boolean validate(String schemaJson, String dataJson) {
        try {
            return doValidate(schemaJson, dataJson);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private boolean doValidate(String schemaJson, String dataJson) throws IOException {
        JsonNode schemaNode = mapper.readTree(schemaJson);
        JsonNode dataNode = mapper.readTree(dataJson);
        JsonSchema schema = factory.getSchema(schemaNode);
        Set<ValidationMessage> errors = schema.validate(dataNode);
        return errors.isEmpty();
    }
}
