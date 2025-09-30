package com.github.yohannestz.kifiyabankapp.ui.base.state

data class TextEditorState<T>(
    var current: T,
    var valid: Boolean = false,
    var dirty: Boolean = false,
    var checks: MutableList<Pair<(T) -> Boolean, Int>> = mutableListOf(),
    var errors: MutableList<Int> = mutableListOf(),
    var edited: Boolean = false,
    var touched: Boolean = false,
) {
    private fun getValidatedState(state: TextEditorState<T> = this): TextEditorState<T> {
        val newState = state.copy(valid = true, dirty = false, errors = mutableListOf())

        for ((test, errorResId) in newState.checks) {
            if (!test(newState.current)) {
                newState.valid = false
                newState.errors.add(errorResId)
            }
        }

        newState.dirty = newState.edited && !newState.valid
        return newState
    }

    fun getTouchedState(): TextEditorState<T> = getValidatedState(copy(touched = true))
    fun getEditedState(): TextEditorState<T> = getValidatedState(copy(edited = true))
    fun getUpdatedState(value: T): TextEditorState<T> = getValidatedState(copy(current = value))

    fun addCheck(check: (T) -> Boolean, errorResId: Int): TextEditorState<T> {
        checks.add(check to errorResId)
        return this
    }
}