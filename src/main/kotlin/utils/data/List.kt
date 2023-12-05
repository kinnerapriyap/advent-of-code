package utils.data

fun List<Int>.product(): Int = fold(1) {acc, i -> acc * i}