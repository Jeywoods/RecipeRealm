package com.jeywoods.reciperealm.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    //user.value содержит объект FirebaseUser с полями:
    //uid (уникальный ID)
    //displayName (имя пользователя)
    //email (почта)
    val user = mutableStateOf(auth.currentUser)

    fun logout(context: android.content.Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        GoogleSignIn.getClient(context, gso).signOut()
        auth.signOut()
        user.value = null
    }
}