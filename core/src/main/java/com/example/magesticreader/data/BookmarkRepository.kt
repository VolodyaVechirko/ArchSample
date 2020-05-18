package com.example.magesticreader.data

import com.example.magesticreader.domain.Bookmark
import com.example.magesticreader.domain.Document

class BookmarkRepository(private val dataSource: BookmarkDataSource) {

    suspend fun addBookmark(
        document: Document,
        bookmark: Bookmark
    ) = dataSource.add(document, bookmark)

    suspend fun removeBookmark(
        document: Document,
        bookmark: Bookmark
    ) = dataSource.remove(document, bookmark)


    suspend fun getBookmarks(
        document: Document
    ) = dataSource.read(document)


}