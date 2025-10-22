package com.example.mobdev_3s_5l

data class Photo (
    var id: String?,
    var owner: String?,
    var secret: String?,
    var server: String?,
    var farm: Int,
    var title: String?,
    var isPublic: Int,
    var isFriend: Int,
    var isFamily: Int,
)

data class PhotoPage (
    var page: Int,
    var pages: Int,
    var perpage: Int,
    var total: Int,
    var photo: List<Photo>
)

data class Wrapper (
    var photos: PhotoPage
)