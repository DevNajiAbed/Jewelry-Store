package com.naji.jewelrystore.jewelry.domain.model

import com.google.firebase.firestore.DocumentId

data class Product(
    @DocumentId
    var id: String? = null,
    val name: String,
    val imageUrl: String,
    val price: Float
)
