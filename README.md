# math-expression-parser
[<img src="https://img.shields.io/travis/facaiy/math-expression-parser.svg"/>](https://travis-ci.org/facaiy/math-expression-parser)
[<img src="https://img.shields.io/maven-central/v/io.github.facaiy/math-expression-parser.svg">](http://search.maven.org/#search|ga|1|g:"io.github.facaiy"%20AND%20a:"math-expression-parser")

A scala library for parsing mathemitical expressions with support for parentheses and variables.

features:
+ math operators: `+`, `-`, `*`, `/`, `**`(power)
+ parentheses `( )` and comma `,`
+ all function of [`scala.math`](http://www.scala-lang.org/api/2.12.1/scala/math/index.html), except of `random`, `E` and `PI`
+ variable name: `$` with valid Java variable name


### Install

1. maven
   ```
   <dependency>
     <groupId>io.github.facaiy</groupId>
     <artifactId>math-expression-parser</artifactId>
     <version>0.0.2</version>
   </dependency>
   ```


### Usage

A simple example:

```scala
import io.github.facaiy.math.expression.MathExp

val str = "1.0 + sqrt(2 * $a1) + $a2 ** 2"
val ex = MathExp.parse(str)

val variables = Map("a1" -> 2, "a2" -> 1)
val output = ex.eval(variables)
// output = 4.0

val output1 = ex.eval(Map("a1" -> 8.0, "a2" -> 2))
// output1 = 9.0
```
