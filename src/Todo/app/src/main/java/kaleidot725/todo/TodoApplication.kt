package kaleidot725.todo

import android.app.Application
import kaleidot725.todo.model.SingletonModels

class TodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SingletonModels.init(this)
    }
}