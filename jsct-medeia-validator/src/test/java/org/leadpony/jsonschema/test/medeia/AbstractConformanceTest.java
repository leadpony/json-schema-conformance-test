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
package org.leadpony.jsonschema.test.medeia;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.leadpony.jsonschema.test.base.ConformanceTest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worldturner.medeia.api.JsonSchemaVersion;
import com.worldturner.medeia.api.StringSchemaSource;
import com.worldturner.medeia.api.ValidationFailedException;
import com.worldturner.medeia.api.jackson.MedeiaJacksonApi;
import com.worldturner.medeia.schema.validation.SchemaValidator;

/**
 * @author leadpony
 */
abstract class AbstractConformanceTest extends ConformanceTest {

    private static ObjectMapper mapper;
    private static JsonFactory factory;

    private static MedeiaJacksonApi api;
    private static JsonSchemaVersion schemaVersion;

    public static void setUp(JsonSchemaVersion version) {
        mapper = new ObjectMapper();
        factory = mapper.getFactory();
        api = new MedeiaJacksonApi();
        schemaVersion = version;
    }

    @Override
    protected boolean validate(String schemaJson, String dataJson) {
        SchemaValidator validator = loadSchema(schemaJson);
        try (JsonParser originalParser = factory.createParser(dataJson);
                JsonParser parser = api.decorateJsonParser(validator, originalParser)) {
            mapper.readTree(parser);
            return true;
        } catch (ValidationFailedException e) {
            return false;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static SchemaValidator loadSchema(String json) {
        return api.loadSchema(
                new StringSchemaSource(json, schemaVersion));
    }
}
