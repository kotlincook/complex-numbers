# complex-numbers
Math interface for complex numbers including the four basic arithmetic operations +, -, *, / and
typical functions like exp, sin, cos, log, sqrt, etc.

## Special feature
The feature of this interface is, that it provides the functions mentioned above as default
implementations. But these functions could easily be replaced (overwritten) by a class which
would pass the computation (for example) to the well tested Apache common package due to
performance or confidence reasons. 

## Web site with further information

http://kotlinmath.org

# Examples 
You can define the complex number 4+3i in four ways:
* ```val z1 = 4 + 3.I```
* ```val z2 = 4 + 3 * I```
* ```val z3 = complexOf(3, 4)```
* ```val z4 = "4+3i".toComplex()```

You can use the operators +, -, *, and / with any other numeric type:
* ```val w1 = 1.5F / z1 * 2 - 4.32 + 10L + (3.toBigDecimal() * I)```

You can use the constants ZERO, ONE, and INF for 0, 1 and infinity as compley numbers
* ```val thisIsTrue = -1 / ZERO == INF & ONE / 0 == INF & 3 / INF == ZERO```

.I creates a pure imaginary number; .R creates a complex number without imaginary part:
* ```val two = 2.R // number two as complex number```
* ```val fourI = 4.I // four times i```

There are standard function like exp, sin, cos, log and sqrt:
* ```val w1 = I * z1 + exp(I * PI/2) + sqrt(-9)```


