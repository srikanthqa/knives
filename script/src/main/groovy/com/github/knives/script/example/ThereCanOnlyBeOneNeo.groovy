@Singleton 
class Neo {
	final def name = "Neo" 
}

assert Neo.getInstance() instanceof Neo
assert Neo.instance instanceof Neo

new Neo()