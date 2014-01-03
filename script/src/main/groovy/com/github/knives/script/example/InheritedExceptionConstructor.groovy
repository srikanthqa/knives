import groovy.transform.InheritConstructors

@InheritConstructors
class SomeNewException extends Exception { }

println new SomeNewException('some message')
println new SomeNewException('some message', new Exception())
println new SomeNewException(new Exception())
println new SomeNewException()