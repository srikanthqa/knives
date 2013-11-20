import groovy.transform.ToString
import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.AnnotationCollector

@ToString(excludes=["a"])
@EqualsAndHashCode
@Immutable
@AnnotationCollector
@interface Alias {

}

@Alias
class Foo {
	Integer a, b
}
assert Foo.class.annotations.size()==3
assert new Foo(1,2).toString() == "Foo(2)"