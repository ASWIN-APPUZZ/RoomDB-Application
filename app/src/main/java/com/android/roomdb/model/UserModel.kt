package com.android.roomdb.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserModel (
    val userId: Int,
    val userName: String,
    val userEmail: String,
    val userAge: Int
) : Parcelable
