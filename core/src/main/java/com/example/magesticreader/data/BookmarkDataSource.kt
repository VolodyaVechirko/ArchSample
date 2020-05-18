package com.example.magesticreader.data

import com.example.magesticreader.domain.Bookmark
import com.example.magesticreader.domain.Document

interface BookmarkDataSource {

    suspend fun add(document: Document, bookmark: Bookmark)

    suspend fun read(document: Document): List<Bookmark>

    suspend fun remove(document: Document, bookmark: Bookmark)
}
