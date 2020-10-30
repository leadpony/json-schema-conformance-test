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
package org.leadpony.jsonschema.test.snow;

import com.google.gson.JsonElement;
import com.qindesign.json.schema.Annotation;
import com.qindesign.json.schema.Error;
import com.qindesign.json.schema.JSON;
import com.qindesign.json.schema.JSONPath;
import com.qindesign.json.schema.MalformedSchemaException;
import com.qindesign.json.schema.Option;
import com.qindesign.json.schema.Options;
import com.qindesign.json.schema.Specification;
import com.qindesign.json.schema.Validator;
import org.leadpony.jsonschema.test.base.ConformanceTest;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Shawn Silverman (shawn@pobox.com)
 */
abstract class AbstractConformanceTest extends ConformanceTest {
    private static Map<com.qindesign.net.URI, JsonElement> knownIDs;
    private static Map<com.qindesign.net.URI, URL> knownURLs;
    private static Options options;

    private static final AtomicInteger testNum = new AtomicInteger(0);

    static void setUp(Specification spec) throws IOException {
        knownIDs = Collections.emptyMap();
        knownURLs = new HashMap<>();
        knownURLs.put(com.qindesign.net.URI.parseUnchecked("http://localhost:1234"),
                      new URL("http://localhost:1234/"));

        options = new Options();
        options.set(Option.FORMAT, true);
        options.set(Option.CONTENT, true);
        options.set(Option.DEFAULT_SPECIFICATION, spec);
    }

    @Override
    protected boolean validate(String schemaJson, String dataJson) {
        JsonElement testSchema = JSON.parse(new StringReader(schemaJson));
        JsonElement testData = JSON.parse(new StringReader(dataJson));
        try {
            // Use a URN for the base URI for this test because we don't have
            // access to the file URI here
            Validator validator = new Validator(
                testSchema,
                com.qindesign.net.URI.parseUnchecked("urn:jsct-test:" + testNum.incrementAndGet()),
                knownIDs, knownURLs, options);
            Map<JSONPath, Map<String, Map<JSONPath, Annotation<?>>>> annotations = new HashMap<>();
            Map<JSONPath, Map<JSONPath, Error<?>>> errors = new HashMap<>();
            return validator.validate(testData, annotations, errors);
        } catch (MalformedSchemaException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
