package com.naji.jewelrystore.core.domain.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId
    var id: String? = null,
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val shoppingCartList: List<String> = emptyList()
)