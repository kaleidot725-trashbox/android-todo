package kaleidot725.todo.main

interface MainNavigator {
    fun editTextDialog(title : String, current : String, apply : ((name : String) -> Unit))
}