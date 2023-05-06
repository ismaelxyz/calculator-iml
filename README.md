# CalculatorIML

A basic example on the use of the official Java graphical library (awt and swing) together with a handmade parser.

## Architecture
This project was done using the MVC (Model View Controller) design pattern.

## Build and Run

To build or run it you only need Java (I recommend the Java Development Kit A.K.A JDK version 19.0.1), for example:

```bash
# Just build
mvn package
# Just run
java -cp target/CalculatorIML-1.0.0.jar com.uptbal.calculator.view.Window
```

additionally it has the `make.rb` helper script written in the Ruby language, which allows you to save time and execute some commands (build, build a jar, run, etc) in a comfortable way.