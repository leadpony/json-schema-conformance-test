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

import com.qindesign.json.schema.Specification;
import org.junit.jupiter.api.BeforeAll;
import org.leadpony.jsonschema.test.base.Fixture;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * @author Shawn Silverman (shawn@pobox.com)
 */
public class Draft2019_09Test extends AbstractConformanceTest {
    public static Stream<Fixture> fixtures() throws IOException {
        return fromSpec("draft2019-09");
    }

    @BeforeAll
    public static void setUpOnce() throws IOException {
        setUp(Specification.DRAFT_2019_09);
    }
}
