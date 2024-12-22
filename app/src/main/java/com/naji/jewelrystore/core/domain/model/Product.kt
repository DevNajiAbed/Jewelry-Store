package com.naji.jewelrystore.core.domain.model

import com.google.firebase.firestore.DocumentId

data class Product(
    @DocumentId
    var id: String? = null,
    val categoryId: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val price: Float = 0f
)
