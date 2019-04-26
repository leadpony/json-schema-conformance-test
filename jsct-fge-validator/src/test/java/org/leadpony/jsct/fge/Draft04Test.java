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
package org.leadpony.jsct.fge;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.leadpony.jsct.base.ConformanceTest;
import org.leadpony.jsct.base.Fixture;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.load.configuration.LoadingConfiguration;
import com.github.fge.jsonschema.core.load.download.URIDownloader;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;

/**
 * @author leadpony
 */
public class Draft04Test extends ConformanceTest {

    private static ObjectMapper mapper;
    private static JsonValidator validator;

    @BeforeAll
    public static void setUpOnce() {
        mapper = new ObjectMapper();

        validator = JsonSchemaFactory.newBuilder()
            .setValidationConfiguration(getValidationConfig())
            .setLoadingConfiguration(getLoadingConfig())
            .freeze()
            .getValidator();
    }

    public static Stream<Fixture> fixtures() throws IOException {
        return fromSpec("draft4");
    }

    @Override
    protected boolean validate(String schemaJson, String dataJson) {
        try {
            return doValidate(schemaJson, dataJson);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private boolean doValidate(String schemaJson, String dataJson) throws IOException, ProcessingException {
        JsonNode schemaNode = mapper.readTree(schemaJson);
        JsonNode dataNode = mapper.readTree(dataJson);
        ProcessingReport report = validator.validate(schemaNode, dataNode);
        return report.isSuccess();
    }

    private static ValidationConfiguration getValidationConfig() {
        return ValidationConfiguration.newBuilder()
                .setDefaultVersion(SchemaVersion.DRAFTV4)
                .freeze();
    }

    private static LoadingConfiguration getLoadingConfig() {
        return LoadingConfiguration.newBuilder()
                .addScheme("http", new URIDownloader() {
                    @Override
                    public InputStream fetch(URI source) throws IOException {
                        return openRemoteResource(source);
                    }
                })
                .freeze();
    }
}
