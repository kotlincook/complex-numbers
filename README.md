# complex-numbers
A small library for the programming language Kotlin to work comfortably with 
[complex numbers](https://en.wikipedia.org/wiki/Complex_number). 

It includes the four basic arithmetic operations +, -, *, / and
typical functions like exp, sin, cos, log, sqrt, etc.

A nice feature of Kotlin is that the operators +, -, *, / can be overloaded so that
they work with all other numeric types in all combinations.  

## Web site
On http://kotlinmath.org you can find further information and some examples 
of how to use this library.

## Build
Maven (tested with version 3.6.3)

# Examples 
You can define the complex number 4+3i in four ways:
* ```val z1 = 4 + 3.I```
* ```val z2 = 4 + 3 * I```
* ```val z3 = complex(3, 4)```
* ```val z4 = "4+3i".toComplex()```

You can use the operators +, -, *, and / with any other numeric type:
* ```val w1 = 1.5F / z1 * 2 - 4.32 + 10L + (3.toBigDecimal() * I)```

There are standard function like exp, sin, cos, log and sqrt:
* ```val w1 = I * z1 + exp(I * PI/2) + sqrt(-9)```

.I creates a pure imaginary number; .R creates a complex number without imaginary part:
* ```val two = 2.R // number two as complex number```
* ```val fourI = 4.I // four times i```

There are constants INF, NaN (also ZERO and ONE) as infinity and "Not a Number" which
can also be used for calculations:
* ```-1 / ZERO == INF```
* ```ONE / 0 == INF``` 
* ```3 / INF == ZERO```
* ```INF + INF == NaN```
* ```ln(0) == NaN```
* ```exp(INF) == NaN```
* ```sin(NaN) == NaN```

Note that there is no -INF in complex numbers, so that ```-INF == INF```. 

## Special feature
The feature of this interface is, that it provides the functions mentioned above as default
implementations. But these functions could easily be replaced (overwritten) by a class which
would pass the computation (for example) to the well tested Apache common package due to
performance or confidence reasons. 