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
package org.leadpony.jsct.base;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author leadpony
 */
public abstract class ConformanceTest {

    protected static final Logger log = Logger.getLogger(ConformanceTest.class.getName());

    private static final Path testSuiteRoot = Paths.get("..", "JSON-Schema-Test-Suite");
    private static final Path testsPath = testSuiteRoot.resolve("tests");
    private static final Path remotePath = testSuiteRoot.resolve("remotes");

    public static Stream<Fixture> fromSpec(String name) throws IOException {
        Path dir = testsPath.resolve(name);
        return Files.walk(dir)
                .filter(ConformanceTest::isTestFile)
                .flatMap(Fixture::load);
    }

    @ParameterizedTest
    @MethodSource("fixtures")
    public void test(Fixture fixture) {
        boolean result = validate(fixture.getSchema(), fixture.getData());
        assertThat(result).isEqualTo(fixture.isValid());
    }

    public static InputStream openRemoteResource(URI uri) {
        String pathPart = uri.getPath().substring(1);
        Path local = remotePath.resolve(pathPart).normalize();
        try {
            return Files.newInputStream(local);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    protected abstract boolean validate(String schemaJson, String dataJson);

    private static boolean isTestFile(Path path) {
        if (!Files.isRegularFile(path)) {
            return false;
        }
        return path.getFileName().toString().endsWith(".json");
    }
}
