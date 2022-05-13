package practice.imagine.exception

class DestinationNotFoundException(message: String, cause: Throwable? = null): RuntimeException(message, cause)
class CarNotFoundException(message: String,cause: Throwable?=null): RuntimeException(message,cause)