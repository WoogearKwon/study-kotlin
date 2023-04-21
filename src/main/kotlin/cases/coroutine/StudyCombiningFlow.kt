package cases.coroutine

import cases.StudyCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

class StudyCombiningFlow : StudyCase {
    override fun run() = runBlocking {
//        merge()
//        zip()
        combine()
    }
}

/**
 * merge(): 가장 단순한 방식 - 두 개의 플로우를 하나로 병합
 * - 아무런 수정 없음
 **/
suspend fun merge() {
    val ints: Flow<Int> = flowOf(1, 3, 5)
        .onEach { delay(500) }
    val doubles: Flow<Int> = flowOf(2, 4, 6)
        .onEach { delay(200) }
    val together: Flow<Number> = merge(ints, doubles)

    together.collect { println(it) }
    // result: 2 4 1 6 3 5
}

/**
 * zip(): 두 개의 플로우를 한 쌍으로 만듬
 * - 요소의 개수가 다를 경우엔 짝이 맞는 요소들만 사용됨
 * - 짝이 없는 아이템이 나타나면 플로우를 닫음
 * */
suspend fun zip() {
    val ints: Flow<Int> = flowOf(1, 3, 5, 7)
        .onEach { delay(500) }
    val doubles: Flow<Int> = flowOf(2, 4, 6)
        .onEach { delay(200) }

    ints.zip(doubles) {
            f1, f2 -> f1 + f2
        println("f1($f1) + f2($f2) = ${f1 + f2}")
    }.collect()
    // result: 3 7 11
}

/**
 * combine()
 * - zip과 비슷하지만 느린 플로우를 기다림
 * - 짝이 없어도 이전에 사용했던 아이템을 사용해서 값을 병합함
 * - 기다린 후에 두 플로우를 쌍으로 만들어 생산
 * - 병합 할 때 사용되는 새 아이템은 이전 것를 대체함
 * - 양쪽의 플로우가 닫힐 때까지 병합을 실행함
 * */
suspend fun combine() {
    val ints: Flow<Int> = flowOf(1, 3, 5, 7)
        .onEach { delay(500) }
    val doubles: Flow<Int> = flowOf(2, 4, 6)
        .onEach { delay(300) }

    ints.combine(doubles) { f1, f2 ->
        f1 + f2
        println("f1($f1) + f2($f2) = ${f1 + f2}")
    }.collect()
    // result: 3 5 7 9 11 13
}