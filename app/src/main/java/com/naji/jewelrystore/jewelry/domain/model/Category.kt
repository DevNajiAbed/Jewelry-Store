package com.naji.jewelrystore.jewelry.domain.model

import com.google.firebase.firestore.DocumentId

data class Category(
    @DocumentId
    val id: String? = null,
    val name: String,
    val imgUrl: String
)