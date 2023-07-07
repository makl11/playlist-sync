fun generateRandomString(
    length: Int, charSet: List<Char> = (('0'..'9') + ('A'..'Z') + ('a'..'z'))
) = List(length) { charSet.random() }.joinToString("")