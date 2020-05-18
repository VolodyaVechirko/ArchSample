package com.example.magesticreader.interactors

import com.example.magesticreader.data.BookmarkRepository
import com.example.magesticreader.domain.Bookmark
import com.example.magesticreader.domain.Document

class RemoveBookmark(private val repository: BookmarkRepository) {
    suspend operator fun invoke(document: Document, bookmark: Bookmark) =
        repository.removeBookmark(document, bookmark)
}