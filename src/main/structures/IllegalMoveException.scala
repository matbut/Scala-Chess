package structures

final case class IllegalMoveException(private val message: String = "",
                                 private val cause: Throwable = None.orNull)
  extends Exception(message, cause)