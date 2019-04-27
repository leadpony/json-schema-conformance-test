# JSON Schema Conformance Test

This project provides conformance tests to the [JSON Schema Specification]. All test cases are from official [JSON Schema Test Suite], including both mandatory and optional ones.

## Test Results

The following table shows the number of failures occurred while testing popular JSON validators written in JVM languages as of 2019-04-26.

| Software | Version | Draft-07 | Draft-06 | Draft-04 |
| --- | --- | ---: | ---: | ---: |
| [everit-org/json-schema] | 1.11.1 | 21 | 8 | 9 |
| [java-json-tools/json-schema-validator] | 2.2.10 | n/a | n/a | 11 |
| [Justify] | 0.16.0 | 0 | 0 | 0 |
| [networknt/json-schema-validator] | 1.0.6 | n/a | n/a | 24 |

Note that _n/a_ in the table means that the software does not support the version of the specification.

## How to Run

The following tools are required to build this software.
* JDK 8 or higher
* Maven 3.6.0

The command below will build and run all tests.
```
$ mvn clean test
```

## Copyright Notice
Copyright &copy; 2019 leadpony. This software is licensed under [Apache License, Versions 2.0][Apache 2.0 License].

[Apache 2.0 License]: https://www.apache.org/licenses/LICENSE-2.0
[JSON Schema Specification]: https://json-schema.org/
[JSON Schema Test Suite]: https://github.com/json-schema-org/JSON-Schema-Test-Suite

[everit-org/json-schema]: https://github.com/everit-org/json-schema
[java-json-tools/json-schema-validator]: https://github.com/java-json-tools/json-schema-validator
[Justify]: https://github.com/leadpony/justify
[networknt/json-schema-validator]: https://github.com/networknt/json-schema-validator
