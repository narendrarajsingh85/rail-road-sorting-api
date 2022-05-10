package practice.imagine

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("practice.imagine")
		.start()
}

