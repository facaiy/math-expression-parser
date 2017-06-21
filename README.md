# math-expression-parser

A scala library for parsing mathemitical expressions with support for parentheses and variables.

features:
+ math operators: `+`, `-`, `*`, `/`, `**`(power)
+ parentheses `( )` and comma `,`
+ function: `sqrt`, `log`, `log10`, `log1p`, `abs`
+ variable name: `$` with valid Java variable name


### Install

```bash
mvn clean install
```


### Usage

A simple example:

```scala
val str = "1.0 + sqrt(2 * $a1) + $a2 ** 2"
val ex = MathExp.parse(str)

val variables = Map("a1" -> 2, "a2" -> 1)
val output = ex.eval(variables)
// output = 4.0

val output1 = ex.eval(Map("a1" -> 8.0, "a2" -> 2))
// output1 = 9.0
```
