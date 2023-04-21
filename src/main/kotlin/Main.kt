import cases.StudyCase
import cases.coroutine.StudyCombiningFlow

fun main() {
    val cases = HashMap<Int, StudyCase>()
    cases[0] = StudyCombiningFlow()

    cases[cases.size - 1]?.run()
}

