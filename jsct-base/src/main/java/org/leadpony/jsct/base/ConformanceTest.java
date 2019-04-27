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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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

    private static Server server;

    public static Stream<Fixture> fromSpec(String name) throws IOException {
        Path dir = testsPath.resolve(name);
        return Files.walk(dir)
                .filter(ConformanceTest::isTestFile)
                .flatMap(Fixture::load);
    }

    @BeforeAll
    public static void startServer() throws Exception {
        server = new Server(1234);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setResourceBase(remotePath.toString());
        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(new DefaultHandler());
        server.setHandler(handlers);

        server.start();
    }

    @AfterAll
    public static void stopServer() throws Exception {
        server.stop();
    }

    @ParameterizedTest
    @MethodSource("fixtures")
    public void test(Fixture fixture) {
        boolean result = validate(fixture.getSchema(), fixture.getData());
        assertThat(result).isEqualTo(fixture.isValid());
    }

    protected abstract boolean validate(String schemaJson, String dataJson);

    private static boolean isTestFile(Path path) {
        if (!Files.isRegularFile(path)) {
            return false;
        }
        return path.getFileName().toString().endsWith(".json");
    }
}
