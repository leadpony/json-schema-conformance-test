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
package org.leadpony.jsct.justify;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonReader;

import org.leadpony.jsct.base.ConformanceTest;
import org.leadpony.justify.api.JsonSchema;
import org.leadpony.justify.api.JsonSchemaReader;
import org.leadpony.justify.api.JsonSchemaReaderFactory;
import org.leadpony.justify.api.JsonValidationService;
import org.leadpony.justify.api.Problem;
import org.leadpony.justify.api.ProblemHandler;
import org.leadpony.justify.api.SpecVersion;

/**
 * @author leadpony
 */
abstract class AbstractConformanceTest extends ConformanceTest {

    private static JsonValidationService service;
    private static JsonSchemaReaderFactory schemaReaderFactory;

    static void setUp(SpecVersion version) {
        service = JsonValidationService.newInstance();
        schemaReaderFactory = service.createSchemaReaderFactoryBuilder()
                .withSchemaResolver(AbstractConformanceTest::resolveSchema)
                .withDefaultSpecVersion(version)
                .build();
    }

    @Override
    protected boolean validate(String schemaJson, String dataJson) {
        JsonSchema schema = readSchema(schemaJson);
        List<Problem> problems = new ArrayList<>();
        ProblemHandler handler = problems::addAll;
        try (JsonReader reader = service.createReader(new StringReader(dataJson), schema, handler)) {
            reader.readValue();
        }
        return problems.isEmpty();
    }

    private JsonSchema readSchema(String json) {
        try (JsonSchemaReader reader = schemaReaderFactory.createSchemaReader(new StringReader(json))) {
            return reader.read();
        }
    }

    private static JsonSchema resolveSchema(URI id) {
        try (InputStream in = id.toURL().openStream();
             JsonSchemaReader reader = schemaReaderFactory.createSchemaReader(in)) {
            return reader.read();
        } catch (MalformedURLException e) {
            log.severe(e.getMessage());
            return null;
        } catch (IOException e) {
            log.severe(e.getMessage());
            return null;
        }
    }
}
