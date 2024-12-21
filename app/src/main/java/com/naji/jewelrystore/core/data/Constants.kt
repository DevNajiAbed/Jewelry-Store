package com.naji.jewelrystore.core.data

object Constants {
    sealed interface Collection {
        class Users : Collection {
            companion object {
                const val COLLECTION_NAME = "users"
                const val FIELD_EMAIL = "email"
                const val FIELD_USERNAME = "username"
                const val FIELD_PASSWORD = "password"
                const val FIELD_SHOPPING_CART_LIST = "shoppingCartList"
            }
        }
        class Categories : Collection {
            companion object {
                const val COLLECTION_NAME = "categories"
                const val FIELD_NAME = "name"
                const val FIELD_IMAGE_URL = "imageUrl"
            }
        }
        class Products : Collection {
            companion object {
                const val COLLECTION_NAME = "products"
                const val FIELD_NAME = "name"
                const val FIELD_IMAGE_URL = "imageUrl"
                const val FIELD_PRICE = "price"
            }
        }
    }
}