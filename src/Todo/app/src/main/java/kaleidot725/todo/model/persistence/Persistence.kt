package kaleidot725.todo.model.persistence

interface Persistence<T> {
    fun save(list: List<T>)
    fun load() : List<T>
}