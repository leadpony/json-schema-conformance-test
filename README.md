# JSON Schema Conformance Test

[![Build Status](https://travis-ci.org/leadpony/json-schema-conformance-test.svg?branch=master)](https://travis-ci.org/leadpony/json-schema-conformance-test)

This project provides conformance tests to the [JSON Schema Specification]. All test cases are from official [JSON Schema Test Suite], including both mandatory and optional ones.

## Test Results

The following table shows the number of failures occurred while testing popular JSON validators written in JVM languages as of 2020-11-03.

| Software | Version | Draft 2019-09 | Draft-07 | Draft-06 | Draft-04 |
| --- | --- | ---: | ---: | ---: | ---: |
| [everit-org/json-schema] | 1.12.1 | n/a | 75 | 35 | 31 |
| [java-json-tools/json-schema-validator] | 2.2.14 | n/a | n/a | n/a | 25 |
| [Justify] | 3.1.0 | n/a | 0 | 0 | 0 |
| [networknt/json-schema-validator] | 1.0.44 | n/a | 92 | 47 | 31 |
| [ssilverman/snowy-json] | 0.15.0 | 0 | 0 | 0 | n/a |

Note that _n/a_ in the table means that the software does not support the version of the specification.

## How to Run

The following tools are required to build and run this software.
* JDK 11 or higher
* Maven 3.6.0

The command below will build and run all tests.
```bash
$ git clone --recursive https://github.com/leadpony/json-schema-conformance-test.git
$ cd json-schema-conformance-test/
$ mvn clean test
```

## Copyright Notice
Copyright &copy; 2019-2020 leadpony. This software is licensed under [Apache License, Versions 2.0][Apache 2.0 License].

[Apache 2.0 License]: https://www.apache.org/licenses/LICENSE-2.0
[JSON Schema Specification]: https://json-schema.org/
[JSON Schema Test Suite]: https://github.com/json-schema-org/JSON-Schema-Test-Suite

[everit-org/json-schema]: https://github.com/everit-org/json-schema
[java-json-tools/json-schema-validator]: https://github.com/java-json-tools/json-schema-validator
[Justify]: https://github.com/leadpony/justify
[networknt/json-schema-validator]: https://github.com/networknt/json-schema-validator
[ssilverman/snowy-json]: https://github.com/ssilverman/snowy-json
